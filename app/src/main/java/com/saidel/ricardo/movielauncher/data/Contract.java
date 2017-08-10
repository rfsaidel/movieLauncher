package com.saidel.ricardo.movielauncher.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class Contract {
    public static final String CONTENT_AUTHORITY = "com.saidel.ricardo.movielauncher";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIE = "movie";

    public static final class MovieEntry implements BaseColumns {
        public static String MOVIE_PATH = "movie";
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String TABLE_NAME = "movie";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_VOTE = "vote";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_COVER_URL = "cover_url";

        public static Uri buildMovieUrl(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
