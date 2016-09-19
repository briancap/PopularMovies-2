package com.example.brian.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Brian on 9/15/2016.
 */
public class FavoritesProvider extends ContentProvider{

    private static final UriMatcher sUriMatcher = matchUri();
    private FavoritesDB mFavoritesDB;
    private static final SQLiteQueryBuilder mQueryBuilder;

    static final int FAVORITE = 100;

    static { //static block allows the declaration of a bunch of static variables at once, learned this from Sunshine
        //TODO: is there any reason these are decalred here and not in onCreate()???
        //TODO: I copied this from Sunshine and I personally would probably do this in onCreate if i didn't copy
        mQueryBuilder = new SQLiteQueryBuilder();
        mQueryBuilder.setTables(FavoritesContract.FavoriteTable.TABLE_NAME);

    }


    @Override
    public boolean onCreate() {
        mFavoritesDB = new FavoritesDB(getContext());
        return true;

    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;

        //??? get the Cursor associated with the Favorites table
        cursor = mFavoritesDB.getReadableDatabase().query(
                FavoritesContract.FavoriteTable.TABLE_NAME
                , projection //TODO: should make a projection before attempting to use one
                , selection
                , selectionArgs
                , null
                , null
                , sortOrder
        );
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        final int match = sUriMatcher.match(uri);

        if(match == FAVORITE){
            //set to DIR becasue there can be more than one favorite
            return FavoritesContract.FavoriteTable.DIR_TYPE;
        } else {
            throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }

    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mFavoritesDB.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        if(match == FAVORITE){
            long _id = db.insert(FavoritesContract.FavoriteTable.TABLE_NAME, null, values);
            if(_id > 0){
                //TODO: this URI needs to append the _id of the row inserted.
                // TODO: Udacity build this with a custom method in the Provider
                    returnUri = FavoritesContract.FavoriteTable.TABLE_URI;
            } else {
                throw new android.database.SQLException("*** FAILED TO INSERT ROWS *** " + uri);
            }
        }

        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        //TODO: delete favorites when they are unfavorited
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        //TODO: look into this, but it probably doens't need to be used right away
        //TODO: the rating is proabbly the only thing that changes and not sure that's important
        return 0;
    }

    static UriMatcher matchUri(){
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = FavoritesContract.AUTHORITY;

        uriMatcher.addURI(authority, FavoritesContract.FAVORITE, FAVORITE);

        return uriMatcher;

    }
} //END OF MovieProvider
