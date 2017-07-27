package com.example.android.popularmovies.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.android.popularmovies.data.Contract.MovieEntry;

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
                    + MovieEntry.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL, "
                    + MovieEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, "
                    + MovieEntry.COLUMN_MOVIE_POPULARITY + " TEXT NOT NULL, "
                    + MovieEntry.COLUMN_VOTE_AVERAGE + " REAL, "
                    + MovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, "
                    + MovieEntry.RELEASE_DATE + " TEXT NOT NULL, "
                    + MovieEntry.BACKDROP_PATH + " TEXT NOT NULL, "

                    + "UNIQUE (" + MovieEntry.COLUMN_MOVIE_ID + ") ON CONFLICT REPLACE );";

            db.execSQL(SQL_CREATE_MOVIE_TABLE);
        }catch (SQLException e){
            Toast.makeText(mContext, "" + e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            String SQL_DROP_VEHICLE_TABLE = "DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME;
            db.execSQL(SQL_DROP_VEHICLE_TABLE);
            onCreate(db);

        } catch (SQLException e) {
            Toast.makeText(mContext, "" + e, Toast.LENGTH_LONG).show();
        }
    }
}
