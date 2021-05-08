package com.example.myworkoutcreatingtrainsdemo.room.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myworkoutcreatingtrainsdemo.room.Exercise;

import java.util.List;

@Dao
public interface ExerciseDao {
    @Insert
    void addExercise(Exercise exercise);
    @Delete
    void removeExercise(Exercise exercise);
    @Update
    void updateExercise(Exercise exercise);

    @Query("SELECT * FROM Exercise")
    List<Exercise> getExercises();

    @Query("SELECT * FROM Exercise WHERE id = :exerciseId")
    Exercise getExerciseById(long exerciseId);

}
