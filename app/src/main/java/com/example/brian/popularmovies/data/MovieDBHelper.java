package com.example.brian.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.brian.popularmovies.data.MovieContract.PopularTable;

/**
 * Created by Brian on 9/15/2016.
 */
public class MovieDBHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "movies.db";

    public MovieDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE_POPULAR =
                "CREATE TABLE IF NOT EXISTS "
                + PopularTable.TABLE_NAME + " ("
                + PopularTable._ID                  + " INTEGER PRIMARY KEY, "
                + PopularTable.COLUMN_TITLE         + " TEXT NULL, "
                + PopularTable.COLUMN_THUMBNAIL     + " TEXT NULL "
                + PopularTable.COLUMN_DESCRIPTION   + " TEXT NULL "
                + PopularTable.COLUMN_RATING        + " TEXT NULL "
                + PopularTable.COLUMN_RELEASE_DATE  + " TEXT NULL "
                + " );";
        db.execSQL(SQL_CREATE_TABLE_POPULAR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PopularTable.TABLE_NAME);
        onCreate(db);
    }
}
