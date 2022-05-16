package com.example.taxiapp;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RejseplanenApiBuilder {
    private static RejseplanenAPI rejseplanenAPI;

    public static RejseplanenAPI getRejseplanenAPI(){
        if(rejseplanenAPI==null){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(interceptor);
            rejseplanenAPI = new Retrofit.Builder()
                    .baseUrl("http://xmlopen.rejseplanen.dk/bin/rest.exe/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build()
                    .create(RejseplanenAPI.class);
        }
        return rejseplanenAPI;
    }
}
