package com.example.android.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by HARUN on 8/8/2017.
 */

public class Review {
    @SerializedName("id")
    private final String id;

    @SerializedName("author")
    private final String author;

    @SerializedName("content")
    private final String content;

    @SerializedName("url")
    private final String url;

    @SerializedName("results")
    private List<Review> reviews;

    public Review(String id, String author, String content, String url) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

    public List<Review> getReviews() {
        return reviews;
    }
}
