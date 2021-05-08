package com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


import com.example.myworkout.MainActivity;
import com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo.room.DBHelper;
import com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo.room.Exercise;
import com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo.room.Train;
import com.example.myworkout.R;

import java.util.List;

public class TrainActivity extends AppCompatActivity {
    private TextView tvNumOfSet, tvNumOfRep, tvNameOfExercise, tvNameOfTrain, tvStateExercise, tvTimer, tvCurrentEx, tvCurrentSet;
    private TableLayout tlSetNum, tlAboutEx;
    private ConstraintLayout clStateTimer;
    private Button cmd;
    private Train train;
    private List<Exercise> exercises;
    private Exercise tempExercise;
    private int command = 0, counter = 0;
    private long sets, setsNumber, timeRest;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);

        train = (Train) getIntent().getExtras().getSerializable(Train.class.getSimpleName());
        exercises = DBHelper.getInstance(getApplicationContext()).getAppDatabase().getTrainDao().getTrainExercisesById(train.getId());

        findAllWidgets();
        setAllWidgetsVisibility(false);
        startTrain();


    }

    private void findAllWidgets() {
        tvNumOfSet = findViewById(R.id.textViewSetNumTr);
        tvNumOfRep = findViewById(R.id.textViewRepNumTr);
        tvNameOfExercise = findViewById(R.id.textViewExerciseNameTr);
        tvNameOfTrain = findViewById(R.id.textViewNameOfTrainTr);
        cmd = findViewById(R.id.buttonCommand);
        tvStateExercise = findViewById(R.id.textViewStateExercise);
        tvTimer = findViewById(R.id.timer);
        tvCurrentEx = findViewById(R.id.tvCurrentExercise);
        tvCurrentSet = findViewById(R.id.textViewNumOfSet);

        tlSetNum = findViewById(R.id.tlSetNum);
        tlAboutEx = findViewById(R.id.tlAboutExercise);

        clStateTimer = findViewById(R.id.clStateTimer);

    }
    private void setTimerVisibility(boolean visibility) {
        if (visibility == true) {
            clStateTimer.setVisibility(ConstraintLayout.VISIBLE);
        }
        else {
            clStateTimer.setVisibility(ConstraintLayout.GONE);
        }
    }
    private void setAllWidgetsVisibility(boolean visibility) {
        if (visibility == false) {
            tlSetNum.setVisibility(TableLayout.GONE);
            tlAboutEx.setVisibility(TableLayout.GONE);
            clStateTimer.setVisibility(ConstraintLayout.GONE);
            tvCurrentEx.setVisibility(TextView.GONE);
            tvNameOfExercise.setVisibility(TextView.GONE);
        } else {
            tlSetNum.setVisibility(TableLayout.VISIBLE);
            tlAboutEx.setVisibility(TableLayout.VISIBLE);
            clStateTimer.setVisibility(ConstraintLayout.VISIBLE);
            tvCurrentEx.setVisibility(TextView.VISIBLE);
            tvNameOfExercise.setVisibility(TextView.VISIBLE);
        }

    }

    private void startTrain(){
        tvNameOfExercise.setText(train.getName());
        cmd.setText("Начать тренировку!");
        cmdAction();
    }

    private void setExerciseWidgets(Exercise ex) {
            setsNumber = Long.parseLong(ex.getSetsNumber());
            timeRest = Long.parseLong(ex.getTimeRest());
            tvNameOfExercise.setText(ex.getName());
            tvNumOfRep.setText(ex.getRepsNumber());
            tvNumOfSet.setText(ex.getSetsNumber());
            cmd.setText("Подход готов!");
            sets = 1;
        }


    private Exercise getNextExercise() {
        if (counter < exercises.size()) {
            return exercises.get(counter);
        }
        else {
            return null;
        }
    }

    private void cmdAction() {
        cmd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (command) {
                    case 0: {
                        cmd.setText("Подход готов!");
                        setAllWidgetsVisibility(true);
                        setTimerVisibility(false);
                        tempExercise = getNextExercise();
                        setExerciseWidgets(tempExercise);
                        counter++;
                        command = 1;
                        break;
                    }
                    case 1: {
                        if (sets < setsNumber) {
                            cmd.setText("Пропустить отдых");
                            sets++;
                            command = 2;
                        }
                        else {
                            cmd.setText("Следующее упражнение");
                            command = 3;
                        }
                        setTimerVisibility(true);
                        reverseTimer((int) timeRest, tvTimer);
                        break;
                    }
                    case 2: {
                        cmd.setText("Подход готов!");
                        tvCurrentSet.setText(Long.toString(sets));
                        countDownTimer.cancel();
                        setTimerVisibility(false);
                        command = 1;
                        break;
                    }
                    case 3: {
                        tempExercise = getNextExercise();
                        if (tempExercise == null)
                        {
                            setAllWidgetsVisibility(false);
                            cmd.setText("Выйти");
                            command = 4;
                            break;
                        }
                        setExerciseWidgets(tempExercise);
                        sets = 1;
                        tvCurrentSet.setText(Long.toString(sets));
                        countDownTimer.cancel();
                        setTimerVisibility(false);
                        counter++;
                        command = 1;
                        break;
                    }
                    case 4: {
                        Intent intent = new Intent(TrainActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    default: {
                        break;
                    }
                }
            }
        });
    }

    public void reverseTimer(int Seconds, final TextView tv){

        countDownTimer = new CountDownTimer(Seconds* 1000+1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                tv.setText(String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));
            }



            public void onFinish() {
                setTimerVisibility(false);
                tvCurrentSet.setText(Long.toString(sets));
                cmd.setText("Подход готов!");
                command = 1;
            }
        };
        countDownTimer.start();
    }
}