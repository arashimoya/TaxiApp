package com.example.taxiapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Arrival {

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("stop")
    @Expose
    public String stop;
    @SerializedName("time")
    @Expose
    public String time;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("messages")
    @Expose
    public String messages;
    @SerializedName("track")
    @Expose
    public String track;
    @SerializedName("rtTime")
    @Expose
    public String rtTime;
    @SerializedName("rtDate")
    @Expose
    public String rtDate;
    @SerializedName("rtTrack")
    @Expose
    public String rtTrack;
    @SerializedName("origin")
    @Expose
    public String origin;
    @SerializedName("JourneyDetailRef")
    @Expose
    public JourneyDetailRef journeyDetailRef;

    public String getTime() {
        return time;
    }

    public String getOrigin() {
        return origin;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getStop() {
        return stop;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public String getMessages() {
        return messages;
    }

    public String getTrack() {
        return track;
    }

    public String getRtTime() {
        return rtTime;
    }

    public String getRtDate() {
        return rtDate;
    }

    public String getRtTrack() {
        return rtTrack;
    }

    public JourneyDetailRef getJourneyDetailRef() {
        return journeyDetailRef;
    }

    @Override
    public String toString() {
        return "Arrival{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", stop='" + stop + '\'' +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", id='" + id + '\'' +
                ", messages='" + messages + '\'' +
                ", track='" + track + '\'' +
                ", rtTime='" + rtTime + '\'' +
                ", rtDate='" + rtDate + '\'' +
                ", rtTrack='" + rtTrack + '\'' +
                ", origin='" + origin + '\'' +
                ", journeyDetailRef=" + journeyDetailRef +
                '}';
    }
}

