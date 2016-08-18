package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.EmployeeMap;

/**
 * Created by Operator on 24.03.2016.
 *
 *
 */
public class CircleLabel {

    public CircleLabel() {
    }

    public CircleLabel(double longitude, double latitude, double radius) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.radius = radius;
    }

    private double longitude;
    private double latitude;
    private double radius;

    //public static double longitude;
    //public static double latitude ;
    //public static double radius;


    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getRadius() {
        return radius;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
