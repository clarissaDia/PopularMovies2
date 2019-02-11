package com.example.android.popularmovies2.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.android.popularmovies2.models.Movies;

import java.util.List;

public class MoviesViewModel extends AndroidViewModel {

    private LiveData<List<Movies>> favoriteMovies;

    public MoviesViewModel (Application application){
        super(application);
        AppDataBase mDb = AppDataBase.getInstance(application);
        favoriteMovies = mDb.moviesDao().getAllMovies();
    }

    public LiveData<List<Movies>> getFavoriteMovies (){
        return favoriteMovies;
    }
}
