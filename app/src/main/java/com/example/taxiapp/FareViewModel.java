package com.example.taxiapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class FareViewModel extends ViewModel {

    private final Repository repository;

    public FareViewModel(){
        repository = Repository.getInstance();
    }

    public LiveData<Fare> getFare(){

        return repository.getReceivedFare();
    }

    public void searchForFare(){
        repository.searchForFare();
    }

}
