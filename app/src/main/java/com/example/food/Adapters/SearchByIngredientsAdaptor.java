package com.example.food.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.Listeners.RecipeClickListener;
import com.example.food.Models.Recipe;
import com.example.food.Models.UsedIngredient;
import com.example.food.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchByIngredientsAdaptor extends RecyclerView.Adapter<SearchByIngredientsViewHolder>{
    Context context;
    List<UsedIngredient> ingredients= new ArrayList<>();
    RecipeClickListener listener;

    public SearchByIngredientsAdaptor(Context context, ArrayList<UsedIngredient> ingredients, RecipeClickListener listener) {
        this.context = context;
        this.ingredients = ingredients;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SearchByIngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchByIngredientsViewHolder(LayoutInflater.from(context).inflate(R.layout.list_random_recipe,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchByIngredientsViewHolder holder, int position) {
       // holder.textView_title.setText(ingredients.get(position).title);
        //holder.textView_title.setSelected(true);
       // holder.textView_likes.setText(ingredients.get(position).aggregateLikes+" Likes");
       // holder.textView_servings.setText(ingredients.get(position).servings+" Servings");
       // holder.textView_time.setText(ingredients.get(position).readyInMinutes+" Minutes");
        Picasso.get().load(ingredients.get(position).image).into(holder.imageView_food);

        holder.random_list_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRecipeClicked(String.valueOf(ingredients.get(holder.getAdapterPosition()).id));
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }
}
class SearchByIngredientsViewHolder extends RecyclerView.ViewHolder{
    CardView random_list_container;
    TextView textView_title,textView_servings,textView_likes,textView_time;
    ImageView imageView_food;
    public SearchByIngredientsViewHolder(@NonNull View itemView) {
        super(itemView);
        random_list_container = itemView.findViewById(R.id.random_list_container);
        textView_title = itemView.findViewById(R.id.textView_title);
        textView_servings = itemView.findViewById(R.id.textView_servings);
        textView_likes = itemView.findViewById(R.id.textView_likes);
        textView_time = itemView.findViewById(R.id.textView_time);
        imageView_food = itemView.findViewById(R.id.imageView_food);

    }
}
