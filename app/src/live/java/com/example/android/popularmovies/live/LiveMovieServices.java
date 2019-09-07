package com.example.android.popularmovies.live;

import android.util.Log;

import com.example.android.popularmovies.data.Contract;
import com.example.android.popularmovies.data.LocalStore;
import com.example.android.popularmovies.infrastructure.PopularMoviesApplication;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.model.Review;
import com.example.android.popularmovies.model.Trailer;
import com.example.android.popularmovies.service.MovieService;
import com.squareup.otto.Subscribe;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.android.popularmovies.data.Contract.*;

class LiveMovieServices extends BaseLiveService {
    private static final String LOG_TAG = LiveMovieServices.class.getSimpleName();
    private final LocalStore localStore = new LocalStore(application);

    LiveMovieServices(PopularMoviesApplication application, MovieWebServices api) {
        super(application, api);
    }

    @Subscribe
    public void getMovieMessage(final MovieService.MovieServerRequest request) {
        Log.w(LOG_TAG, "getMovieMessage: " + request.param);

        Call<Movie> call = api.getMovies(request.param, PopularMoviesApplication.API_KEY);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    if (request.param.equals("popular") || request.param.equals("top_rated")) {

//                        String sMovieWithFavAndIdSelection = MovieEntry.TABLE_NAME + "." + MovieEntry.COLUMN_FAVOURITE + " != ?";
//                        String[] selectionArgs = new String[]{String.valueOf(1)};
                        application.getContentResolver().delete(MovieEntry.CONTENT_URI, null, null);

                        for (Movie movie : response.body().getMovieInfo()) {
                            Log.w(LOG_TAG, "onResponse: " + request.param + " " + movie.getTitle());
                            localStore.storeMovieData(movie);
                        }

                    } else {
                        Log.w(LOG_TAG, "onResponse: " + request.param + " " + response.body().getTitle());
                    }
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.w(LOG_TAG, "onFailure: " + t.getMessage());

            }
        });
    }

    @Subscribe
    public void getTrailerMessage(final MovieService.TrailerServerRequest request) {
        Log.w(LOG_TAG, "getTrailerMessage: " + request.id);
        Call<Trailer> call = api.getTrailer(request.id, request.videos, PopularMoviesApplication.API_KEY);
        call.enqueue(new Callback<Trailer>() {
            @Override
            public void onResponse(Call<Trailer> call, Response<Trailer> response) {
                if (response.isSuccessful()) {
                    application.getContentResolver().delete(TrailerEntry.CONTENT_URI, null, null);
                    for (Trailer trailer : response.body().getTrailers()) {
                        Log.w(LOG_TAG, "onResponse: videos " + request.id + " " + trailer.getName());
                        localStore.storeTrailerData(trailer);
                    }
                }
            }

            @Override
            public void onFailure(Call<Trailer> call, Throwable t) {
                Log.w(LOG_TAG, "onFailure: " + t.getMessage());

            }
        });
    }

    @Subscribe
    public void getReviewMessage(final MovieService.ReviewServerRequest request) {
        Log.w(LOG_TAG, "getReviewMessage: " + request.id);
        Call<Review> call = api.getReview(request.id, request.reviews, PopularMoviesApplication.API_KEY);
        call.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                if (response.isSuccessful()) {
                    application.getContentResolver().delete(ReviewEntry.CONTENT_URI, null, null);
                    for (Review review : response.body().getReviews()) {
                        Log.w(LOG_TAG, "onResponse: reviews " + request.id + " " + review.getAuthor());
                        localStore.storeReviewsData(review);
                    }
                }
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                Log.w(LOG_TAG, "onFailure: " + t.getMessage());

            }
        });
    }
}
