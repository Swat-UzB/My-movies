package com.example.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.mymovies.databinding.ActivityFavouriteBinding;

public class FavouriteActivity extends AppCompatActivity {
    private ActivityFavouriteBinding favouriteBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favouriteBinding = ActivityFavouriteBinding.inflate(getLayoutInflater());
        View view = favouriteBinding.getRoot();
        setContentView(view);
    }
}