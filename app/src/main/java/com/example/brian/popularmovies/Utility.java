package com.example.brian.popularmovies;

import android.database.Cursor;

import com.example.brian.popularmovies.data.FavoritesContract;

/**
 * Created by Brian on 9/24/2016.
 */
public class Utility {

    // strings could be moved to the String res/values file, but I like "Utility." more than "R.string." for now

    public static final String baseImageUrl = "http://image.tmdb.org/t/p/w185//";

    //JSON uri
    final static String BASE_URL_MAIN               = "http://api.themoviedb.org/3/movie/";
    final static String BASE_YOUTUBE_URL            = "https://www.youtube.com/watch?v=";
    final static String API_KEY_PARAM               = "api_key";

    //JSON uri different tags
    final static String POPULAR                     = "popular";
    final static String TOP_RATED                   = "top_rated";
    final static String TRAILER                     = "videos";
    final static String REVIEW                      = "reviews";

    //JSON request switch statement
    final static String MAIN_DATA_TAG               = "main";
    final static String MOVIE_TRAILER_TAG           = "trailer";
    final static String MOVIE_REVIEW_TAG            = "review";

    //Tags for main JSON parsing
    public static final String MOVIE_ARRAY_TAG      = "results";
    public static final String ID_TAG               = "id";
    public static final String TITLE_TAG            = "original_title";
    public static final String THUMBNAIL_TAG        = "poster_path";
    public static final String OVERVIEW_TAG         = "overview";
    public static final String RATING_TAG           = "vote_average";
    public static final String RELEASE_DATE_TAG     = "release_date";


    //Tags for trailer JSON parsing
    public static final String YOUTUBE_KEY_TAG      = "key";

    //Tags for comment JSON parsing
    public static final String COMMENT_AUTHOR_TAG   = "author";
    public static final String COMMENTS_TAG         = "content";






}
