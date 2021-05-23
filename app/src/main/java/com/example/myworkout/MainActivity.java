package com.example.myworkout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo.Create_Train_Activity;
import com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo.TrainActivity;
import com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo.TrainsListActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private Button bTrain, bCreateTrain, bSeeTrains, bSeeExercises, bSome,bLogout,bCount;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference,databaseReference1;
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
        bCount=findViewById(R.id.button_calory);
        bCreateTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Create_Train_Activity.class);
                intent.putExtra("ActivityName", "MainActivity");
                startActivity(intent);
            }
        });
        bSeeTrains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TrainsListActivity.class);
                intent.putExtra("training", false);
                startActivity(intent);
            }
        });
        bTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TrainsListActivity.class);
                intent.putExtra("training", true);
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
        bCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,CountCalory.class);
                startActivity(i);
            }
        });
        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAuth();
            }
        });

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        databaseReference1 = FirebaseDatabase.getInstance().getReference("Users");
        userIdDB=firebaseUser.getUid();

        final TextView greetingTV=findViewById(R.id.textViewGreeting);

        databaseReference1.child(userIdDB).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserAuth userAuth=snapshot.getValue(UserAuth.class);
                if(userAuth!=null){
                    String names = userAuth.name;
                    String login = userAuth.login;
                    greetingTV.setText("Привет, "+ names+ "! Удачной тренировки!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this,"We work on this",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void goToAuth() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Вы уверены, что хотите выйти?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this,AuthorizationActivity.class));
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

    @Override
    public void onBackPressed() {
        goToAuth();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
