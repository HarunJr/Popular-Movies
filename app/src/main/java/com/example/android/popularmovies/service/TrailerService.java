package com.example.android.popularmovies.service;

import android.util.Log;

/**
 * Created by HARUN on 8/6/2017.
 */

public class TrailerService {
    private static final String LOG_TAG = TrailerService.class.getSimpleName();

    private TrailerService(){
        Log.w(LOG_TAG, "MovieService: ");
    }

    public static class TrailerServerRequest{
        public final int param;

        public TrailerServerRequest(int parameter){
            this.param = parameter;
            Log.w(LOG_TAG, "TrailerServerRequest: " + parameter);
        }
    }

}
