package com.example.android.popularmovies.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.popularmovies.infrastructure.PopularMoviesApplication;
import com.squareup.otto.Bus;

public class BaseActivity extends AppCompatActivity {
    PopularMoviesApplication application;
    Bus bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (PopularMoviesApplication) getApplication();
        bus = application.getBus();
        bus.register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }
}
