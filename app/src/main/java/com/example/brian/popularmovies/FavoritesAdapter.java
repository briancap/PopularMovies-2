package com.example.brian.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

/**
 * Created by Brian on 9/21/2016.
 */
public class FavoritesAdapter extends CursorAdapter {

    public FavoritesAdapter(Context context, Cursor cursor, int flags){
        super(context, cursor, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_movies, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }
}
