package com.example.mymovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mainBinding.getRoot();
        setContentView(view);
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
    }

    public void onClickSetPopularity(View view) {
        setMethodOfSort(false);
        mainBinding.switcher.setChecked(false);
        mainBinding.textViewPopularity.setTextColor(getResources().getColor(R.color.purple_700));
        mainBinding.textViewTopRated.setTextColor(getResources().getColor(R.color.white));
    }

    public void onClickSetTopRated(View view) {
        setMethodOfSort(true);
        mainBinding.switcher.setChecked(true);
        mainBinding.textViewPopularity.setTextColor(getResources().getColor(R.color.white));
        mainBinding.textViewTopRated.setTextColor(getResources().getColor(R.color.purple_700));
    }

    private void setMethodOfSort(boolean isTopRated) {
        int methodOfSort = NetworkUtils.POPULARITY;
        if (isTopRated) {
            methodOfSort = NetworkUtils.TOP_RATED;
        }
        JSONObject jsonObject = NetworkUtils.getJSONFromNetwork(methodOfSort, 1);
        List<Movie> movies = JSONUtils.getMoviesFromJSON(jsonObject);
        movieAdapter.setMovies(movies);
    }
}