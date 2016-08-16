package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.controller.MessagesService;

import android.content.SharedPreferences;

import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.GCMConstants.MessageConstant;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Operator on 14.07.2016.
 */

//<!-- 14 jule 16 12:41
//TODO_DONE_ALL_FROM_TOTAL_TIME_3H >> *--1--*
//TODO_d+11min create message using JSON
//TODO_d+11min with different status about GPS/AIRPLANE MODE
public class ControlMessageToM {

    // private final String typeSpy = "Alert";
    JSONObject message;
    JSONObject data;
    //private double radius;
    private String  employeeEmail;
    // CircleLabel circle;
    private  int messageConstant;
    SharedPreferences shared;


    public ControlMessageToM (  String employeeEmail, int messageConstant  ){
        this.employeeEmail = employeeEmail;
        this.messageConstant = messageConstant;

    }

    public String getSpyMessage (){
        data = new JSONObject();
        try {

            data.put ("status_spy", messageConstant   );

        } catch (JSONException e) {
            e.printStackTrace();
        }


        //second wrapper
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
