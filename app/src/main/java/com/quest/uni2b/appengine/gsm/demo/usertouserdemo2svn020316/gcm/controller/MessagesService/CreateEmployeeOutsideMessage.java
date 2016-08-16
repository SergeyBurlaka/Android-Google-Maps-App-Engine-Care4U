package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.controller.MessagesService;

import android.content.SharedPreferences;

import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.EmployeeMap.CircleLabel;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.GCMConstants.MessageConstant;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Operator on 01.04.2016.
 */
public class CreateEmployeeOutsideMessage {

    // private final String typeSpy = "Alert";
    JSONObject message;
    JSONObject data;
    private double longitude;
    private double latitude;
    //private double radius;
    private String  employeeEmail;
   // CircleLabel circle;
    private  int messageConstant;
    SharedPreferences shared;
    private CircleLabel circleLabel;


    //<!---16:00 16june16
    //TODO_d+frm9min set in data also status ENTER or EXIT or begin spying

    //-->

    public CreateEmployeeOutsideMessage ( String employeeEmail, int messageConstant){
        this.messageConstant = messageConstant;
        this.employeeEmail = employeeEmail;


    }


            public CreateEmployeeOutsideMessage ( double latitude,double longitude, String employeeEmail, int messageConstant, CircleLabel circleLabel  ){
        this.latitude = latitude;
        this.longitude = longitude;
       // this.radius = radius;
        // this.eEmail = employeeEmail;
        this.employeeEmail = employeeEmail;

        this.messageConstant = messageConstant;

        this.circleLabel = circleLabel;

    }

    public String getSpyMessage (){
        data = new JSONObject();
        try {
            data.put("latitude",latitude);

            data.put("longitude",longitude);

           //<!---21june16  18:40
            //TODO_d+:1
            //TODO_d+ use sharedPreference
        //   shared = getSharedPreferences("info",MODE_PRIVATE);
            //Using getXXX- with XX is type date you wrote to file "name_file"
         //   string_temp = shared.getString("manager id","");

            data.put("circleLongitude", circleLabel.getLongitude());
            data.put("circleLatitude", circleLabel.getLatitude());
            data.put("circleRadius",circleLabel.getRadius());

            //-->

            data.put ("status_spy", messageConstant   );

        } catch (JSONException e) {
            e.printStackTrace();
        }

        message = new JSONObject();
        try {

            //TODO_abortes set new name ENTER_EXIT_MESSAGE

            message.put("kind", MessageConstant.OUTSIDE_MESSAGE);
            message.put("from",employeeEmail);
            message.put("data",data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String spyMessage = message.toString();

        return spyMessage;
    }







}
