package com.example.myworkoutcreatingtrainsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myworkoutcreatingtrainsdemo.adapters.ExerciseAdapter;
import com.example.myworkoutcreatingtrainsdemo.room.DBHelper;
import com.example.myworkoutcreatingtrainsdemo.room.Dao.TrainDao;
import com.example.myworkoutcreatingtrainsdemo.room.Exercise;
import com.example.myworkoutcreatingtrainsdemo.room.Train;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Create_Train_Activity extends AppCompatActivity {
    private String trainName, targetMuscles;
    private TextView trainNameView, targetMusclesView;
    private Button btn_save;
    private FloatingActionButton btn_delete, btn_add;
    private TrainDao trainDao;
    private RecyclerView recyclerView;
    private List<Exercise> exercises;
    private long lastTrainId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__train_);
        trainNameView = findViewById(R.id.trainNameView);
        targetMusclesView = findViewById(R.id.targetMusclesView);

        //Вызвать диалоговое окошко с вводом имени и целевых мышц тренировки
        trainDao = DBHelper.getInstance(getApplicationContext()).getAppDatabase().getTrainDao();
        Train train;

        if (getIntent().getStringExtra("ActivityName").equals("CreateExerciseActivity"))
        {
            train = (Train) getIntent().getExtras().getSerializable(Train.class.getSimpleName());
        }
        else if (getIntent().getStringExtra("ActivityName").equals("TrainExercisesActivity")) {
            train = (Train) getIntent().getExtras().getSerializable(Train.class.getSimpleName());
        }
        else if (getIntent().getStringExtra("ActivityName").equals("UpdateExerciseActivity")) {
            train = (Train) getIntent().getExtras().getSerializable(Train.class.getSimpleName());
        }
        else//Если из MainActivity
        {
            setLastTrainId(trainDao);
            //train = new Train(lastTrainId, trainName, targetMuscles);
            train = new Train(lastTrainId, "Тренировка", "нет", "0");
            addTrainInThread(trainDao, train);
        }
        trainNameView.setText(train.getName());
        targetMusclesView.setText(train.getTargetMuscles());
        trainNameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                train.setName(train.getName()+ " " + train.getId());
                trainNameView.setText(train.getName());
            }
        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        prepareData(train);
        ExerciseAdapter adapter = new ExerciseAdapter(this, exercises, true);
        recyclerView.setAdapter(adapter);

        btn_add = findViewById(R.id.button_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Create_Train_Activity.this, Create_Exercise_Activity.class);
                intent.putExtra(Train.class.getSimpleName(), train);
                startActivity(intent);
            }
        });

        btn_save = findViewById(R.id.button_save_train);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Проверка на заполненность всех полей
                updateTrainInThread(trainDao, train);
                Intent intent = new Intent(Create_Train_Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn_delete = findViewById(R.id.button_delete_creatingTrain);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTrainInThread(trainDao, train);
                Intent intent = new Intent(Create_Train_Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setLastTrainId(TrainDao trainDao) {
            if (!Long.toString(trainDao.getLastId()).equals("")) {
                lastTrainId = trainDao.getLastId() + 1;
            } else {
                lastTrainId = 0;
            }

    }

    private void prepareData(Train train) {
        exercises = DBHelper.getInstance(getApplicationContext()).getAppDatabase().getTrainDao().getTrainExercisesById(train.getId());
    }

    private void addTrainInThread(TrainDao trainDao, Train train) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                trainDao.addTrain(train);
            }
        }).start();
    }

    private void updateTrainInThread(TrainDao trainDao, Train train) {
        trainName = trainNameView.getText().toString();
        targetMuscles = targetMusclesView.getText().toString();
        train.setName(trainName);
        train.setTargetMuscles(targetMuscles);
        new Thread(new Runnable() {
            @Override
            public void run() {
                trainDao.updateTrain(train);
            }
        }).start();
    }
    private void deleteTrainInThread(TrainDao trainDao, Train train) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                trainDao.removeTrain(train);
            }
        }).start();
    }
}