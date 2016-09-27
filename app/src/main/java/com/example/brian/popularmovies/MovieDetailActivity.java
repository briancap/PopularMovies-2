package com.example.brian.popularmovies;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brian.popularmovies.data.FavoritesContract;
import com.example.brian.popularmovies.data.FavoritesDB;

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.sort_order:
                Intent sortIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(sortIntent);
                break;
            case R.id.favorites:
                Intent favIntent = new Intent(getApplicationContext(), FavoritesActivity.class);
                startActivity(favIntent);
                break;
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    public void handleCheckBoxAction(View v){
        //cast the view to CheckBox so we can get to the IsChecked method
        CheckBox cb = (CheckBox) v;

        //return true if movie in db, false if not
        boolean movieInFavorites = FavoritesDB.isMovieInFavoritesDB(getApplicationContext(), oneMovieData.get(Utility.ID_TAG).toString());

        Log.e(LOG_TAG, Boolean.toString(movieInFavorites));

        if(R.id.favoritesCheckBox == v.getId()) {
            //isChecked returns the state that occurs after the click
            //isChecked = True should add to Favorites
            //isChecked = False should delete
            if(cb.isChecked()){
                if (!movieInFavorites){ //user wants to add movie to favorites and the movie is not in favorites already
                    addFavoriteToDB();
                    Toast.makeText(
                            getApplicationContext()
                            , (String) oneMovieData.get(Utility.TITLE_TAG) + " " + getString(R.string.add_to_favorites_msg)
                            , Toast.LENGTH_SHORT
                    ).show();
                } else{
                    //do nothing because the movie is already in favorites
                }

            } else {
                deleteFavoriteFromDB();
                Toast.makeText(
                        getApplicationContext()
                        , (String) oneMovieData.get(Utility.TITLE_TAG) + " " + getString(R.string.delete_from_favorites_msg)
                        , Toast.LENGTH_SHORT
                ).show();
            }

        } else { //if the Checkbox is not the view that calls this then something is seriously wrong and the show needs to be stopped
            throw new UnsupportedOperationException("running onClick off wrong view element");
        }
    }

    public void addFavoriteToDB(){
        //Toast.makeText(getApplicationContext(), "add", Toast.LENGTH_SHORT).show();
        ContentValues contentValues = new ContentValues();

        //put data into ContentValues
        contentValues.put(FavoritesContract.FavoriteTable.COLUMN_FAVORITE_ID    , (int)     oneMovieData.get(Utility.ID_TAG));
        contentValues.put(FavoritesContract.FavoriteTable.COLUMN_TITLE          , (String)  oneMovieData.get(Utility.TITLE_TAG));
        contentValues.put(FavoritesContract.FavoriteTable.COLUMN_THUMBNAIL      , (String)  oneMovieData.get(Utility.THUMBNAIL_TAG));
        contentValues.put(FavoritesContract.FavoriteTable.COLUMN_DESCRIPTION    , (String)  oneMovieData.get(Utility.OVERVIEW_TAG));
        contentValues.put(FavoritesContract.FavoriteTable.COLUMN_RATING         ,           oneMovieData.get(Utility.RATING_TAG).toString()); //this one is toString instead of cast because you cant cast Doubles to Strings
        contentValues.put(FavoritesContract.FavoriteTable.COLUMN_RELEASE_DATE   , (String)  oneMovieData.get(Utility.RELEASE_DATE_TAG));

        //insert data into db
       getContentResolver().insert(FavoritesContract.FavoriteTable.FAVORITES_URI, contentValues);
    }

    public void deleteFavoriteFromDB(){
        getContentResolver().delete(
                FavoritesContract.FavoriteTable.FAVORITES_URI
                , FavoritesContract.FavoriteTable.COLUMN_FAVORITE_ID + " = ?"
                , new String[]{oneMovieData.get(Utility.ID_TAG).toString()}
        );

    }
}
