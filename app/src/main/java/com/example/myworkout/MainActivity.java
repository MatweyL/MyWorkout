package com.example.myworkout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button bTrain, bCreateTrain, bSeeTrains, bSeeExercises, bSome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bTrain = findViewById(R.id.button_train);
        bCreateTrain = findViewById(R.id.button_create_train);
        bSeeTrains = findViewById(R.id.button_trains_list);
        bSeeExercises = findViewById(R.id.button_show_exercises_list);
        bSome = findViewById(R.id.button_some_b);
        bCreateTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateTrain.class);
                startActivity(intent);
            }
        });
    }
}