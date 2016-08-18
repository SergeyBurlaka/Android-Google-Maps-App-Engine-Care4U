package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.EmployeeDataWork;

/**
 * Created by Operator on 01.04.2016.
 */
public class EmployeeLocation {

    private  String employeeEmail;
   // private  boolean isHere = false;
    private  double employeeLongitude = 0;
    private  double employeeLatitude = 0;
    private  double circleLongitude ;
    private   double circle1latitude;
    private  double radius;
    private  int statusConst;


    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public double getEmployeeLongitude() {
        return employeeLongitude;
    }

    public double getEmployeeLatitude() {
        return employeeLatitude;
    }

    public double getCircleLongitude() {
        return circleLongitude;
    }

    public double getCircle1latitude() {
        return circle1latitude;
    }

    public double getRadius() {
        return radius;
    }

    public int getStatusConst() {
        return statusConst;
    }

    public void setStatusConst(int statusConst) {
        this.statusConst = statusConst;
    }

    public void setEmployeeLongitude(double employeeLongitude) {
        this.employeeLongitude = employeeLongitude;
    }

    public void setEmployeeLatitude(double employeeLatitude) {
        this.employeeLatitude = employeeLatitude;
    }

    public void setCircleLongitude(double circleLongitude) {
        this.circleLongitude = circleLongitude;
    }

    public void setCircle1latitude(double circle1latitude) {
        this.circle1latitude = circle1latitude;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
