package com.saidel.ricardo.movielauncher.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.saidel.ricardo.movielauncher.R;
import com.saidel.ricardo.movielauncher.object.Movie;
import com.saidel.ricardo.movielauncher.util.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoviesAdapter extends BaseAdapter {

    private ArrayList<Movie> mMovieList = new ArrayList<>();
    private Context mContext;
    private LayoutInflater inflater;


    public MoviesAdapter(Context context, ArrayList<Movie> movieList) {
        mMovieList = movieList;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mMovieList.size();
    }

    @Override
    public Movie getItem(int i) {
        return mMovieList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.movie_item, viewGroup, false);
        ImageView movieCover = (ImageView) view.findViewById(R.id.movie_cover);
        Picasso.with(mContext).load(Constants.URL_LOAD_IMAGE_W185 + mMovieList.get(i).getPosterPath()).into(movieCover);
        return view;
    }

    public void setData(ArrayList<Movie> movies) {
        mMovieList = movies;
    }
}
