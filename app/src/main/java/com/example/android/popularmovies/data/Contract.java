package com.example.android.popularmovies.data;

import android.content.ContentResolver;
import android.net.Uri;

public class Contract {
    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    static final String CONTENT_AUTHORITY = "com.example.android.popularmovies";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.example.android.popularmovies.app/movies/ is a valid path for
    // looking at movie data. content://com.example.android.popularmovies.app/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    static final String PATH_MOVIE = "movie";

    public static final class MovieEntry {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
//        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static final String TABLE_NAME = "movie";

        public static final String COLUMN_MOVIE_ID = "id";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_MOVIE_TITLE = "title";
        public static final String COLUMN_ORIGINAL_TITLE = "original_title";
        public static final String COLUMN_MOVIE_POPULARITY = "popularity";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String RELEASE_DATE = "release_date";
        public static final String BACKDROP_PATH = "backdrop_path";

        public static Uri buildMovieDataUri(
                String image_url, String image_title, String original_title, double vote_average,
                String overview, String release_date, String backdropPath) {
            return CONTENT_URI.buildUpon()
                    .appendPath(image_url)
                    .appendPath(image_title)
                    .appendPath(original_title)
                    .appendPath(String.valueOf(vote_average))
                    .appendPath(overview)
                    .appendPath(release_date)
                    .appendPath(backdropPath)
                    .build();
        }

        public static String getMovieUrlFromUri(Uri mUri) {
            return mUri.getPathSegments().get(1);
        }

        public static String getTitleFromUri(Uri mUri) {
            return mUri.getPathSegments().get(2);
        }

//        public static String getOriginalTitleFromUri(Uri mUri) {
//            return mUri.getPathSegments().get(3);
//        }

        public static String getVoteAverageFromUri(Uri mUri) {
            return mUri.getPathSegments().get(4);
        }

        public static String getOverviewFromUri(Uri mUri) {
            return mUri.getPathSegments().get(5);
        }

        public static String getReleaseDateFromUri(Uri mUri) {
            return mUri.getPathSegments().get(6);
        }

        public static String getBackdropPathFromUri(Uri mUri) {
            return mUri.getPathSegments().get(7);
        }
    }
}
