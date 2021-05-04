package com.example.myworkout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView title;
    private EditText  editTextName, editTextLogin,editTextPassword;
    private FirebaseAuth firebaseAuth;
    private Button buttonReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth=FirebaseAuth.getInstance();

        title=findViewById(R.id.tvTitleRP);
        title.setOnClickListener(this);
        buttonReg=findViewById(R.id.RPRegButton);
        buttonReg.setOnClickListener(this);

        editTextName=findViewById(R.id.editTextTextPersonName);
        editTextLogin=findViewById(R.id.AuEditTextLogin);
        editTextPassword=findViewById(R.id.RPEditTextPassword);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvTitleRP:
                startActivity(new Intent(this,AuthorizationActivity.class));
                break;
            case R.id.RPRegButton:
                buttonReg();
                break;

    }}

    private void buttonReg() {
        String name =  editTextName.getText().toString().trim();
        String login = editTextLogin.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (name.isEmpty()){
            editTextName.setError("Name is required");
            editTextName.requestFocus();
            return;
        }
        if (login.isEmpty()){
            editTextLogin.setError("Login is required");
            editTextLogin.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(login).matches()){
            editTextLogin.setError("Provide valid login(email)");
            editTextLogin.requestFocus();
            return;
        }
        if (password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        if (password.length()<8){
            editTextPassword.setError("Min length password should be 8 symphols");
            editTextPassword.requestFocus();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(login,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            UserAuth userAuth = new UserAuth(name, login);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(userAuth).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this,"Register is susscesfull", Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(RegisterActivity.this,"Unsussesfull",Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(RegisterActivity.this,"Unsussesfullly",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}