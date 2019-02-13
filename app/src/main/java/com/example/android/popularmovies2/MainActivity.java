package com.example.android.popularmovies2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies2.adapters.PosterAdapter;
import com.example.android.popularmovies2.database.MoviesViewModel;
import com.example.android.popularmovies2.models.Movies;
import com.example.android.popularmovies2.utils.JsonUtils;
import com.example.android.popularmovies2.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PosterAdapter.PosterClickListener {
    private RecyclerView mRecyclerView;
    private PosterAdapter.PosterClickListener posterClickListener;
    private ProgressBar mProgressbar;
    private TextView mErrorTextView;
    private ArrayList<Movies> moviesArrayList;
    private MoviesViewModel moviesViewModel;
    private PosterAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        moviesArrayList = new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movie_main);
        adapter = new PosterAdapter(moviesArrayList, posterClickListener);
        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);


        GridLayoutManager gridManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(gridManager);
        mRecyclerView.setHasFixedSize(true);
        posterClickListener = this;

        mErrorTextView = (TextView) findViewById(R.id.tv_error);
        mProgressbar = (ProgressBar) findViewById(R.id.pb_loading);

        loadMovies();
    }

    private void loadMovies() {
        showMoviesDataView();
        URL url = NetworkUtils.buildUrl(NetworkUtils.SORT_POPULAR);
        new fetchMovies().execute(url);
    }

    private void showMoviesDataView() {
        mErrorTextView.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showMoviesError() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    public void loadFavorite (){
        moviesViewModel.getFavoriteMovies().observe(this, new Observer<List<Movies>>() {
            @Override
            public void onChanged(@Nullable List<Movies> movies) {
                if (movies != null){
                    final ArrayList<Movies> arrayList = new ArrayList<>(movies);
                    if (arrayList.isEmpty()){
                        showMoviesError();
                    }
                    loadMovies();
                }


            }
        });
    }

    @Override
    public void onClick(int posterPosition) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(intent.EXTRA_TEXT, moviesArrayList.get(posterPosition));
        startActivity(intent);
    }

    public class fetchMovies extends AsyncTask<URL, Void, ArrayList<Movies>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Movies> doInBackground(URL... urls) {
            URL url = urls[0];
            String response = null;
            try {
                response = NetworkUtils.getResponseFromUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (response != null) {
                moviesArrayList = JsonUtils.estractMoviesFromJson(response);
            }
            return moviesArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<Movies> movies) {
            mProgressbar.setVisibility(View.INVISIBLE);
            if (movies != null) {
                showMoviesDataView();
                PosterAdapter posterAdapter = new PosterAdapter(movies, posterClickListener);
                mRecyclerView.setAdapter(posterAdapter);
            } else {
                showMoviesError();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_by_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_by_popular:
                new fetchMovies().execute(NetworkUtils.buildUrl(NetworkUtils.SORT_POPULAR));
                return true;
            case R.id.sort_by_top_rated:
                new fetchMovies().execute(NetworkUtils.buildUrl(NetworkUtils.SORT_TOP_RATED));
                return true;
            case R.id.sort_by_favorite:
loadFavorite();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}