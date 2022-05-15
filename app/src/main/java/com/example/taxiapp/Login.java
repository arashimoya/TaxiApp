package com.example.taxiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText Email, Password;
    Button Login;
    TextView Register;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Email = findViewById(R.id.EmailAddressLog);
        Password = findViewById(R.id.PasswordLog);
        Login = findViewById(R.id.loginButton);
        Register = findViewById(R.id.goToRegister);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),NavigationActivity.class));
            finish();
        }

        Login.setOnClickListener(v -> {
            String email = Email.getText().toString().trim();
            String password = Password.getText().toString().trim();

            if(TextUtils.isEmpty(email)){
                Email.setError("Email is required to proceed.");
            }

            if(TextUtils.isEmpty(password)){
                Password.setError("Password is required to proceed.");
            }

            progressBar.setVisibility(View.VISIBLE);

            fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(this,"User Created",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), com.example.taxiapp.Login.class));
                }else {
                    Toast.makeText(this,"Error!",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }

            });
        });
        Register.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),Register.class)));
    }
}