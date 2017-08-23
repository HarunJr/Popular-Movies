package com.example.android.popularmovies.adapters;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.activities.MovieDetailsActivity;
import com.example.android.popularmovies.model.Review;

/**
 * Created by HARUN on 8/8/2017.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {
    private static final String LOG_TAG = ReviewsAdapter.class.getSimpleName();
//    private final Context mContext;
    private Cursor mCursor;
    private static final int TYPE_REVIEW = 1;

    public ReviewsAdapter() {
        Log.w(LOG_TAG, "ReviewsAdapter: ");
    }

    @Override
    public ReviewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.w(LOG_TAG, "ReviewsViewHolder ");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        view.setFocusable(true);
        return new ReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewsViewHolder holder, int position) {
        Log.w(LOG_TAG, "onBindViewHolder: ");
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        Log.w(LOG_TAG, "getItemCount: " + mCursor.getCount());
        return mCursor.getCount();    }

    @Override
    public int getItemViewType(int position) {
        Log.w(LOG_TAG, "getItemViewType: " + position);
        return TYPE_REVIEW;
    }


    public void swapCursor(Cursor data) {
        mCursor = data;
        notifyDataSetChanged();
//        mEmptyView.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.GONE);

    }

    public class ReviewsViewHolder extends RecyclerView.ViewHolder {
        private final TextView authorText;
        private final TextView reviewView;

        ReviewsViewHolder(View itemView) {
            super(itemView);
            authorText = (TextView) itemView.findViewById(R.id.author_text);
            reviewView = (TextView) itemView.findViewById(R.id.tv_review);
        }

        void bind(int position) {
            Review review = getReviewData(position);
            Log.w(LOG_TAG, "bind review: "+review.getAuthor());
            authorText.setText(review.getAuthor());
            reviewView.setText(review.getContent());

        }
    }

    private Review getReviewData(int position) {
        mCursor.moveToPosition(position);

        String id = mCursor.getString(MovieDetailsActivity.COL_REVIEW_ID);
        String author = mCursor.getString(MovieDetailsActivity.COL_AUTHOR);
        String content = mCursor.getString(MovieDetailsActivity.COL_CONTENT);
        String url = mCursor.getString(MovieDetailsActivity.COL_URL);

        return new Review(id,author,content,url);
    }
}
