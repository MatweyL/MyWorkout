package com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Train implements Serializable {
    @PrimaryKey()
    long id;
    @ColumnInfo(name = "name")
    private String name;

    String timeOfTraining;

    public String getTimeOfTraining() {
        return timeOfTraining;
    }

    public void setTimeOfTraining(String timeOfTraining) {
        this.timeOfTraining = timeOfTraining;
    }

    public Train(long id, String name, String targetMuscles, String timeOfTraining) {
        this.id = id;
        this.name = name;
        this.targetMuscles = targetMuscles;
        this.timeOfTraining = timeOfTraining;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTargetMuscles() {
        return targetMuscles;
    }

    public void setTargetMuscles(String targetMuscles) {
        this.targetMuscles = targetMuscles;
    }

    @ColumnInfo(name = "targetMuscles")
    private String targetMuscles;
}
