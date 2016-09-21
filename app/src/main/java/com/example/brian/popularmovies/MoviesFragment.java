package com.example.brian.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by brian on 5/17/16.
 * Description:
 *  this contains the main content for the primary activity
 */
public class MoviesFragment extends Fragment{
    static final String LOG_TAG = "***MoviesFragment***";
    String [] imagePaths;
    ImageAdapter mImageAdapter;
    Map<Integer, Map<String, Object>> allData;

    String baseImageUrl = "http://image.tmdb.org/t/p/w185//";

    final String BASE_URL = "http://api.themoviedb.org/3/movie/";
    final String API_KEY_PARAM = "api_key";
    final String API_KEY = "****API KEY GOES HERE*****";



    final String MOVIE_ARRAY_TAG = "results";
    final String TITLE_TAG = "original_title";
    final String THUMBNAIL_TAG = "poster_path";
    final String OVERVIEW_TAG = "overview";
    final String RATING_TAG = "vote_average";
    final String RELEASE_DATE_TAG = "release_date";

    final String POPULAR = "popular";
    final String TOP_RATED = "top_rated";

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container
            , Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);
        GridView gridView = (GridView) rootView.findViewById(R.id.gridview);
        mImageAdapter = new ImageAdapter(getContext(), imagePaths);
        gridView.setAdapter(mImageAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                HashMap<Integer, Map<String, Object>> map = (HashMap) allData.get(position);
                intent.putExtra("map", map);
                startActivity(intent);
            }
        });


        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        getMovieData();
    }

    public void getMovieData(){
        MovieApiDataRequest movieApiDataRequest = new MovieApiDataRequest();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sort = sharedPreferences.getString(getString(R.string.pref_sort_key)
                ,   getString(R.string.pref_sort_default));
        //Log.e(LOG_TAG, sort);
        if(sort.equals(getString(R.string.pref_sort_highest_rated))) {
            movieApiDataRequest.execute(TOP_RATED);
        } else {
            movieApiDataRequest.execute(POPULAR);
        }
    }

    public String [] getImagesPathsFromResultMap(Map<Integer, Map<String, Object>> resultMap){
        if(resultMap != null){
            imagePaths = new String[resultMap.size()];

            for(int i = 0; i < resultMap.size(); i++){
                //get the Map at position i, then get the title from the single movie map
                String movieSpecificUrl =  (String) (resultMap.get(i)).get(THUMBNAIL_TAG);
                if(movieSpecificUrl != null){
                    imagePaths[i] = (baseImageUrl + movieSpecificUrl);
                }

            }
        } else {
            Log.e(LOG_TAG, "result is null");
        }

        return imagePaths;
    }

    public class MovieApiDataRequest extends AsyncTask<String, Void, Map<Integer, Map<String, Object>>>{

        @Override
        public Map<Integer, Map<String, Object>> doInBackground(String... params){

            HttpURLConnection urlConnection = null;
            BufferedReader bufferedReader = null;
            String jsonResponse;

            try{

                Uri uri = Uri.parse(BASE_URL).buildUpon()
                        .appendPath(params[0])
                        .appendQueryParameter(API_KEY_PARAM, API_KEY)
                        .build();

                URL url = new URL(uri.toString());


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder stringBuilder = new StringBuilder();

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                while ((line = bufferedReader.readLine()) != null){
                    stringBuilder   .append(line)
                            .append("\n");
                }

                jsonResponse = stringBuilder.toString();

                allData = parseJsonResponse(jsonResponse);


            } catch (Exception e){
                Log.e(LOG_TAG, e.toString());
                return null;
            } finally {
                if (urlConnection != null){
                    urlConnection.disconnect();
                }

                if (bufferedReader != null){
                    try {
                        bufferedReader.close();
                    } catch (Exception e){
                        Log.e(LOG_TAG, e.toString());
                    }
                }
            }

            return allData;
        }

        @Override
        public void onPostExecute(Map<Integer, Map<String, Object>> result){
            super.onPostExecute(result);

            // get String[] of images
            imagePaths = getImagesPathsFromResultMap(result);

            if (imagePaths != null){
                mImageAdapter.updateAdpater(imagePaths);
            }

        }

        public Map<Integer, Map<String, Object>> parseJsonResponse(String jsonResponse)
                throws JSONException{

            Map<Integer, Map<String, Object>> allMovieData = new HashMap<>();//holds data from all movies

            JSONObject fullResponse = new JSONObject(jsonResponse);
            JSONArray movieArray = fullResponse.getJSONArray(MOVIE_ARRAY_TAG);

            //loop through the JSON array of movies
            for (int i = 0; i< movieArray.length(); i++){
                Map<String, Object> oneMovieData = new HashMap<>(); //holds all data from a single movie

                JSONObject singleMovie = movieArray.getJSONObject(i); //gets the movie at the current for loop iteration

                //gets each data point for a single movie based and the defined API JSON tag
                String title = singleMovie.getString(TITLE_TAG);
                String thumbnail = singleMovie.getString(THUMBNAIL_TAG);
                String overview = singleMovie.getString(OVERVIEW_TAG);
                Double rating = singleMovie.getDouble(RATING_TAG);
                String releaseDate = singleMovie.getString(RELEASE_DATE_TAG);

                //add all movie data to a MAP so it can be extracted using the tag
                oneMovieData.put(TITLE_TAG, title);
                oneMovieData.put(THUMBNAIL_TAG, thumbnail);
                oneMovieData.put(OVERVIEW_TAG, overview);
                oneMovieData.put(RATING_TAG, rating);
                oneMovieData.put(RELEASE_DATE_TAG, releaseDate);

                allMovieData.put(i, oneMovieData);//add single movie to all movies map

            }

            return allMovieData;
        }


    }
}





