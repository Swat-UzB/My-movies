package com.example.mymovies.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("SELECT * from favourite_name")
    LiveData<List<FavouriteMovie>> getAllFavouriteMovies();

    @Query("SELECT * FROM favourite_name WHERE id ==:movieId")
    FavouriteMovie getFavouriteMovieById(int movieId);

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

    @Delete
    void deleteFavouriteMovie(FavouriteMovie movie);

    @Insert
    void insertFavouriteMovie(FavouriteMovie movie);
}
