package com.example.android.popularmovies.live;

import android.util.Log;

import com.example.android.popularmovies.infrastructure.PopularMoviesApplication;
import com.squareup.otto.Bus;

abstract class BaseLiveService{
    private static final String LOG_TAG = BaseLiveService.class.getSimpleName();
    final PopularMoviesApplication application;
    final MovieWebServices api;

//    public BaseLiveService(PopularMoviesApplication context, boolean autoInitialize, MovieWebServices api) {
//        super(context, autoInitialize);
//        this.application = context;
//        this.api = api;
//        Bus bus = application.getBus();
//        bus.register(this);
//    }

//    public BaseLiveService(PopularMoviesApplication context, boolean autoInitialize, boolean allowParallelSyncs, MovieWebServices api) {
//        super(context, autoInitialize, allowParallelSyncs);
//        this.application = context;
//        this.api = api;
//        Bus bus = application.getBus();
//        bus.register(this);
//    }

    BaseLiveService(PopularMoviesApplication application, MovieWebServices api){
        this.application = application;
        this.api = api;
        Bus bus = application.getBus();
        bus.register(this);
        Log.w(LOG_TAG, "BaseLiveService bus.post" );
    }
}
