package com.example.brian.popularmovies;

import android.database.Cursor;

import com.example.brian.popularmovies.data.FavoritesContract;

/**
 * Created by Brian on 9/24/2016.
 */
public class Utility {

    //TODO: strings should be moved to the String res/values file, but I like "Utility." more than "R.string." for now

    public static final String baseImageUrl = "http://image.tmdb.org/t/p/w185//";

    final static String MOVIE_ARRAY_TAG            = "results";
    public static final String ID_TAG              = "id";
    public static final String TITLE_TAG           = "original_title";
    public static final String THUMBNAIL_TAG       = "poster_path";
    public static final String OVERVIEW_TAG        = "overview";
    public static final String RATING_TAG          = "vote_average";
    public static final String RELEASE_DATE_TAG    = "release_date";

    final static String BASE_URL           = "http://api.themoviedb.org/3/movie/";
    final static String API_KEY_PARAM      = "api_key";

    final static String POPULAR    = "popular";
    final static String TOP_RATED  = "top_rated";



}
