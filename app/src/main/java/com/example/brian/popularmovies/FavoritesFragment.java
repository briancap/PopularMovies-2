package com.example.brian.popularmovies;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.example.brian.popularmovies.data.FavoritesContract;

import java.util.HashMap;
import java.util.Map;

public class FavoritesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    String LOG_TAG = getClass().getSimpleName();
    static String [] imagePaths;
    static ImageAdapter mImageAdapter;
    static Map<Integer, Map<String, Object>> allData = new HashMap<>();

    static final int LOADER_ID = 0;      //needs to be unique for every loader in the activity

    public FavoritesFragment(){

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        getLoaderManager().initLoader(LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);
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
                if(allData != null) {
                    Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                    HashMap<Integer, Map<String, Object>> map = (HashMap) allData.get(position);
                    intent.putExtra("map", map);
                    startActivity(intent);
                }
            }
        });

        return rootView;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // build uri using contract
        Uri favoritesUri = FavoritesContract.FavoriteTable.FAVORITES_URI; //do i need the id that Udacity tacks on the end of this??

        CursorLoader cursorLoader = new CursorLoader(   getActivity()   //context
                , favoritesUri  //uri
                , null          //projection // this is where the projection from above would get passed in
                , null          //selection
                , null          //selectionArgs
                , null);        //sortOrder

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data.getCount() > 0) {
            imagePaths = new String[data.getCount()];

            data.moveToFirst(); //start the cursor at the begining

            for (int i = 0; i < data.getCount(); i++) {
                Map<String, Object> oneMovieData = new HashMap<>(); //make a new map to hold a single row of data

                //Log.e(LOG_TAG, data.getString(2));

                oneMovieData.put(Utility.ID_TAG, data.getInt(1));
                oneMovieData.put(Utility.TITLE_TAG, data.getString(2));
                oneMovieData.put(Utility.THUMBNAIL_TAG, data.getString(3));
                oneMovieData.put(Utility.OVERVIEW_TAG, data.getString(4));
                oneMovieData.put(Utility.RATING_TAG, data.getString(5));
                oneMovieData.put(Utility.RELEASE_DATE_TAG, data.getString(6));

                allData.put(i, oneMovieData); //add single row to all data

                imagePaths[i] = Utility.baseImageUrl + data.getString(3); //images for the grid view

                data.moveToNext();

            }

            //update adapter with data from the cursor
            mImageAdapter.updateAdpater(imagePaths);
        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
