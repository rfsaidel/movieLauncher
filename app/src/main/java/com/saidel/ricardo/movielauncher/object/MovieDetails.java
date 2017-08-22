package com.saidel.ricardo.movielauncher.object;

/**
 * Created by rfsai on 19/08/2017.
 */

public class MovieDetails {
    private long mId;
    private String mHomepage;
    private String mImdbId;
    private String mBudget;
    private String mRevenue;
    private String mRuntime;

    public void setId(long id) {
        this.mId = id;
    }

    public long getId() {
        return mId;
    }

    public void setHomepage(String page) {
        this.mHomepage = page;
    }

    public String getHomepage() {
        return mHomepage;
    }

    public void setImdbId(String imdbId) {
        this.mImdbId = imdbId;
    }

    public String getImdbId() {
        return mImdbId;
    }

    public void setBudget(String budget) {
        this.mBudget = budget;
    }

    public String getBudget() {
        return mBudget;
    }

    public void setRevenue(String revenue) {
        this.mRevenue = revenue;
    }

    public String getRevenue() {
        return mRevenue;
    }

    public void setRuntime(String runtime) {
        this.mRuntime = runtime;
    }

    public String getRuntime() {
        return mRuntime;
    }

}
