package com.example.myworkout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private Button bTrain, bCreateTrain, bSeeTrains, bSeeExercises, bSome,bLogout;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private String userIdDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bTrain = findViewById(R.id.button_train);
        bCreateTrain = findViewById(R.id.button_create_train);
        bSeeTrains = findViewById(R.id.button_trains_list);
        bSeeExercises = findViewById(R.id.button_show_exercises_list);
        bSome = findViewById(R.id.button_some_b);
        bLogout=findViewById(R.id.button_logout);
        bCreateTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateTrain.class);
                startActivity(intent);
            }
        });
        bSeeExercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,ReadActivity.class);
                startActivity(i);
            }
        });
        bSome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,FoodActivity.class);
                startActivity(i);

            }
        });
        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this,AuthorizationActivity.class));

            }
        });
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        userIdDB=firebaseUser.getUid();

        final TextView greetingTV=findViewById(R.id.textViewGreeting);

        databaseReference.child(userIdDB).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserAuth userAuth=snapshot.getValue(UserAuth.class);
                if(userAuth!=null){
                    String names = userAuth.name;
                    String login = userAuth.login;
                    greetingTV.setText("Hello, "+ names+ " Удачной тренировки!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this,"We work on this",Toast.LENGTH_LONG).show();
            }
        });
    }

}
