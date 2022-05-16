package com.example.taxiapp.DB;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.taxiapp.Model.StopLocation;

import java.util.List;

@androidx.room.Dao
public interface Dao {

    @Insert
    void insert(StopLocation stopLocation);
    @Update
    void update(StopLocation stopLocation);
    @Delete
    void delete(StopLocation stopLocation);
    @Query("DELETE FROM stop_table")
    void deleteAllNotes();
    @Query("SELECT * FROM stop_table")
    LiveData<List<StopLocation>> getAllStops();


}

