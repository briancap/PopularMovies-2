package com.example.brian.popularmovies;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.brian.popularmovies.data.FavoritesDB;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.Map;

/**
 * Created by brian on 7/23/16.
 */
public class MoviesDetailFragment extends Fragment {

    Map<String, Object> oneMovieData;

    DisplayMetrics displayMetrics;
    int imageWidth;
    int imageHeight;

    public MoviesDetailFragment(){

    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);

        Bundle bundle = getArguments();
        oneMovieData = (Map) bundle.get("map");

        displayMetrics = getContext().getResources().getDisplayMetrics();
        imageWidth = (displayMetrics.widthPixels)/3;
        imageHeight = (displayMetrics.heightPixels/3);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container
            , Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.fragment_movies_detail, container, false);

        TextView    title       = (TextView) rootView.findViewById(R.id.Title);
        CheckBox    checkBox    = (CheckBox) rootView.findViewById(R.id.favoritesCheckBox);
        ImageView   thumbnail   = (ImageView) rootView.findViewById(R.id.Thumbnail);
        TextView    releaseDate = (TextView) rootView.findViewById(R.id.ReleaseDate);
        TextView    rating      = (TextView) rootView.findViewById(R.id.Rating);
        TextView    description = (TextView) rootView.findViewById(R.id.Description);

        title.setText(oneMovieData.get(Utility.TITLE_TAG).toString());

        boolean movieInDb = FavoritesDB.isMovieInFavoritesDB(getActivity(), oneMovieData.get(Utility.ID_TAG).toString());
        checkBox.setChecked(movieInDb);

        Picasso.with(getContext())
                .load(Utility.baseImageUrl + oneMovieData.get(Utility.THUMBNAIL_TAG).toString())
                .resize(imageWidth, imageHeight)
                .into(thumbnail);
        releaseDate.setText(oneMovieData.get(Utility.RELEASE_DATE_TAG).toString());
        rating.setText(oneMovieData.get(Utility.RATING_TAG).toString());
        description.setText(oneMovieData.get(Utility.OVERVIEW_TAG).toString());


        return rootView;
    }






}
