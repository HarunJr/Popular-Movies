package com.example.android.popularmovies.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.android.popularmovies.data.Contract.MovieEntry;
import com.example.android.popularmovies.model.Movie;

import java.util.ArrayList;

public class LocalStore {
    private static final String LOG_TAG = LocalStore.class.getSimpleName();
    private SQLiteDatabase db;
    private final DbHelper dbHelper;
    private final Context mContext;

    public LocalStore(Context context) {
        this.mContext = context;
        dbHelper = new DbHelper(mContext);
    }

    private void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    private void close() {
        db.close();
    }

    public void storeMovieData(Movie movie) {
        open();
        ArrayList<ContentValues> movieList = new ArrayList<>();
        ContentValues vehicleValues = new ContentValues();
        Log.w(LOG_TAG, "storeMovieData: " + movie.getTitle());

        vehicleValues.put(MovieEntry.COLUMN_MOVIE_ID, movie.getId());
        vehicleValues.put(MovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        vehicleValues.put(MovieEntry.COLUMN_MOVIE_TITLE, movie.getTitle());
        vehicleValues.put(MovieEntry.COLUMN_ORIGINAL_TITLE, movie.getOriginalTitle());
        vehicleValues.put(MovieEntry.COLUMN_MOVIE_POPULARITY, movie.getPopularity());
        vehicleValues.put(MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
        vehicleValues.put(MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
        vehicleValues.put(MovieEntry.RELEASE_DATE, movie.getReleaseDate());
        vehicleValues.put(MovieEntry.BACKDROP_PATH, movie.getBackdropPath());

        movieList.add(vehicleValues);

        if (movieList.size() > 0) {
            ContentValues[] cvArray = new ContentValues[movieList.size()];
            movieList.toArray(cvArray);

            //TODO: bulkInsert
            long vehicleRowId = mContext.getContentResolver().bulkInsert(MovieEntry.CONTENT_URI, cvArray);

            if (vehicleRowId > 0) {
                Log.w(LOG_TAG, "storeMoviesDataSUCCESS: " + movie.getTitle());

            } else {
                Log.w(LOG_TAG, " >>>> ERROR Inserting into SQLiteDb: ");
            }
            close();
        }
    }
}
