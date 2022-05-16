package com.example.taxiapp.ui.timetable;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.taxiapp.Model.Arrival;
import com.example.taxiapp.Repository;
import com.example.taxiapp.Model.StopLocation;

import java.util.List;

public class TimetableViewModel extends AndroidViewModel {
    private final MutableLiveData<String> mText;
    private LiveData<List<Arrival>> arrivalLiveData;
    private LiveData<List<StopLocation>> stopsLiveData;
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final Repository repository;


    public TimetableViewModel(Application app) {
        super(app);
        repository = Repository.getInstance(app);
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


    public LiveData<List<StopLocation>> getAllStops(){
        isLoading.setValue(true);
        stopsLiveData = repository.getStops();
        return stopsLiveData;
    }


    public LiveData<String> getText() {
        return mText;
    }
}