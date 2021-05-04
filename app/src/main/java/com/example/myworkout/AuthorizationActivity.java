package com.example.myworkout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthorizationActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvRegister;
    private EditText editTextLA,editTextPA;
    private Button buttonSigIn;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization_layout);
        tvRegister = findViewById(R.id.Au_textView2);
        tvRegister.setOnClickListener(this);
        buttonSigIn = findViewById(R.id.AuRegButton);
        buttonSigIn.setOnClickListener(this);
        editTextLA=findViewById(R.id.AuEditTextLogin);
        editTextPA=findViewById(R.id.AuEditTextPassword);
        progressBar = findViewById(R.id.progressBar);
        firebaseAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Au_textView2:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case R.id.AuRegButton:
                buttonSigIn();
                break;
        }
    }

    private void buttonSigIn() {
        String login = editTextLA.getText().toString().trim();
        String password = editTextPA.getText().toString().trim();

        if (login.isEmpty()){
            editTextLA.setError("Login is required");
            editTextLA.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(login).matches()){
            editTextLA.setError("Provide valid login(email)");
            editTextLA.requestFocus();
            return;
        }
        if (password.isEmpty()){
            editTextPA.setError("Password is required");
            editTextPA.requestFocus();
            return;
        }
        if (password.length()<8){
            editTextPA.setError("Min length password should be 8 symphols");
            editTextPA.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.signInWithEmailAndPassword(login,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
                    if (firebaseUser.isEmailVerified())
                    {
                    Toast.makeText(AuthorizationActivity.this,"Success",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AuthorizationActivity.this,MainActivity.class));
                    }
                    else{
                        firebaseUser.sendEmailVerification();
                        Toast.makeText(AuthorizationActivity.this,"Check email to verify",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(AuthorizationActivity.this,"Check your data",Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}
