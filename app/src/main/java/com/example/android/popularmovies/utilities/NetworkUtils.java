package com.example.android.popularmovies.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.infrastructure.PopularMoviesApplication;
import com.example.android.popularmovies.service.MovieService;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

/**
 * Created by HARUN on 8/4/2017.
 */

public class NetworkUtils {
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    public static void initNetworkConnection(String query, PopularMoviesApplication application, Context context){
        if (isNetworkAvailable(application)){
            if (query.equals("popular") || query.equals("top_rated")){
                application.getBus().post(new MovieService.MovieServerRequest(query));

            } else {
                application.getBus().post(new MovieService.TrailerServerRequest(query, "videos"));
                application.getBus().post(new MovieService.ReviewServerRequest(query, "reviews"));
            }

        }else {
            Toast.makeText(application, "No Internet Connection! ", Toast.LENGTH_LONG).show();
        }
    }

    public static boolean isNetworkAvailable(Context application) {
        ConnectivityManager cm = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        Log.w(LOG_TAG, "isNetworkAvailable" + networkInfo);
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public static void loadImage(String url, ImageView imageView, Context mContext) {
        Log.w(LOG_TAG, "loadImage " + url);

        Picasso.with(mContext)
                .load(PopularMoviesApplication.BASE_PICTURE_URL+url)
                .networkPolicy(isNetworkAvailable(mContext)? NetworkPolicy.NO_CACHE : NetworkPolicy.OFFLINE)
                .error(android.R.drawable.stat_notify_error)
                .placeholder(R.mipmap.ic_image_black_36dp)
                .fit()
                .into(imageView);
    }
}
