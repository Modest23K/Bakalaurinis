package com.example.food.Listeners;

import com.example.food.Models.Instructions;

import java.util.List;

public interface InstructionsListener {
    void didFetch(List<Instructions> response, String message);
    void didError(String message);
}
