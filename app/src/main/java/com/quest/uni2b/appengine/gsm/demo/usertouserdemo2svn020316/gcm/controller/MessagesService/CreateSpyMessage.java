package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.controller.MessagesService;


import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.EmployeeMap.CircleLabel;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.GCMConstants.MessageConstant;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateSpyMessage {


    // private final String typeSpy = "Alert";
    JSONObject message;
    JSONObject data;
    private double longitude;
    private double latitude;
    private double radius;
    private String  managerEmail;

   public CreateSpyMessage ( double latitude,double longitude, double radius, String managerEmail){
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
       // this.eEmail = employeeEmail;
        this.managerEmail = managerEmail;

    }



    public CreateSpyMessage (CircleLabel circleLabel, String managerEmail){
        this.latitude = circleLabel.getLatitude();
        this.longitude = circleLabel.getLongitude();
        this.radius = circleLabel.getRadius();
        // this.eEmail = employeeEmail;
        this.managerEmail = managerEmail;

    }

    public String getSpyMessage (){
        data = new JSONObject();
        try {
            data.put("latitude",latitude);

            data.put("longitude",longitude);
            data.put("radius",radius);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        message = new JSONObject();
        try {
            message.put("kind", MessageConstant.KIND_SPY);
            message.put("from",managerEmail);
            message.put("data",data);

            //TODO_aborted: put "geofence" true or false

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String spyMessage = message.toString();

        return spyMessage;
    }


}