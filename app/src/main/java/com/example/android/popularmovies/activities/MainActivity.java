package com.example.android.popularmovies.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.adapters.MoviesAdapter;
import com.example.android.popularmovies.data.Contract;
import com.example.android.popularmovies.data.Contract.FavouriteEntry;
import com.example.android.popularmovies.data.Contract.MovieEntry;
import com.example.android.popularmovies.data.LocalStore;
import com.example.android.popularmovies.model.Movie;

import static com.example.android.popularmovies.activities.MovieDetailsActivity.MOVIE_FAV_KEY;
import static com.example.android.popularmovies.utilities.NetworkUtils.initNetworkConnection;

public class MainActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String SCROLL_POSITION_KEY = "scroll_position";
    private static final String RECYCLERVIEW_STATE_KEY = "rv_state";
    private static final String POPULAR = "popular";
    private static final String TOP_RATED = "top_rated";
    private static final int MOVIE_LOADER = 0;
    private MoviesAdapter moviesAdapter;
    private Spinner mSpinner;
    RecyclerView mRecyclerView;
    Parcelable mRecyclerViewState;
    GridLayoutManager gridLayoutManager;
    int mScrollPosition = RecyclerView.NO_POSITION;

    public static final String[] MOVIE_COLUMN = {
            MovieEntry.TABLE_NAME + "." + MovieEntry.COLUMN_MOVIE_ID,
            MovieEntry.COLUMN_POSTER_PATH,
            MovieEntry.COLUMN_MOVIE_TITLE,
            MovieEntry.COLUMN_ORIGINAL_TITLE,
            MovieEntry.COLUMN_MOVIE_POPULARITY,
            MovieEntry.COLUMN_VOTE_AVERAGE,
            MovieEntry.COLUMN_OVERVIEW,
            MovieEntry.RELEASE_DATE,
            MovieEntry.BACKDROP_PATH,
    };
    // These indices are tied to MOVIE_COLUMN.  If MOVIE_COLUMN changes, these must change
    public static final int COL_ID = 0;
    public static final int COL_PATH = 1;
    public static final int COL_TITLE = 2;
    public static final int COL_ORIGINAL_TITLE = 3;
    public static final int COL_POPULARITY = 4;
    public static final int COL_VOTE_AVERAGE = 5;
    public static final int COL_OVERVIEW = 6;
    public static final int COL_RELEASE_DATE = 7;
    public static final int COL_BACKDROP_PATH = 8;

    public static final String[] FAVOURITE_COLUMN = {
            FavouriteEntry.TABLE_NAME + "." + FavouriteEntry.COLUMN_FAV_ID,
            FavouriteEntry.COLUMN_POSTER_PATH,
            FavouriteEntry.COLUMN_FAV_TITLE,
            FavouriteEntry.COLUMN_VOTE_AVERAGE,
            FavouriteEntry.COLUMN_OVERVIEW,
            FavouriteEntry.RELEASE_DATE,
            FavouriteEntry.BACKDROP_PATH,};

    public static final int COL_FAV_ID = 0;
    public static final int COL_FAV_PATH = 1;
    public static final int COL_FAV_TITLE = 2;
    public static final int COL_FAV_VOTE_AVERAGE = 3;
    public static final int COL_FAV_OVERVIEW = 4;
    public static final int COL_FAV_RELEASE_DATE = 5;
    public static final int COL_FAV_BACKDROP_PATH = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null){
            Log.w(LOG_TAG, "savedInstanceState: " );
            initNetworkConnection(POPULAR, application, this);
        }else {
            Log.w(LOG_TAG, "savedInstanceStateNOTNULL: " + savedInstanceState);
        }
        initViews();
        getSupportLoaderManager().initLoader(MOVIE_LOADER, null, this);
//        addPopularMoviesFragment();
    }

//    private void addPopularMoviesFragment(){
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.movies_fragment_container, new PopularMoviesFragment())
//                .addToBackStack(null)
//                .commit();
//    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.main_activity_toolbar);
        mSpinner = (Spinner) findViewById(R.id.spinner);
        setSupportActionBar(mToolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.custom_spinner,
                getResources().getStringArray(R.array.spinner_items));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String items = mSpinner.getSelectedItem().toString();
                selectSpinnerOptions(items);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                Log.i(LOG_TAG,"onNothingSelected Selected : ");
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_movies);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            gridLayoutManager = new GridLayoutManager(this, 2);
            mRecyclerView.setLayoutManager(gridLayoutManager);
        } else {
            gridLayoutManager = new GridLayoutManager(this, 4);
            mRecyclerView.setLayoutManager(gridLayoutManager);
        }

        mRecyclerView.setHasFixedSize(true);
        moviesAdapter = new MoviesAdapter(this, new MoviesAdapter.MoviesAdapterOnClickHandler() {
            @Override
            public void onClick(Movie movie, MoviesAdapter.MoviesViewHolder vh) {
                Log.w(LOG_TAG, "onClick: " + movie.getBackdropPath());
                startActivity(new Intent(getApplicationContext(), MovieDetailsActivity.class)
                        .putExtra(MOVIE_FAV_KEY, movie));
            }
        });
        mRecyclerView.setAdapter(moviesAdapter);
    }

    private void selectSpinnerOptions(String items) {
        switch (items) {
            case "most popular":
                Log.i(LOG_TAG, "most popular Selected : " + items);
                initNetworkConnection(POPULAR, application, getApplicationContext());
                break;
            case "highest rated":
                Log.i(LOG_TAG, "highest rated Selected : " + items);
                initNetworkConnection(TOP_RATED, application, getApplicationContext());
                getSupportLoaderManager().restartLoader(MOVIE_LOADER, null, MainActivity.this);
                break;
            case "favourite":
                Log.i(LOG_TAG, "favourite Selected : " + items);
                LocalStore localStore = new LocalStore(application);
                application.getContentResolver().delete(Contract.MovieEntry.CONTENT_URI, null, null);
                localStore.getFavMovies();
                getSupportLoaderManager().restartLoader(MOVIE_LOADER, null, MainActivity.this);
                break;
            default:
                Log.i(LOG_TAG, "most popular Selected : " + items);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mRecyclerViewState = mRecyclerView.getLayoutManager().onSaveInstanceState();
        gridLayoutManager = (GridLayoutManager) mRecyclerView.getLayoutManager();
        if (gridLayoutManager != null){
            mScrollPosition = gridLayoutManager.findFirstVisibleItemPosition();
            outState.putInt(SCROLL_POSITION_KEY, mScrollPosition);
            outState.putParcelable(RECYCLERVIEW_STATE_KEY, mRecyclerViewState);
            Log.w(LOG_TAG, "state onSaveInstanceState: mScrollPosition "+mScrollPosition);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.containsKey(SCROLL_POSITION_KEY)){
            mRecyclerViewState = savedInstanceState.getParcelable(RECYCLERVIEW_STATE_KEY);
            mScrollPosition = savedInstanceState.getInt(SCROLL_POSITION_KEY);
            mRecyclerView.scrollToPosition(mScrollPosition);
            Log.w(LOG_TAG, "state onRestoreInstanceState: "+mScrollPosition);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mRecyclerViewState != null){
            mRecyclerView.getLayoutManager().onRestoreInstanceState(mRecyclerViewState);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.w(LOG_TAG, "onCreateLoader: ");

        return new CursorLoader(
                this,
                MovieEntry.CONTENT_URI,
                MOVIE_COLUMN,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor data) {

        moviesAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        moviesAdapter.swapCursor(null);
    }

}
