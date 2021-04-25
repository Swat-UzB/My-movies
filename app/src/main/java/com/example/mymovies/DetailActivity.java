package com.example.mymovies;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mymovies.databinding.ActivityDetailBinding;


public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding detailBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailBinding = ActivityDetailBinding.inflate(getLayoutInflater());
        View view = detailBinding.getRoot();
        setContentView(view);
    }
}
