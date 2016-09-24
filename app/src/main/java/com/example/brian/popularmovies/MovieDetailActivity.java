package com.example.brian.popularmovies;

import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewParent;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by brian on 5/17/16.
 * Description:
 * Display Movie title, image, plot summary user rating, and release date to details activity
 */
public class MovieDetailActivity extends AppCompatActivity {
    final String LOG_TAG = getClass().getSimpleName();

    HashMap<String, Object> oneMovieData;

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

    public void addDeleteFromFavorites(View v){
        //TODO:call insert or delete based int he check box value
        CheckBox cb = (CheckBox) v;

        if(R.id.favoritesCheckBox == v.getId()) {
            //isChecked returns the state that occurs after the click
            //isChecked = True should add to Favorites
            //isCahecked = False should delete
            if(cb.isChecked()){
                Toast.makeText(getApplicationContext(), "yes and checked", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "yes and not checked", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), "missed", Toast.LENGTH_SHORT).show();
        }
    }
}
