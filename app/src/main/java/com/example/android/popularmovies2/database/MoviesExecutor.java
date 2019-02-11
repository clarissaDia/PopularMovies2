package com.example.android.popularmovies2.database;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MoviesExecutor {

    private static final Object LOCK = new Object();
    private static MoviesExecutor instance;
    private final Executor diskIO;

    private MoviesExecutor (Executor diskIO){

        this.diskIO = diskIO;
    }

    public static MoviesExecutor getInstance(){
        if (instance == null){
            synchronized (LOCK){
                instance = new MoviesExecutor(Executors.newSingleThreadExecutor());
            }
        }

        return instance;
    }

    public Executor diskIO(){
        return diskIO;
    }
}
