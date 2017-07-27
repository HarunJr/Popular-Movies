package com.example.android.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ParentModel {

    @SerializedName("results")
    public List<Movie> movieInfo;

}
