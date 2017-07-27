package com.example.android.popularmovies.live;

import android.util.Log;

import com.example.android.popularmovies.data.Contract;
import com.example.android.popularmovies.data.LocalStore;
import com.example.android.popularmovies.infrastructure.PopularMoviesApplication;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.model.ParentModel;
import com.example.android.popularmovies.service.MovieService;
import com.squareup.otto.Subscribe;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class LiveMovieServices extends BaseLiveService {
    private static final String LOG_TAG = LiveMovieServices.class.getSimpleName();
    private final LocalStore localStore = new LocalStore(application);

    LiveMovieServices(PopularMoviesApplication application, MovieWebServices api) {
        super(application, api);
    }

    @Subscribe
    public void getMovieMessage(final MovieService.MovieServerRequest request){
        Log.w(LOG_TAG, "getMovieMessage: " + request.param);
        Call<ParentModel> call = api.getMovies(request.param, PopularMoviesApplication.API_KEY);
        call.enqueue(new Callback<ParentModel>() {
            @Override
            public void onResponse(Call<ParentModel> call, Response<ParentModel> response) {
                if (response.isSuccessful()){
                    application.getContentResolver().delete(Contract.MovieEntry.CONTENT_URI, null, null);

                    for (Movie movie : response.body().movieInfo) {
                        Log.w(LOG_TAG, "onResponse: " + movie.getTitle());
                        localStore.storeMovieData(movie);

                    }
                }
            }

            @Override
            public void onFailure(Call<ParentModel> call, Throwable t) {
                Log.w(LOG_TAG, "onFailure: " + t.getMessage());

            }
        });


    }

}
