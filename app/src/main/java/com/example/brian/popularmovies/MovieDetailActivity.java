package com.example.brian.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by brian on 5/17/16.
 * Description:
 * Display Movie title, image, plot summary user rating, and release date to details activity
 */
public class MovieDetailActivity extends AppCompatActivity {
    static final String LOG_TAG = "*MoviesDetailActivity*";
    HashMap<String, Object> oneMovieData;
    final String TITLE_TAG = "original_title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_detail);

        Intent intent = getIntent();
        if(intent != null) {
            //String test = (String) intent.getExtras().get("map");
            oneMovieData = (HashMap) intent.getSerializableExtra("map");
            //Log.e(LOG_TAG, oneMovieData.get(TITLE_TAG).toString());
        } else {
            Log.e(LOG_TAG, "movie data extra from intent is null");
        }

        if(savedInstanceState == null){
            Bundle bundle = new Bundle();
            bundle.putSerializable("map", oneMovieData);

            MoviesDetailFragment moviesDetailFragment = new MoviesDetailFragment();
            moviesDetailFragment.setArguments(bundle);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.container_detail, moviesDetailFragment)
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }
}
