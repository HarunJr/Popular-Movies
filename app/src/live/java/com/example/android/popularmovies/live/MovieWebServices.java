package com.example.android.popularmovies.live;

import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.model.Review;
import com.example.android.popularmovies.model.Trailer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface MovieWebServices {
    String MOVIES_ENDPOINT = "3/movie/{parameter}";
    String TRAILER_REVIEW_ENDPOINT = MOVIES_ENDPOINT+"/{parameter2}";

    @GET(MOVIES_ENDPOINT)
    Call<Movie> getMovies(@Path("parameter") String requestType,
                          @Query("api_key") String apiKey);

    @GET(TRAILER_REVIEW_ENDPOINT)
    Call<Trailer> getTrailer (@Path("parameter")  String id,
                              @Path("parameter2") String requestType,
                              @Query("api_key") String apiKey);

    @GET(TRAILER_REVIEW_ENDPOINT)
    Call<Review> getReview (@Path("parameter")  String id,
                            @Path("parameter2") String requestType,
                            @Query("api_key") String apiKey);
}
