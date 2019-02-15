package com.example.android.popularmovies2.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.android.popularmovies2.models.Movies;

import java.util.List;

@Dao
public interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addMovie (Movies movies);

    @Query("Delete FROM movies_table WHERE movie_id = :movieId")
    void removeMovie (int movieId);

    @Query("SELECT * FROM movies_table ORDER BY movie_id")
    LiveData<List<Movies>> getAllMovies();

    @Query("SELECT * FROM movies_table WHERE movie_id = :movieId")
    LiveData<Movies> getMovie (int movieId);

}
