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
    Button Register;
    TextView Login;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Email = findViewById(R.id.EmailAddress);
        Password = findViewById(R.id.Password);
        Register = findViewById(R.id.registerButton);
        Login = findViewById(R.id.goToLogIn);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),NavigationActivity.class));
            finish();
        }

        Register.setOnClickListener(v -> {
            String email = Email.getText().toString().trim();
            String password = Password.getText().toString().trim();

            if(TextUtils.isEmpty(email)){
                Email.setError("Email is required to proceed.");
            }

            if(TextUtils.isEmpty(password)){
                Password.setError("Password is required to proceed.");
            }

            progressBar.setVisibility(View.VISIBLE);

            fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(this,"Logged In Successfully",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), com.example.taxiapp.Login.class));
                }else {
                    Toast.makeText(this,"Error!"+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }

            });
        });
        Login.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),Login.class)));
    }
}