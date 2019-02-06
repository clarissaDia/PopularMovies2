package com.example.android.popularmovies2.utils;

import com.example.android.popularmovies2.models.Movies;
import com.example.android.popularmovies2.models.Trailers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {
    /*estract movies from JSON*/
    private static final String MOVIE_RESULTS = "results";
    private static final String ORIGINAL_TITLE = "original_title";
    private static final String SYNOPSIS = "overview";
    private static final String RELEASE_DATE = "release_date";
    private static final String AVERAGE_VOTE = "vote_average";
    private static final String POSTER_PATH = "poster_path";
    private static final String IMAGE_PATH = "http://image.tmdb.org/t/p/w185";

    /*estract trailers from JSON*/
    public static final String MOVIE_ID = "id";
    private static final String VIDEO_RESULT = "results";
    private static final String TRAILER_NAME = "name";
    private static final String TRAILER_KEY = "key";
    private static final String SITE = "site";

    public static ArrayList<Movies> estractMoviesFromJson(String json) {

        ArrayList<Movies> moviesList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray movieArray = jsonObject.getJSONArray(MOVIE_RESULTS);
            for (int i = 0; i < movieArray.length(); i++) {
                String title;
                String synopsis;
                String releaseDate;
                String averageVote;
                String image;
                JSONObject movie = movieArray.getJSONObject(i);
                title = movie.getString(ORIGINAL_TITLE);
                synopsis = movie.getString(SYNOPSIS);
                releaseDate = movie.getString(RELEASE_DATE);
                averageVote = movie.getString(AVERAGE_VOTE);
                image = IMAGE_PATH + movie.getString(POSTER_PATH);
                moviesList.add(new Movies(title, synopsis, releaseDate, averageVote, image));
            }

        } catch (JSONException e) {

            e.printStackTrace();
        }
        return moviesList;
    }

    public static ArrayList<Trailers> estractTrailers (String trailersJson){
        ArrayList<Trailers> trailersList = new ArrayList<>();
        try{
            JSONObject trailersObject = new JSONObject(trailersJson);
            JSONArray trailersResults = trailersObject.getJSONArray(VIDEO_RESULT);
            if (trailersResults.length() != 0){
                for (int i = 0; i < trailersResults.length(); i++){
                    int movieId;
                    String trailerName;
                    String trailerKey;
                    String trailerSite;
                    String trailerLink;

                    JSONObject trailer = trailersResults.getJSONObject(i);
                    movieId = trailer.getInt(MOVIE_ID);
                    trailerName = trailer.getString(TRAILER_NAME);
                    trailerKey = trailer.getString(TRAILER_KEY);
                    trailerSite = trailer.getString(SITE);
                    trailerLink = NetworkUtils.buildYoutubeUrl(trailerKey);
                    trailersList.add(new Trailers(movieId, trailerName,trailerKey,trailerSite, trailerLink));

                }
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return trailersList;
    }

}
