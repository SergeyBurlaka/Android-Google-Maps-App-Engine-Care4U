package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.ManagerMap;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.R;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.EmployeeMap.CircleLabel;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.GCMConstants.MessageConstant;

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


    C4Spy(UserMapsActivity context ) {

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
        //TODO_d+from4h 22june 19:00: 2>>
        //TODO_d+from3:30h check status spy from sharedGetCircle preference
        //TODO_d+from3:30h: if status not equals no_SPY - set circle


        //Sh Pr "status_spy_view" is list of employee emails and theirs statuses
        //sharedGetStatus = getSharedPreferences(MessageConstant.STATUS_SPY_VIEW,MODE_PRIVATE);
      //  sharedGetStatus.getInt( employeeSelected, MessageConstant.NO_SPY);


        //sharedGetCircle = getSharedPreferences(employeeSelected,MODE_PRIVATE);
        //Using getXXX- with XX is type date you wrote to file "name_file"
        circleLabelForDraw.setLatitude(Double.longBitsToDouble(sharedGetCircle.getLong(MessageConstant.CIRCLE_LATITUDE, 0))) ;
        circleLabelForDraw.setLongitude(Double.longBitsToDouble(sharedGetCircle.getLong(MessageConstant.CIRCLE_LONGITUDE, 0)));
        circleLabelForDraw.setRadius(Double.longBitsToDouble(sharedGetCircle.getLong(MessageConstant.CIRCLE_RADIUS,0))); ;

        workAroundSetRadiusCircle (circleLabelForDraw);


        return circleLabelForDraw;

    }

    void workAroundSetRadiusCircle(CircleLabel circleLabelForDraw) {
        //Toast.makeText(getApplicationContext(),String.valueOf(circleLabelForDraw.getRadius()), Toast.LENGTH_SHORT).show();
        if(circleLabelForDraw.getRadius()==0) circleLabelForDraw.setRadius( START_RADIUS_VALUE);
        //<!-- 28.07.2016
        //TODO_later if lat=0 & long=0
        //TODO_later set radius in current position
       // if(circleLabelForDraw.getLatitude() == 0 &&mCurrentLocation!=null) {circleLabelForDraw.setLatitude(mCurrentLocation.getLatitude()); circleLabelForDraw.setLongitude(mCurrentLocation.getLatitude());}
        //-->
    }




}
