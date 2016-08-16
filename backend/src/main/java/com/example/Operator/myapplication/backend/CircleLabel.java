package com.example.Operator.myapplication.backend;

/**
 * Created by Ветал on 24.03.2016.
 */
public class CircleLabel {


    private double longitude;
    private double latitude;
    private int radius;

    CircleLabel(){}


    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public int getRadius() {
        return radius;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}