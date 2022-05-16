package com.example.taxiapp.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.taxiapp.Model.StopLocation;

@Database(entities = {StopLocation.class},version = 2)
public abstract class StopLocationDatabase extends RoomDatabase {
    private static StopLocationDatabase instance;
    public abstract Dao dao();

    public static synchronized StopLocationDatabase getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    StopLocationDatabase.class, "stop_location_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
