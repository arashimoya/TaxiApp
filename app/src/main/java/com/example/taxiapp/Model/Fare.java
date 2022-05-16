package com.example.taxiapp.Model;

import com.google.gson.annotations.SerializedName;

public class Fare {
    @SerializedName("id")
    private int id;
    @SerializedName("pickupAddress")
    private String pickupAddress;
    @SerializedName("destinationAddress")
    private String destinationAddress;
    @SerializedName("customerName")
    private String name;

    public int getId() {
        return id;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public String getName() {
        return name;
    }
}
