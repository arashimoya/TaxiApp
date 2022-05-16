package com.example.taxiapp.RDS;

import com.example.taxiapp.Model.ArrivalBoard;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArrivalResponse {
    @SerializedName("ArrivalBoard")
    @Expose
    public ArrivalBoard arrivalBoard;

    public ArrivalBoard getArrivalBoard() {
        return arrivalBoard;
    }
}
