package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.map.circle;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.R;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.map.CircleLabel;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.constants.MessageConstant;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.map.UserMapsActivity;

/**
 * Created by Operator on 22.07.2016.
 */
public class C4Spy implements CSetStrategy {
    String employeeSelected;
    private final double START_RADIUS_VALUE= 100.;
    SharedPreferences sharedGetCircle;
    //SharedPreferences sharedGetStatus;
    //Location mCurrentLocation;
    UserMapsActivity context;

    public C4Spy(UserMapsActivity context) {

        this.context = context;
        this.employeeSelected = context.employeeSelected;

        sharedGetCircle = context.getSharedPreferences(employeeSelected,context.MODE_PRIVATE);
        // saveCircleLblShared = context.getSharedPreferences(employeeSelected, context.MODE_PRIVATE).edit();

       // this.mCurrentLocation = context.mCurrentLocation;

    }

    @Override
    public Circle setCircle(GoogleMap googleMap) {
        // if (getStatus () == MessageConstant.NO_SPY) return;
        CircleLabel circleLabel = getCircleLabel ();
        // public static int parseColor (String colorString)
        Circle oldCircle  = googleMap.addCircle(new CircleOptions()
                .radius(circleLabel.getRadius())
                .center(new LatLng(circleLabel.getLatitude(), circleLabel.getLongitude()))
                .strokeColor(Color.BLACK)
                // Fill color of the circle
                // 0x represents, this is an hexadecimal code
                // 55 represents percentage of transparency. For 100% transparency, specify 00.
                // For 0% transparency ( ie, opaque ) , specify ff
                // The remaining 6 characters(00ff00) specify the fill color

                //TODO_done set green
                .fillColor(ContextCompat.getColor(context, R.color.outside_color_pink))
                // Border width of the circle
                .strokeWidth(2));
        return oldCircle;
    }


     /*
    *   Getter Circle
    *
    * */
    CircleLabel getCircleLabel (){
        CircleLabel circleLabelForDraw = new CircleLabel();
        //<!---13:54 22june16
        circleLabelForDraw.setLatitude(Double.longBitsToDouble(sharedGetCircle.getLong(MessageConstant.CIRCLE_LATITUDE, 0))) ;
        circleLabelForDraw.setLongitude(Double.longBitsToDouble(sharedGetCircle.getLong(MessageConstant.CIRCLE_LONGITUDE, 0)));
        circleLabelForDraw.setRadius(Double.longBitsToDouble(sharedGetCircle.getLong(MessageConstant.CIRCLE_RADIUS,0))); ;
        workAroundSetRadiusCircle (circleLabelForDraw);
        return circleLabelForDraw;
    }

    void workAroundSetRadiusCircle(CircleLabel circleLabelForDraw) {
        //Toast.makeText(getApplicationContext(),String.valueOf(circleLabelForDraw.getRadius()), Toast.LENGTH_SHORT).show();
        if(circleLabelForDraw.getRadius()==0) circleLabelForDraw.setRadius( START_RADIUS_VALUE);
    }

}
