package com.example.myworkoutcreatingtrainsdemo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myworkoutcreatingtrainsdemo.adapters.TrainAdapter;
import com.example.myworkoutcreatingtrainsdemo.room.DBHelper;
import com.example.myworkoutcreatingtrainsdemo.room.Train;

import java.util.List;

public class TrainsListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Train> trains;
    private boolean training;

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

    }

    private void prepareData() {
        trains = DBHelper.getInstance(getApplicationContext()).getAppDatabase().getTrainDao().getTrains();
    }
}