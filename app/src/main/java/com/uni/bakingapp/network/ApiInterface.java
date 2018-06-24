package com.uni.bakingapp.network;

import com.uni.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET ("android-baking-app-json")
    Call<List<Recipe>> getRecipes();
}

