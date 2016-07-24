package com.example.brian.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Map;

/**
 * Created by brian on 7/23/16.
 */
public class MoviesDetailFragment extends Fragment {

    Map<String, Object> oneMovieData;

    final String TITLE_TAG = "original_title";
    final String THUMBNAIL_TAG = "poster_path";
    final String OVERVIEW_TAG = "overview";
    final String RATING_TAG = "vote_average";
    final String RELEASE_DATE_TAG = "release_date";

    public MoviesDetailFragment(){

    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);

        Bundle bundle = getArguments();
        oneMovieData = (Map) bundle.get("map");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container
            , Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.movies_detail_fragment, container, false);

        TextView    title       = (TextView) rootView.findViewById(R.id.Title);
        ImageView   thumbnail   = (ImageView) rootView.findViewById(R.id.Thumbnail);
        TextView    releaseDate = (TextView) rootView.findViewById(R.id.ReleaseDate);
        TextView    runtime     = (TextView) rootView.findViewById(R.id.Runtime);
        TextView    rating      = (TextView) rootView.findViewById(R.id.Rating);
        TextView    description = (TextView) rootView.findViewById(R.id.Description);

        title.setText(oneMovieData.get(TITLE_TAG).toString());
        Picasso.with(getContext())
                .load(THUMBNAIL_TAG)
                //.resize(150, 150)
                .into(thumbnail);
        releaseDate.setText(oneMovieData.get(RELEASE_DATE_TAG).toString());
        runtime.setText("who knows");
        rating.setText(oneMovieData.get(RATING_TAG).toString());
        description.setText(oneMovieData.get(OVERVIEW_TAG).toString());


        return rootView;
    }




}
