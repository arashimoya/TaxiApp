package com.example.taxiapp.RDS;

import com.example.taxiapp.Model.StopLocation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RejseplanenAPI {
    @GET("location/")
    Call<List<StopLocation>> getLocation(@Query("input") String userInput, @Query("format") String format );
    
    @GET("arrivalBoard")
    Call<ArrivalResponse> getArrivals(@Query("id")  int id, @Query("format") String format);
}
