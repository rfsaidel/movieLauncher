package com.saidel.ricardo.movielauncher.data;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import com.saidel.ricardo.movielauncher.object.Movie;

import java.util.ArrayList;

public class MovieDAO implements BaseColumns {

    private Context mContext;

    public MovieDAO(final Context context) {
        mContext = context;
    }

    public long insert(Movie movie) {
        Uri item = mContext.getContentResolver().insert(Contract.MovieEntry.CONTENT_URI, getContentValues(movie));
        return ContentUris.parseId(item);
    }

    public void deleteAll() {
        int rowDeleted = mContext.getContentResolver().delete(Contract.MovieEntry.CONTENT_URI, null, null);
    }

    public ArrayList<Movie> getMovies() {
        ArrayList<Movie> movies = new ArrayList<>();
        Cursor cursor = mContext.getContentResolver().query(Contract.MovieEntry.CONTENT_URI, null, null, null, null);
        if (cursor != null & cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Movie movie = resultMovie(cursor);
                movies.add(movie);
            }
        }
        return movies;
    }

    /*public ArrayList<Movie> getMovie() {
    }*/

    private Movie resultMovie(Cursor cursor) {
        Movie movie = new Movie();
        movie.setId(cursor.getInt(cursor.getColumnIndex(Contract.MovieEntry._ID)));
        movie.setTitle(cursor.getString(cursor.getColumnIndex(Contract.MovieEntry.COLUMN_TITLE)));
        movie.setOverview(cursor.getString(cursor.getColumnIndex(Contract.MovieEntry.COLUMN_OVERVIEW)));
        movie.setVoteAverage(cursor.getString(cursor.getColumnIndex(Contract.MovieEntry.COLUMN_VOTE)));
        movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(Contract.MovieEntry.COLUMN_RELEASE_DATE)));
        movie.setPosterPath(cursor.getString(cursor.getColumnIndex(Contract.MovieEntry.COLUMN_COVER_URL)));
        return movie;
    }

    private ContentValues getContentValues(Movie movie) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.MovieEntry.COLUMN_TITLE, movie.getTitle());
        contentValues.put(Contract.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
        contentValues.put(Contract.MovieEntry.COLUMN_COVER_URL, movie.getPosterPath());
        contentValues.put(Contract.MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        contentValues.put(Contract.MovieEntry.COLUMN_VOTE, movie.getVoteAverage());
        return contentValues;
    }
}
