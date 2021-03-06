package com.example.android.popularmovies.utilities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HARUN on 8/18/2017.
 */

 public class SavedState extends android.view.View.BaseSavedState {
    public int mScrollPosition;
    SavedState(Parcel in) {
        super(in);
        mScrollPosition = in.readInt();
    }
    public SavedState(Parcelable superState) {
        super(superState);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(mScrollPosition);
    }
    public static final Parcelable.Creator<SavedState> CREATOR
            = new Parcelable.Creator<SavedState>() {
        @Override
        public SavedState createFromParcel(Parcel in) {
            return new SavedState(in);
        }

        @Override
        public SavedState[] newArray(int size) {
            return new SavedState[size];
        }
    };
}

