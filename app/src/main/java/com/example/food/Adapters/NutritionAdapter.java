package com.example.food.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.Models.Nutrition;
import com.example.food.Models.NutritionAPI;
import com.example.food.Models.Step;
import com.example.food.R;
import com.example.food.RecipeDetails;

import java.util.List;

public class NutritionAdapter extends RecyclerView.Adapter<NutritionViewHolder>{
    public String calories;
    public String carbs;
    public String fat;
    public String protein;
    Context context;
    List<Nutrition> list;

    public NutritionAdapter(Context context, List<Nutrition> list) {
        this.context = context;
        this.list = list;
    }



    @NonNull
    @Override
    public NutritionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NutritionViewHolder(LayoutInflater.from(context).inflate(R.layout.list_nutrition,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull NutritionViewHolder holder, int position) {
        holder.textView_calories.setText(list.get(position).calories);
        holder.textView_carbs.setText(list.get(position).carbs);
        holder.textView_protein.setText(list.get(position).protein);
        holder.textView_fat.setText(list.get(position).fat);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class NutritionViewHolder extends RecyclerView.ViewHolder{

    CardView Nutrition_conteiner;
    TextView textView_fat, textView_carbs, textView_calories, textView_protein;
    public NutritionViewHolder(@NonNull View itemView) {
        super(itemView);
        Nutrition_conteiner = itemView.findViewById(R.id.Nutrition_conteiner);
        textView_fat = itemView.findViewById(R.id.textView_fat);
        textView_carbs = itemView.findViewById(R.id.textView_carbs);
        textView_calories = itemView.findViewById(R.id.textView_calories);
        textView_protein = itemView.findViewById(R.id.textView_protein);


    }
}
