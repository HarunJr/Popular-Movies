package com.example.android.popularmovies.live;

import com.example.android.popularmovies.model.ParentModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface MovieWebServices {
    String MOVIES_ENDPOINT = "3/movie/{parameter}";

    @GET(MOVIES_ENDPOINT)
    Call<ParentModel> getMovies(@Path("parameter") String requestType,
                              @Query("api_key") String apiKey);

}
