package com.example.android.popularmovies2;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies2.adapters.TrailersAdapter;
import com.example.android.popularmovies2.models.Movies;
import com.example.android.popularmovies2.models.Trailers;
import com.example.android.popularmovies2.utils.JsonUtils;
import com.example.android.popularmovies2.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.android.popularmovies2.utils.JsonUtils.MOVIE_ID;

public class DetailActivity extends AppCompatActivity implements TrailersAdapter.TrailerClickListener {

private ArrayList<Trailers> trailers;
    private RecyclerView mTrailersRecyclerView;
    private TrailersAdapter.TrailerClickListener trailersClickListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ImageView detailImage = (ImageView) findViewById(R.id.iv_detail_image);
        TextView title = (TextView) findViewById(R.id.tv_title);
        TextView synopsis = (TextView) findViewById(R.id.tv_synopsis);
        TextView releaseDate = (TextView) findViewById(R.id.tv_release_date);
        TextView voteAverage = (TextView) findViewById(R.id.tv_vote_average);


        trailers = new ArrayList<>();
        mTrailersRecyclerView = (RecyclerView) findViewById(R.id.rv_trailers_detail);
        trailersClickListener = this;


        Intent intent = getIntent();

        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            Movies movies = intent.getParcelableExtra(Intent.EXTRA_TEXT);
            Picasso.get().load(movies.getImage()).into(detailImage);
            title.setText(movies.getTitle());
            synopsis.setText(movies.getOverView());
            releaseDate.setText(movies.getReleaseDate());
            voteAverage.setText(movies.getVote());
        }


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false);
        mTrailersRecyclerView.setLayoutManager(layoutManager);
        mTrailersRecyclerView.setHasFixedSize(true);

loadtrailers();

    }

 void loadtrailers (){
     URL url = NetworkUtils.buildTrailer(MOVIE_ID);
        new fetchTrailers().execute(url);
 }


    @Override
    public void onItemClick(Trailers trailers) {
        Uri youtube = Uri.parse(trailers.getTrailerUrl());
        Intent youtubeIntent = new Intent(Intent.ACTION_VIEW, youtube);
        if (youtubeIntent.resolveActivity(getPackageManager()) != null);
        startActivity(youtubeIntent);

    }


    public class fetchTrailers extends AsyncTask<URL,Void, ArrayList<Trailers>> {

            @Override
            protected ArrayList<Trailers> doInBackground(URL... urls) {
                URL url = urls[0];
                String response = null;
                try {
                    response = NetworkUtils.getResponseFromUrl(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (response != null) {
                    trailers = JsonUtils.estractTrailers(response);
                }
                return trailers;

            }

            @Override
            protected void onPostExecute(ArrayList<Trailers> trailers) {
                TrailersAdapter trailersAdapter = new TrailersAdapter(trailers, trailersClickListener);
                mTrailersRecyclerView.setAdapter(trailersAdapter);
            }
        }
    }