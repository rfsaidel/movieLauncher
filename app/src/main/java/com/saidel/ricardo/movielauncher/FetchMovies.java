package com.saidel.ricardo.movielauncher;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FetchMovies extends AsyncTask<Void, Void, Void> {

    private String LOG_TAG = "FetchMovies";
    private Observer mCallback;

    public FetchMovies(Observer callback){
        mCallback = callback;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String moviesJsonStr;

        try {
            String APIkey = "PASTE-HERE";
            String baseUrl = "http://api.themoviedb.org/3/movie/popular?api_key="+APIkey;
            URL url = new URL(baseUrl);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            moviesJsonStr = buffer.toString();

            getMovieDataFromJson(moviesJsonStr);
        } catch (Exception e) {
            e.printStackTrace();
        }   finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return null;
    }

    private void getMovieDataFromJson(String moviewJsonStr)
            throws JSONException {
        JSONObject moviesJson = new JSONObject(moviewJsonStr);
        JSONArray results = moviesJson.getJSONArray("results");
        ArrayList movies = new ArrayList<>();
        for(int i = 0; i < results.length(); i++) {
            JSONObject movie = results.getJSONObject(i);
            movies.add(movie.get("poster_path"));
        }
        mCallback.onFetchMoviesTaskCompleted(movies);
    }

    public interface Observer {
        void onFetchMoviesTaskCompleted(ArrayList movies);
    }
}
