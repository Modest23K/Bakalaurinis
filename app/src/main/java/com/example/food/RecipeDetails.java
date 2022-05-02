package com.example.food;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food.Adapters.IngredientsAdapter;
import com.example.food.Adapters.InstructionsAdaper;
import com.example.food.Adapters.NutritionAdapter;
import com.example.food.Adapters.SimilarRecipeAdapter;
import com.example.food.Listeners.InstructionsListener;
import com.example.food.Listeners.NutritionListener;
import com.example.food.Listeners.RecipeClickListener;
import com.example.food.Listeners.RecipeDetailsListener;
import com.example.food.Listeners.SimilarRecipesListener;
import com.example.food.Models.Instructions;
import com.example.food.Models.Nutrition;
import com.example.food.Models.NutritionAPI;
import com.example.food.Models.RecipeDetailsResponse;
import com.example.food.Models.SimilarRecipeResponse;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class RecipeDetails extends AppCompatActivity {
    int id;
    String meal_name;

    TextView textView_meal_name,textView_meal_source,summary,textView_fat,textView_carbs,textView_calories,info1;
    ImageView imageView_meal;
    RecyclerView recycler_meal_ingredients,Recycler_meal_similar,recycler_meal_instructions, recycler_nutrition;
    RequestManager manager;
    ProgressDialog dialog;
    IngredientsAdapter ingredientsAdapter;
    SimilarRecipeAdapter similarRecipeAdapter;
    InstructionsAdaper instructionsAdaper;
    NutritionAdapter nutritionAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        findViews();
        id = Integer.parseInt(getIntent().getStringExtra("id"));
        manager = new RequestManager(this);
        manager.getRecipeDetails(recipeDetailsListener, id, true);
        manager.getSimilarRecipes(similarRecipesListener, id);
        manager.getInstructions(instructionsListener, id);
        //manager.getNutrition(nutritionListener,id);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading...");
        dialog.show();

        Button share = findViewById(R.id.sharebutton);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.setType("text/plain");
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this great recipe " +meal_name+" in the FOOD app");
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out this great recipe " +meal_name+" in the FOOD app");
                startActivity(Intent.createChooser(sendIntent,"Share using"));
            }
        });
    }

    private void findViews() {
        textView_meal_name = findViewById(R.id.textView_meal_name);
        textView_meal_source = findViewById(R.id.textView_meal_source);
        imageView_meal = findViewById(R.id.imageView_meal);
        recycler_meal_ingredients = findViewById(R.id.recycler_meal_ingredients);
        Recycler_meal_similar = findViewById(R.id.Recycler_meal_similar);
        recycler_meal_instructions = findViewById(R.id.recycler_meal_instructions);
       // recycler_nutrition = findViewById(R.id.recycler_nutrition);
        textView_fat = findViewById(R.id.textView_fat);
        textView_carbs = findViewById(R.id.textView_carbs);
        textView_calories = findViewById(R.id.textView_calories);
        //textView_protein = findViewById(R.id.textView_protein);
        info1=findViewById(R.id.info1);
    }


    private final NutritionListener nutritionListener = new NutritionListener() {

        @Override
        public void didFetch(List<Nutrition> response, String message) {

            recycler_nutrition.setHasFixedSize(true);
            recycler_nutrition.setLayoutManager(new LinearLayoutManager(RecipeDetails.this, LinearLayoutManager.HORIZONTAL, false));
            nutritionAdapter = new NutritionAdapter(RecipeDetails.this,  response);
            recycler_nutrition.setAdapter(nutritionAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetails.this, message,Toast.LENGTH_SHORT).show();
        }
    };

    private final RecipeDetailsListener recipeDetailsListener = new RecipeDetailsListener() {
        @Override
        public void didFetch(RecipeDetailsResponse response, String message) {
            dialog.dismiss();
            textView_meal_name.setText(response.title);
            meal_name = response.title;

            textView_meal_source.setText(response.sourceName);

            String part1=response.summary.replaceAll("</b>","");
            String part2 = part1.replace("<b>","");

            info1.setText(part2.split("<a href")[0]);
            Picasso.get().load(response.image).into(imageView_meal);

            recycler_meal_ingredients.setHasFixedSize(true);
            recycler_meal_ingredients.setLayoutManager(new LinearLayoutManager(RecipeDetails.this, LinearLayoutManager.HORIZONTAL, false));
            ingredientsAdapter = new IngredientsAdapter(RecipeDetails.this, response.extendedIngredients);
            recycler_meal_ingredients.setAdapter(ingredientsAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetails.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private final SimilarRecipesListener similarRecipesListener = new SimilarRecipesListener() {
        @Override
        public void didFetch(List<SimilarRecipeResponse> response, String message) {
            Recycler_meal_similar.setHasFixedSize(true);
            Recycler_meal_similar.setLayoutManager(new LinearLayoutManager(RecipeDetails.this, LinearLayoutManager.HORIZONTAL, false));
            similarRecipeAdapter = new SimilarRecipeAdapter(RecipeDetails.this,response,recipeClickListener);
            Recycler_meal_similar.setAdapter(similarRecipeAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetails.this, message,Toast.LENGTH_SHORT).show();
        }
    };

    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {
            startActivity(new Intent(RecipeDetails.this,RecipeDetails.class)
                    .putExtra("id",id));

        }
    };

    private final InstructionsListener instructionsListener = new InstructionsListener() {
        @Override
        public void didFetch(List<Instructions> response, String message) {
            recycler_meal_instructions.setHasFixedSize(true);
            recycler_meal_instructions.setLayoutManager(new LinearLayoutManager(RecipeDetails.this,LinearLayoutManager.VERTICAL,false));
            instructionsAdaper = new InstructionsAdaper(RecipeDetails.this, response);
            recycler_meal_instructions.setAdapter(instructionsAdaper);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetails.this, message,Toast.LENGTH_SHORT).show();
        }
    };
}