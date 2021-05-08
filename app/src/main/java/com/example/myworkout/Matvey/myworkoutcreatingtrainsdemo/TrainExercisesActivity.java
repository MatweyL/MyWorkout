package com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo.adapters.ExerciseAdapter;
import com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo.room.DBHelper;
import com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo.room.Exercise;
import com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo.room.Train;
import com.example.myworkout.R;

import java.util.List;

public class TrainExercisesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView trainNameView, targetMusclesView, textViewTimeOfTrain2;
    private Button btn_update;
    private List<Exercise> exercises;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_exercises);

        Train train = (Train) getIntent().getExtras().getSerializable(Train.class.getSimpleName());

        trainNameView = findViewById(R.id.textViewNameOfTrain);
        targetMusclesView = findViewById(R.id.textViewTargetMusclesInTrain);
        trainNameView.setText(train.getName());
        targetMusclesView.setText(train.getTargetMuscles());
        textViewTimeOfTrain2 = findViewById(R.id.textViewTimeOfTrain2);
        String trTime = Double.toString((Double.parseDouble(train.getTimeOfTraining()) / 60));
        textViewTimeOfTrain2.setText(trTime);
        recyclerView = findViewById(R.id.recycler_view_exercises_in_train);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        prepareData(train);
        ExerciseAdapter adapter = new ExerciseAdapter(this, exercises, false);
        recyclerView.setAdapter(adapter);

        btn_update = findViewById(R.id.button_update_train);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainExercisesActivity.this, Create_Train_Activity.class);
                intent.putExtra(Train.class.getSimpleName(), train);
                intent.putExtra("ActivityName", "TrainExercisesActivity");
                startActivity(intent);
            }
        });
    }
    private void prepareData(Train train) {
        exercises = DBHelper.getInstance(getApplicationContext()).getAppDatabase().getTrainDao().getTrainExercisesById(train.getId());
    }
}