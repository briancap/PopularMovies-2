package com.example.brian.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;


public class FavoritesAdapter extends CursorAdapter {

    public FavoritesAdapter(Context context, Cursor cursor, int flags){
        super(context, cursor, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_favorites, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView Title = (TextView) view.findViewById(R.id.favorites_title);
        TextView Rating = (TextView) view.findViewById(R.id.favorites_rating);
        TextView ReleaseDate = (TextView) view.findViewById(R.id.favorites_release_date);

        Title.setText(cursor.getString(2)); //TODO:make projection so i don't have to hardcode column indexes
        Rating.setText(" " + cursor.getString(5));
        ReleaseDate.setText(" " + cursor.getString(6));

    }
}
