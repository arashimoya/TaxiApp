package com.example.taxiapp;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class Repository {
    private final Dao dao;
    private static Repository instance;
    private final MutableLiveData<Fare> searchedFare;

    private Repository(){
        dao = Dao.getInstance();
        searchedFare = new MutableLiveData<>();
    }

    public static Repository getInstance(){
        if(instance==null){
            instance = new Repository();
        }
        return instance;
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
                Log.d("Retrofit","Messages successfully received!");
                Log.d("Retrofit", new Gson().toJson(response.body()));
                Log.d("Retrofit", "Message successfully parsed!");
                Log.d("Retrofit", searchedFare.getValue().getDestinationAddress());

            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Fare> call, Throwable t) {
                Log.i("Retrofit","Something went wrong! :(");
            }
        });
    }
}
