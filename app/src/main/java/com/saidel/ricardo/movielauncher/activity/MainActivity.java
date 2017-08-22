package com.saidel.ricardo.movielauncher.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import com.saidel.ricardo.movielauncher.R;
import com.saidel.ricardo.movielauncher.adapter.MoviesAdapter;
import com.saidel.ricardo.movielauncher.data.MovieDAO;
import com.saidel.ricardo.movielauncher.fetch.FetchMovies;
import com.saidel.ricardo.movielauncher.fetch.FetchSearch;
import com.saidel.ricardo.movielauncher.object.Movie;
import com.saidel.ricardo.movielauncher.receiver.ConnectionChange;
import com.saidel.ricardo.movielauncher.util.Constants;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements FetchMovies.Observer, FetchSearch.Observer, ConnectionChange.Observer {

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
                        .putExtra(Constants.MOVIE_INTENT, movie)
                        .putExtra(Constants.MOVIE_ID, movie.getId());
                startActivity(intent);
            }
        });

        moviesPanel.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount >= totalItemCount) {
                }
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        setSearchBar(menu);
        return true;
    }

    private void setSearchBar(Menu menu) {
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search_menu));
        final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                FetchSearch fetchMovies = new FetchSearch((FetchSearch.Observer) mContext);
                fetchMovies.setQuery(query);
                fetchMovies.execute();
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    }
}
