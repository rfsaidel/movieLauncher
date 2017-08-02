package com.saidel.ricardo.movielauncher.data;

import android.provider.BaseColumns;

public class Contract {
    public static final class MovieEntry implements BaseColumns{
        public static final String TABLE_NAME = "movie";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_VOTE = "vote";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_COVER_URL = "cover_url";
    }
}
