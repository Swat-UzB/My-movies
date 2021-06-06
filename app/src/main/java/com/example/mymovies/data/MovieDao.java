package com.example.mymovies.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("SELECT * from movies")
    LiveData<List<Movie>> getAllMovies();

    @Query("SELECT * FROM movies WHERE id ==:movieId")
    Movie getMovieById(int movieId);

    @Query("DELETE FROM movies")
    void deleteAllMovies();

    @Delete
    void deleteMovie(Movie movie);

    @Insert
    void insertMovie(Movie movie);
}
