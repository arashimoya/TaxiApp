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
    @SerializedName("hasActive")
    private boolean hasActive;

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

    public boolean isHasActive() {
        return hasActive;
    }

    public void setHasActive(boolean hasActive) {
        this.hasActive = hasActive;
    }

    @Override
    public String toString() {
        return "Fare{" +
                "id=" + id +
                ", pickupAddress='" + pickupAddress + '\'' +
                ", destinationAddress='" + destinationAddress + '\'' +
                ", name='" + name + '\'' +
                ", hasActive=" + hasActive +
                '}';
    }
}
