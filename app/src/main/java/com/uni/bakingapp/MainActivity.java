package com.uni.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.uni.bakingapp.model.Recipe;
import com.uni.bakingapp.network.ApiClient;
import com.uni.bakingapp.network.ApiInterface;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_hello) TextView mTextView;

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

        // Initialize interface for getting recipes from network
        recipesInterface = ApiClient.getClient().create(ApiInterface.class);

        loadRecipes();
    }

    void loadRecipes(){

        Call<List<Recipe>> call = recipesInterface.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>>call, Response<List<Recipe>>response) {
                List<Recipe> recipes = response.body();
                Timber.d("Number of recipes received: " + recipes.size());
            }

            @Override
            public void onFailure(Call<List<Recipe>>call, Throwable t) {
                Timber.d(t.toString());
            }
        });
    }

    @OnClick(R.id.tv_hello)
    public void test(){
        mTextView.setText("Tested!");
    }
}
