package com.example.android.popularmovies.live;

import android.util.Log;

import com.example.android.popularmovies.infrastructure.PopularMoviesApplication;
import com.squareup.otto.Bus;

class BaseLiveService {
    private static final String LOG_TAG = BaseLiveService.class.getSimpleName();
    final PopularMoviesApplication application;
    final MovieWebServices api;

    BaseLiveService(PopularMoviesApplication application, MovieWebServices api){
        this.application = application;
        this.api = api;
        Bus bus = application.getBus();
        bus.register(this);
        Log.w(LOG_TAG, "BaseLiveService bus.post" );
    }
}
