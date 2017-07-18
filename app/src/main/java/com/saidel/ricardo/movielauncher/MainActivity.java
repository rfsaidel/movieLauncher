package com.saidel.ricardo.movielauncher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements FetchMovies.Observer {

    ArrayList mMovieList;
    MoviesAdapter mMoviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridView moviesPanel = (GridView) this.findViewById(R.id.movie_panel);
        mMovieList = new ArrayList<>();
        mMoviesAdapter = new MoviesAdapter(this, mMovieList);
        moviesPanel.setAdapter(mMoviesAdapter);
        FetchMovies fetchMovies = new FetchMovies(this);
        fetchMovies.execute();
    }

    @Override
    public void onFetchMoviesTaskCompleted(ArrayList movies) {
        mMoviesAdapter.setData(movies);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMoviesAdapter.notifyDataSetChanged();
            }
        });
    }
}
