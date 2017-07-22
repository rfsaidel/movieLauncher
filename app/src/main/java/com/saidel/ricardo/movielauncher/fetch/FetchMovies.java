package com.saidel.ricardo.movielauncher.fetch;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.saidel.ricardo.movielauncher.BuildConfig;
import com.saidel.ricardo.movielauncher.object.Movie;
import com.saidel.ricardo.movielauncher.util.Constants;

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

    private String SCHEME = "http";
    private String AUTHORITY = "api.themoviedb.org";
    private String PATH = "3";
    private String PATH_TYPE = "movie";
    private String PATH_CAT = "popular";
    private String API_KEY = "api_key";
    private String METHOD = "GET";


    public FetchMovies(Observer callback){
        mCallback = callback;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String moviesJsonStr;

        try {
            Uri.Builder builder = new Uri.Builder();
            builder.scheme(SCHEME)
                    .authority(AUTHORITY)
                    .appendPath(PATH)
                    .appendPath(PATH_TYPE)
                    .appendPath(PATH_CAT)
                    .appendQueryParameter(API_KEY, Constants.MOVIE_API_KEY);
            String movieUrl = builder.build().toString();
            URL url = new URL(movieUrl);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(METHOD);
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
        ArrayList<Movie> movies = new ArrayList<>();
        for(int i = 0; i < results.length(); i++) {
            Movie movieObj = new Movie();
            JSONObject movie = results.getJSONObject(i);
            movieObj.setPosterPath(movie.get("poster_path").toString());
            movieObj.setOverview(movie.get("overview").toString());
            movieObj.setReleaseDate(movie.get("release_date").toString());
            movieObj.setVoteAverage(movie.get("vote_average").toString());
            movieObj.setTitle(movie.get("title").toString());
            movies.add(movieObj);
        }
        mCallback.onFetchMoviesTaskCompleted(movies);
    }

    public interface Observer {
        void onFetchMoviesTaskCompleted(ArrayList<Movie> movies);
    }
}
