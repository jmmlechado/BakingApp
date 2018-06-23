package com.uni.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.uni.bakingapp.model.Recipe;
import com.uni.bakingapp.network.ApiClient;
import com.uni.bakingapp.network.ApiInterface;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements RecipesAdapter.RecipesAdapterOnClickHandler{


    private static final int    COLUMNS = 2;

    // TODO Remove
    @BindView(R.id.tv_hello) TextView mTextView;

    @BindView(R.id.pb_loading_indicator) ProgressBar mProgressIndicator;
    @BindView(R.id.tv_error_check_network) TextView mTextError;
    @BindView(R.id.rv_recipe) RecyclerView mRecyclerView;

    private RecyclerView.LayoutManager mLayoutManager;
    private RecipesAdapter mRecipesAdapter;

    private ApiInterface recipesInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        Timber.d("Timber up!");

        // Do UI binding with Butterknife
        ButterKnife.bind(this);

        mRecipesAdapter = new RecipesAdapter(this);
        mRecyclerView.setAdapter(mRecipesAdapter);

        mLayoutManager = new GridLayoutManager(this,COLUMNS,GridLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        // Initialize interface for getting recipes from network
        recipesInterface = ApiClient.getClient().create(ApiInterface.class);

        loadRecipes();
    }

    void loadRecipes(){

        mProgressIndicator.setVisibility(View.VISIBLE);
        Call<ArrayList<Recipe>> call = recipesInterface.getRecipes();
        call.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>>call, Response<ArrayList<Recipe>>response) {
                ArrayList<Recipe> recipes = response.body();
                Timber.d("Number of recipes received: " + recipes.size());
                mProgressIndicator.setVisibility(View.INVISIBLE);
                mTextError.setVisibility(View.INVISIBLE);
                mRecyclerView.setVisibility(View.VISIBLE);
                mRecipesAdapter.setRecipes(recipes);
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>>call, Throwable t) {
                Timber.d(t.toString());
                mProgressIndicator.setVisibility(View.INVISIBLE);
                mTextError.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.INVISIBLE);
            }
        });
    }

    // TODO Remove
    @OnClick(R.id.tv_hello)
    public void test(){
        mTextView.setText("Tested!");
    }

    @Override
    public void onClick(Recipe recipe) {
        Context context = this;
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, recipe);
        startActivity(intent);

    }
}
