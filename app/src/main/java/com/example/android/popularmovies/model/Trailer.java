package com.example.android.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by HARUN on 8/7/2017.
 */

public class Trailer {
    @SerializedName("id")
    private final String id;

    @SerializedName("key")
    private final String key;

    @SerializedName("name")
    private final String name;

    private final String thumbnail_url;

    @SerializedName("results")
    private List<Trailer> trailers;

    public Trailer(String id, String trailer_key, String trailer_name, String thumbnail_url) {
        this.id = id;
        this.key = trailer_key;
        this.name = trailer_name;
        this.thumbnail_url = thumbnail_url;
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public List<Trailer> getTrailers() {
        return trailers;
    }

}
