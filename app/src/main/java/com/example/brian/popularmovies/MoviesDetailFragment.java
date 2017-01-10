package com.example.brian.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.brian.popularmovies.data.FavoritesDB;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.Map;

/**
 * Created by brian on 7/23/16.
 */
public class MoviesDetailFragment extends Fragment {
    static String LOG_TAG = "MoviesDetailFragment";
    static Map<String, Object> oneMovieData;
    static Map<Integer, Map<String, Object>> reviewData;
    static String youtubeLink;
    static ListView commentLV;

    DisplayMetrics displayMetrics;
    int imageWidth;
    int imageHeight;

    public MoviesDetailFragment(){

    }


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);

        Bundle bundle = getArguments();
        oneMovieData = (Map) bundle.get(Integer.toString(R.string.map));

        displayMetrics = getContext().getResources().getDisplayMetrics();
        imageWidth = (displayMetrics.widthPixels)/3;
        imageHeight = (displayMetrics.heightPixels/3);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container
            , Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.fragment_movies_detail, container, false);

        TextView    title           = (TextView)    rootView.findViewById(R.id.Title);
        CheckBox    checkBox        = (CheckBox)    rootView.findViewById(R.id.favoritesCheckBox);
        ImageView   thumbnail       = (ImageView)   rootView.findViewById(R.id.Thumbnail);
        TextView    releaseDate     = (TextView)    rootView.findViewById(R.id.ReleaseDate);
        TextView    rating          = (TextView)    rootView.findViewById(R.id.Rating);
        TextView    description     = (TextView)    rootView.findViewById(R.id.Description);
        TextView    trailerButton   = (Button)      rootView.findViewById(R.id.launchTrailer);

        commentLV                   = (ListView)    rootView.findViewById(R.id.reviewListView);

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

        trailerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(youtubeLink != null){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink)));
                }
            }
        });



        return rootView;
    }

    @Override
    public void onStart(){
        super.onStart();

        MovieData trailer = new MovieData(getContext(), Utility.MOVIE_TRAILER_TAG);
        trailer.execute(Utility.TRAILER);

        MovieData reviews = new MovieData(getContext(), Utility.MOVIE_REVIEW_TAG);
        reviews.execute(Utility.REVIEW);
    }

    //probably should use these functions to set return variables instead of static references from MovieData
    public static void setYoutubeLink(String result){
        youtubeLink = result;
    }

    public static void setReviewData(Map<Integer, Map<String, Object>> result){
        reviewData = result;
        commentLV.setAdapter(new ReviewAdapter(reviewData));
    }








}// END OF CLASS
