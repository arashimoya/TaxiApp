package com.example.taxiapp;

import com.google.firebase.Timestamp;

public class Message {
    private String name;
    private String message;
    private String date;
    private String time;
    private String userId;
    private Timestamp timestamp;

    public Message(){}

    public Message(String name, String message, String date, String time, Timestamp timestamp, String userId) {
        this.name = name;
        this.message = message;
        this.date = date;
        this.time = time;
        this.timestamp = timestamp;
        this.userId = userId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getUserId(){
        return userId;
    }




}

