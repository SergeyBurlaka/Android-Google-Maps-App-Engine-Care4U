package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.ManagerMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.EmployeeDataWork.EmployeeLocation;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.R;

/**
 * Created by Operator on 22.07.2016.
 */
public class C4SpyFNotification implements CSetStrategy {

    EmployeeLocation employeeLocation;
    UserMapsActivity context;
    String employeeSelected;
    Marker markerEmployee;

    C4SpyFNotification(UserMapsActivity context) {
        super( );
        this.context = context;
        this.employeeLocation = context.employeeLocation;
        this.employeeSelected = context.employeeSelected;
    }


    @Override
    public Circle setCircle(GoogleMap map) {

        Circle oldCircle  = map.addCircle(new CircleOptions()
                .radius(employeeLocation.getRadius())
                .center(new LatLng(employeeLocation.getCircle1latitude(), employeeLocation.getCircleLongitude()))
                .strokeColor(Color.BLACK)

                // Fill color of the circle
                // 0x represents, this is an hexadecimal code
                // 55 represents percentage of transparency. For 100% transparency, specify 00.
                // For 0% transparency ( ie, opaque ) , specify ff
                // The remaining 6 characters(00ff00) specify the fill color
                //.fillColor(0x64bbc5)
                . fillColor(ContextCompat.getColor(context, R.color.outside_color_pink))
                // Border width of the circle
                .strokeWidth(2));
        //  Log.v(TAG, "location"+employeeLocation.latitude+"/"+employeeLocation.longitude);
        // mMarker = mMap.addMarker(markerEmployeeLoc.position(new LatLng(latitude, longitude)).title(employeeSelected));
        markerEmployee = map.addMarker( new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromBitmap(writeTextOnDrawable(R.mipmap.foot, "" )))
                .position(new LatLng(employeeLocation.getEmployeeLatitude(), employeeLocation.getEmployeeLongitude())).title(employeeSelected+ " crossed area here." )

        );

        //camera

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target( markerEmployee.getPosition())
                //.zoom(map.getCameraPosition().zoom)
                .zoom(17)
                //.bearing(targetBearing)
                //.tilt(20)
                .build();

        // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mMarker.getPosition(), 14));

        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        map.animateCamera(cameraUpdate);


        return oldCircle;
    }


    /*
       Create marker in map
   */
    Bitmap writeTextOnDrawable(int drawableId, String text) {

        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), drawableId)
                .copy(Bitmap.Config.ARGB_4444, true);

        Typeface tf = Typeface.create("Helvetica", Typeface.BOLD);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        paint.setTypeface(tf);
        paint.setFakeBoldText(true);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(convertToPixels(context, 11));

        Rect textRect = new Rect();
        //paint.getTextBounds(text, 0, text.length(), textRect);

        Canvas canvas = new Canvas(bm);
        // canvas.drawText(text, 0, paint.getTextBounds(text, 0, text.length(), textRect), paint);

        //If the text is bigger than the canvas , reduce the font size
        if(textRect.width() >= (canvas.getWidth() - 4))     //the padding on either sides is considered as 4, so as to appropriately fit in the text
            paint.setTextSize(convertToPixels(context, 7));        //Scaling needs to be used for different dpi's

        //Calculate the positions
        int xPos = (canvas.getWidth() / 2) - 2;     //-2 is for regulating the x position offset

        //"- ((paint.descent() + paint.ascent()) / 2)" is the distance from the baseline to the center.
        int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2)) ;

        canvas.drawText(text, xPos, yPos, paint);

        return  bm;
    }


    public static int convertToPixels(Context context, int nDP)
    {
        final float conversionScale = context.getResources().getDisplayMetrics().density;

        return (int) ((nDP * conversionScale) + 0.5f) ;

    }



}
