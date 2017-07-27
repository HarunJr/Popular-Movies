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
}
