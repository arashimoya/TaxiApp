package com.example.taxiapp;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TaxiApiMock {
    @GET("v3/bb60297c-6dcf-4092-9062-0f086faf7aa7")
    Call<Fare> getFare();
}
