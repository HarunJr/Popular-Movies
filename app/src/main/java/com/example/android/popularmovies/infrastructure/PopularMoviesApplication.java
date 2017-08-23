package com.example.android.popularmovies.infrastructure;

import android.app.Application;

import com.example.android.popularmovies.live.Module;
import com.squareup.otto.Bus;

public class PopularMoviesApplication extends Application {
    public static final String BASE_URL = "https://api.themoviedb.org/";
    public static final String BASE_PICTURE_URL = "http://image.tmdb.org/t/p/w185/";
    public static final String BASE_PICTURE_YOU_TUBE_URL = "http://img.youtube.com/vi/";
    public static final String BASE_VIDEO_YOU_TUBE_URL = "https://www.youtube.com/watch?v=";
    public static final String API_KEY = "605c285cd4ce04f8dda29304b02367ea";

    private final Bus bus;

    public PopularMoviesApplication(){
        bus = new Bus();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Module.Register(this);
    }

    public Bus getBus() {
        return bus;
    }

}
