package com.example.android.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie implements Parcelable{
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

    @SerializedName("results")
    private List<Movie> movieInfo;

    private boolean favourite;

    public Movie(int id, String image_url, String image_title, String original_title, double vote_average,
                 String overview, String release_date, String backdrop_path, boolean favourite) {
        this.id =id;
        this.posterPath = image_url;
        this.title = image_title;
        this.originalTitle = original_title;
        this.voteAverage = vote_average;
        this.overview = overview;
        this.releaseDate = release_date;
        this.backdropPath = backdrop_path;
        this.favourite = favourite;
    }

    public Movie(int id, String image_url, String image_title, double vote_average,
                 String overview, String release_date, String backdrop_path) {
        this(id,image_url,image_title,"",vote_average,overview,release_date,backdrop_path,true);
    }

    private Movie(Parcel source) {
        this(source.readInt(),source.readString(),source.readString(),source.readString(),source.readDouble()
                ,source.readString(),source.readString(),source.readString(), source.readInt() == 1);
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

    public List<Movie> getMovieInfo() {
        return movieInfo;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }
    public boolean getFavourite() {
        return favourite;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(posterPath);
        dest.writeString(title);
        dest.writeString(originalTitle);
        dest.writeDouble(voteAverage);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString(backdropPath);
        dest.writeInt(favourite ? 1:0);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[0];
        }
    };


}
