package com.example.android.popularmovies2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies2.adapters.ReviewsAdapter;
import com.example.android.popularmovies2.adapters.TrailersAdapter;
import com.example.android.popularmovies2.database.AppDataBase;
import com.example.android.popularmovies2.database.DetailViewModel;
import com.example.android.popularmovies2.database.DetailViewModelFactory;
import com.example.android.popularmovies2.database.MoviesExecutor;
import com.example.android.popularmovies2.models.Movies;
import com.example.android.popularmovies2.models.Reviews;
import com.example.android.popularmovies2.models.Trailers;
import com.example.android.popularmovies2.utils.JsonUtils;
import com.example.android.popularmovies2.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class DetailActivity extends AppCompatActivity implements TrailersAdapter.TrailerClickListener {


    private Movies movies;
    private ArrayList<Trailers> trailers;
    private RecyclerView mTrailersRecyclerView;
    private TrailersAdapter.TrailerClickListener trailersClickListener;


    private ArrayList<Reviews> reviews;
    private RecyclerView mReviewsRecyclerView;
    private ReviewsAdapter mAdapter;
    private TextView mNoReviews;

    private AppDataBase mDb;

    private FloatingActionButton favoriteFab;
    private boolean isClicked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ImageView detailImage = (ImageView) findViewById(R.id.iv_detail_image);
        final TextView title = (TextView) findViewById(R.id.tv_title);
        TextView synopsis = (TextView) findViewById(R.id.tv_synopsis);
        TextView releaseDate = (TextView) findViewById(R.id.tv_release_date);
        TextView voteAverage = (TextView) findViewById(R.id.tv_vote_average);


        trailers = new ArrayList<>();
        mTrailersRecyclerView = (RecyclerView) findViewById(R.id.rv_trailers_detail);
        trailersClickListener = this;


        reviews = new ArrayList<>();
        mReviewsRecyclerView = (RecyclerView) findViewById(R.id.rv_reviews_detail);
        mNoReviews = (TextView) findViewById(R.id.tv_no_reviews);

        mDb = AppDataBase.getInstance(this);


        Intent intent = getIntent();

        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            movies = intent.getParcelableExtra(Intent.EXTRA_TEXT);
            Picasso.get().load(movies.getImage()).into(detailImage);
            title.setText(movies.getTitle());
            synopsis.setText(movies.getSynopsis());
            releaseDate.setText(movies.getReleaseDate());
            voteAverage.setText(movies.getAverageVote());
            String movieId = String.valueOf(movies.getMovieId());
            URL trailersUrl = NetworkUtils.buildTrailer(movieId);
            loadTrailers(trailersUrl);
            URL reviewsUrl = NetworkUtils.buildReviews(movieId);
            loadReviews(reviewsUrl);
            favoriteFab = findViewById(R.id.fab_favorite);
            setFavoriteFab(movies.getMovieId());
            favoriteFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    click();

                }
            });
        }


        LinearLayoutManager trailersLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mTrailersRecyclerView.setLayoutManager(trailersLayoutManager);
        mTrailersRecyclerView.setHasFixedSize(true);


        LinearLayoutManager reviewsLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false);
        mReviewsRecyclerView.setLayoutManager(reviewsLayoutManager);
        mReviewsRecyclerView.setHasFixedSize(true);

    }

    public void setFavoriteFab(final int id) {
        DetailViewModelFactory factory = new DetailViewModelFactory(mDb, id);
        final DetailViewModel viewModel = ViewModelProviders.of(this, factory).get(DetailViewModel.class);
        viewModel.getMovie().observe(this, new Observer<Movies>() {
            @Override
            public void onChanged(@Nullable Movies moviesDb) {
                if (moviesDb == null) {
                    isClicked = false;
                    favoriteFab.setImageResource(R.drawable.heart_favorite_border_black);
                } else if (moviesDb.getMovieId() == movies.getMovieId()) {
                    favoriteFab.setImageResource(R.drawable.heart_favorite_black);
                    isClicked = true;
                }
            }
        });
    }

    public void click() {
        if (isClicked) {
            MoviesExecutor.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDb.moviesDao().removeMovie(movies.getMovieId());

                }
            });
            Toast.makeText(this, getString(R.string.removed), Toast.LENGTH_LONG).show();
            isClicked = false;

        } else {
            MoviesExecutor.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDb.moviesDao().addMovie(movies);

                }
            });
            Toast.makeText(this, getString(R.string.added), Toast.LENGTH_LONG).show();
            isClicked = true;

        }
    }

    public void loadTrailers(URL url) {
        new fetchTrailers().execute(url);
    }

    public void loadReviews(URL url) {
        new fetchReviews().execute(url);
    }

    private void showNoReviews() {
        mReviewsRecyclerView.setVisibility(View.INVISIBLE);
        mNoReviews.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(Trailers trailers) {
        Uri youtube = Uri.parse(trailers.getTrailerUrl());
        Intent youtubeIntent = new Intent(Intent.ACTION_VIEW, youtube);
        if (youtubeIntent.resolveActivity(getPackageManager()) != null) ;
        startActivity(youtubeIntent);
    }

    public class fetchTrailers extends AsyncTask<URL, Void, ArrayList<Trailers>> {

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


    public class fetchReviews extends AsyncTask<URL, Void, ArrayList<Reviews>> {

        @Override
        protected ArrayList<Reviews> doInBackground(URL... urls) {
            URL url = urls[0];
            String response = null;
            try {
                response = NetworkUtils.getResponseFromUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (response != null) {
                reviews = JsonUtils.estractReviews(response);
            }
            return reviews;
        }

        @Override
        protected void onPostExecute(ArrayList<Reviews> reviews) {
            if (reviews != null) {
                ReviewsAdapter reviewsAdapter = new ReviewsAdapter(reviews);
                mReviewsRecyclerView.setAdapter(reviewsAdapter);

            } else {
                showNoReviews();
            }
        }
    }
}