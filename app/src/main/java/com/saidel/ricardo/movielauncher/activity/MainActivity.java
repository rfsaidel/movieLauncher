package com.saidel.ricardo.movielauncher.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.saidel.ricardo.movielauncher.R;
import com.saidel.ricardo.movielauncher.adapter.MoviesAdapter;
import com.saidel.ricardo.movielauncher.data.DbHelper;
import com.saidel.ricardo.movielauncher.data.MovieDAO;
import com.saidel.ricardo.movielauncher.fetch.FetchMovies;
import com.saidel.ricardo.movielauncher.object.Movie;
import com.saidel.ricardo.movielauncher.receiver.ConnectionChange;
import com.saidel.ricardo.movielauncher.util.Constants;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements FetchMovies.Observer, ConnectionChange.Observer {

    private ArrayList<Movie> mMovieList;
    private MoviesAdapter mMoviesAdapter;
    private Context mContext;
    private ConnectionChange mInternetReceiver;
    private MovieDAO mMovieDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        GridView moviesPanel = (GridView) this.findViewById(R.id.movie_panel);

        mMovieDAO = new MovieDAO(this);
        mMovieList = mMovieDAO.getMovies();

        mMoviesAdapter = new MoviesAdapter(this, mMovieList);
        moviesPanel.setAdapter(mMoviesAdapter);

        moviesPanel.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Movie movie = mMoviesAdapter.getItem(position);
                Intent intent = new Intent(mContext, DetailMovieActivity.class)
                        .putExtra(Constants.MOVIE_INTENT, movie);
                startActivity(intent);
            }
        });

        registerConnectionWatcher();
    }

    @Override
    public void onFetchMoviesTaskCompleted(ArrayList<Movie> movies) {
        mMoviesAdapter.setData(movies);
        mMovieDAO.deleteAll();
        for (int i = 0; i < movies.size(); i++) {
            long newRowId = mMovieDAO.insert(movies.get(i));
            movies.get(i).setId(newRowId);
            Log.v("r.saidel","newRowId: "+newRowId);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMoviesAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterConnectionReceiver();
    }

    public void registerConnectionWatcher() {
        mInternetReceiver = new ConnectionChange(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(mInternetReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(mInternetReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    public void unregisterConnectionReceiver() {
        try {
            unregisterReceiver(mInternetReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionChange(boolean isConnected) {
        if (isConnected) {
            FetchMovies fetchMovies = new FetchMovies(this);
            fetchMovies.execute();
        }
    }
}
