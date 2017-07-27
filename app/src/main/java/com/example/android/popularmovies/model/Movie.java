package com.example.android.popularmovies.model;

import com.google.gson.annotations.SerializedName;

public class Movie {
    @SerializedName("id")
    private final int id;

    @SerializedName("poster_path")
    private final String posterPath;

    @SerializedName("original_title")
    private final String originalTitle;

    @SerializedName("title")
    private final String title;

    @SerializedName("vote_average")
    private final double voteAverage;

    @SerializedName("popularity")
    private String popularity;

    @SerializedName("overview")

    private final String overview;

    @SerializedName("backdrop_path")
    private final String backdropPath;

    @SerializedName("release_date")
    private final String releaseDate;

    public Movie(int id, String image_url, String image_title, String original_title, double vote_average,
                 String overview, String release_date, String backdrop_path) {
        this.id =id;
        this.posterPath = image_url;
        this.title = image_title;
        this.originalTitle = original_title;
        this.voteAverage = vote_average;
        this.overview = overview;
        this.releaseDate = release_date;
        this.backdropPath = backdrop_path;
    }

    public int getId() {
        return id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

}
