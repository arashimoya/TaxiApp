package com.example.taxiapp.RDS;

import com.example.taxiapp.Model.Arrival;
import com.example.taxiapp.Model.Fare;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TaxiApiMock {
    @GET("v3/bb60297c-6dcf-4092-9062-0f086faf7aa7")
    Call<Fare> getFare();

    @GET("v3/0ee194b0-6db5-4cc2-b222-217bb02fe33f")
    Call<List<Arrival>> getArrivals();
}
