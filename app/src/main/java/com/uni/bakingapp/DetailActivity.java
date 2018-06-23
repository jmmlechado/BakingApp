package com.uni.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.uni.bakingapp.model.Recipe;

import timber.log.Timber;

public class DetailActivity extends AppCompatActivity{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent!=null){
            Recipe recipe = intent.getParcelableExtra(Intent.EXTRA_TEXT);
            Timber.d(recipe.getName());
        }

    }
}
