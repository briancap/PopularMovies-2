package com.example.brian.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

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
import java.util.Objects;

/**
 * Created by Brian on 9/27/2016.
 */
public class MovieData extends AsyncTask<String, Void, Map<Integer, Map<String, Object>>>{
    final String LOG_TAG = getClass().getSimpleName();
    String [] imagePaths;
    ImageAdapter mImageAdapter;

    Map<Integer, Map<String, Object>> allData;
    String trailerURL;
    Map<String, Object> reviews;

    Context context;
    String requestType;

    public MovieData(Context context, String type){
        this.context = context;
        this.requestType = type;
    }

    @Override
    public Map<Integer, Map<String, Object>> doInBackground(String... params){

        HttpURLConnection urlConnection = null;
        BufferedReader bufferedReader = null;
        String jsonResponse;

        try{
            Uri uri = null;

            switch(requestType) {
                case Utility.MAIN_DATA_TAG:
                    uri = Uri.parse(Utility.BASE_URL_MAIN).buildUpon()
                        .appendPath(params[0])
                        //stole Udacity's BuildConfig style of declaring the API_KEY
                        //...so i stop accidentally committing the key and having to erase commit history
                        //...for an important file
                        .appendQueryParameter(Utility.API_KEY_PARAM, BuildConfig.API_KEY)
                        .build();

                    jsonResponse = getJsonDataAsString(uri);

                    allData = parseMainJsonResponse(jsonResponse);

                    break;

                //trailers and reviews need to add the ID, could also do this by passing in extra params
                case Utility.MOVIE_TRAILER_TAG:
                case Utility.MOVIE_REVIEW_TAG:
                    uri = Uri.parse(Utility.BASE_URL_MAIN).buildUpon()
                            .appendPath(MoviesDetailFragment.oneMovieData.get(Utility.ID_TAG).toString()) // add movie ID
                            .appendPath(params[0])
                            .appendQueryParameter(Utility.API_KEY_PARAM, BuildConfig.API_KEY)
                            .build();

                    jsonResponse = getJsonDataAsString(uri);
                    trailerURL = getTrailerUrlFromJson(jsonResponse);

                    break;

                default:
                    uri = null;
            }

            jsonResponse = getJsonDataAsString(uri);
            allData = parseMainJsonResponse(jsonResponse);

        } catch (JSONException e){
            e.printStackTrace();
        }

        return allData;
    }

    @Override
    public void onPostExecute(Map<Integer, Map<String, Object>> result){
        super.onPostExecute(result);

        switch(requestType) { //TODO: add switch statement here to return data based on type
            case Utility.MAIN_DATA_TAG:
               // Map<Integer, Map<String, Object>> resultHolder = (Map<Integer, Map<String, Object>>) result;

                MoviesFragment.allData = result;
                // get String[] of images
                MoviesFragment.imagePaths = getImagesPathsFromResultMap(result);

                if (imagePaths != null) {
                    MoviesFragment.mImageAdapter.updateAdpater(imagePaths);
                }

                break;

            case Utility.MOVIE_TRAILER_TAG:

                //MoviesDetailFragment.youtubeLink =

                break;
            case Utility.MOVIE_REVIEW_TAG:


                break;

            default:
                break;

        }

    }

    public String getJsonDataAsString(Uri uri){

        HttpURLConnection urlConnection = null;
        BufferedReader bufferedReader = null;
        String jsonResponse;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            URL url = new URL(uri.toString());


            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();


            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while ((line = bufferedReader.readLine()) != null){
                stringBuilder   .append(line)
                        .append("\n");
            }
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

        jsonResponse = stringBuilder.toString();

        return jsonResponse;
    }

    public Map<Integer, Map<String, Object>> parseMainJsonResponse(String jsonResponse) throws JSONException{

        Map<Integer, Map<String, Object>> allMovieData = new HashMap<>();//holds data from all movies

        JSONObject fullResponse = new JSONObject(jsonResponse);
        JSONArray movieArray = fullResponse.getJSONArray(Utility.MOVIE_ARRAY_TAG);

        //loop through the JSON array of movies
        for (int i = 0; i< movieArray.length(); i++){
            Map<String, Object> oneMovieData = new HashMap<>(); //holds all data from a single movie

            JSONObject singleMovie = movieArray.getJSONObject(i); //gets the movie at the current for loop iteration

            //gets each data point for a single movie based and the defined API JSON tag
            int    id           = singleMovie.getInt(Utility.ID_TAG);
            String title        = singleMovie.getString(Utility.TITLE_TAG);
            String thumbnail    = singleMovie.getString(Utility.THUMBNAIL_TAG);
            String overview     = singleMovie.getString(Utility.OVERVIEW_TAG);
            Double rating       = singleMovie.getDouble(Utility.RATING_TAG);
            String releaseDate  = singleMovie.getString(Utility.RELEASE_DATE_TAG);

            //add all movie data to a MAP so it can be extracted using the tag
            oneMovieData.put(Utility.ID_TAG, id);
            oneMovieData.put(Utility.TITLE_TAG, title);
            oneMovieData.put(Utility.THUMBNAIL_TAG, thumbnail);
            oneMovieData.put(Utility.OVERVIEW_TAG, overview);
            oneMovieData.put(Utility.RATING_TAG, rating);
            oneMovieData.put(Utility.RELEASE_DATE_TAG, releaseDate);

            allMovieData.put(i, oneMovieData);//add single movie to all movies map

        }


        return allMovieData;
    }

    public String getTrailerUrlFromJson(String jsonResponse)throws JSONException{
        JSONObject fullResponse = new JSONObject(jsonResponse);
        JSONArray movieArray = fullResponse.getJSONArray(Utility.MOVIE_ARRAY_TAG);

        //TODO: review this, currently getting the middle quality review 0, 1, 2
        JSONObject singleTrailer = movieArray.getJSONObject(1);
        String trailerKey = singleTrailer.getString(Utility.YOUTUBE_KEY_TAG);

        return trailerKey;
    }

    public String getReviewsFromJson(String jsonResponse)throws JSONException{
        JSONObject fullResponse = new JSONObject(jsonResponse);
        JSONArray movieArray = fullResponse.getJSONArray(Utility.MOVIE_ARRAY_TAG);

        for (int i = 0; i< movieArray.length(); i++){
            JSONObject singleReview = movieArray.getJSONObject(i);

            String author   = singleReview.getString(Utility.COMMENT_AUTHOR_TAG);
            String comment  = singleReview.getString(Utility.COMMENTS_TAG);

            reviews.put(Utility.COMMENT_AUTHOR_TAG, author);
            reviews.put(Utility.COMMENTS_TAG, comment);
        }


        return null;
    }

    public String [] getImagesPathsFromResultMap(Map<Integer, Map<String, Object>> resultMap){
        if(resultMap != null){
            imagePaths = new String[resultMap.size()];

            for(int i = 0; i < resultMap.size(); i++){
                //get the Map at position i, then get the title from the single movie map
                String movieSpecificUrl =  (String) (resultMap.get(i)).get(Utility.THUMBNAIL_TAG);
                if(movieSpecificUrl != null){
                    imagePaths[i] = (Utility.baseImageUrl + movieSpecificUrl);
                }

            }
        } else {
            Log.e(LOG_TAG, "result is null");
        }

        return imagePaths;
    }


}