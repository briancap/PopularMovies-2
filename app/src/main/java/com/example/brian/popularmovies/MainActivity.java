package com.example.brian.popularmovies;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.brian.popularmovies.data.FavoritesContract;

public class MainActivity extends AppCompatActivity {
    final String LOG_TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null){

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        switch(id){
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




}
