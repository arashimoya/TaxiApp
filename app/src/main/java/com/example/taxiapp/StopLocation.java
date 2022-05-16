package com.example.taxiapp;

import com.google.gson.annotations.SerializedName;

public class StopLocation {
    @SerializedName("x")
    int x;
    @SerializedName("y")
    int y;
    @SerializedName("id")
    int id;
    @SerializedName("stop")
    private String stop;

    public StopLocation(int id, String stop) {
        this.id = id;
        this.stop = stop;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public String getStop() {
        return stop;
    }
}
