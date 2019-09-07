package com.example.android.popularmovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.activities.MovieDetailsActivity;
import com.example.android.popularmovies.infrastructure.PopularMoviesApplication;
import com.example.android.popularmovies.model.Trailer;
import com.squareup.picasso.Picasso;

/**
 * Created by HARUN on 8/7/2017.
 */

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailersViewHolder> {
    private static final String LOG_TAG = TrailersAdapter.class.getSimpleName();
    final private TrailersAdapterOnClickHandler mClickHandler;
    private final Context mContext;
    private Cursor mCursor;
    private static final int TYPE_TRAILER = 1;

    public TrailersAdapter(Context context, TrailersAdapterOnClickHandler trailersAdapterOnClickHandler) {
        this.mContext = context;
        this.mClickHandler = trailersAdapterOnClickHandler;
        Log.w(LOG_TAG, "TrailersAdapter: " + context);
    }

    @Override
    public TrailersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item, parent, false);
        view.setFocusable(true);
        return new TrailersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailersViewHolder holder, int position) {
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
        return TYPE_TRAILER;
    }

    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
//        mEmptyView.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    public class TrailersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView imageView;

        TrailersViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.trailer_image_item);
            itemView.setOnClickListener(this);
            Log.w(LOG_TAG, "TrailersViewHolder: ");
        }

        void bind(int position) {

            Trailer trailer = getTrailerData(position);
            Log.w(LOG_TAG, "bind: " + trailer.getId() + "\n" + trailer.getThumbnail_url());

            if (imageView != null) {
                Picasso.get()
                        .load(trailer.getThumbnail_url()).
                        error(android.R.drawable.stat_notify_error)
                        .placeholder(R.mipmap.ic_image_black_36dp)
                        .fit().centerCrop()
                        .into(imageView);
            }
        }

        private Trailer getTrailerData(int position) {
            mCursor.moveToPosition(position);

            String id = mCursor.getString(MovieDetailsActivity.COL_TRAILER_ID);
            String trailer_key = mCursor.getString(MovieDetailsActivity.COL_KEY);
            String trailer_name = mCursor.getString(MovieDetailsActivity.COL_NAME);

            String thumbnail_url = PopularMoviesApplication.BASE_PICTURE_YOU_TUBE_URL.concat(trailer_key).concat("/hqdefault.jpg");

            return new Trailer(id, trailer_key, trailer_name, thumbnail_url);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Trailer trailer = getTrailerData(adapterPosition);

            Log.w(LOG_TAG, "onClick: " + trailer.getName());
            mClickHandler.onClick(trailer.getKey(), this);
        }
    }

    public interface TrailersAdapterOnClickHandler {
        void onClick(String key, TrailersViewHolder vh);
    }
}
