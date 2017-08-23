package com.example.android.popularmovies.service;

import android.util.Log;

public class MovieService {
    private static final String LOG_TAG = MovieService.class.getSimpleName();

    private MovieService(){
        Log.w(LOG_TAG, "MovieService: ");

    }

    public static class MovieServerRequest{
        public final String param;

        public MovieServerRequest(String parameter){
            this.param = parameter;
            Log.w(LOG_TAG, "MovieServerRequest: " + parameter);

        }
    }

    public static class TrailerServerRequest{
        public final String id;
        public final String videos;

        public TrailerServerRequest(String id, String videos){
            this.id = id;
            this.videos = videos;
            Log.w(LOG_TAG, "TrailerServerRequest: " + id);
        }
    }

    public static class ReviewServerRequest{
        public final String id;
        public final String reviews;

        public ReviewServerRequest(String id, String reviews){
            this.id = id;
            this.reviews = reviews;
            Log.w(LOG_TAG, "ReviewServerRequest: " + id);
        }
    }

}
