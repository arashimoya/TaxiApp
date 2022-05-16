package com.example.taxiapp;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class Repository {
    private final Dao dao;
    private static Repository instance;
    private final MutableLiveData<Fare> searchedFare;
    private MutableLiveData<List<Arrival>> searchedArrivals;
    private MutableLiveData<List<StopLocation>> searchedStops;
    private MutableLiveData<ArrivalBoard> arrivalBoardMutableLiveData;

    private TaxiApiMock taxiApi;
    private RejseplanenAPI rejseplanenAPI;

    private Repository(){
        dao = Dao.getInstance();

        searchedFare = new MutableLiveData<>();
        searchedArrivals = new MutableLiveData<>();
        searchedStops = new MutableLiveData<>();
        arrivalBoardMutableLiveData = new MutableLiveData<>();

        taxiApi = ServiceGenerator.getTaxiApi();
        rejseplanenAPI = RejseplanenApiBuilder.getRejseplanenAPI();
    }

    public static Repository getInstance(){
        if(instance==null){
            instance = new Repository();
        }
        return instance;
    }

    public MutableLiveData<List<Arrival>> getReceivedArrivals() {return searchedArrivals;}

//    public void searchForArrivals(){
//        TaxiApiMock taxiApiMock = ServiceGenerator.getTaxiApi();
//        Call<List<Arrival>> call = taxiApiMock.getArrivals();
//        call.enqueue(new Callback<List<Arrival>>() {
//            @EverythingIsNonNull
//            @Override
//            public void onResponse(Call<List<Arrival>> call, Response<List<Arrival>> response) {
//                if(response.isSuccessful())
//                    searchedArrivals.setValue(response.body());
//                Log.d("Retrofit","Messages successfully received!");
//                Log.d("Retrofit", new Gson().toJson(response.body()));
//                Log.d("Retrofit", "Message successfully parsed!");
//
//                for (Arrival arrival: Objects.requireNonNull(searchedArrivals.getValue())) {
//                    Log.d("Retrofit",arrival.toString());
//                }
//            }
//
//            @EverythingIsNonNull
//            @Override
//            public void onFailure(Call<List<Arrival>> call, Throwable t) {
//                Log.i("Retrofit","Something went wrong! :(");
//            }
//        });
//    }
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

    public LiveData<List<StopLocation>> getStops(String userInput){
        rejseplanenAPI.getLocation(userInput,"json").enqueue(new Callback<List<StopLocation>>() {
            @Override
            public void onResponse(Call<List<StopLocation>> call, @NonNull Response<List<StopLocation>> response) {
                searchedStops.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<StopLocation>> call, Throwable t) {
                Log.i("Retrofit","Something went wrong! :(");
            }
        });
        return searchedStops;
    }



}
