package com.example.myworkoutcreatingtrainsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myworkoutcreatingtrainsdemo.room.DBHelper;
import com.example.myworkoutcreatingtrainsdemo.room.Exercise;
import com.example.myworkoutcreatingtrainsdemo.room.Train;

public class UpdateExercise extends AppCompatActivity {
    EditText etName, etNumberReps, etNumberSets, etTimeExercise, etTimeRest;
    private Button btn_save, btn_delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_exercise);
        Exercise exercise = (Exercise) getIntent().getExtras().getSerializable(Exercise.class.getSimpleName());
        etName =findViewById(R.id.editTextName);
        etNumberReps = findViewById(R.id.editTextNumberOfReps);
        etNumberSets = findViewById(R.id.editTextNumberOfSets);
        etTimeExercise = findViewById(R.id.editTextTimeOfExercise);
        etTimeRest = findViewById(R.id.editTextTimeOfRest);

        etName.setText(exercise.getName());
        etNumberReps.setText(exercise.getRepsNumber());
        etNumberSets.setText(exercise.getSetsNumber());
        etTimeExercise.setText(exercise.getTimeExercise());
        etTimeRest.setText(exercise.getTimeRest());
        Train train = DBHelper.getInstance(getApplicationContext()).getAppDatabase().getTrainDao().getTrainById(exercise.getTrainId());
        btn_save = findViewById(R.id.button_save_exercise);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkData(etName, etNumberReps, etNumberSets, etTimeRest))
                {
                    decreaseTimeTraining(train, exercise);
                    exercise.setName(getString(etName));
                    exercise.setRepsNumber(getString(etNumberReps));
                    exercise.setSetsNumber(getString(etNumberSets));
                    exercise.setTimeExercise(getTimeExercise(etTimeExercise));
                    exercise.setTimeRest(getString(etTimeRest));
                    increaseTimeTraining(train, exercise);
                    updateExerciseInThread(exercise);
                    updateTrainTimeInThread(train);
                    Toast.makeText(getApplicationContext(), "Сохранено!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateExercise.this, Create_Train_Activity.class);

                    intent.putExtra(Train.class.getSimpleName(), train);
                    intent.putExtra("ActivityName", "UpdateExerciseActivity");
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Заполните все поля!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_delete = findViewById(R.id.button_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseTimeTraining(train, exercise);
                deleteExerciseInThread(exercise);
                Intent intent = new Intent(UpdateExercise.this, Create_Train_Activity.class);
                intent.putExtra(Train.class.getSimpleName(), train);
                intent.putExtra("ActivityName", "UpdateExerciseActivity");
                startActivity(intent);
            }
        });

    }
    private void increaseTimeTraining(Train train, Exercise exercise){
        train.setTimeOfTraining(Long.toString(Long.parseLong(train.getTimeOfTraining()) +
                Long.parseLong(exercise.getSetsNumber())*(Long.parseLong(exercise.getTimeRest()) + getTimeExLong(exercise.getTimeExercise()))) );
    }
    private long getTimeExLong(String timeEx){
        if (timeEx.equals("без времени")){
            return 0;
        }
        else {
            return Long.parseLong(timeEx);
        }
    }
    private void deleteExerciseInThread(Exercise exercise) {
        new Thread(new Runnable() {
            @Override
            public void run() {
               DBHelper.getInstance(getApplicationContext()).getAppDatabase().getExerciseDao().removeExercise(exercise);
            }
        }).start();
    }

    private void decreaseTimeTraining(Train train, Exercise exercise){
        train.setTimeOfTraining(Long.toString(
                Long.parseLong(train.getTimeOfTraining()) - (Long.parseLong(exercise.getTimeRest()) + Long.parseLong(exercise.getTimeExercise())) * Long.parseLong(exercise.getSetsNumber())
        ));
    }

    private void updateExerciseInThread(Exercise exercise) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DBHelper.getInstance(getApplicationContext()).getAppDatabase().getExerciseDao().updateExercise(exercise);
            }
        }).start();;
    }
    private String getTimeExercise(EditText et){
        if (TextUtils.isEmpty(et.getText().toString())){
            return  "без времени";
        }
        else {
            return etTimeExercise.getText().toString();
        }
    }

    private String getString(EditText et){
        return et.getText().toString();
    }
    private boolean checkData(EditText nm, EditText nR, EditText nS, EditText tR){
        return !(TextUtils.isEmpty(nm.getText().toString()) || TextUtils.isEmpty(nR.getText().toString()) ||
                TextUtils.isEmpty(nS.getText().toString())|| TextUtils.isEmpty(tR.getText().toString()));
    }

    private void updateTrainTimeInThread(Train train) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DBHelper.getInstance(getApplicationContext()).getAppDatabase().getTrainDao().updateTrain(train);
            }
        }).start();
    }
}