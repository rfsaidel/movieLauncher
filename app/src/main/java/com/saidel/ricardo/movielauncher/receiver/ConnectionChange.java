package com.saidel.ricardo.movielauncher.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.saidel.ricardo.movielauncher.fetch.FetchMovies;

public class ConnectionChange extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(netInfo != null && netInfo.isConnected()){
            Log.v("r.saidel","CONNECTEC");
        }else{
            Log.v("r.saidel","DISCONNECTEC");
        }

    }
}
