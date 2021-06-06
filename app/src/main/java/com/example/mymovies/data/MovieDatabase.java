package com.example.mymovies.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {
    private static MovieDatabase movieDatabase;
    public static final String DB_NAME = "movies.db";
    private static final Object LOCK = new Object();

    public static MovieDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if (movieDatabase == null) {
                movieDatabase = Room.databaseBuilder(context, MovieDatabase.class, DB_NAME)
                        .build();
            }
        }
        return movieDatabase;
    }

    public abstract MovieDao movieDao();
}
