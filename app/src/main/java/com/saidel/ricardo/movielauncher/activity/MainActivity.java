package com.saidel.ricardo.movielauncher.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.saidel.ricardo.movielauncher.fetch.FetchMovies;
import com.saidel.ricardo.movielauncher.adapter.MoviesAdapter;
import com.saidel.ricardo.movielauncher.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements FetchMovies.Observer {

    ArrayList mMovieList;
    MoviesAdapter mMoviesAdapter;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        GridView moviesPanel = (GridView) this.findViewById(R.id.movie_panel);
        mMovieList = new ArrayList<>();
        mMoviesAdapter = new MoviesAdapter(this, mMovieList);
        moviesPanel.setAdapter(mMoviesAdapter);

        moviesPanel.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String movie_url = mMoviesAdapter.getItem(position);
                Intent intent = new Intent(mContext, DetailMovieActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, movie_url);
                startActivity(intent);
            }
        });

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
