package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.model.saving;

import android.content.Context;
import android.content.SharedPreferences;

import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.constants.MessageConstant;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Operator on 01.08.2016.
 */
public class Data4EEmailShP extends SavingInManagerShP {

     private String employeeEmail;
    private JSONObject data;
    SharedPreferences.Editor saveCircleLblShared;

    SharedPreferences readSharedPreferences;

    public Data4EEmailShP(Context context, String employeeEmail, JSONObject data) {
        super(context);
        this.employeeEmail = employeeEmail;
        this.data = data;
    }



    @Override
    SharedPreferences.Editor getShPEditor() {
        return  context.getSharedPreferences(employeeEmail, context.MODE_PRIVATE).edit();
    }

    @Override
    SharedPreferences getShP() {
        return null;
    }




    @Override
    void commitData() {
        saveCircleLblShared = getShPEditor();
        try {
            saveCircleLblShared.putLong( MessageConstant.CIRCLE_LATITUDE, Double.doubleToLongBits(data.getDouble("circleLatitude"))) ;

        //end  commit
        saveCircleLblShared.putLong( MessageConstant.CIRCLE_LONGITUDE, Double.doubleToLongBits(data.getDouble("circleLongitude"))) ;
        saveCircleLblShared.putLong( MessageConstant.CIRCLE_RADIUS, Double.doubleToLongBits(data.getDouble("circleRadius"))) ;
        saveCircleLblShared.commit();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    void commitStatus(int status) {
        saveCircleLblShared = getShPEditor();

        saveCircleLblShared.putInt(MessageConstant.CLICKED_SPY,  status );
        saveCircleLblShared.commit();

    }


    @Override
    void readData() {

    }


    @Override
    void readStatus() {

    }
}
