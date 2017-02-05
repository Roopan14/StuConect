package com.example.roopan.stuconnect;

/**
 * Created by Roopan on 30-12-2016.
 */

public class LocDetails {

    String latitude,longitude,date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public LocDetails()
    {
        //empty reqd.
    }

    public LocDetails(String latitude, String longitude, String date)
    {
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

}
