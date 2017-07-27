package com.example.android.popularmovies.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.Contract;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {
    private static final String LOG_TAG = MovieDetailsActivity.class.getSimpleName();

    private String imageUrl;
    private String imageTitle;
    private String releaseDate;
    private String averageRating;
    private String overview;
    private String backdropPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        getDataFromMainActivity();
        initViews();
    }

    private void getDataFromMainActivity() {
        //get vehicle value from uri path
        Intent intent = getIntent();
        Uri mUri = intent.getData();
        imageUrl = Contract.MovieEntry.getMovieUrlFromUri(mUri);
        imageTitle = Contract.MovieEntry.getTitleFromUri(mUri);
        releaseDate = Contract.MovieEntry.getReleaseDateFromUri(mUri);
        averageRating = Contract.MovieEntry.getVoteAverageFromUri(mUri);
        overview = Contract.MovieEntry.getOverviewFromUri(mUri);
        backdropPath = Contract.MovieEntry.getBackdropPathFromUri(mUri);
        Log.w(LOG_TAG, "getDataFromMainActivity " + "intent" + "---" + backdropPath);
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.details_activity_toolbar);
        setSupportActionBar(mToolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle(imageTitle);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(imageTitle);

        ImageView imageView = (ImageView) findViewById(R.id.movie_image);
        ImageView imageViewDetail = (ImageView) findViewById(R.id.movie_image_detail);
        TextView tvImageTitle = (TextView) findViewById(R.id.tv_image_details_title);
        TextView tvReleaseDate = (TextView) findViewById(R.id.tv_release_date);
        TextView tvAverageRating = (TextView) findViewById(R.id.tv_average_rating);
        TextView tvOverview = (TextView) findViewById(R.id.tv_overview);

        loadImage(backdropPath, imageView);
        loadImage(imageUrl, imageViewDetail);

        String dateTitle = getResources().getString(R.string.release_date)+ ": "+releaseDate;
        String ratingTitle = getResources().getString(R.string.average_rating)+ ": "+averageRating + "/10";
        tvImageTitle.setText(imageTitle);
        tvReleaseDate.setText(dateTitle);
        tvAverageRating.setText(ratingTitle);
        tvOverview.setText(overview);
    }

    private void loadImage(String url, ImageView imageView){

        Picasso.with(this)
                .load(url)
                .error(android.R.drawable.stat_notify_error)
                .placeholder(R.mipmap.ic_image_black_24dp)
                .fit()
                .into(imageView);
    }
}
