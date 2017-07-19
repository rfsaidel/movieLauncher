package com.saidel.ricardo.movielauncher.object;

import java.io.Serializable;

public final class Movie implements Serializable{
    private String mTitle;
    private String mVoteAverage;
    private String mOverview;
    private String mReleaseDate;
    private String mPosterPath;

    public void setTitle(String title){
        mTitle = title;
    }
    public String getTitle(){
        return mTitle;
    }

    public void setVoteAverage(String vote){
        mVoteAverage = vote;
    }
    public String getVoteAverage(){
        return mVoteAverage;
    }

    public void setOverview(String overview){
        mOverview = overview;
    }
    public String getOverview(){
        return mOverview;
    }

    public void setReleaseDate(String date){
        mReleaseDate = date;
    }
    public String getReleaseDate(){
        return mReleaseDate;
    }

    public void setPosterPath(String path){
        mPosterPath = path;
    }
    public String getPosterPath(){
        return mPosterPath;
    }
}


