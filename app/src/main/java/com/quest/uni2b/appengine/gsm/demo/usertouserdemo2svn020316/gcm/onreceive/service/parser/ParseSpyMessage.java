package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.onreceive.service.parser;

import android.content.Context;
import android.content.SharedPreferences;

import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.constants.EmplConst4ShPrfOrIntent;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.map.CircleLabel;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.geofence.MyGeofence;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Operator on 24.03.2016.
 */
public class ParseSpyMessage {

    private JSONObject data;
    private CircleLabel сircle;
    private MyGeofence myGeofence;
    private int mId = 0;
    private Context context;
    private SharedPreferences.Editor editorCircleLoc;

    public ParseSpyMessage(JSONObject data, Context context){
        this.context = context;
        this.data = data;
    }


    public MyGeofence getMyGeofence (){

        //<!--14june16
        //create MyGeofence obj
        // set lat-de, long-de & radius to MyGeofence object
        try {
            myGeofence = new MyGeofence(mId, data.getDouble("latitude"), data.getDouble("longitude"), (float)data.getDouble("radius"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return myGeofence;
    }


    public CircleLabel getСircle() {
            сircle = new CircleLabel();
        //data = new JSONObject();
        try {
            сircle.setLatitude(data.getDouble("latitude"));
            сircle.setLongitude( data.getDouble("longitude"));
            сircle.setRadius(data.getDouble("radius"));
            //<!-- 21 june 16 18:40
            // save in shared  preference
            //for GPS SERVICE
            editorCircleLoc = context.getSharedPreferences(EmplConst4ShPrfOrIntent.CIRCLE_LOCATION, context.MODE_PRIVATE).edit();
            editorCircleLoc.putLong( EmplConst4ShPrfOrIntent.CIRCLE_LATITUDE, Double.doubleToLongBits(data.getDouble("latitude"))) ;
            //end  commit
            editorCircleLoc.putLong( EmplConst4ShPrfOrIntent.CIRCLE_LONGITUDE, Double.doubleToLongBits(data.getDouble("longitude"))) ;
            editorCircleLoc.putLong( EmplConst4ShPrfOrIntent.CIRCLE_RADIUS, Double.doubleToLongBits(data.getDouble("radius"))) ;
            editorCircleLoc.commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return сircle;
    }
}
