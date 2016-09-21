package com.example.brian.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FavoritesFragment extends Fragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container
            , Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);

        return rootView;
    }




}
