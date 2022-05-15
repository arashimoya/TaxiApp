package com.example.taxiapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static TaxiApiMock taxiApi;

    public static TaxiApiMock getTaxiApi(){
        if(taxiApi==null){
            taxiApi = new Retrofit.Builder()
                    .baseUrl("https://run.mocky.io/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(TaxiApiMock.class);
        }
        return taxiApi;
    }
}
