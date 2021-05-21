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
    private TextView trainNameView, targetMusclesView, textViewTimeOfTrainMinutes2, textViewTimeOfTrainSeconds2;
    private Button btn_update, btn_share;
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

        textViewTimeOfTrainMinutes2 = findViewById(R.id.textViewTimeOfTrainMinutes2);
        textViewTimeOfTrainSeconds2 = findViewById(R.id.textViewTimeOfTrainSeconds2);
        Integer trTimeMinutes = Integer.parseInt(train.getTimeOfTraining()) / 60;
        Integer trTimeSeconds = Integer.parseInt(train.getTimeOfTraining()) % 60;
        textViewTimeOfTrainMinutes2.setText(Integer.toString(trTimeMinutes));
        textViewTimeOfTrainSeconds2.setText(Integer.toString(trTimeSeconds));
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

        btn_share = findViewById(R.id.button_share_train);
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer message = new StringBuffer();
                message.append("Составил крутую тренировку в приложении MyWorkout, оцените)\n");
                message.append("По времени она занимает около ").append(trTimeMinutes).append(" мин. ").append(trTimeSeconds).append(" сек").append('\n');
                message.append("Целевые мышцы: ");
                message.append(train.getTargetMuscles()).append('\n').append("Вот список упражнений:").append('\n');
                for (Exercise ex: exercises) {
                    message.append(ex.getName()).append(": ").append(ex.getSetsNumber()).append('x').append(ex.getRepsNumber()).append(", ");
                    if (!ex.getTimeExercise().equals("0")) {
                        message.append("вес снаряда - ").append(ex.getTimeExercise()).append(" кг, ");
                    }
                    message.append("отдых между подходами - ").append(ex.getTimeRest()).append(" c;").append('\n');
                }
                message.append("Пойду тренироваться!");
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String msg =  message.toString();
                intent.putExtra(Intent.EXTRA_TEXT, msg);
                Intent selectIntent = Intent.createChooser(intent, "Как вы хотите поделиться тренировкой&");
                startActivity(selectIntent);
            }
        });
    }
    private void prepareData(Train train) {
        exercises = DBHelper.getInstance(getApplicationContext()).getAppDatabase().getTrainDao().getTrainExercisesById(train.getId());
    }
}