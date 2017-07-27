package com.example.android.popularmovies.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.Toast;

import com.example.android.popularmovies.MoviesAdapter;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.Contract.MovieEntry;
import com.example.android.popularmovies.infrastructure.PopularMoviesApplication;
import com.example.android.popularmovies.service.MovieService;

public class MainActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int MOVIE_LOADER = 0;
    private MoviesAdapter moviesAdapter;
    private Spinner mSpinner;

    private static final String[] MOVIE_COLUMN = {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initNetworkConnection("popular");
        declareViews();
        getSupportLoaderManager().initLoader(MOVIE_LOADER, null, this);
//        addPopularMoviesFragment();
    }

//    private void addPopularMoviesFragment(){
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.movies_fragment_container, new PopularMoviesFragment())
//                .addToBackStack(null)
//                .commit();
//    }

    private void declareViews() {
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
                switch (items){
                    case "most popular":
                        Log.i(LOG_TAG,"most popular Selected : " +items);
                        initNetworkConnection("popular");
                        break;
                    case "highest rated":
                        Log.i(LOG_TAG,"highest rated Selected : "+ items);
                        initNetworkConnection("top_rated");
//                        bus.post(new MovieService.MovieServerRequest("top_rated"));
                        getSupportLoaderManager().restartLoader(MOVIE_LOADER, null, MainActivity.this);
                        break;
                    default:
                        Log.i(LOG_TAG,"most popular Selected : "+ items);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                Log.i(LOG_TAG,"onNothingSelected Selected : ");
            }
        });

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_movies);
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setHasFixedSize(true);

        moviesAdapter = new MoviesAdapter(this, new MoviesAdapter.MoviesAdapterOnClickHandler() {
            @Override
            public void onClick(Uri uri, MoviesAdapter.MoviesViewHolder vh) {
                Log.w(LOG_TAG, "onClick: "+ uri);
                startActivity(new Intent(getApplicationContext(), MovieDetailsActivity.class)
                        .setData(uri));
            }
        });
        mRecyclerView.setAdapter(moviesAdapter);
    }

    private void initNetworkConnection(String query){
        if (isNetworkAvailable(application)){
            bus.post(new MovieService.MovieServerRequest(query));

        }else {
            Toast.makeText(application, "No Internet Connection! ", Toast.LENGTH_LONG).show();
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

    private static boolean isNetworkAvailable(PopularMoviesApplication application) {
        ConnectivityManager cm = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        Log.w(LOG_TAG, "isNetworkAvailable" + networkInfo);
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

}
