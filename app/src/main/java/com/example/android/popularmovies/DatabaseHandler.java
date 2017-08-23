package com.example.android.popularmovies;

import android.content.ContentResolver;
import android.util.Log;

import com.madrapps.asyncquery.AsyncQueryHandler;

/**
 * Created by HARUN on 8/10/2017.
 */

public class DatabaseHandler extends AsyncQueryHandler {
    public DatabaseHandler(ContentResolver cr) {
        super(cr);
    }

    @Override
    protected void onBulkInsertComplete(int token, Object cookie, int result) {
        super.onBulkInsertComplete(token, cookie, result);
        Log.d("DatabaseHandler", "Bulk Insert Done" + result);
    }
}
