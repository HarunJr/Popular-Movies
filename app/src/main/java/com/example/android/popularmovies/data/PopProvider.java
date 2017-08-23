package com.example.android.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.popularmovies.data.Contract.FavouriteEntry;
import com.example.android.popularmovies.data.Contract.MovieEntry;
import com.example.android.popularmovies.data.Contract.ReviewEntry;
import com.example.android.popularmovies.data.Contract.TrailerEntry;

import static com.example.android.popularmovies.data.Contract.CONTENT_AUTHORITY;
import static com.example.android.popularmovies.data.Contract.PATH_FAVOURITE;
import static com.example.android.popularmovies.data.Contract.PATH_MOVIE;
import static com.example.android.popularmovies.data.Contract.PATH_REVIEW;
import static com.example.android.popularmovies.data.Contract.PATH_TRAILER;

@SuppressWarnings("ConstantConditions")
public class PopProvider extends ContentProvider {
    private static final String LOG_TAG = PopProvider.class.getSimpleName();
    private DbHelper mOpenHelper;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static final int MOVIES = 100;
    private static final int MOVIE_WITH_ID = 101;

    private static final int TRAILERS = 200;
    private static final int REVIEWS = 300;

    private static final int FAVOURITE = 400;
    private static final int FAVOURITE_WITH_ID = 401;

    private static final String sMovieWithIdSelection =
            MovieEntry.TABLE_NAME +
                    "." + MovieEntry.COLUMN_MOVIE_ID + " = ?";

    private static final String sFavouriteWithIdSelection =
            FavouriteEntry.TABLE_NAME +
                    "." + FavouriteEntry.COLUMN_FAV_ID + " = ?";

    @Override
    public boolean onCreate() {
        mOpenHelper = new DbHelper(getContext());
        return true;
    }

