package com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo.room.DBHelper;
import com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo.room.Exercise;
import com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo.room.Train;
import com.example.myworkout.R;

public class UpdateExercise extends AppCompatActivity {
    EditText etName, etNumberReps, etNumberSets, etTimeExercise, etTimeRest;
    private Button btn_save, btn_delete, bp1, bp2, bp3, bp4, bm1, bm2, bm3, bm4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_exercise);
        Exercise exercise = (Exercise) getIntent().getExtras().getSerializable(Exercise.class.getSimpleName());
        findAllWidgets();
        setButtonsCommand();
        etName.setText(exercise.getName());
        etNumberReps.setText(exercise.getRepsNumber());
        etNumberSets.setText(exercise.getSetsNumber());
        etTimeExercise.setText(exercise.getTimeExercise());
        etTimeRest.setText(exercise.getTimeRest());
        Train train = DBHelper.getInstance(getApplicationContext()).getAppDatabase().getTrainDao().getTrainById(exercise.getTrainId());

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkData(etName, etNumberReps, etNumberSets, etTimeRest, etTimeExercise))
                {
                    decreaseTimeTraining(train, exercise);
                    exercise.setName(getString(etName));
                    exercise.setRepsNumber(getString(etNumberReps));
                    exercise.setSetsNumber(getString(etNumberSets));
                    exercise.setTimeExercise(getString(etTimeExercise));
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


        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateExercise.this);
                builder.setTitle("Вы уверены, что хотите удалить упражнение?");
                builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        decreaseTimeTraining(train, exercise);
                        deleteExerciseInThread(exercise);
                        Intent intent = new Intent(UpdateExercise.this, Create_Train_Activity.class);
                        intent.putExtra(Train.class.getSimpleName(), train);
                        intent.putExtra("ActivityName", "UpdateExerciseActivity");
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog ad = builder.create();
                ad.show();

            }
        });


    }
    private void increaseTimeTraining(Train train, Exercise exercise){
        train.setTimeOfTraining(Long.toString(Long.parseLong(train.getTimeOfTraining()) +
                Long.parseLong(exercise.getSetsNumber())*(Long.parseLong(exercise.getTimeRest()))) );
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
                Long.parseLong(train.getTimeOfTraining()) - (Long.parseLong(exercise.getTimeRest())) * Long.parseLong(exercise.getSetsNumber())
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

    private String getString(EditText et){
        return et.getText().toString();
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

    private void findAllWidgets(){
        etName =findViewById(R.id.editTextName);
        etNumberReps = findViewById(R.id.editTextNumberOfReps);
        etNumberSets = findViewById(R.id.editTextNumberOfSets);
        etTimeExercise = findViewById(R.id.editTextNumberOfWeight);
        etTimeRest = findViewById(R.id.editTextTimeOfRest);
        bp1 = findViewById(R.id.button_p1u);
        bm1 = findViewById(R.id.button_m1u);
        bp2 = findViewById(R.id.button_p2u);
        bm2 = findViewById(R.id.button_m2u);
        bp3 = findViewById(R.id.button_p3u);
        bm3 = findViewById(R.id.button_m3u);
        bp4 = findViewById(R.id.button_p4u);
        bm4 = findViewById(R.id.button_m4u);
        btn_save = findViewById(R.id.button_save_exercise);
        btn_delete = findViewById(R.id.button_delete);
    }

    private void setButtonsCommand(){
        bm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(getString(etNumberReps)) > 1) {
                    etNumberReps.setText(Integer.toString(-1 + Integer.parseInt(getString(etNumberReps))));
                }
            }
        });
        bm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(getString(etNumberSets)) > 1) {
                    etNumberSets.setText(Integer.toString(-1 + Integer.parseInt(getString(etNumberSets))));
                }
            }
        });
        bm3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(getString(etTimeExercise)) > 0) {
                    etTimeExercise.setText(Integer.toString(-1 + Integer.parseInt(getString(etTimeExercise))));
                }
            }
        });
        bm4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(getString(etTimeRest)) > 1) {
                    etTimeRest.setText(Integer.toString(-1 + Integer.parseInt(getString(etTimeRest))));
                }
            }
        });
        bp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etNumberReps.setText(Integer.toString(1 + Integer.parseInt(getString(etNumberReps))));
            }
        });
        bp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etNumberSets.setText(Integer.toString(1 + Integer.parseInt(getString(etNumberSets))));
            }
        });
        bp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etTimeExercise.setText(Integer.toString(1 + Integer.parseInt(getString(etTimeExercise))));
            }
        });
        bp4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etTimeRest.setText(Integer.toString(1 + Integer.parseInt(getString(etTimeRest))));
            }
        });
    }
}