package com.example.taxiapp.ui.previousRides;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.taxiapp.Model.Fare;
import com.example.taxiapp.Repository;

import java.util.List;

public class HistoryViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<List<Fare>> faresLiveData = new MutableLiveData<>();
    private final Repository repository;

    public HistoryViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
        faresLiveData = repository.getFares();
    }

    public MutableLiveData<List<Fare>> getFaresLiveData() {
        return faresLiveData;
    }

    public MutableLiveData<Boolean> getIsLoading(){
        return isLoading;
    }
}
