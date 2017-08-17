package com.saidel.ricardo.movielauncher.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.saidel.ricardo.movielauncher.fetch.FetchMovies;
import com.saidel.ricardo.movielauncher.object.Movie;

import java.util.ArrayList;

public class ConnectionChange extends BroadcastReceiver {

    private Observer mCallBack;

    public ConnectionChange(Observer callback){
        mCallBack = callback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            mCallBack.onConnectionChange(true);
        } else {
            mCallBack.onConnectionChange(false);
        }

    }

    public interface Observer {
        void onConnectionChange(boolean isConnected);
    }
}
