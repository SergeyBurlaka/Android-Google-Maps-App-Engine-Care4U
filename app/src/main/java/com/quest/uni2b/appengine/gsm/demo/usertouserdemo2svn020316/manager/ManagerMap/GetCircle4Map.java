package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.ManagerMap;

import android.content.SharedPreferences;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.GCMConstants.MessageConstant;

/**
 * Created by Operator on 22.07.2016.
 */
public class GetCircle4Map {

    GoogleMap map;
    private SharedPreferences.Editor saveCircleLblShared;
    String employeeSelected;
    UserMapsActivity context;
    Circle circleSaveInShared;;

    public GetCircle4Map(UserMapsActivity context){
        this.context = context;
        this.employeeSelected = context.employeeSelected;
        //this.employeeLocation = context.employeeLocation;
        this.map = context.mMap;
        //this.employeeLocation = context.employeeLocation;
        //sharedGetCircle = context.getSharedPreferences(employeeSelected,context.MODE_PRIVATE);
        saveCircleLblShared = context.getSharedPreferences(employeeSelected, context.MODE_PRIVATE).edit();
    }

    /*
    *   Save Circle. Method has bag. Because save only marker
      *   But if status spy - must save the circle
    */
    void saveCircleLabel(Circle saveCircle ) {
        /*LatLng ll_circle = circleSaveInShared.getCenter();
        double lat = ll_circle.latitude;
        double lng = ll_circle.longitude;
        double radius = circleSaveInShared.getRadius();*/
        if (saveCircle == null) return;
        LatLng ll_circle = saveCircle.getCenter();
        double lat = ll_circle.latitude;
        double lng = ll_circle.longitude;
        double radius = saveCircle.getRadius();
        // saveCircleLblShared = this.getSharedPreferences(employeeSelected, this.MODE_PRIVATE).edit();
        saveCircleLblShared.putLong(MessageConstant.CIRCLE_LATITUDE, Double.doubleToLongBits(lat) );
        saveCircleLblShared.putLong(MessageConstant.CIRCLE_LONGITUDE, Double.doubleToLongBits(lng) );
        saveCircleLblShared.putLong(MessageConstant.CIRCLE_RADIUS, Double.doubleToLongBits(radius) );
        saveCircleLblShared.commit();
    }


    public Circle executeSetCircle (CSetStrategy circleStrategy){
       return circleStrategy.setCircle(map);
    }

}
