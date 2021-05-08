package com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo.room.Dao.ExerciseDao;
import com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo.room.Dao.TrainDao;

@Database(entities = {Train.class, Exercise.class}, version = 3)
public abstract class MyDatabase extends RoomDatabase {
    public abstract TrainDao getTrainDao();
    public abstract ExerciseDao getExerciseDao();
}
