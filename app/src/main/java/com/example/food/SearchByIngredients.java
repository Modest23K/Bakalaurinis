package com.example.food;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.food.Adapters.RandomRecipeAdaptor;
import com.example.food.Adapters.SearchByIngredientsAdaptor;
import com.example.food.Listeners.RecipeClickListener;
import com.example.food.Listeners.SearchByIngredientsListener;
import com.example.food.Models.SearchByIngredientsModel;

import java.util.ArrayList;
import java.util.List;

public class SearchByIngredients extends AppCompatActivity {
    SearchView searchView;
    RecyclerView recycler_byIngredients;
    SearchByIngredientsAdaptor searchByIngredientsAdaptor;
    RequestManager manager;
    String ingredients;
    RecipeClickListener recipeClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_ingredients);
        searchView = findViewById(R.id.searchView_home);
        recycler_byIngredients = findViewById(R.id.recycler_byIngredients);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                manager.CallRecipesByIngredients(searchByIngredientsListener, ingredients);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    private final SearchByIngredientsListener searchByIngredientsListener = new SearchByIngredientsListener() {


        @Override
        public void didFetch(SearchByIngredientsModel response, String message) {
            recycler_byIngredients = findViewById(R.id.recycler_random);
            recycler_byIngredients.setHasFixedSize(true);
            recycler_byIngredients.setLayoutManager(new GridLayoutManager(SearchByIngredients.this, 1));
            searchByIngredientsAdaptor = new SearchByIngredientsAdaptor(SearchByIngredients.this, response.usedIngredients, recipeClickListener);
            recycler_byIngredients.setAdapter(searchByIngredientsAdaptor);
        }

        @Override
        public void didError(String message) {

        }
    };
}