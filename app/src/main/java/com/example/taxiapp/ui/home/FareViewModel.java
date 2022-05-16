package com.example.taxiapp.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.taxiapp.Model.Fare;
import com.example.taxiapp.Repository;

public class FareViewModel extends AndroidViewModel {

    private final Repository repository;

    public FareViewModel(Application app){
        super(app);
        repository = Repository.getInstance(app);
    }

    public LiveData<Fare> getFare(){

        return repository.getReceivedFare();
    }

    public void searchForFare(){
        repository.searchForFare();
    }

}
