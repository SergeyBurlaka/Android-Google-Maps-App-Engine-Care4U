package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.controller.MessagesService;

import android.content.Context;
import android.content.SharedPreferences;

import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.GCMConstants.MessageConstant;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.EmployeeDataWork.EmployeeLocation;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Operator on 01.04.2016.
 */
public class ParseEmployeeOutsideMessage {


    private final String employeeEmail;
    private JSONObject data;
    private EmployeeLocation employeeLocation;

    private SharedPreferences.Editor editorCircleLoc;
    private Context context;

    public ParseEmployeeOutsideMessage(JSONObject data, Context context, String employeeEmail){
        this.context = context;

        this.data = data;
        this.employeeEmail = employeeEmail;
    }


    public EmployeeLocation  getСircle() {



        //data = new JSONObject();
        try {

            //<!--- 16:06 16june16


            employeeLocation = new EmployeeLocation();
            /*
            employeeLocation.setEmployeeLatitude(data.getDouble("latitude"));
            employeeLocation.setEmployeeLongitude( data.getDouble("longitude"));
          //  employeeLocation.setHere( true);

           employeeLocation.setCircleLongitude( data.getDouble("circleLongitude"));
           employeeLocation.setCircle1latitude(data.getDouble("circleLatitude"));
            employeeLocation.setRadius( data.getDouble("circleRadius")); */


            //TODO_d+from14min get also from data STATUS message : ENTER EXIT OR BEGIN
            employeeLocation.setStatusConst(data.getInt("status_spy"  ));

            //-->

            //<!---13:42 22june 16
            //TODO_d+ from 4 h total in 19:00: 1>>
            //TODO_d+from2:30 : save in shared preference circle location & radius

            //<!--6:27
            //TODO_d+>>1
            //TODO_d+: 15.07.2016
            //TODO_d+ bag because U have only one circle saving in sh preference
            //TODO_d+ but U must save spy circle for each employee in manager list



            editorCircleLoc = context.getSharedPreferences(employeeEmail, context.MODE_PRIVATE).edit();
            editorCircleLoc.putLong( MessageConstant.CIRCLE_LATITUDE, Double.doubleToLongBits(data.getDouble("circleLatitude"))) ;
            //end  commit
            editorCircleLoc.putLong( MessageConstant.CIRCLE_LONGITUDE, Double.doubleToLongBits(data.getDouble("circleLongitude"))) ;
            editorCircleLoc.putLong( MessageConstant.CIRCLE_RADIUS, Double.doubleToLongBits(data.getDouble("circleRadius"))) ;


            editorCircleLoc.commit();


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return employeeLocation;
    }
}
