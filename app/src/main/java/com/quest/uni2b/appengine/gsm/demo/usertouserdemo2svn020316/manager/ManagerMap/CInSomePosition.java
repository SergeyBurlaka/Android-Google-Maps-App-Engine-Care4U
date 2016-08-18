package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.ManagerMap;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.R;

/**
 * Created by Operator on 22.07.2016.
 */
public class CInSomePosition implements CSetStrategy {

    LatLng lLCircle;
    double radius;
    Marker   marker;
    UserMapsActivity context;
    public void setRadius(double radius) {
        this.radius = radius;
    }
    public void setlLCircle(LatLng lLCircle) {
        this.lLCircle = lLCircle;
    }

    CInSomePosition(UserMapsActivity context, LatLng lLCircle, double radius) {
        super();
        this.context = context;
        this.lLCircle = lLCircle;
        this.radius = radius;
    }

    @Override
    public Circle setCircle(GoogleMap map) {
        marker = map.addMarker(new MarkerOptions()
                .position(lLCircle)
                .draggable(true)
        );

        // Toast.makeText(getApplicationContext(),"GGGGGGGG"+String.valueOf(circleLabel4E.getRadius()), Toast.LENGTH_SHORT).show();

        // draw circle on map
        //
       Circle circleAreaMarker = map.addCircle(new CircleOptions()
                .center(lLCircle)
                .radius(radius)
                .strokeColor(Color.BLACK)

                .fillColor(ContextCompat.getColor(context, R.color.outside_color_pink))

                // Border width of the circle
                .strokeWidth(2)
        );



        return circleAreaMarker;

    }
}
