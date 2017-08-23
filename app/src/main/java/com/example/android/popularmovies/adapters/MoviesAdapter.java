package com.example.android.popularmovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.activities.MainActivity;
import com.example.android.popularmovies.data.LocalStore;
import com.example.android.popularmovies.model.Movie;

import static com.example.android.popularmovies.utilities.NetworkUtils.loadImage;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {
    private static final String LOG_TAG = MoviesAdapter.class.getSimpleName();
    final private MoviesAdapterOnClickHandler mClickHandler;
    private final Context mContext;
    private Cursor mCursor;
    private static final int TYPE_ITEM = 1;
    Movie movie;
    public MoviesAdapter(Context application, MoviesAdapterOnClickHandler mClickHandler) {
        this.mClickHandler = mClickHandler;
        this.mContext = application;
        Log.w(LOG_TAG, "MoviesAdapter: " + application);
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.w(LOG_TAG, "onCreateViewHolder: ");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movies_item, parent, false);
        view.setFocusable(true);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {
        Log.w(LOG_TAG, "onBindViewHolder: ");

        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        Log.w(LOG_TAG, "getItemCount: " + mCursor.getCount());
        return mCursor.getCount();
    }

    @Override
    public int getItemViewType(int position) {
        Log.w(LOG_TAG, "getItemViewType: " + position);
        return TYPE_ITEM;
    }

    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
//        mEmptyView.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView imageView;
        private final TextView tvTitle;

        MoviesViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.movie_image_item);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_image_title);
            itemView.setOnClickListener(this);
            Log.w(LOG_TAG, "MoviesViewHolder: ");
        }

        void bind(int position) {

            movie = getMovieData(position);
            Log.w(LOG_TAG, "bind: " + movie.getPosterPath() + "\n" + movie.getTitle());

            if (imageView != null) {
                loadImage(movie.getPosterPath(), imageView, mContext);
                tvTitle.setText(movie.getTitle());
            }
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            movie = getMovieData(adapterPosition);

            Log.w(LOG_TAG, "onClick: "+ movie.getBackdropPath());
            mClickHandler.onClick(movie, this);
        }

        private Movie getMovieData(int position){
            mCursor.moveToPosition(position);

            int id = mCursor.getInt(MainActivity.COL_ID);
            String image_url = mCursor.getString(MainActivity.COL_PATH);
            String image_title = mCursor.getString(MainActivity.COL_TITLE);
            String original_title = mCursor.getString(MainActivity.COL_ORIGINAL_TITLE);
            double vote_average = mCursor.getDouble(MainActivity.COL_VOTE_AVERAGE);
            String overview  = mCursor.getString(MainActivity.COL_OVERVIEW);
            String release_date = mCursor.getString(MainActivity.COL_RELEASE_DATE);
            String backdrop_path = mCursor.getString(MainActivity.COL_BACKDROP_PATH);

            LocalStore localStore = new LocalStore(mContext);
            boolean isFavourite = localStore.isFavourite(mContext, id);

            return new Movie(id, image_url, image_title, original_title, vote_average, overview, release_date, backdrop_path, isFavourite);
        }
    }

    public interface MoviesAdapterOnClickHandler {
        void onClick(Movie uri, MoviesViewHolder vh);
    }

}
