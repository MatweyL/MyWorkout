package com.example.myworkout;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class AuthorizationActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView tvRegister, tvReset;
    private EditText editTextLA,editTextPA;
    private Button buttonSigIn;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private static final String TAG = "MyApp";
    //private ProgressBar progressBar;
    public static boolean isConnectingToInternet(Context context) {

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
    /*public boolean isInternetAvailable() {
        try {
            final InetAddress address = InetAddress.getByName("www.google.com");
            return !address.equals("");
        } catch (UnknownHostException exception) {
            Log.e(TAG,"сключение",exception);
            // Log error
        }
        return false;
    }*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization_layout);
        tvRegister = findViewById(R.id.Au_textView2);
        tvRegister.setOnClickListener(this);
        tvReset=findViewById(R.id.Au_textView1);
        tvReset.setOnClickListener(this);
        buttonSigIn = findViewById(R.id.RPRegButton);
        buttonSigIn.setOnClickListener(this);
        editTextLA=findViewById(R.id.AuEditTextLogin);
        editTextPA=findViewById(R.id.RPEditTextPassword);
        //progressBar = findViewById(R.id.progressBar);
        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Au_textView2:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case R.id.RPRegButton:
                buttonSigIn();
                break;
            case R.id.Au_textView1:
                startActivity(new Intent(this, ResetPassword.class));
                break;
        }
    }

    private void buttonSigIn() {
        String login = editTextLA.getText().toString().trim();
        String password = editTextPA.getText().toString().trim();

        if (login.isEmpty()){
            editTextLA.setError("Введите логин");
            editTextLA.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(login).matches()){
            editTextLA.setError("Введите корректный логин");
            editTextLA.requestFocus();
            return;
        }
        if (password.isEmpty()){
            editTextPA.setError("Введите пароль");
            editTextPA.requestFocus();
            return;
        }
        if (password.length()<8){
            editTextPA.setError("Минимальная длина 8 символов");
            editTextPA.requestFocus();
            return;
        }
        //progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.signInWithEmailAndPassword(login,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
                    if (firebaseUser.isEmailVerified())
                    {
                    Toast.makeText(AuthorizationActivity.this,"Успех",Toast.LENGTH_LONG).show();
                    //progressBar.setVisibility(View.VISIBLE);
                    startActivity(new Intent(AuthorizationActivity.this,MainActivity.class));
                    }
                    else{
                        firebaseUser.sendEmailVerification();
                        Toast.makeText(AuthorizationActivity.this,"Проверьте почту для подтверждения",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    //Toast.makeText(AuthorizationActivity.this,"Проверьте введенные данные",Toast.LENGTH_LONG).show();
                    if (isConnectingToInternet(getApplicationContext())) {
                       Toast.makeText(AuthorizationActivity.this,"Проверьте введенные данные",Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(AuthorizationActivity.this,"Отсутствует подключение к интернету. Некоторые функции могут юыть недоступны",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(AuthorizationActivity.this,MainActivity.class));
                    }
                    //progressBar.setVisibility(View.GONE);
                }
            }
        });

    }

}
