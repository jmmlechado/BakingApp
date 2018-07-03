package com.uni.bakingapp.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.uni.bakingapp.GlideApp;
import com.uni.bakingapp.R;
import com.uni.bakingapp.model.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesAdapterViewHolder> {

    private ArrayList<Recipe> mRecipes;
    private final RecipesAdapter.RecipesAdapterOnClickHandler mClickHandler;

    public interface RecipesAdapterOnClickHandler {
        void onClick(Recipe recipe);
    }

    public RecipesAdapter(RecipesAdapter.RecipesAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class RecipesAdapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_recipe_image) ImageView mRecipeImageView;
        @BindView(R.id.tv_recipe_name)  TextView mRecipeTextView;

        public RecipesAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        // Click execution, passing data (related to position) to handler
        @OnClick
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Recipe recipe =  mRecipes.get(adapterPosition);
            mClickHandler.onClick(recipe);
        }
    }

    @Override
    public RecipesAdapter.RecipesAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForGridItem = R.layout.recipe_grid_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForGridItem, viewGroup, shouldAttachToParentImmediately);
        return new RecipesAdapter.RecipesAdapterViewHolder(view);
    }

    // Item visualization (getting data to show)
    @Override
    public void onBindViewHolder(RecipesAdapter.RecipesAdapterViewHolder recipesAdapterViewHolder, int position) {
        Recipe recipe = mRecipes.get(position);

        // movieData.setTag(position);         // Use tag to save movie position on list
        String recipeImage = recipe.getImage();

        // TODO (REFERENCE) Check if connection is available
        // Reviewer --> http://www.androidhive.info/2012/07/android-detect-internet-connection-status/
        // Reviewer --> http://stackoverflow.com/a/32437461

        Context context = recipesAdapterViewHolder.mRecipeImageView.getContext();
        GlideApp.with(context)
                .load(R.drawable.cloud_download)
                .into(recipesAdapterViewHolder.mRecipeImageView);
        recipesAdapterViewHolder.mRecipeTextView.setText(recipe.getName());


/*
            Picasso.with(context)
                    .load(posterPath)
                    //        .placeholder(R.drawable.cloud_download)
                    .error(R.drawable.alert_circle)
                    .into(movieArrayAdapterViewHolder.mMovieImageView);
        }
*/


    }

    @Override
    public int getItemCount() {
        if (null == mRecipes) return 0;
        return mRecipes.size();
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        mRecipes = recipes;
        notifyDataSetChanged();
    }

}
