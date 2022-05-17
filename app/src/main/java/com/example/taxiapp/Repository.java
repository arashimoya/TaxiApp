package com.example.taxiapp;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.taxiapp.DB.Dao;
import com.example.taxiapp.DB.StopLocationDatabase;
import com.example.taxiapp.Model.Arrival;
import com.example.taxiapp.Model.ArrivalBoard;
import com.example.taxiapp.Model.Fare;
import com.example.taxiapp.Model.StopLocation;
import com.example.taxiapp.RDS.ArrivalResponse;
import com.example.taxiapp.RDS.RejseplanenAPI;
import com.example.taxiapp.RDS.RejseplanenApiBuilder;
import com.example.taxiapp.RDS.ServiceGenerator;
import com.example.taxiapp.RDS.TaxiApiMock;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class Repository {
    private final Dao dao;
    private static Repository instance;
    private final MutableLiveData<Fare> searchedFare;
    private final MutableLiveData<List<Fare>> searchedFares;
    private MutableLiveData<List<Arrival>> searchedArrivals;
    private LiveData<List<StopLocation>> searchedStops;
    private MutableLiveData<ArrivalBoard> arrivalBoardMutableLiveData;
    private ExecutorService executorService;

    private TaxiApiMock taxiApi;
    private RejseplanenAPI rejseplanenAPI;

    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String UserID;

    private Repository(Application app){
        StopLocationDatabase database = StopLocationDatabase.getInstance(app);
        dao = database.dao();
        executorService = Executors.newFixedThreadPool(2);
        searchedStops = dao.getAllStops();



        searchedFare = new MutableLiveData<>();
        searchedFares = new MutableLiveData<>();

        searchedArrivals = new MutableLiveData<>();

        arrivalBoardMutableLiveData = new MutableLiveData<>();

        taxiApi = ServiceGenerator.getTaxiApi();
        rejseplanenAPI = RejseplanenApiBuilder.getRejseplanenAPI();

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        UserID = fAuth.getCurrentUser().getUid();
        fetchFares();
    }

    public static Repository getInstance(Application app){
        if(instance==null){
            instance = new Repository(app);
        }
        return instance;
    }

    public MutableLiveData<List<Arrival>> getReceivedArrivals() {return searchedArrivals;}


    public LiveData<List<Arrival>> getArrivals(int id){
        Log.d("Retrofit", String.valueOf(id));
        rejseplanenAPI.getArrivals(id, "json").enqueue(new Callback<ArrivalResponse>() {

            @Override
            public void onResponse(Call<ArrivalResponse> call, @NonNull Response<ArrivalResponse> response) {
               searchedArrivals.setValue(new ArrayList<Arrival>(response.body().arrivalBoard.arrival));
                Log.i("Retrofit","Success!");
                Log.d("RetrofitGSON", new Gson().toJson(response.body()));

            }

            @Override
            public void onFailure(Call<ArrivalResponse> call, Throwable t) {
                Log.i("Retrofit","Something went wrong! :(");
            }
        });
        return searchedArrivals;
    }

    public LiveData<Fare> getReceivedFare(){
        return searchedFare;
    }


    public void updateFareFStore(List<Fare> fares){
        CollectionReference usersColl = fStore.collection("users");
        DocumentReference documentReference = usersColl.document(UserID);
        Map<String,Object> updates = new HashMap<>();
        updates.put("fares",FieldValue.delete());
        documentReference.update(updates);
        Map<String, Object> faresToUpdate = new HashMap<>();
        faresToUpdate.put("fares",fares);
        documentReference.set(faresToUpdate)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("Fstore", "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Fstore","Error updating document",e);
                    }
                });
    }

    public void fetchFares(){
        List<Fare> myFares = new ArrayList<>();
        CollectionReference usersRef = fStore.collection("users");
        DocumentReference userIdRef = usersRef.document(UserID);
        userIdRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    List<Fare> faresList = document.toObject(FareDocument.class).fares;
                    Log.d("Firestore", faresList.toString());
                    Log.d("Firestore", String.valueOf((faresList.get(0).isHasActive())));
                    searchedFares.setValue(faresList);

                }
            }
        });
    }

    public MutableLiveData<List<Fare>> getFares(){
        return searchedFares;
    }

    public LiveData<List<StopLocation>> getStops(){
         return searchedStops;
    }



}
