package com.example.myworkout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {
    private EditText emailET;
    private Button buttonReset;
    private ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        emailET=findViewById(R.id.RPEditTextPassword);
        buttonReset = findViewById(R.id.RPRegButton);
        //progressBar=findViewById(R.id.progressBar2);
        firebaseAuth=FirebaseAuth.getInstance();
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonReset();
            }
        });

    }

    private void buttonReset() {
        String login = emailET.getText().toString().trim();
        if (login.isEmpty()){
            emailET.setError("Введите логин");
            emailET.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(login).matches()){
            emailET.setError("Введите корректный логин");
            emailET.requestFocus();
            return;
        }
        //progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.sendPasswordResetEmail(login).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ResetPassword.this,"Проверьте вашу почту", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(ResetPassword.this,"Попробуйте снова",Toast.LENGTH_LONG).show();

                }
            }
        });
    }

}