package com.saidel.ricardo.movielauncher.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class DetailMovieActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_detail_movie);
        Intent intent = this.getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            String url = intent.getStringExtra(Intent.EXTRA_TEXT);
            Log.v("r.saidel", "url: " + url);
        }
    }
}
