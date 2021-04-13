package com.example.myworkout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    //private MainActivityMenu mainActivityMenuFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try{
            MainActivityMenu mainActivityMenuFragment = new MainActivityMenu();
            FragmentTransaction fr = getSupportFragmentManager().beginTransaction();
            fr.replace(R.id.container_main, mainActivityMenuFragment);
            fr.commit();
        }catch (Exception e){
            Log.i("In transaction", "blin(");
        }

    }
}
/*
com.example.myworkout public class MainActivityMenu
extends Fragment
A simple Fragment subclass. Use the MainActivityMenu.newInstance factory method to create an instance of this fragment.
  MyWorkout.app
 */