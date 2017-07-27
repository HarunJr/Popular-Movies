package com.example.android.popularmovies.live;

import android.util.Log;

import com.example.android.popularmovies.infrastructure.PopularMoviesApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Module {
    private static final String LOG_TAG = Module.class.getSimpleName();

    public static void Register(PopularMoviesApplication application){
        new LiveMovieServices(application, createMovieService());
    }

    private static MovieWebServices createMovieService(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PopularMoviesApplication.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Log.w(LOG_TAG, "MovieWebServices " + retrofit.create(MovieWebServices.class));

        return retrofit.create(MovieWebServices.class);
    }
}
