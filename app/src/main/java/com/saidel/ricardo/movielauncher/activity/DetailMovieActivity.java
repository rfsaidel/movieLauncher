package com.saidel.ricardo.movielauncher.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.saidel.ricardo.movielauncher.R;
import com.saidel.ricardo.movielauncher.data.MovieDAO;
import com.saidel.ricardo.movielauncher.object.Movie;
import com.saidel.ricardo.movielauncher.util.Constants;
import com.squareup.picasso.Picasso;

public class DetailMovieActivity extends Activity {
    private TextView mMovieTitle;
    private TextView mMovieVoteAverage;
    private TextView mMovieOverview;
    private TextView mMovieReleaseDate;
    private ImageView mMovieCover;
    private long mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();

        setContentView(R.layout.activity_detail_movie);
        mMovieTitle = (TextView) findViewById(R.id.movie_title);
        mMovieVoteAverage = (TextView) findViewById(R.id.movie_vote_average);
        mMovieOverview = (TextView) findViewById(R.id.movie_overview);
        mMovieReleaseDate = (TextView) findViewById(R.id.movie_release_date);
        mMovieCover = (ImageView) findViewById(R.id.movie_cover);

//        if (intent != null && intent.hasExtra(Constants.MOVIE_INTENT)) {
//            Movie movie = (Movie) intent.getSerializableExtra(Constants.MOVIE_INTENT);
//        }
        if (intent != null && intent.hasExtra(Constants.MOVIE_ID)) {
            mId = intent.getLongExtra(Constants.MOVIE_ID, 0);
            MovieDAO movieDAO = new MovieDAO(this);
            Movie movie = movieDAO.getMovie(mId);

            mMovieTitle.setText(movie.getTitle());
            mMovieVoteAverage.setText(movie.getVoteAverage() + "/10");
            mMovieOverview.setText(movie.getOverview());
            mMovieReleaseDate.setText(movie.getReleaseDate().substring(0, 4));
            Picasso.with(this).load(Constants.URL_LOAD_IMAGE_W342 + movie.getPosterPath()).into(mMovieCover);


        }
    }
}