    private static UriMatcher buildUriMatcher() {
        // I know what you're thinking.  Why create a UriMatcher when you can use regular
        // expressions instead?  Because you're not crazy, that's why.

        // All paths added to the UriMatcher have a corresponding code to return when a match is
        // found.  The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        // For each URI you want to add, create a corresponding code.
        matcher.addURI(CONTENT_AUTHORITY, PATH_MOVIE, MOVIES);
        matcher.addURI(CONTENT_AUTHORITY, PATH_MOVIE + "/#", MOVIE_WITH_ID);

        matcher.addURI(CONTENT_AUTHORITY, PATH_TRAILER, TRAILERS);
        matcher.addURI(CONTENT_AUTHORITY, PATH_REVIEW, REVIEWS);
        matcher.addURI(CONTENT_AUTHORITY, PATH_FAVOURITE, FAVOURITE);
        matcher.addURI(CONTENT_AUTHORITY, PATH_FAVOURITE + "/#", FAVOURITE_WITH_ID);
        return matcher;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIES: {
                Log.w(LOG_TAG, "called; MOVIES");
                retCursor = getMovies(projection);
                break;
            }
            case TRAILERS: {
                Log.w(LOG_TAG, "called; TRAILERS");
                retCursor = getTrailers(projection);
                break;
            }
            case REVIEWS: {
                Log.w(LOG_TAG, "called; REVIEWS");
                retCursor = getReviews(projection);
                break;
            }
            case FAVOURITE: {
                Log.w(LOG_TAG, "called; FAVOURITE");
                retCursor = getFavouriteMovies(projection);
                break;
            }
            case FAVOURITE_WITH_ID: {
                Log.w(LOG_TAG, "called; FAVOURITE_WITH_ID");
                retCursor = getFavouriteWithId(uri, projection);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        assert retCursor != null;
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    private Cursor getFavouriteWithId(Uri uri, String[] projection) {
        int id = FavouriteEntry.getFavouriteId(uri);
        String[] selectionArgs = {String.valueOf(id)};

        return mOpenHelper.getReadableDatabase().query(
                FavouriteEntry.TABLE_NAME,
                projection,
                sFavouriteWithIdSelection,
                selectionArgs,
                null,
                null,
                null
        );    }

    private Cursor getFavouriteMovies(String[] projection) {
        Log.w(LOG_TAG, "called; getFavouriteMovies");
        return mOpenHelper.getReadableDatabase().query(
                FavouriteEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
    }

    private Cursor getSingleMovie(Uri uri, String[] projection) {
        Log.w(LOG_TAG, "called; getSingleMovie");

        int id = MovieEntry.getMovieIdFromUri(uri);
        String[] selectionArgs = {String.valueOf(id)};

        return mOpenHelper.getReadableDatabase().query(
                MovieEntry.TABLE_NAME,
                projection,
                sMovieWithIdSelection,
                selectionArgs,
                null,
                null,
                null
        );
    }


    private Cursor getMovies(String[] projection) {
        Log.w(LOG_TAG, "called; getMovies");

        return mOpenHelper.getReadableDatabase().query(
                MovieEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
    }

    private Cursor getTrailers(String[] projection) {
        Log.w(LOG_TAG, "called; getTrailers");

        return mOpenHelper.getReadableDatabase().query(
                TrailerEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
    }

    private Cursor getReviews(String[] projection) {
        Log.w(LOG_TAG, "called; getReviews");

        return mOpenHelper.getReadableDatabase().query(
                ReviewEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                return MovieEntry.CONTENT_TYPE;
            case TRAILERS:
                return TrailerEntry.CONTENT_TYPE;
            case REVIEWS:
                return ReviewEntry.CONTENT_TYPE;
            case FAVOURITE:
                return FavouriteEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        Log.w(LOG_TAG, "called; delete");
        // this makes delete all rows return the number of rows deleted
        if (null == selection) selection = "1";
        switch (match) {
            case MOVIES:
                rowsDeleted = db.delete(
                        MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case MOVIE_WITH_ID:
                int movieId = MovieEntry.getMovieIdFromUri(uri);
                selectionArgs = new String[]{String.valueOf(movieId)};
                rowsDeleted = db.delete(
                        MovieEntry.TABLE_NAME, sMovieWithIdSelection, selectionArgs);
                break;
            case TRAILERS:
                rowsDeleted = db.delete(
                        TrailerEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case REVIEWS:
                rowsDeleted = db.delete(
                        ReviewEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case FAVOURITE:
                rowsDeleted = db.delete(
                        FavouriteEntry.TABLE_NAME, selection, selectionArgs);
                Log.w(LOG_TAG, "called; delete FAVOURITE");
                break;
            case FAVOURITE_WITH_ID:
                int id = FavouriteEntry.getFavouriteId(uri);
                selectionArgs = new String[]{String.valueOf(id)};

                rowsDeleted = db.delete(
                        FavouriteEntry.TABLE_NAME, sFavouriteWithIdSelection, selectionArgs);
                Log.w(LOG_TAG, "called; delete FAVOURITE_WITH_ID");
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        if (null == selection) selection = "1";
        switch (match) {
            case MOVIES:
                rowsUpdated = db.update(
                        MovieEntry.TABLE_NAME, values, selection, selectionArgs);
                Log.w(LOG_TAG, "called; update MOVIES");
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        // this makes delete all rows return the number of rows deleted
        switch (match) {
            case MOVIES:
                int returnCount = 0;
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long _id = db.insertWithOnConflict(MovieEntry.TABLE_NAME, null, value, SQLiteDatabase.CONFLICT_REPLACE);
                        Log.w(LOG_TAG, "bulkInsert; MOVIES: " + value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;

            case TRAILERS:
                returnCount = 0;
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long _id = db.insertWithOnConflict(TrailerEntry.TABLE_NAME, null, value, SQLiteDatabase.CONFLICT_REPLACE);
                        Log.w(LOG_TAG, "bulkInsert; TRAILERS: " + value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;

            case REVIEWS:
                returnCount = 0;
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long _id = db.insertWithOnConflict(ReviewEntry.TABLE_NAME, null, value, SQLiteDatabase.CONFLICT_REPLACE);
                        Log.w(LOG_TAG, "bulkInsert; REVIEWS: " + value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            case FAVOURITE:
                returnCount = 0;
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long _id = db.insertWithOnConflict(FavouriteEntry.TABLE_NAME, null, value, SQLiteDatabase.CONFLICT_REPLACE);
                        Log.w(LOG_TAG, "bulkInsert; FAVOURITE: " + value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }
}
