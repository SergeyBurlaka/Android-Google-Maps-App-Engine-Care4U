package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.geofence;

/**
 * Created by Operator on 13.06.2016.
 */
import com.google.android.gms.location.Geofence;

import java.io.Serializable;

public class MyGeofence implements Serializable {

    private static final int ONE_MINUTE = 60000;

    private int id;
    private double latitude;
    private double longitude;
    private float radius;
    //private int transitionType;

   /*
    public MyGeofence(int id, double latitude, double longitude, float radius, int transitionType) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
       this.transitionType = transitionType;
    }*/

    public MyGeofence(int id, double latitude, double longitude, float radius) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        // this.transitionType = transitionType;
    }

    public Geofence toGeofence() {
        return new Geofence.Builder()

                // Set the request ID of the geofence. This is a string to identify this
                // geofence.
                .setRequestId(String.valueOf(id))

                //detect ENTER OR EXIT
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                        Geofence.GEOFENCE_TRANSITION_EXIT)

                .setCircularRegion(latitude, longitude, radius)

                //если вы передаете в качестве параметра NEVER_EXPIRE,
                // то вы обязаны позаботиться об удалении объекта самостоятельно.
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .build();
    }

   /*
    public int getTransitionType() {
        return transitionType;
    }*/
}