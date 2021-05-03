package com.example.myworkout;

import android.widget.ImageView;

public class Food {
    String title,characteristic,calories, urlFood;
    ImageView imageView2;

    public ImageView getImageView2() {
        return imageView2;
    }

    public void setImageView2(ImageView imageView2) {
        this.imageView2 = imageView2;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getUrlFood() {
        return urlFood;
    }

    public void setUrlFood(String urlFood) {
        this.urlFood = urlFood;
    }
}
