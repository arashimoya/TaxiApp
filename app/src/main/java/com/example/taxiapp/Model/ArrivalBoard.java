package com.example.taxiapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArrivalBoard {

    @SerializedName("noNamespaceSchemaLocation")
    @Expose
    public String noNamespaceSchemaLocation;
    @SerializedName("Arrival")
    @Expose
    public List<Arrival> arrival = null;

}