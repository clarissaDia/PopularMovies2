package com.example.android.popularmovies2.database;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.LogRecord;

public class MoviesExecutor {

    private static final Object LOCK = new Object();
    private static MoviesExecutor instance;
    private final Executor diskIO;
    private final Executor mainThread;
    private final Executor networkIO;


    private MoviesExecutor (Executor diskIO,Executor networkIO, Executor mainThread){

        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;


    }

    public static MoviesExecutor getInstance(){
        if (instance == null){
            synchronized (LOCK){
                instance = new MoviesExecutor(Executors.newSingleThreadExecutor(),Executors.newFixedThreadPool(3),
                        new MainThreadExecutor());
            }
        }

        return instance;
    }

    public Executor diskIO(){
        return diskIO;
    }

    public Executor mainThread (){
        return mainThread;
    }

    public Executor networkIO() {
        return networkIO;
    }

private static class MainThreadExecutor implements Executor{
    private android.os.Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(Runnable command) {
        mainThreadHandler.post(command);
    }
}
    }
