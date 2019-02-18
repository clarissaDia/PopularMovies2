package com.example.android.popularmovies2.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    public static final String SORT_POPULAR = "popular";
    public static final String SORT_TOP_RATED = "top_rated";
    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String TRAILER_URL = "https://api.themoviedb.org/3/movie/";
    private static final String API_KEY = "api_key";
    /* Add here your own API key */
    private static final String MY_API_KEY = "";
    /*trailers endpoints*/
    private static final String TRAILERS = "videos";


    /*youtube url*/
    private static final String YOUTUBE_BASE_URL = "https://www.youtube.com/watch";
    private static final String YOUTUBE_QUERY_PARAM = "v";
    private static final String YOUTUBE_PREVIEW = "https://img.youtube.com/vi/";
    private static final String YOUTUBE_JPG = "0.jpg";

    /*reviews URL*/
    private static final String REVIEWS = "reviews";

    public static URL buildUrl(String sortPopular) {

        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(sortPopular).appendQueryParameter(API_KEY, MY_API_KEY).build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildTrailer(String movieId) {

        Uri uri = Uri.parse(TRAILER_URL).buildUpon().appendPath(movieId).appendPath(TRAILERS)
                .appendQueryParameter(API_KEY, MY_API_KEY).build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String buildYoutubeUrl(String trailerkey) {
        Uri uri = Uri.parse(YOUTUBE_BASE_URL).buildUpon().appendQueryParameter(YOUTUBE_QUERY_PARAM, trailerkey).build();
        return uri.toString();

    }

    public static String buildYoutubePreview(String movieId) {
        Uri uri = Uri.parse(YOUTUBE_PREVIEW).buildUpon().appendEncodedPath(movieId)
                .appendPath(YOUTUBE_JPG).build();
        return uri.toString();
    }

    public static URL buildReviews(String movieId) {
        Uri uri = Uri.parse(BASE_URL).buildUpon().appendPath(movieId).appendPath(REVIEWS)
                .appendQueryParameter(API_KEY, MY_API_KEY).build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;

    }

    public static String getResponseFromUrl(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = connection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            connection.disconnect();
        }
    }
}