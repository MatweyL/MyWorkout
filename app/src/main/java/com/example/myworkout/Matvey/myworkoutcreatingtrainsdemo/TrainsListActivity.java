package com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myworkout.MainActivity;
import com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo.adapters.TrainAdapter;
import com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo.room.DBHelper;
import com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo.room.Train;
import com.example.myworkout.R;

import java.util.List;


public class TrainsListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Train> trains;
    private boolean training;
    private ImageButton button_to_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trains_list);
        recyclerView = findViewById(R.id.recycler_view_trains);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        prepareData();
        training = getIntent().getExtras().getBoolean("training", false);
        TrainAdapter adapter = new TrainAdapter(this, trains, training);
        recyclerView.setAdapter(adapter);
        button_to_menu = findViewById(R.id.button_back_to_main_menuM);
        button_to_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainsListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void prepareData() {
        trains = DBHelper.getInstance(getApplicationContext()).getAppDatabase().getTrainDao().getTrains();
    }
}