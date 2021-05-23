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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myworkout.MainActivity;
import com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo.adapters.ExerciseAdapter;
import com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo.room.DBHelper;
import com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo.room.Dao.TrainDao;
import com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo.room.Exercise;
import com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo.room.Train;
import com.example.myworkout.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Create_Train_Activity extends AppCompatActivity {
    private String trainName, targetMuscles;
    private EditText etTargetMuscles, etTrainName;
    private Button btn_save;
    private FloatingActionButton btn_delete, btn_add;
    private TrainDao trainDao;
    private RecyclerView recyclerView;
    private Train train;
    private List<Exercise> exercises;
    private long lastTrainId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__train_);
        findAllWidgets();

        trainDao = DBHelper.getInstance(getApplicationContext()).getAppDatabase().getTrainDao();


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
            train = new Train(lastTrainId, "Тренировка " + Long.toString(lastTrainId), "нет", "0");
            addTrainInThread(trainDao, train);
        }

        etTrainName.setText(train.getName());
        etTargetMuscles.setText(train.getTargetMuscles());


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        prepareData(train);
        ExerciseAdapter adapter = new ExerciseAdapter(this, exercises, true);
        recyclerView.setAdapter(adapter);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                train.setName(getString(etTrainName, train.getName()));
                train.setTargetMuscles(getString(etTargetMuscles, "нет"));
                updateTrainInThread(trainDao, train);
                Intent intent = new Intent(Create_Train_Activity.this, Create_Exercise_Activity.class);
                intent.putExtra(Train.class.getSimpleName(), train);
                startActivity(intent);
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Проверка на заполненность всех полей
                if (checkData(etTrainName, etTargetMuscles)) {
                    if (exercises.size() > 0) {
                        train.setName(getString(etTrainName, train.getName()));
                        train.setTargetMuscles(getString(etTargetMuscles, "нет"));
                        updateTrainInThread(trainDao, train);
                        Intent intent = new Intent(Create_Train_Activity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Создайте хотя бы одно упражнение", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    if (!checkData(etTrainName)) {
                        Toast.makeText(getApplicationContext(), "Заполните поле имени", Toast.LENGTH_SHORT).show();
                    }
                    if (!checkData(etTargetMuscles)) {
                        Toast.makeText(getApplicationContext(), "Заполните поле целевых мышц", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTrain();
            }
        });
    }

    private void deleteTrain() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Create_Train_Activity.this);
        builder.setTitle("Вы уверены, что хотите удалить тренировку?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteTrainInThread(trainDao, train);
                Intent intent = new Intent(Create_Train_Activity.this, MainActivity.class);
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
        trainName = etTrainName.getText().toString();
        targetMuscles = etTargetMuscles.getText().toString();
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
    private String getString(EditText et){
        return et.getText().toString();
    }
    private String getString(EditText et, String defaultValue){
        if (TextUtils.isEmpty(et.getText().toString())) {
            return  defaultValue;
        }
        else {
            return et.getText().toString();
        }
    }
    private boolean checkData(EditText nm, EditText tm) {
        return !(TextUtils.isEmpty(getString(nm)) || TextUtils.isEmpty(getString(tm)));
    }
    private boolean checkData(EditText et) {
        return !(TextUtils.isEmpty(getString(et)));
    }
    private void findAllWidgets() {
        etTrainName = findViewById(R.id.editTextTrainNameView);
        etTargetMuscles = findViewById(R.id.editTextTargetMuscles);
        recyclerView = findViewById(R.id.recycler_view);
        btn_add = findViewById(R.id.button_add);
        btn_save = findViewById(R.id.button_save_train);
        btn_delete = findViewById(R.id.button_delete_creatingTrain);
    }

    @Override
    public void onBackPressed(){
        deleteTrain();
    }
}