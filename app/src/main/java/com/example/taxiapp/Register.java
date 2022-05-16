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

public class Register extends AppCompatActivity {
    EditText Email, Password;
    Button RegisterR;
    TextView LoginR;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Email = findViewById(R.id.EmailAddress);
        Password = findViewById(R.id.Password);
        RegisterR = findViewById(R.id.registerButton);
        LoginR = findViewById(R.id.goToLogIn);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),NavigationActivity.class));
            finish();
        }

        RegisterR.setOnClickListener(v -> {
            String email = Email.getText().toString().trim();
            String password = Password.getText().toString().trim();

            if(TextUtils.isEmpty(email)){
                Email.setError("Email is required to proceed.");
                return;
            }

            if(TextUtils.isEmpty(password)){
                Password.setError("Password is required to proceed.");
                return;
            }



            fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(com.example.taxiapp.Register.this,"User Created",Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(Register.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();


                }

            });
        });
        LoginR.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),Login.class)));
    }
}