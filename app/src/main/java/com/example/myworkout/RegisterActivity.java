package com.example.myworkout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
    private ProgressBar progressBar;

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

        //progressBar=findViewById(R.id.progressBar3);

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
            editTextName.setError("Введите имя");
            editTextName.requestFocus();
            return;
        }
        if (login.isEmpty()){
            editTextLogin.setError("Введите электронную почту(@gmail)");
            editTextLogin.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(login).matches()){
            editTextLogin.setError("Введите корректный элнетронную почту(@gmail)");
            editTextLogin.requestFocus();
            return;
        }
        if (password.isEmpty()){
            editTextPassword.setError("Введите пароль");
            editTextPassword.requestFocus();
            return;
        }
        if (password.length()<8){
            editTextPassword.setError("Минимальная длина 8 символов");
            editTextPassword.requestFocus();
            return;
        }
        //progressBar.setVisibility(View.VISIBLE);
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
                                        firebaseAuth.getCurrentUser().sendEmailVerification();
                                        Toast.makeText(RegisterActivity.this,"Регистрация прошла успешно, пожалуйста, проверьте почту для подтверждения", Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(RegisterActivity.this,AuthorizationActivity.class);
                                        startActivity(i);
                                        //progressBar.setVisibility(View.VISIBLE);
                                    }else{
                                        Toast.makeText(RegisterActivity.this,"Что-то пошло не так",Toast.LENGTH_LONG).show();
                                        //progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(RegisterActivity.this,"Что-то пошло не так",Toast.LENGTH_LONG).show();
                            //progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}