package com.example.myworkout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.squareup.picasso.Picasso;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FoodMyAdapter extends RecyclerView.Adapter<FoodMyAdapter.MyViewHolder> {
    Context context;
    ArrayList<Food> list;

    public FoodMyAdapter(Context context, ArrayList<Food> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_food,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Food food = list.get(position);
        holder.title.setText(food.getTitle());
        holder.protein.setText(Double.toString(food.getProtein()));
        holder.fats.setText(Double.toString(food.getFats()));
        holder.carbohydrates.setText(Double.toString(food.getCarbohydrates()));
        holder.calories.setText(Double.toString(food.getCalories()));
        //holder.urlFood.setText(food.getUrlFood());

        String imageURL=null;
        imageURL=food.getUrlFood();
        Picasso.get().load(imageURL).into(holder.imageView);
        //Picasso.get().load(food.getUrlFood()).into();

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder {

        TextView title,protein,fats, carbohydrates,calories, urlFood;
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitle);
            protein= itemView.findViewById(R.id.tvProtein);
            fats=itemView.findViewById(R.id.tvFats);
            carbohydrates=itemView.findViewById(R.id.tv??arbohydrates);
            calories =itemView.findViewById(R.id.tvCalory);
            //urlFood = itemView.findViewById(R.id.imageFood);
            imageView=itemView.findViewById(R.id.imageFood);
        }
    }
}
