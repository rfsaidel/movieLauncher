package com.saidel.ricardo.movielauncher.data;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.BaseColumns;

import com.saidel.ricardo.movielauncher.object.Movie;

public class MovieDAO implements BaseColumns {

    private Context mContext;

    public MovieDAO(final Context context) {
        mContext = context;
    }

    public long insert(Movie movie){
        Uri item = mContext.getContentResolver().insert(Contract.MovieEntry.CONTENT_URI, getContentValues(movie));
        return 0;
    }

    private ContentValues getContentValues(Movie movie){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.MovieEntry.COLUMN_TITLE, movie.getTitle());
        contentValues.put(Contract.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
        contentValues.put(Contract.MovieEntry.COLUMN_COVER_URL, movie.getPosterPath());
        contentValues.put(Contract.MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        contentValues.put(Contract.MovieEntry.COLUMN_VOTE, movie.getVoteAverage());
        return contentValues;
    }
}
