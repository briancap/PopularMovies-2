package com.example.brian.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Brian on 9/15/2016.
 */
public class MovieProvider extends ContentProvider{

    private static final UriMatcher sUriMatcher = matchUri();
    private MovieDBHelper mMovieDBHelper;
    private static final SQLiteQueryBuilder sWeatherByLocationSettingQueryBuilder;

    static final int POPULAR = 100;


    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    static UriMatcher matchUri(){
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        uriMatcher.addURI(authority, MovieContract.POPULAR, POPULAR);



        return uriMatcher;

    }
} //END OF MovieProvider
