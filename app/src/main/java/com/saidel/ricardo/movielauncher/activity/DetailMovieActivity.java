package com.saidel.ricardo.movielauncher.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.saidel.ricardo.movielauncher.R;
import com.saidel.ricardo.movielauncher.object.Movie;
import com.saidel.ricardo.movielauncher.util.Constants;

public class DetailMovieActivity extends Activity {
    private TextView mMovieTitle;
    private TextView mMovieVoteAverage;
    private TextView mMovieOverview;
    private TextView mMovieReleaseDate;
    private TextView mMoviePosterPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();

        setContentView(R.layout.activity_detail_movie);
        mMovieTitle = (TextView) findViewById(R.id.movie_title);
        mMovieVoteAverage = (TextView) findViewById(R.id.movie_vote_average);
        mMovieOverview = (TextView) findViewById(R.id.movie_overview);
        mMovieReleaseDate = (TextView) findViewById(R.id.movie_release_date);
        mMoviePosterPath = (TextView) findViewById(R.id.movie_poster_path);

        if (intent != null && intent.hasExtra(Constants.MOVIE_INTENT)) {
            Movie movie = (Movie) intent.getSerializableExtra(Constants.MOVIE_INTENT);

            mMovieTitle.setText(movie.getTitle());
            mMovieVoteAverage.setText(movie.getVoteAverage());
            mMovieOverview.setText(movie.getOverview());
            mMovieReleaseDate.setText(movie.getReleaseDate());
            mMoviePosterPath.setText(movie.getPosterPath());
        }
    }
}
