package com.example.taxiapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class CbRadioActivity extends AppCompatActivity {
    private ImageButton SendMessage;
    private EditText userMessageInput;
    private String currentUserID, currentUserName, currentDate, currentTime;
    List<Message> messageArrayList;
    MessageAdapter messageAdapter;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;

    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cb_radio);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        messageArrayList = new ArrayList<>();
        messageAdapter = new MessageAdapter(CbRadioActivity.this,messageArrayList);  
        currentUserID = fAuth.getCurrentUser().getUid();

        recyclerView = findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(CbRadioActivity.this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(messageAdapter);

        InitializeFields();
        getUserInfo();
        EventChangeListener();

        SendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavedMessageToDatabase();
                userMessageInput.getText().clear();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();


    }

    private void EventChangeListener() {
        fStore.collection("cbradio")
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if(error != null){
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.d("Error", error.getMessage());
                    return;
                }

               for (DocumentChange dc : value.getDocumentChanges()) {
                   if(dc.getType() == DocumentChange.Type.ADDED){
                       messageArrayList.add(dc.getDocument().toObject(Message.class));
                   }
                   messageAdapter.notifyDataSetChanged();
               }



               if(progressDialog.isShowing()) 
                   progressDialog.dismiss();  
            }
        });
    }

    private void InitializeFields() {


        SendMessage = (ImageButton) findViewById(R.id.sendMessageButton);
        userMessageInput = (EditText) findViewById(R.id.inputMessage);


    }

    private void getUserInfo() {

        DocumentReference UserRef = fStore.collection("users").document(currentUserID);
        UserRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                currentUserName = value.getString("fName");

            }
        });
    }

    private void SavedMessageToDatabase() {
        String message = userMessageInput.getText().toString();


        if(TextUtils.isEmpty(message)){
            Toast.makeText(this,"Please write message first.",Toast.LENGTH_SHORT).show();
        }else {
            Calendar callForDate = Calendar.getInstance();
            SimpleDateFormat currentDateFormat = new SimpleDateFormat("MMM dd, yyyy");
            currentDate = currentDateFormat.format(callForDate.getTime());

            Calendar callForTime = Calendar.getInstance();
            SimpleDateFormat currentTimeFormat = new SimpleDateFormat("hh:mm a");
            currentTime = currentTimeFormat.format(callForTime.getTime());


            CollectionReference chatRef = fStore.collection("cbradio");

            HashMap<String, Object> messageInfoMap = new HashMap<>();
            messageInfoMap.put("name", currentUserName);
            messageInfoMap.put("message",message);
            messageInfoMap.put("date", currentDate);
            messageInfoMap.put("time",currentTime);
            messageInfoMap.put("timestamp", FieldValue.serverTimestamp());
            messageInfoMap.put("userId",fAuth.getUid());
            chatRef.add(messageInfoMap);





        }
    }
}