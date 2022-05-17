package com.example.taxiapp.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.taxiapp.Model.Fare;
import com.example.taxiapp.Repository;

import java.util.List;

public class FareViewModel extends AndroidViewModel {

    private final Repository repository;
    private MutableLiveData<List<Fare>> fares;
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public FareViewModel(Application app){
        super(app);
        fares = new MutableLiveData<>();
        repository = Repository.getInstance(app);
        fares = repository.getFares();
    }

    public MutableLiveData<Boolean> getIsLoading(){
        return isLoading;
    }

    public LiveData<Fare> getFare(){
        isLoading.setValue(true);
        return repository.getReceivedFare();
    }

    public void updateFares(List<Fare> fares){
        repository.updateFareFStore(fares);
    }

    public MutableLiveData<List<Fare>> getFares(){
        isLoading.setValue(true);
        return repository.getFares();
    }






}
