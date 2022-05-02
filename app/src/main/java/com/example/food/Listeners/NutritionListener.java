package com.example.food.Listeners;


import com.example.food.Models.Nutrition;

import java.util.List;

public interface NutritionListener {
    void didFetch(List<Nutrition> response, String message);
    void didError(String message);
}
