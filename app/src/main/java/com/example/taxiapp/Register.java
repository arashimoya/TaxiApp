package com.example.taxiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private static final String TAG = "TAG" ;
    EditText Email, Password, FullName;
    Button RegisterR;
    TextView LoginR;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    ProgressBar progressBar;
    String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FullName = findViewById(R.id.PersonName);
        Email = findViewById(R.id.EmailAddress);
        Password = findViewById(R.id.Password);
        RegisterR = findViewById(R.id.registerButton);
        LoginR = findViewById(R.id.goToLogIn);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),NavigationActivity.class));
            finish();
        }

        RegisterR.setOnClickListener(v -> {
            String email = Email.getText().toString().trim();
            String password = Password.getText().toString().trim();
            String name = FullName.getText().toString();

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
                    UserID = fAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fStore.collection("users").document(UserID);
                    Map<String,Object> user = new HashMap<>();
                    user.put("fName",name);
                    user.put("email",email);
                    documentReference.set(user).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("TAG", "OnFailure"+e.toString());
                        }
                    });
                    startActivity(new Intent(getApplicationContext(),NavigationActivity.class));

                }else {
                    Toast.makeText(Register.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();


                }

            });
        });
        LoginR.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),Login.class)));
    }
}