package com.example.android.popularmovies.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.adapters.ReviewsAdapter;
import com.example.android.popularmovies.adapters.TrailersAdapter;
import com.example.android.popularmovies.data.Contract.ReviewEntry;
import com.example.android.popularmovies.data.Contract.TrailerEntry;
import com.example.android.popularmovies.data.LocalStore;
import com.example.android.popularmovies.infrastructure.PopularMoviesApplication;
import com.example.android.popularmovies.model.Movie;

import static com.example.android.popularmovies.utilities.NetworkUtils.initNetworkConnection;
import static com.example.android.popularmovies.utilities.NetworkUtils.loadImage;

public class MovieDetailsActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String LOG_TAG = MovieDetailsActivity.class.getSimpleName();
    private static final int TRAILER_LOADER = 1;
    private static final int REVIEW_LOADER = 2;
    public static final String MOVIE_FAV_KEY = "movie";

    private static boolean isFavourite = true;

    private Menu menu;

    private TrailersAdapter trailerAdapter;
    private ReviewsAdapter reviewsAdapter;
    RecyclerView mRecyclerViewTrailer;
    RecyclerView mRecyclerViewReview;

    private static final String[] TRAILER_COLUMN = {
            TrailerEntry.TABLE_NAME + "." + TrailerEntry.COLUMN_TRAILER_ID,
            TrailerEntry.COLUMN_TRAILER_KEY,
            TrailerEntry.COLUMN_TRAILER_NAME,

    };

    // These indices are tied to TRAILER_COLUMN.  If TRAILER_COLUMN changes, these must change
    public static final int COL_TRAILER_ID = 0;
    public static final int COL_KEY = 1;
    public static final int COL_NAME = 2;

    private static final String[] REVIEW_COLUMN = {
            ReviewEntry.TABLE_NAME + "." + TrailerEntry.COLUMN_TRAILER_ID,
            ReviewEntry.COLUMN_REVIEW_AUTHOR,
            ReviewEntry.COLUMN_REVIEW_CONTENT,
            ReviewEntry.COLUMN_REVIEW_URL,

    };

    public static final int COL_REVIEW_ID = 0;
    public static final int COL_AUTHOR = 1;
    public static final int COL_CONTENT = 2;
    public static final int COL_URL = 3;

    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        getDataFromMainActivity();
        initNetworkConnection(String.valueOf(movie.getId()), application, this);
        initViews();
        getSupportLoaderManager().initLoader(TRAILER_LOADER, null, this);
        getSupportLoaderManager().initLoader(REVIEW_LOADER, null, this);
    }

    private void getDataFromMainActivity() {
        //get vehicle value from uri path
        Intent intent = getIntent();
        if (intent != null) {
            movie = intent.getParcelableExtra(MOVIE_FAV_KEY);
            Log.w(LOG_TAG, " getDataFromMainActivity favourite" + "\n" + movie.getId() + "\n" + movie.getTitle() + "\n"
                    + movie.getPosterPath() + "\n" + movie.getVoteAverage() + "\n" + movie.getReleaseDate() + "\n"
                    + movie.getBackdropPath());
        }
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.details_activity_toolbar);
        setSupportActionBar(mToolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle(imageTitle);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(movie.getTitle());

        ImageView imageView = (ImageView) findViewById(R.id.movie_image);
        ImageView imageViewDetail = (ImageView) findViewById(R.id.movie_image_detail);
        TextView tvImageTitle = (TextView) findViewById(R.id.tv_image_details_title);
        TextView tvReleaseDate = (TextView) findViewById(R.id.tv_release_date);
        TextView tvAverageRating = (TextView) findViewById(R.id.tv_average_rating);
        TextView tvOverview = (TextView) findViewById(R.id.tv_overview);
        mRecyclerViewTrailer = (RecyclerView) findViewById(R.id.recyclerView_trailers);
        mRecyclerViewReview = (RecyclerView) findViewById(R.id.recyclerView_reviews);

        loadImage(movie.getBackdropPath(), imageView, this);
        loadImage(movie.getPosterPath(), imageViewDetail, this);

        String dateTitle = getResources().getString(R.string.release_date) + ": " + movie.getReleaseDate();
        String ratingTitle = getResources().getString(R.string.average_rating) + ": " + movie.getVoteAverage() + "/10";
        tvImageTitle.setText(movie.getTitle());
        tvReleaseDate.setText(dateTitle);
        tvAverageRating.setText(ratingTitle);
        tvOverview.setText(movie.getOverview());

        setAdapterToRecyclerview();
    }

    private void setAdapterToRecyclerview() {
        mRecyclerViewTrailer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerViewTrailer.setNestedScrollingEnabled(false);
        mRecyclerViewTrailer.setHasFixedSize(true);
        trailerAdapter = new TrailersAdapter(this, new TrailersAdapter.TrailersAdapterOnClickHandler() {
            @Override
            public void onClick(String key, TrailersAdapter.TrailersViewHolder vh) {
                Log.w(LOG_TAG, "onClick: " + key);
                String url = PopularMoviesApplication.BASE_VIDEO_YOU_TUBE_URL.concat(key);
                Uri webPage = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);

                }
            }

//            @Override
//            public void onClick(Uri uri, TrailersAdapter.TrailersViewHolder vh) {
//                Log.w(LOG_TAG, "onClick: "+ uri);
//                Intent i = new Intent(Intent.ACTION_VIEW, uri);
//                i.setData(uri);
//                startActivity(i);
//            }
        });
        mRecyclerViewTrailer.setAdapter(trailerAdapter);

        mRecyclerViewReview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewReview.setNestedScrollingEnabled(false);
        reviewsAdapter = new ReviewsAdapter();
        mRecyclerViewReview.setAdapter(reviewsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        isFavourite = movie.getFavourite();
        getMenuInflater().inflate(R.menu.details_menu, menu);
        menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_action_fav));

        if (isFavourite) {
            setImageAsFavourite();
        } else {
            unFavouriteImage();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_fav:
                Log.w(LOG_TAG, "action_fav: ");

                setFavouriteState();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setFavouriteState() {
        Log.w(LOG_TAG, "isFavourite: " + movie.getTitle() + " " + isFavourite);
        LocalStore localStore = new LocalStore(application);

        if (!isFavourite) {
            localStore.storeFavMovieData(movie);
            setImageAsFavourite();
        } else {
            localStore.deleteFavMovieData(application, movie);
            unFavouriteImage();
        }
    }

    private void setImageAsFavourite() {
        menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_action_fav_fill));
        movie.setFavourite(true);
        Log.w(LOG_TAG, "setImageAsFavourite: ");
    }

    private void unFavouriteImage() {
        menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_action_fav));
        movie.setFavourite(false);
        Log.w(LOG_TAG, "unFavouriteImage: ");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(MOVIE_FAV_KEY, isFavourite);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(MOVIE_FAV_KEY)){
            isFavourite = savedInstanceState.getBoolean(MOVIE_FAV_KEY);
            Log.w(LOG_TAG, "state onRestoreInstanceState: "+isFavourite);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case TRAILER_LOADER: {
                Log.w(LOG_TAG, "onCreateLoader: TRAILER_LOADER");
                return new CursorLoader(
                        this,
                        TrailerEntry.CONTENT_URI,
                        TRAILER_COLUMN,
                        null,
                        null,
                        null
                );
            }
            case REVIEW_LOADER: {
                Log.w(LOG_TAG, "onCreateLoader: REVIEW_LOADER");
                return new CursorLoader(
                        this,
                        ReviewEntry.CONTENT_URI,
                        REVIEW_COLUMN,
                        null,
                        null,
                        null
                );
            }
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == TRAILER_LOADER) {
            Log.w(LOG_TAG, "onLoadFinished: TRAILER_LOADER");
            trailerAdapter.swapCursor(data);
        } else {
            Log.w(LOG_TAG, "onLoadFinished: REVIEW_LOADER");
            reviewsAdapter.swapCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (loader.getId() == TRAILER_LOADER) {
            trailerAdapter.swapCursor(null);
        } else {
            reviewsAdapter.swapCursor(null);
        }
    }
}
