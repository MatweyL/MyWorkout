package com.example.myworkoutcreatingtrainsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnCreateTrain, btnSeeTrains, btnTrain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCreateTrain = findViewById(R.id.button_create_train);
        btnCreateTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Create_Train_Activity.class);
                intent.putExtra("ActivityName", "MainActivity");
                startActivity(intent);
            }
        });
        btnSeeTrains = findViewById(R.id.button_trains_list);
        btnSeeTrains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TrainsListActivity.class);
                intent.putExtra("training", false);
                startActivity(intent);
            }
        });
        btnTrain = findViewById(R.id.buttonTrain);
        btnTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TrainsListActivity.class);
                intent.putExtra("training", true);
                startActivity(intent);
            }
        });
    }
}