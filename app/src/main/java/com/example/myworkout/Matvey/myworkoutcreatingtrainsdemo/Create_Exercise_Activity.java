package com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo.room.DBHelper;
import com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo.room.Dao.ExerciseDao;
import com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo.room.Exercise;
import com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo.room.Train;
import com.example.myworkout.R;

public class Create_Exercise_Activity extends AppCompatActivity {
    EditText etName, etNumberReps, etNumberSets, etTimeExercise, etTimeRest;
    Button btn_save, bp1, bp2, bp3, bp4, bm1, bm2, bm3, bm4;
    ExerciseDao exerciseDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__exercise_);
        exerciseDao = DBHelper.getInstance(getApplicationContext()).getAppDatabase().getExerciseDao();
        Train train = (Train) getIntent().getExtras().getSerializable(Train.class.getSimpleName());
        long trainID =train.getId();
        findAllWidgets();
        setButtonsCommand();
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkData(etName, etNumberReps, etNumberSets, etTimeRest, etTimeExercise)){
                    Exercise exercise = new Exercise(0, trainID, getString(etName), getString(etNumberReps), getString(etNumberSets), getString(etTimeExercise), getString(etTimeRest));
                    addExerciseInThread(exercise);
                    increaseTimeTraining(train, exercise);
                    updateTrainTimeInThread(train);
                    Toast.makeText(getApplicationContext(), "Добавлено!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Create_Exercise_Activity.this, Create_Train_Activity.class);
                    intent.putExtra("ActivityName", "CreateExerciseActivity");
                    intent.putExtra(Train.class.getSimpleName(), train);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "Заполните все поля!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void findAllWidgets(){
        etName =findViewById(R.id.editTextName);
        etNumberReps = findViewById(R.id.editTextNumberOfReps);
        etNumberSets = findViewById(R.id.editTextNumberOfSets);
        etTimeExercise = findViewById(R.id.editTextNumberOfWeight);
        etTimeRest = findViewById(R.id.editTextTimeOfRest);
        btn_save = findViewById(R.id.button_save_exercise);
        bp1 = findViewById(R.id.button_p1c);
        bm1 = findViewById(R.id.button_m1c);
        bp2 = findViewById(R.id.button_p2c);
        bm2 = findViewById(R.id.button_m2c);
        bp3 = findViewById(R.id.button_p3c);
        bm3 = findViewById(R.id.button_m3c);
        bp4 = findViewById(R.id.button_p4c);
        bm4 = findViewById(R.id.button_m4c);
    }

    private void increaseTimeTraining(Train train, Exercise exercise){
        train.setTimeOfTraining(Long.toString(Long.parseLong(train.getTimeOfTraining()) +
                Long.parseLong(exercise.getSetsNumber())*(Long.parseLong(exercise.getTimeRest()))) );
    }


    private void addExerciseInThread(Exercise exercise)
    {
        new Thread(new Runnable() {
            public void run() {
                exerciseDao.addExercise(exercise);
            }
        }).start();
    }

    private String getString(EditText et){
        return et.getText().toString();
    }
    private String getString(EditText et, int defaultValue){
        if (TextUtils.isEmpty(et.getText().toString())) {
            return Integer.toString(defaultValue);
        }
        else {
            return et.getText().toString();
        }
    }

    private boolean checkData(EditText nm, EditText nR, EditText nS, EditText tR, EditText nW){
        return !(TextUtils.isEmpty(getString(nm)) || TextUtils.isEmpty(getString(nR)) ||
                TextUtils.isEmpty(getString(nS))|| TextUtils.isEmpty(getString(tR))
                || TextUtils.isEmpty(getString(nW)) || getString(nR).equals("0")
                || getString(nS).equals("0") || getString(tR).equals("0"));
    }

    private void updateTrainTimeInThread(Train train) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DBHelper.getInstance(getApplicationContext()).getAppDatabase().getTrainDao().updateTrain(train);
            }
        }).start();
    }
    private void setButtonsCommand(){
        bm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(getString(etNumberReps)) > 1) {
                    etNumberReps.setText(Integer.toString(-1 + Integer.parseInt(getString(etNumberReps, 0))));
                }
            }
        });
        bm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(getString(etNumberSets)) > 1) {
                    etNumberSets.setText(Integer.toString(-1 + Integer.parseInt(getString(etNumberSets, 0))));
                }
            }
        });
        bm3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(getString(etTimeExercise)) > 0) {
                    etTimeExercise.setText(Integer.toString(-1 + Integer.parseInt(getString(etTimeExercise, 0))));
                }
            }
        });
        bm4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(getString(etTimeRest)) > 1) {
                    etTimeRest.setText(Integer.toString(-1 + Integer.parseInt(getString(etTimeRest, 0))));
                }
            }
        });
        bp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etNumberReps.setText(Integer.toString(1 + Integer.parseInt(getString(etNumberReps, 0))));
            }
        });
        bp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etNumberSets.setText(Integer.toString(1 + Integer.parseInt(getString(etNumberSets, 0))));
            }
        });
        bp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etTimeExercise.setText(Integer.toString(1 + Integer.parseInt(getString(etTimeExercise, 0))));
            }
        });
        bp4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etTimeRest.setText(Integer.toString(1 + Integer.parseInt(getString(etTimeRest, 0))));
            }
        });
    }
}