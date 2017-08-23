package com.example.android.popularmovies.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.android.popularmovies.DatabaseHandler;
import com.example.android.popularmovies.activities.MainActivity;
import com.example.android.popularmovies.data.Contract.FavouriteEntry;
import com.example.android.popularmovies.data.Contract.MovieEntry;
import com.example.android.popularmovies.data.Contract.ReviewEntry;
import com.example.android.popularmovies.data.Contract.TrailerEntry;
import com.example.android.popularmovies.infrastructure.PopularMoviesApplication;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.model.Review;
import com.example.android.popularmovies.model.Trailer;

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
        ContentValues movieValues = new ContentValues();

        movieValues.put(MovieEntry.COLUMN_MOVIE_ID, movie.getId());
        movieValues.put(MovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        movieValues.put(MovieEntry.COLUMN_MOVIE_TITLE, movie.getTitle());
        movieValues.put(MovieEntry.COLUMN_ORIGINAL_TITLE, movie.getOriginalTitle());
        movieValues.put(MovieEntry.COLUMN_MOVIE_POPULARITY, movie.getPopularity());
        movieValues.put(MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
        movieValues.put(MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
        movieValues.put(MovieEntry.RELEASE_DATE, movie.getReleaseDate());
        movieValues.put(MovieEntry.BACKDROP_PATH, movie.getBackdropPath());

        movieList.add(movieValues);

        if (movieList.size() > 0) {
            ContentValues[] cvArray = new ContentValues[movieList.size()];
            movieList.toArray(cvArray);

            //TODO: bulkInsert With Handler
            DatabaseHandler handler = new DatabaseHandler(mContext.getContentResolver());
            handler.startBulkInsert(1, null, MovieEntry.CONTENT_URI, cvArray);
        }
        close();
    }

    public void storeTrailerData(Trailer trailer) {
        open();
        ArrayList<ContentValues> movieList = new ArrayList<>();
        ContentValues trailerValues = new ContentValues();
        Log.w(LOG_TAG, "storeTrailerData: " + trailer.getName());

        trailerValues.put(TrailerEntry.COLUMN_TRAILER_ID, trailer.getId());
        trailerValues.put(TrailerEntry.COLUMN_TRAILER_KEY, trailer.getKey());
        trailerValues.put(TrailerEntry.COLUMN_TRAILER_NAME, trailer.getName());

        movieList.add(trailerValues);

        if (movieList.size() > 0) {
            ContentValues[] cvArray = new ContentValues[movieList.size()];
            movieList.toArray(cvArray);

            //TODO: bulkInsert
            long vehicleRowId = mContext.getContentResolver().bulkInsert(TrailerEntry.CONTENT_URI, cvArray);
            if (vehicleRowId > 0) {
                Log.w(LOG_TAG, "storeMoviesDataSUCCESS: " + trailer.getName());
            } else {
                Log.w(LOG_TAG, " >>>> ERROR Inserting into SQLiteDb: ");
            }
        }
        close();
    }

    public void storeReviewsData(Review review) {
        Log.w(LOG_TAG, "storeReviewData: " + review.getAuthor());
        open();
        ArrayList<ContentValues> movieList = new ArrayList<>();
        ContentValues reviewValues = new ContentValues();

        reviewValues.put(ReviewEntry.COLUMN_REVIEW_ID, review.getId());
        reviewValues.put(ReviewEntry.COLUMN_REVIEW_AUTHOR, review.getAuthor());
        reviewValues.put(ReviewEntry.COLUMN_REVIEW_CONTENT, review.getContent());
        reviewValues.put(ReviewEntry.COLUMN_REVIEW_URL, review.getUrl());

        movieList.add(reviewValues);

        if (movieList.size() > 0) {
            ContentValues[] cvArray = new ContentValues[movieList.size()];
            movieList.toArray(cvArray);

            //TODO: bulkInsert
            long vehicleRowId = mContext.getContentResolver().bulkInsert(ReviewEntry.CONTENT_URI, cvArray);

            if (vehicleRowId > 0) {
                Log.w(LOG_TAG, "storeReviewsDataSUCCESS: " + review.getAuthor());

            } else {
                Log.w(LOG_TAG, " >>>> ERROR Inserting into SQLiteDb: ");
            }
        }
        close();
    }

    public void storeFavMovieData(Movie movie) {
        open();
        ArrayList<ContentValues> movieList = new ArrayList<>();
        ContentValues favValues = new ContentValues();
        Log.w(LOG_TAG, "storeFavData: " + movie.getTitle()+"  "+movie.getPosterPath());

        favValues.put(FavouriteEntry.COLUMN_FAV_ID, movie.getId());
        favValues.put(FavouriteEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        favValues.put(FavouriteEntry.COLUMN_FAV_TITLE, movie.getTitle());
        favValues.put(FavouriteEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
        favValues.put(FavouriteEntry.COLUMN_OVERVIEW, movie.getOverview());
        favValues.put(FavouriteEntry.RELEASE_DATE, movie.getReleaseDate());
        favValues.put(FavouriteEntry.BACKDROP_PATH, movie.getBackdropPath());

        movieList.add(favValues);

        if (movieList.size() > 0) {
            ContentValues[] cvArray = new ContentValues[movieList.size()];
            movieList.toArray(cvArray);

            //TODO: bulkInsert
            long vehicleRowId = mContext.getContentResolver().bulkInsert(FavouriteEntry.CONTENT_URI, cvArray);

            if (vehicleRowId > 0) {
                Log.w(LOG_TAG, "storeFavMovieDataSUCCESS: " + movie.getTitle());

            } else {
                Log.w(LOG_TAG, " >>>> ERROR Inserting into SQLiteDb: ");
            }
        }
        close();
    }

    public boolean isFavourite(Context mContext, int movieId) {
        Uri movieFavouriteWithIdUri = FavouriteEntry.buildFavouriteDataUri(movieId);
        Cursor cursor = mContext.getContentResolver().query(
                movieFavouriteWithIdUri, MainActivity.FAVOURITE_COLUMN, null, null, null);

        assert cursor != null;
        if (cursor.getCount() > 0){
            Log.w(LOG_TAG, "isFavourite: ");
            cursor.close();
            return true;
        }else {
            cursor.close();
            Log.w(LOG_TAG, "isNOTFavourite: ");
            return false;
        }
    }

    public void deleteFavMovieData(PopularMoviesApplication application, Movie movie) {
        Uri movieFavouriteWithIdUri = FavouriteEntry.buildFavouriteDataUri(movie.getId());
        application.getContentResolver().delete(movieFavouriteWithIdUri, null, null);
    }

    public void getFavMovies() {
        Movie movie = null;
        Cursor cursor = mContext.getContentResolver().query(
                FavouriteEntry.CONTENT_URI, MainActivity.FAVOURITE_COLUMN, null, null, null);

        assert cursor != null;
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                int movieId = cursor.getInt(MainActivity.COL_FAV_ID);
                String movieImagePath = cursor.getString(MainActivity.COL_FAV_PATH);
                String movieTitle = cursor.getString(MainActivity.COL_FAV_TITLE);
                double movieAv = cursor.getDouble(MainActivity.COL_FAV_VOTE_AVERAGE);
                String movieOverview = cursor.getString(MainActivity.COL_FAV_OVERVIEW);
                String movieReleaseDate = cursor.getString(MainActivity.COL_FAV_RELEASE_DATE);
                String movieBackPath = cursor.getString(MainActivity.COL_FAV_BACKDROP_PATH);
                Log.w(LOG_TAG, "getFavMoviesById: "+movieTitle+" "+ movieImagePath);

                movie = new Movie(movieId, movieImagePath, movieTitle, movieAv, movieOverview, movieReleaseDate,movieBackPath);
                storeMovieData(movie);

            }while (cursor.moveToNext());
            cursor.close();
        }else {
            Log.w(LOG_TAG, "cursor is null: " + cursor.getCount() + " \ntransactionWithId: ");
            cursor.close();
        }
    }
}
