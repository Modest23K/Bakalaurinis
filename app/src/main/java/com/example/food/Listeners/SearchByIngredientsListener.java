package com.example.food.Listeners;

import com.example.food.Models.SearchByIngredientsModel;

public interface SearchByIngredientsListener {
    void didFetch(SearchByIngredientsModel response, String message);
    void didError(String message);
}
