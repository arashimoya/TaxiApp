package com.example.taxiapp.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
@Entity(tableName = "stop_table")
public class StopLocation {
    int x;
    int y;
    @PrimaryKey(autoGenerate = false)
    int id;
    private String stop;

    public StopLocation(int id, String stop) {
        this.id = id;
        this.stop = stop;
    }

    @Override
    public String toString() {
        return "StopLocation{" +
                "x=" + x +
                ", y=" + y +
                ", id=" + id +
                ", stop='" + stop + '\'' +
                '}';
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


    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }
}
