package com.example.brian.popularmovies;

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
import android.widget.ListView;

import com.example.brian.popularmovies.data.FavoritesContract;

public class FavoritesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    String LOG_TAG = getClass().getSimpleName();

    static final int LOADER_ID = 0;      //needs to be unique for every loader in the activity
    FavoritesAdapter mFavoritesAdapter;

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

        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);

        //TODO: make a new favorites adapter with this cursor
        mFavoritesAdapter = new FavoritesAdapter(getActivity(), null, 0);

        ListView favoritesListView = (ListView) rootView.findViewById(R.id.favorites_list_view);
        if(favoritesListView != null) {
            favoritesListView.setAdapter(mFavoritesAdapter);
        }
        else {
            Log.e(LOG_TAG, "listview is null");
        }

        //TODO: use content resolver to get a cursor

        return rootView;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //TODO: build uri using contract
        Uri favoritesUri = FavoritesContract.FavoriteTable.FAVORITES_URI; //do i need the id that Udacity tacks on the end of this??

        CursorLoader cursorLoader = new CursorLoader(   getActivity()   //context
                , favoritesUri  //uri
                , null          //projection
                , null          //selection
                , null          //selectionArgs
                , null);        //sortOrder


        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mFavoritesAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mFavoritesAdapter.swapCursor(null);
    }
}
