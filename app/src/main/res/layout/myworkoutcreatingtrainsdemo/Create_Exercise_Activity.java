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
import com.example.myworkoutcreatingtrainsdemo.room.Dao.ExerciseDao;
import com.example.myworkoutcreatingtrainsdemo.room.Exercise;
import com.example.myworkoutcreatingtrainsdemo.room.Train;

public class Create_Exercise_Activity extends AppCompatActivity {
    EditText etName, etNumberReps, etNumberSets, etTimeExercise, etTimeRest;
    Button btn_save;
    ExerciseDao exerciseDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__exercise_);
        exerciseDao = DBHelper.getInstance(getApplicationContext()).getAppDatabase().getExerciseDao();
        Train train = (Train) getIntent().getExtras().getSerializable(Train.class.getSimpleName());
        long trainID =train.getId();
        etName =findViewById(R.id.editTextName);
        etNumberReps = findViewById(R.id.editTextNumberOfReps);
        etNumberSets = findViewById(R.id.editTextNumberOfSets);
        etTimeExercise = findViewById(R.id.editTextTimeOfExercise);
        etTimeRest = findViewById(R.id.editTextTimeOfRest);
        btn_save = findViewById(R.id.button_save_exercise);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkData(etName, etNumberReps, etNumberSets, etTimeRest)){
                    String timeExercise = getTimeExercise(etTimeExercise);
                    Exercise exercise = new Exercise(0, trainID, getString(etName), getString(etNumberReps), getString(etNumberSets), timeExercise, getString(etTimeRest));
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

    private String getTimeExercise(EditText et){
        if (TextUtils.isEmpty(et.getText().toString())){
            return  "без времени";
        }
        else {
            return etTimeExercise.getText().toString();
        }
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