package com.example.mymovies;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.mymovies.data.MainViewModel;
import com.example.mymovies.data.Movie;
import com.example.mymovies.data.MovieDatabase;
import com.example.mymovies.databinding.ActivityDetailBinding;
import com.squareup.picasso.Picasso;


public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding detailBinding;
    private MainViewModel viewModel;
    private int id;

    @SuppressLint("SetTextI18n")
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
        Movie movie = viewModel.getMovieById(id);
        Picasso.get().load(movie.getBigPosterPath()).into(detailBinding.imageView2);
        detailBinding.textViewOriginalTitle.setText(movie.getOriginalTitle());
        detailBinding.textViewTitle.setText(movie.getTitle());
        detailBinding.textViewOverview.setText(movie.getOverview());
        detailBinding.textViewRating.setText(Double.toString(movie.getVoteAverage()));
        detailBinding.textViewReleaseDate.setText(movie.getReleaseDate());
    }
}
