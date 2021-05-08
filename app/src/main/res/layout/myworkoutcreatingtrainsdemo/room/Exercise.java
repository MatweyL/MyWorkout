package com.example.myworkoutcreatingtrainsdemo.room;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Train.class,
        parentColumns = "id",
        childColumns = "trainId",
        onDelete = CASCADE))
public class Exercise implements Serializable {
    @PrimaryKey(autoGenerate = true)
    long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTrainId() {
        return trainId;
    }

    public void setTrainId(long trainId) {
        this.trainId = trainId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRepsNumber() {
        return repsNumber;
    }

    public void setRepsNumber(String repsNumber) {
        this.repsNumber = repsNumber;
    }

    public String getSetsNumber() {
        return setsNumber;
    }

    public void setSetsNumber(String setsNumber) {
        this.setsNumber = setsNumber;
    }

    public String getTimeExercise() {
        return timeExercise;
    }

    public void setTimeExercise(String timeExercise) {
        this.timeExercise = timeExercise;
    }

    public String getTimeRest() {
        return timeRest;
    }

    public void setTimeRest(String timeRest) {
        this.timeRest = timeRest;
    }

    public Exercise(long id, long trainId, String name, String repsNumber, String setsNumber, String timeExercise, String timeRest) {
        this.id = id;
        this.trainId = trainId;
        this.name = name;
        this.repsNumber = repsNumber;
        this.setsNumber = setsNumber;
        this.timeExercise = timeExercise;
        this.timeRest = timeRest;
    }

    long trainId;
    private String name;
    private String repsNumber;
    private String setsNumber;
    private String timeExercise;
    private String timeRest;


}
