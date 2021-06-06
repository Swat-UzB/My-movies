package com.example.mymovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.mymovies.data.MainViewModel;
import com.example.mymovies.data.Movie;
import com.example.mymovies.databinding.ActivityMainBinding;
import com.example.mymovies.utils.JSONUtils;
import com.example.mymovies.utils.NetworkUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mainBinding;
    private MovieAdapter movieAdapter;

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mainBinding.getRoot();
        setContentView(view);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
//        viewModel = new ViewModelProvider(this, ViewModelProvider
//                .AndroidViewModelFactory
//                .getInstance(getApplication()))
//                .get(MainViewModel.class);
        movieAdapter = new MovieAdapter();
        mainBinding.recyclerViewOfFilms.setLayoutManager(new GridLayoutManager(this, 2));
        mainBinding.recyclerViewOfFilms.setAdapter(movieAdapter);
        mainBinding.switcher.setChecked(true);
        mainBinding.switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setMethodOfSort(isChecked);
            }
        });
        mainBinding.switcher.setChecked(false);
        movieAdapter.setClickListener(new MovieAdapter.OnPosterClickListener() {
            @Override
            public void onPosterClick(int position) {
                Movie movie = movieAdapter.getMovies().get(position);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("id", movie.getId());
                startActivity(intent);
            }
        });
        movieAdapter.setReachEndListener(new MovieAdapter.OnReachEndListener() {
            @Override
            public void onReachEnd() {
                Toast.makeText(MainActivity.this, "Reach End", Toast.LENGTH_SHORT).show();
            }
        });
        LiveData<List<Movie>> moviesFromliveData = viewModel.getMoviesLiveData();
        moviesFromliveData.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                movieAdapter.setMovies(movies);
            }
        });
    }

    public void onClickSetPopularity(View view) {
        setMethodOfSort(false);
    }

    public void onClickSetTopRated(View view) {
        setMethodOfSort(true);
    }

    private void setMethodOfSort(boolean isTopRated) {
        int methodOfSort = NetworkUtils.POPULARITY;
        if (isTopRated) {
            mainBinding.switcher.setChecked(true);
            mainBinding.textViewPopularity.setTextColor(getResources().getColor(R.color.white));
            mainBinding.textViewTopRated.setTextColor(getResources().getColor(R.color.purple_700));
            methodOfSort = NetworkUtils.TOP_RATED;
        } else {
            mainBinding.switcher.setChecked(false);
            mainBinding.textViewPopularity.setTextColor(getResources().getColor(R.color.purple_700));
            mainBinding.textViewTopRated.setTextColor(getResources().getColor(R.color.white));
        }
        downloadData(methodOfSort, 1);
    }

    private void downloadData(int methodOfSort, int page) {
        JSONObject jsonObject = NetworkUtils.getJSONFromNetwork(methodOfSort, 1);
        List<Movie> movies = JSONUtils.getMoviesFromJSON(jsonObject);
        if (movies != null && !movies.isEmpty()) {
            viewModel.deleteAllMovies();
            for (Movie movie : movies) {
                viewModel.insertMovie(movie);
            }
        }
    }
}