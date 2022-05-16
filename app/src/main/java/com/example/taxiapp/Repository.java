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
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
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
    private MutableLiveData<List<Arrival>> searchedArrivals;
    private LiveData<List<StopLocation>> searchedStops;
    private MutableLiveData<ArrivalBoard> arrivalBoardMutableLiveData;
    private ExecutorService executorService;

    private TaxiApiMock taxiApi;
    private RejseplanenAPI rejseplanenAPI;

    private Repository(Application app){
        StopLocationDatabase database = StopLocationDatabase.getInstance(app);
        dao = database.dao();
        executorService = Executors.newFixedThreadPool(2);
        searchedStops = dao.getAllStops();



        searchedFare = new MutableLiveData<>();
        searchedArrivals = new MutableLiveData<>();
        arrivalBoardMutableLiveData = new MutableLiveData<>();

        taxiApi = ServiceGenerator.getTaxiApi();
        rejseplanenAPI = RejseplanenApiBuilder.getRejseplanenAPI();
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


    public void searchForFare(){
        TaxiApiMock taxiApiMock = ServiceGenerator.getTaxiApi();
        Call<Fare> call = taxiApiMock.getFare();
        call.enqueue(new Callback<Fare>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Fare> call, Response<Fare> response) {
                if(response.isSuccessful())
                    searchedFare.setValue(response.body());

            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Fare> call, Throwable t) {
                Log.i("Retrofit","Something went wrong! :(");
            }
        });
    }

    public void searchForFares(int id){
        TaxiApiMock taxiApiMock = ServiceGenerator.getTaxiApi();
        Call<Fare> call = taxiApiMock.getFare();
        call.enqueue(new Callback<Fare>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Fare> call, Response<Fare> response) {
                if(response.isSuccessful())
                    searchedFare.setValue(response.body());

            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Fare> call, Throwable t) {
                Log.i("Retrofit","Something went wrong! :(");
            }
        });
    }

    public LiveData<List<StopLocation>> getStops(){
         return searchedStops;
    }



}
