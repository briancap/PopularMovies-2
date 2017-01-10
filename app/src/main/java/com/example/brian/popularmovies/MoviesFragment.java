package com.example.brian.popularmovies;

import android.content.Context;
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
    final String LOG_TAG = getClass().getSimpleName();
    static String [] imagePaths;
    static ImageAdapter mImageAdapter;
    static Map<Integer, Map<String, Object>> allData;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container
            , Bundle savedInstanceState){

        if(savedInstanceState != null){
            allData = (Map) savedInstanceState.getSerializable(Integer.toString(R.string.allData)); //sets allData from savedInstanceState
        } else{
            getMovieData(); //sets allData from postExecute in MovieData
        }

        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);
        GridView gridView = (GridView) rootView.findViewById(R.id.gridview);
        mImageAdapter = new ImageAdapter(getContext(), imagePaths);
        gridView.setAdapter(mImageAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                HashMap<Integer, Map<String, Object>> map = (HashMap) allData.get(position);
                intent.putExtra(Integer.toString(R.string.map), map);
                startActivity(intent);
            }
        });


        return rootView;
    }


    public void getMovieData(){
        //MovieApiDataRequest movieApiDataRequest = new MovieApiDataRequest();
        MovieData movieData = new MovieData(getContext(), Utility.MAIN_DATA_TAG);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sort = sharedPreferences.getString(getString(R.string.pref_sort_key)
                ,   getString(R.string.pref_sort_default));
        //Log.e(LOG_TAG, sort);
        if(sort.equals(getString(R.string.pref_sort_highest_rated))) {
            movieData.execute(Utility.TOP_RATED);
        } else {
            movieData.execute(Utility.POPULAR);
        }

        //Log.e(LOG_TAG, "getMovieData");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putSerializable(Integer.toString(R.string.allData), (HashMap) allData);

    }

}





