package com.example.android.popularmovies.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import static com.example.android.popularmovies.data.Contract.*;

class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;
    private final Context mContext;

    DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {

            String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE "
                    + MovieEntry.TABLE_NAME + " ("
                    + MovieEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, "
                    + MovieEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL, "
                    + MovieEntry.COLUMN_ORIGINAL_TITLE + " TEXT, "
                    + MovieEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, "
                    + MovieEntry.COLUMN_MOVIE_POPULARITY + " TEXT, "
                    + MovieEntry.COLUMN_VOTE_AVERAGE + " REAL, "
                    + MovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, "
                    + MovieEntry.RELEASE_DATE + " TEXT NOT NULL, "
                    + MovieEntry.BACKDROP_PATH + " TEXT NOT NULL, "

                    + "UNIQUE (" + MovieEntry.COLUMN_MOVIE_ID + ") ON CONFLICT REPLACE );";

            String SQL_CREATE_TRAILER_TABLE = "CREATE TABLE "
                    + TrailerEntry.TABLE_NAME + " ("
                    + TrailerEntry.COLUMN_TRAILER_ID + " TEXT NOT NULL, "
                    + TrailerEntry.COLUMN_TRAILER_KEY + " TEXT NOT NULL, "
                    + TrailerEntry.COLUMN_TRAILER_NAME + " TEXT NOT NULL, "

                    + "UNIQUE (" + TrailerEntry.COLUMN_TRAILER_ID + ") ON CONFLICT REPLACE );";

            String SQL_CREATE_REVIEW_TABLE = "CREATE TABLE "
                    + ReviewEntry.TABLE_NAME + " ("
                    + ReviewEntry.COLUMN_REVIEW_ID + " TEXT NOT NULL, "
                    + ReviewEntry.COLUMN_REVIEW_AUTHOR + " TEXT NOT NULL, "
                    + ReviewEntry.COLUMN_REVIEW_CONTENT + " TEXT NOT NULL, "
                    + ReviewEntry.COLUMN_REVIEW_URL + " TEXT NOT NULL, "

                    + "UNIQUE (" + ReviewEntry.COLUMN_REVIEW_ID + ") ON CONFLICT REPLACE );";

            String SQL_CREATE_FAVOURITE_TABLE = "CREATE TABLE "
                    + FavouriteEntry.TABLE_NAME + " ("
                    + FavouriteEntry.COLUMN_FAV_ID + " INTEGER NOT NULL, "
                    + FavouriteEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, "
                    + FavouriteEntry.COLUMN_FAV_TITLE + " TEXT NOT NULL, "
                    + FavouriteEntry.COLUMN_VOTE_AVERAGE + " REAL, "
                    + FavouriteEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, "
                    + FavouriteEntry.RELEASE_DATE + " TEXT NOT NULL, "
                    + FavouriteEntry.BACKDROP_PATH + " TEXT NOT NULL, "

                    + "UNIQUE (" + FavouriteEntry.COLUMN_FAV_ID + ") ON CONFLICT REPLACE );";

            db.execSQL(SQL_CREATE_MOVIE_TABLE);
            db.execSQL(SQL_CREATE_TRAILER_TABLE);
            db.execSQL(SQL_CREATE_REVIEW_TABLE);
            db.execSQL(SQL_CREATE_FAVOURITE_TABLE);

        }catch (SQLException e){
            Toast.makeText(mContext, "" + e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            String SQL_DROP_MOVIE_TABLE = "DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME;
            String SQL_DROP_TRAILER_TABLE = "DROP TABLE IF EXISTS " + TrailerEntry.TABLE_NAME;
            String SQL_DROP_REVIEW_TABLE = "DROP TABLE IF EXISTS " + ReviewEntry.TABLE_NAME;
            String SQL_DROP_FAV_TABLE = "DROP TABLE IF EXISTS " + FavouriteEntry.TABLE_NAME;

            db.execSQL(SQL_DROP_MOVIE_TABLE);
            db.execSQL(SQL_DROP_TRAILER_TABLE);
            db.execSQL(SQL_DROP_REVIEW_TABLE);
            db.execSQL(SQL_DROP_FAV_TABLE);

            onCreate(db);

        } catch (SQLException e) {
            Toast.makeText(mContext, "" + e, Toast.LENGTH_LONG).show();
        }
    }
}
