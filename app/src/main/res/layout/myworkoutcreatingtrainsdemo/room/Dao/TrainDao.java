package com.example.myworkoutcreatingtrainsdemo.room.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myworkoutcreatingtrainsdemo.room.Exercise;
import com.example.myworkoutcreatingtrainsdemo.room.Train;

import java.util.List;

@Dao
public interface TrainDao {
    @Insert
    void addTrain(Train train);
    @Delete
    void removeTrain(Train train);
    @Update
    void updateTrain(Train train);

    @Query("SELECT * FROM Train")
    List<Train> getTrains();

    @Query("SELECT * FROM Train WHERE id = :trainId")
    Train getTrainById(long trainId);

    @Query("SELECT * FROM Exercise WHERE trainId = :thisTrainId")
    List<Exercise> getTrainExercisesById(long thisTrainId);

    @Query("SELECT id FROM Train ORDER BY id DESC LIMIT 1")
    long getLastId();
}
