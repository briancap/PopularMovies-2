package com.example.brian.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.brian.popularmovies.data.FavoritesContract.FavoriteTable;

/**
 * Created by Brian on 9/15/2016.
 */
public class FavoritesDB extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "movies.db";

    public FavoritesDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE_FAVORITES =
                "CREATE TABLE IF NOT EXISTS "
                + FavoriteTable.TABLE_NAME + " ("
                + FavoriteTable.COLUMN_ID_AI         + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FavoriteTable.COLUMN_FAVORITE_ID   + " INTEGER, "
                + FavoriteTable.COLUMN_TITLE         + " TEXT NULL, "
                + FavoriteTable.COLUMN_THUMBNAIL     + " TEXT NULL, "
                + FavoriteTable.COLUMN_DESCRIPTION   + " TEXT NULL, "
                + FavoriteTable.COLUMN_RATING        + " TEXT NULL, "
                + FavoriteTable.COLUMN_RELEASE_DATE  + " TEXT NULL "
                + " );";
        db.execSQL(SQL_CREATE_TABLE_FAVORITES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavoriteTable.TABLE_NAME);
        onCreate(db);
    }

    public void tempDropTable(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS " + FavoriteTable.TABLE_NAME + ";");
        onCreate(db);
    }
}
