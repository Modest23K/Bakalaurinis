package com.example.food;

import android.content.Context;

import com.example.food.Listeners.InstructionsListener;
import com.example.food.Listeners.NutritionListener;
import com.example.food.Listeners.RandomRecipeResponseListener;
import com.example.food.Listeners.RecipeDetailsListener;
import com.example.food.Listeners.SearchByIngredientsListener;
import com.example.food.Listeners.SimilarRecipesListener;
import com.example.food.Models.Instructions;
import com.example.food.Models.Nutrition;
import com.example.food.Models.RandomRecipeApiResponse;
import com.example.food.Models.RecipeDetailsResponse;
import com.example.food.Models.SearchByIngredientsModel;
import com.example.food.Models.SimilarRecipeResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RequestManager {
    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void getRandomRecipes(RandomRecipeResponseListener listener, List<String> tags){
        CallRandomRecipes callRandomRecipes = retrofit.create(CallRandomRecipes.class);
        Call<RandomRecipeApiResponse> call = callRandomRecipes.callRandomRecipe(context.getString(R.string.api_key),"10", tags );
        call.enqueue(new Callback<RandomRecipeApiResponse>() {
            @Override
            public void onResponse(Call<RandomRecipeApiResponse> call, Response<RandomRecipeApiResponse> response) {
                if(!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RandomRecipeApiResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }
    public void getNutrition(NutritionListener listener, int id){
        CallNutrition callNutrition = retrofit.create(CallNutrition.class);
        Call<List<Nutrition>> call = callNutrition.callNutrition(id, context.getString(R.string.api_key));
        call.enqueue(new Callback<List<Nutrition>>() {
            @Override
            public void onResponse(Call<List<Nutrition>> call, Response<List<Nutrition>> response) {
                if(!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<List<Nutrition>> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });

    }
    public void getRecipeDetails(RecipeDetailsListener listener, int id, boolean nutrition){
        CallRecipeDetails callRecipeDetails = retrofit.create(CallRecipeDetails.class);
        Call<RecipeDetailsResponse> call = callRecipeDetails.callRecipeDetails(id, nutrition, context.getString(R.string.api_key));
        call.enqueue(new Callback<RecipeDetailsResponse>() {
            @Override
            public void onResponse(Call<RecipeDetailsResponse> call, Response<RecipeDetailsResponse> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RecipeDetailsResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void getSimilarRecipes(SimilarRecipesListener listener, int id){
        CallSimilarRecipes callSimilarRecipes = retrofit.create(CallSimilarRecipes.class);
        Call<List<SimilarRecipeResponse>> call = callSimilarRecipes.callSimilarRecipe(id, "4", context.getString(R.string.api_key));
        call.enqueue(new Callback<List<SimilarRecipeResponse>>() {
            @Override
            public void onResponse(Call<List<SimilarRecipeResponse>> call, Response<List<SimilarRecipeResponse>> response) {
                if(!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<List<SimilarRecipeResponse>> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });

    }


    public void getInstructions(InstructionsListener listener, int id){

        CallInstructions callInstructions = retrofit.create(CallInstructions.class);
        Call<List<Instructions>> call = callInstructions.callInstructions(id, context.getString(R.string.api_key));
        call.enqueue(new Callback<List<Instructions>>() {
            @Override
            public void onResponse(Call<List<Instructions>> call, Response<List<Instructions>> response) {
                if(!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<List<Instructions>> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void CallRecipesByIngredients(SearchByIngredientsListener listener, String ingredients ){
        CallRecipesByIngredients callRecipesByIngredients = retrofit.create((CallRecipesByIngredients.class));
        Call<SearchByIngredientsModel> call = callRecipesByIngredients.callRecipesByIngredients(ingredients,context.getString(R.string.api_key));
        call.enqueue(new Callback<SearchByIngredientsModel>() {
            @Override
            public void onResponse(Call<SearchByIngredientsModel> call, Response<SearchByIngredientsModel> response) {
                if(!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<SearchByIngredientsModel> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }



    private interface CallRandomRecipes{
        @GET("recipes/random")
        Call<RandomRecipeApiResponse> callRandomRecipe(
                @Query("apiKey") String apiKey,
                @Query("number") String number,
                @Query("tags") List<String> tags
        );
    }
    private interface CallRecipeDetails{
        @GET("recipes/{id}/information")
        Call<RecipeDetailsResponse> callRecipeDetails(
                @Path("id") int id,
                @Query("includeNutrition") boolean nutrition,
                @Query("apiKey") String apiKey

        );
    }
    private interface CallSimilarRecipes{
        @GET("recipes/{id}/similar")
        Call<List<SimilarRecipeResponse>> callSimilarRecipe(
                @Path("id") int id,
                @Query("number") String number,
                @Query("apiKey") String apiKey
        );
    }
    private interface CallInstructions{
        @GET("recipes/{id}/analyzedInstructions")
        Call<List<Instructions>> callInstructions(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }
    private interface CallRecipesByIngredients{
        @GET("recipes/findByIngredients")
        Call<SearchByIngredientsModel> callRecipesByIngredients(
                @Query("apiKey") String apiKey,
                @Query("ingredients") String ingredients
        );
    }
    private interface CallNutrition{
        @GET("recipes/{id}/nutritionWidget.json")
        Call<List<Nutrition>> callNutrition(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }
}
