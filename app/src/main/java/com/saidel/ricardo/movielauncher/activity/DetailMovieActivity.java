package com.saidel.ricardo.movielauncher.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.saidel.ricardo.movielauncher.R;
import com.saidel.ricardo.movielauncher.data.MovieDAO;
import com.saidel.ricardo.movielauncher.fetch.FetchDetails;
import com.saidel.ricardo.movielauncher.object.Movie;
import com.saidel.ricardo.movielauncher.object.MovieDetails;
import com.saidel.ricardo.movielauncher.util.Constants;
import com.squareup.picasso.Picasso;

public class DetailMovieActivity extends Activity implements FetchDetails.Observer {
    private Context mContext;
    private long mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();
        mContext = this;

        setContentView(R.layout.activity_detail_movie);
        TextView panelTitle = (TextView) findViewById(R.id.movie_title);
        View backdropPanel = findViewById(R.id.backdrop_panel);
        ImageView backdropImage = (ImageView) findViewById(R.id.backdrop_image);
        TextView backdropTitle = (TextView) findViewById(R.id.backdrop_title);
        TextView movieVoteAverage = (TextView) findViewById(R.id.movie_vote_average);
        TextView movieOverview = (TextView) findViewById(R.id.movie_overview);
        TextView movieReleaseDate = (TextView) findViewById(R.id.movie_release_date);
        ImageView movieCover = (ImageView) findViewById(R.id.movie_cover);

        if (intent != null && intent.hasExtra(Constants.MOVIE_ID)) {
            mId = intent.getLongExtra(Constants.MOVIE_ID, 0);
            MovieDAO movieDAO = new MovieDAO(this);
            Movie movie = movieDAO.getMovie(mId);

            if (movie.getBackdropPath() != null) {
                backdropPanel.setVisibility(View.VISIBLE);
                panelTitle.setVisibility(View.GONE);
                backdropTitle.setText(movie.getTitle());
                Picasso.with(this).load(Constants.URL_LOAD_IMAGE_W342 + movie.getBackdropPath()).into(backdropImage);
            } else {
                panelTitle.setText(movie.getTitle());
            }

            movieVoteAverage.setText(movie.getVoteAverage() + "/10");
            movieOverview.setText(movie.getOverview());
            movieReleaseDate.setText(movie.getReleaseDate().substring(0, 4));
            Picasso.with(this).load(Constants.URL_LOAD_IMAGE_W342 + movie.getPosterPath()).into(movieCover);

            FetchDetails details = new FetchDetails((FetchDetails.Observer) mContext);
            details.setMovieId(String.valueOf(mId));
            details.execute();
        }
    }

    @Override
    public void onFetchDetailsTaskCompleted(MovieDetails movieDetails) {
        Log.v("r.saidel","onFetchDetailsTaskCompleted DONE!");
    }
}
