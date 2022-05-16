package com.example.taxiapp.ui.timetable;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.taxiapp.Arrival;
import com.example.taxiapp.Repository;
import com.example.taxiapp.StopLocation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import retrofit2.converter.gson.GsonConverterFactory;

public class TimetableViewModel extends ViewModel {
    private final MutableLiveData<String> mText;
    private LiveData<List<Arrival>> arrivalLiveData;
    private MutableLiveData<List<StopLocation>> stopsLiveData;
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final Repository repository;


    public TimetableViewModel() {
        super();
        repository = Repository.getInstance();
        mText = new MutableLiveData<>();
        stopsLiveData = new MutableLiveData<>();
        mText.setValue("This is timetable fragment");
    }

    public MutableLiveData<Boolean> getIsLoading(){
        return isLoading;
    }

    public LiveData<List<Arrival>> getArrivalLiveData(int id){
        isLoading.setValue(true);
        arrivalLiveData = repository.getArrivals(id);
        return arrivalLiveData;
    }


    public MutableLiveData<List<StopLocation>> getAllStops(){
        isLoading.setValue(true);
        return stopsLiveData;
    }


    public LiveData<String> getText() {
        return mText;
    }
}