package com.example.android.popularmovies2.database;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class DetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final AppDataBase mDb;
    private final int mMovieId;

    public DetailViewModelFactory (AppDataBase dataBase, int movieId){
        mDb = dataBase;
        mMovieId = movieId;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DetailViewModel(mDb, mMovieId);
    }
}