package com.example.taxiapp;

public class Dao {
    private static Dao instance;

    public static Dao getInstance(){
        if(instance==null){
            instance = new Dao();
        }
        return instance;
    }
}

