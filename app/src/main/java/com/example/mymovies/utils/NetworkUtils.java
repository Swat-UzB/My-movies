package com.example.mymovies.utils;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class NetworkUtils {

    private static final String BASE_URL = "https://api.themoviedb.org/3/discover/movie";


    private static final String PARAMS_API_KEY = "api_key";
    private static final String PARAMS_LANGUAGE = "language";
    private static final String PARAMS_SORT_BY = "sort_by";
    private static final String PARAMS_PAGE = "page";
    private static final String API_KEY = "3a9f9c29692849142df8745badad43a8";
    private static final String LANGUAGE_VALUE = "ru-RU";
    private static final String SORT_BY_POPULARITY = "popularity.desc";
    private static final String SORT_BY_TOP_RATED = "vote_average.desc";

    public static final int POPULARITY = 0;
    public static final int TOP_RATED = 1;

    private static URL buildURL(int sort, int page) {
        URL url = null;
        String sortMethod;
        if (sort == POPULARITY) {
            sortMethod = SORT_BY_POPULARITY;
        } else {
            sortMethod = SORT_BY_TOP_RATED;
        }
        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY, API_KEY)
                .appendQueryParameter(PARAMS_LANGUAGE, LANGUAGE_VALUE)
                .appendQueryParameter(PARAMS_SORT_BY, sortMethod)
                .appendQueryParameter(PARAMS_PAGE, String.valueOf(page))
                .build();
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static JSONObject getJSONFromNetwork(int sort, int page) {
        JSONObject jsonObject = null;
        URL url = buildURL(sort, page);
        try {
            jsonObject = new JSONLoadTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static class JSONLoadTask extends AsyncTask<URL, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(URL... urls) {
            JSONObject result = null;
            if (urls != null && urls.length > 0) {
                HttpURLConnection connection = null;
                try {
                    connection = (HttpURLConnection) urls[0].openConnection();
                    InputStream inputStream = connection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader reader1 = new BufferedReader(inputStreamReader);
                    StringBuilder builder = new StringBuilder();
                    String line = reader1.readLine();
                    while (line != null) {
                        builder.append(line);
                        line = reader1.readLine();
                    }
                    result = new JSONObject(builder.toString());
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }

            }
            return result;
        }
    }
}
