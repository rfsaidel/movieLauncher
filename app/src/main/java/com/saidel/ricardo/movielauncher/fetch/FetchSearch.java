package com.saidel.ricardo.movielauncher.fetch;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

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

public class FetchSearch extends AsyncTask<Void, Void, Void> {

    private String LOG_TAG = "FetchMovies";
    private Observer mCallback;

    //https://api.themoviedb.org/3/search/movie?api_key=a762c5f63f19a8228dcd7e8df3c10217&language=en-US&query=cars&page=1&include_adult=false

    private String SCHEME = "https";
    private String AUTHORITY = "api.themoviedb.org";
    private String PATH = "3";
    private String PATH_ENGINE = "search";
    private String PATH_TYPE = "movie";
    private String API_KEY = "api_key";
    private String LANGUAGE_ISO = "language";
    private String QUERY_VALUE = "query";
    private String PAGE_NUMBER = "page";
    private String METHOD = "GET";
    private String mQuery = "";

    public FetchSearch(Observer callback) {
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
                    .appendPath(PATH_ENGINE)
                    .appendPath(PATH_TYPE)
                    .appendQueryParameter(API_KEY, Constants.MOVIE_API_KEY)
                    .appendQueryParameter(LANGUAGE_ISO, "en-US")
                    .appendQueryParameter(QUERY_VALUE, mQuery)
                    .appendQueryParameter(PAGE_NUMBER, "1");
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
        } finally {
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
        for (int i = 0; i < results.length(); i++) {
            JSONObject movie = results.getJSONObject(i);
            Log.v("r..saidel","poster_path: "+movie.get("poster_path").toString());
            if (!movie.get("poster_path").toString().equals("null")) {
                Movie movieObj = new Movie();
                movieObj.setPosterPath(movie.get("poster_path").toString());
                movieObj.setOverview(movie.get("overview").toString());
                movieObj.setReleaseDate(movie.get("release_date").toString());
                movieObj.setVoteAverage(movie.get("vote_average").toString());
                movieObj.setTitle(movie.get("title").toString());
                movies.add(movieObj);
            }
        }
        mCallback.onFetchMoviesTaskCompleted(movies);
    }

    public void setQuery(String query) {
        mQuery = query;
    }

    public interface Observer {
        void onFetchMoviesTaskCompleted(ArrayList<Movie> movies);
    }
}
