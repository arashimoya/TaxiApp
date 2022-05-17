package com.example.taxiapp;

public class Message {
    private String name;
    private String message;
    private String date;
    private String time;

    public Message(){}

    public Message(String name, String message, String date, String time) {
        this.name = name;
        this.message = message;
        this.date = date;
        this.time = time;
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



}

