package com.example.mymovies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.mymovies.data.FavouriteMovie;
import com.example.mymovies.data.MainViewModel;
import com.example.mymovies.data.Movie;
import com.example.mymovies.databinding.ActivityDetailBinding;
import com.squareup.picasso.Picasso;


public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding detailBinding;
    private MainViewModel viewModel;
    private int id;
    private Movie movie;
    private FavouriteMovie favouritemovie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailBinding = ActivityDetailBinding.inflate(getLayoutInflater());
        View view = detailBinding.getRoot();
        setContentView(view);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            id = intent.getIntExtra("id", 0);
        } else {
            finish();
        }
         movie = viewModel.getMovieById(id);
        Picasso.get().load(movie.getBigPosterPath()).into(detailBinding.imageView2);
        detailBinding.textViewOriginalTitle.setText(movie.getOriginalTitle());
        detailBinding.textViewTitle.setText(movie.getTitle());
        detailBinding.textViewOverview.setText(movie.getOverview());
        detailBinding.textViewRating.setText(Double.toString(movie.getVoteAverage()));
        detailBinding.textViewReleaseDate.setText(movie.getReleaseDate());
    }

    public void onClickChangeFavourite(View view) {
        favouritemovie = viewModel.getFavouriteMovieById(id);
        if (favouritemovie == null){
            viewModel.insertFavouriteMovie(new FavouriteMovie(movie));
            Toast.makeText(this, "added to favourite", Toast.LENGTH_SHORT).show();
        }else {
            viewModel.deleteFavouriteMovieById(favouritemovie);
            Toast.makeText(this, "deleted from favourite", Toast.LENGTH_SHORT).show();
        }
    }
}
