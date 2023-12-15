package com.example.myapplication.models;

public class LocationModel {
    private int id;
    private double latitude;
    private double longitude;
    private String userInput;

    public LocationModel(int id, double latitude, double longitude, String userInput) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.userInput = userInput;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }
}
