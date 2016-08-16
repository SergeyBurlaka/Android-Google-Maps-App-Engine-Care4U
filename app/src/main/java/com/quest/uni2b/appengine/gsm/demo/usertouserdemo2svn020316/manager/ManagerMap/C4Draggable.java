package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.ManagerMap;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.EmployeeMap.CircleLabel;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.R;

/**
 * Created by Operator on 22.07.2016.
 */
public class  C4Draggable extends C4Spy implements CSetStrategy {



    private CircleLabel circleLabel4E;


    private Marker   marker;


    C4Draggable(UserMapsActivity context) {

        super(context);
    }


    public Marker getMarker() {
        return marker;
    }

    public CircleLabel getCircleLabel4E() {
        return circleLabel4E;
    }

    @Override
    public Circle setCircle(GoogleMap map) {

        //if (MessageConstant.NO_SPY != getStatus())return;

        //<!--10:41
        //// TODO_: 15.07.2016
        circleLabel4E = getCircleLabel();
        LatLng lLCircle = new LatLng( circleLabel4E.getLatitude() , circleLabel4E.getLongitude());

        marker = map.addMarker(new MarkerOptions()
                .position(lLCircle)
                .draggable(true)

        );

        // draw circle on map
        //
        Circle circleAreaMarker = map.addCircle(new CircleOptions()
                .center(lLCircle)
                .radius( circleLabel4E.getRadius())
                .strokeColor(Color.BLACK)
                .fillColor(ContextCompat.getColor(context, R.color.outside_color_pink))

                // Border width of the circle
                .strokeWidth(2)
        );




        return circleAreaMarker;

    }


}
