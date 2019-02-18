package com.example.android.popularmovies2.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.popularmovies2.models.Movies;

public class DetailViewModel extends ViewModel {
    private final LiveData<Movies> movies;

    public DetailViewModel (AppDataBase appDataBase, int movieId){

        movies = appDataBase.moviesDao().getMovie(movieId);
    }
    public LiveData<Movies> getMovie (){
        return movies;
    }
}