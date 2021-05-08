package com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo.room;

import android.content.Context;

import androidx.room.Room;

public class DBHelper {

    private static DBHelper mInstance;
    private Context mCtx;
    private MyDatabase db;

    public DBHelper(Context mContext) {
        this.mCtx = mContext;
        db = Room.databaseBuilder(mCtx, MyDatabase.class, "db_name").allowMainThreadQueries().fallbackToDestructiveMigration().build();
    }

    public static DBHelper getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DBHelper(mCtx);
        }
        return mInstance;
    }

    public MyDatabase getAppDatabase() {
        return db;
    }
}
