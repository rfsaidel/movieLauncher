package com.saidel.ricardo.movielauncher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.saidel.ricardo.movielauncher.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoviesAdapter extends BaseAdapter {

    private ArrayList<String> mMovieList = new ArrayList<>();
    private Context mContext;
    private LayoutInflater inflater;

    public MoviesAdapter(Context context, ArrayList<String> movieList) {
        mMovieList = movieList;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mMovieList.size();
    }

    @Override
    public String getItem(int i) {
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
        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185" + mMovieList.get(i)).into(movieCover);
        return view;
    }

    public void setData(ArrayList movies) {
        mMovieList = movies;
    }
}
