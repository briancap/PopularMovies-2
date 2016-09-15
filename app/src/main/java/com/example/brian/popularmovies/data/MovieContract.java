package com.example.brian.popularmovies.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Brian on 9/15/2016.
 */
public class MovieContract {

    //URI Variables
    public static final String AUTHORITY = "com.example.brian.popularmovies";
    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);

    //Table Names
    public static final String POPULAR = "popular";
    public static final String TOP_RATED = "toprated";
    public static final String FAVORITE = "favortie";

    //inner class for the FAVORITE movies table
    public static final class PopularTable implements BaseColumns {

        public static final String TABLE_NAME = POPULAR;

       // public static final String COLUMN_ID           = "popular_id"; //part of the BaseColumn functionality
        public static final String COLUMN_TITLE        = "title";
        public static final String COLUMN_THUMBNAIL    = "thumbnail";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_RATING       = "rating";
        public static final String COLUMN_DESCRIPTION  = "description";


        public static final Uri TABLE_URI = BASE_URI.buildUpon().appendPath(POPULAR).build();

        public static final String DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
                + "/" + AUTHORITY
                + "/" + POPULAR;

        public static final String ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/" + AUTHORITY
                + "/" + POPULAR;

        //gets the URI ofr a specific movie id
        public static Uri buildFavoriteURI(long movieID){
            return ContentUris.withAppendedId(TABLE_URI, movieID);
        }



    } //END OF FavoriteTable



}//END OF MovieContract
