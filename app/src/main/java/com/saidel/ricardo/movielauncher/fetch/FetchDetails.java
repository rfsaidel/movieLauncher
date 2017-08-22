package com.saidel.ricardo.movielauncher.fetch;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.saidel.ricardo.movielauncher.object.MovieDetails;
import com.saidel.ricardo.movielauncher.util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchDetails extends AsyncTask<Void, Void, Void> {

    private String LOG_TAG = "FetchMovies";
    private Observer mCallback;

    private String SCHEME = "https";
    private String AUTHORITY = "api.themoviedb.org";
    private String PATH = "3";
    private String PATH_TYPE = "movie";
    private String mMovieId = "";
    private String API_KEY = "api_key";
    private String LANGUAGE_ISO = "language";
    private String METHOD = "GET";

    public FetchDetails(Observer callback) {
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
                    .appendPath(mMovieId)
                    .appendQueryParameter(API_KEY, Constants.MOVIE_API_KEY)
                    .appendQueryParameter(LANGUAGE_ISO, Constants.LANGUAGE_US_ISO);
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
        JSONObject detailsJson = new JSONObject(moviewJsonStr);
        MovieDetails movieDetails = new MovieDetails();
        movieDetails.setId(Long.getLong(mMovieId));
        movieDetails.setHomepage(detailsJson.get("homepage").toString());
        movieDetails.setImdbId(detailsJson.get("imdb_id").toString());
        movieDetails.setBudget(detailsJson.get("budget").toString());
        movieDetails.setRevenue(detailsJson.get("revenue").toString());
        movieDetails.setRuntime(detailsJson.get("runtime").toString());
        mCallback.onFetchDetailsTaskCompleted(movieDetails);
    }

    public void setMovieId(String id) {
        mMovieId = id;
    }

    public interface Observer {
        void onFetchDetailsTaskCompleted(MovieDetails movieDetails);
    }
}
