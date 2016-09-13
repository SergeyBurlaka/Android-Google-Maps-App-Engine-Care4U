
package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.gps;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.AsynTaskForEmployee;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.EmployeeAsynTasks;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.constants.EmplConst4ShPrfOrIntent;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.map.CircleLabel;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.constants.MessageConstant;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.onreceive.service.creator.CreateControlMessageToM;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.onreceive.service.creator.CreateEmployeeOutsideMessage;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.notifications.ToEmployeeTurnGPS;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.R;

/**
 * Created by Operator on 15.06.2016.
 */
public class GPSService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    GoogleApiClient mGoogleApiClient;
    LocationRequest locationRequest;
    double  circleLatitude, circleLongitude, circleRadius;
    private final int OUTSIDE = 3;
    private final int INSIDE = 5;
    private final int BEGIN = 0;
    private int locationStatus ;
    NotificationManager nm;
    private  int NOTIFICATION_ID = 123123;
    private CircleLabel circleLabel;
    private SharedPreferences.Editor editorForStsatusSpy;

    private SharedPreferences.Editor saveStatusRequestLoc;
    //private Thread threadForIndicate;
    //private boolean stopSendNotification = false;
    // Идентификатор уведомления
   // private static final int NOTIFY_SPY_FOR_EMPLOYEE_ID = 101;

    public GPSService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO_: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        locationStatus = BEGIN;
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle params = intent.getExtras();
        if  (params != null) {
            circleLatitude = params.getDouble("latitude", 0);
            circleLongitude = params.getDouble("longitude", 0);
            circleRadius = params.getDouble("radius", 0);
        }

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();

        startForeground(NOTIFICATION_ID, new Notification());
        //<!---22june16 14:00
        //TODOd+from2h_total: 1>>>
        //TODO_done+from1:20: set status for employee SPY_STATUS in shared preference
         editorForStsatusSpy = this.getSharedPreferences(EmplConst4ShPrfOrIntent.STATUS_SPY , this.MODE_PRIVATE).edit();
        editorForStsatusSpy.putInt( EmplConst4ShPrfOrIntent.STATUS_SPY , EmplConst4ShPrfOrIntent.SPY  );
        //end  commit
        editorForStsatusSpy.commit();
        //-->
        return  START_REDELIVER_INTENT;
    }


    @Override
    public void onLocationChanged(Location location) {
      // _1 Toast.makeText(this,"GPSService latitude"+Double.toString(location.getLatitude()), Toast.LENGTH_SHORT).show();
      //  Toast.makeText(this,"GPSService longitude"+Double.toString(location.getLatitude()), Toast.LENGTH_SHORT).show();
       // Utility.ReadAndWriteData(this, Utility.readFileName(this), "Still Geofence is not triggered!!!");
        float[] distanceBetween = new float[2];
        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                circleLatitude, circleLongitude, distanceBetween);
        //if outside
        if( distanceBetween[0] >  circleRadius  ){
          //  Toast.makeText(getBaseContext(), "Outside", Toast.LENGTH_LONG).show();
          //check old location Status
           if(locationStatus==INSIDE){
               locationStatus = OUTSIDE;
               showNotification ( "EXIT");
               //<!---16june16 15:50
               //TODO_done+from60min send message to manager  "EXIT" the circle
               sendWinMessageToM(location, MessageConstant.EXIT_CIRCLE);
               //-->
            } else if(locationStatus== BEGIN){

               //set new location Status
               locationStatus = OUTSIDE;
            showNotification ( "Outside in start."+Double.toString(distanceBetween[0]));
               //<!---16june16 15:50
               //TODO_done+from60min: send message to manager  "Outside in start."
               sendWinMessageToM(location, MessageConstant.BEGIN_OUTSIDE);
               //-->
            }
            //if inside
        } else {
            //check old location Status
            if(locationStatus==OUTSIDE){
                //set new location Status
                locationStatus = INSIDE;
                showNotification ( "ENTER");
                //<!---16june16 15:50
                //TODO_done+from60min: send message to manager enter to circle
                sendWinMessageToM(location, MessageConstant.ENTER_CIRCLE);
                //-->
            } else if(locationStatus== BEGIN){
                //set new location Status
                locationStatus = INSIDE;
                showNotification ( "Inside in start.");
                //<!---16june16 15:50
                //TODO_done+from60min: send message to manager Inside in start
                sendWinMessageToM(location, MessageConstant.BEGIN_INSIDE);
                //-->
            }
           // showNotification ( "Inside");
          //  Toast.makeText(getBaseContext(), "Inside", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
       if (!checkGPS ()){ return;}
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationRequest.setFastestInterval(20000);
        locationRequest.setInterval(40000);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,locationRequest,this);

    }



    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //TODO_d+ destroy notification use id
        NotificationManager notifManager= (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notifManager.cancel( EmplConst4ShPrfOrIntent.NOTIFY_SPY_FOR_EMPLOYEE_ID );
        //<!--- 14:06 22june16
        //TODOd+from2h_total 2>>>
        //TODO_d+from1:20: set status NO_SPY in shared preference to employee
        editorForStsatusSpy = this.getSharedPreferences(EmplConst4ShPrfOrIntent.STATUS_SPY , this.MODE_PRIVATE).edit();
        editorForStsatusSpy.putInt( EmplConst4ShPrfOrIntent.STATUS_SPY , EmplConst4ShPrfOrIntent.NO_SPY );
        //end  commit
        editorForStsatusSpy.commit();

        //-->
        //<!--- 14:07
        //TODO_ 3>>>
        //TODO *-later-*
        //TODO_later SEND STOP_SERVICE message used out message to manager
        //-->

      //  Toast.makeText(this, "Service Destroyed", Toast.LENGTH_SHORT).show();

        if(mGoogleApiClient.isConnected()) {
            //mGoogleApiClient.disconnect();
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    private void sendWinMessageToM(Location tempLocation, int statusConst){

        circleLabel = new CircleLabel();
        //Began to send notification to manager  that employee is out
        // get info for making asyn request to server
        SharedPreferences shared = getSharedPreferences("info",MODE_PRIVATE);
        //Using getXXX- with XX is type date you wrote to file "name_file"
        String string_temp = shared.getString("employee id","");
        shared = getSharedPreferences(EmplConst4ShPrfOrIntent.CIRCLE_LOCATION,MODE_PRIVATE);
        //Using getXXX- with XX is type date you wrote to file "name_file"
          circleLabel.setLatitude(Double.longBitsToDouble(shared.getLong(EmplConst4ShPrfOrIntent.CIRCLE_LATITUDE, 0))) ;
        circleLabel.setLongitude(Double.longBitsToDouble(shared.getLong(EmplConst4ShPrfOrIntent.CIRCLE_LONGITUDE, 0)));
        circleLabel.setRadius(Double.longBitsToDouble(shared.getLong(EmplConst4ShPrfOrIntent.CIRCLE_RADIUS, 0))); ;
        if ( string_temp!="") {

            Long myIdInBase = Long.parseLong(string_temp);

            // taking loc data in our message in JSON FORMAT
            CreateEmployeeOutsideMessage createMessage = new CreateEmployeeOutsideMessage(tempLocation.getLatitude(), tempLocation.getLongitude(),"@MATRIX_IS_EXIST", statusConst,circleLabel );
           // CreateEmployeeOutsideMessage createMessage = new CreateEmployeeOutsideMessage(tempLocation.getLatitude(), tempLocation.getLongitude(),"@MATRIX_IS_EXIST", statusConst,circleLabel );
            String message = createMessage.getSpyMessage();
            // SEND_SPY_STATUS is not outside message.
            // It is for  sending different information about employee life.
            //ManagerEmail is not manager email. It is the message!
            // And do not think. It is all MATRIX.
            new EmployeeAsynTasks(this, myIdInBase, message, AsynTaskForEmployee.SEND_SPY_STATUS).execute();
        }
    }

    private void showNotification(String message) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext());
       // android.support.v7.app.NotificationCompat.Builder builder = new android.support.v7.app.NotificationCompat.Builder(this);
        notificationBuilder
                .setSmallIcon(R.mipmap.eye)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.care_u))
                .setContentTitle("Status: " + message)
                .setContentText("Status: " + message)
                .setVibrate(new long[]{500, 500})
                .setAutoCancel(true);
        // Notification notification = builder.getNotification(); // до API 16
       // Notification notification = builder.build();

        //NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(1,  notificationBuilder.build());
    }




    /*
     *  In case of failed start spy because GPS disable
    */
    private boolean checkGPS() {
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            //  Toast.makeText(this, "Provider is disable", Toast.LENGTH_LONG).show();
            //<!-- 12 jule 16 17:15
            //TODO_d+>>1
            //TODO_d+from26min create notification turn GPS
            if(mGoogleApiClient != null && mGoogleApiClient.isConnected() )mGoogleApiClient.disconnect();
            //If requests comes to often, we must set some 15 cek block
            //Because notification also shows to often
            /*if (stopSendNotification){ stopSelf(); return false;}
            blockNotificationSomeTimeThread();*/
            sendFailedSpyMessageToM();
            ToEmployeeTurnGPS gpsNotification = new ToEmployeeTurnGPS(this);
            gpsNotification.ShowNotification();
            stopSelf();
            //-->
            // new CommandWrapper(new EnableGpsCommand(this);
            return false;
        }
        return true;
    }

    private void sendFailedSpyMessageToM() {
        //<!-- 14 jule 16
        //TODO_DONE_ALL_FROM_TOTAL_TIME_3H >> *---1--*
        //TODO_done+19min send notification to manager GPS is disable
        //TODO_done+19min we ask employee toggle GPS on please wait, or connect with employee


        //<!-- 28.07.2016  12:40

        //TODO_38min set in shPrfrnc boolean reqToEmpl_SPY false
        //saveStatusRequestLoc = getSharedPreferences(EmplConst4ShPrfOrIntent.INFO_REQ_SPY, this.MODE_PRIVATE).edit();
        //saveStatusRequestLoc.putBoolean(EmplConst4ShPrfOrIntent.REQ_ACTIVE_SPY, false).commit();
        //-->

        //Began to send notification to manager  that employee is out
        // get info for making asyn request to server
        SharedPreferences shared = getSharedPreferences("info",MODE_PRIVATE);
        //Using getXXX- with XX is type date you wrote to file "name_file"
        String string_temp = shared.getString("employee id","");
        shared = getSharedPreferences(EmplConst4ShPrfOrIntent.CIRCLE_LOCATION,MODE_PRIVATE);

        if ( string_temp!="") {

            Long myIdInBase = Long.parseLong(string_temp);

            // taking loc data in our message in JSON FORMAT
            CreateControlMessageToM createMessage = new CreateControlMessageToM("@MATRIX_IS_EXIST", MessageConstant.SPY_DID_NOT_START);
            String message = createMessage.getSpyMessage();
            // SEND_SPY_STATUS is not outside message.
            // It is for  sending different information about employee life.
            //ManagerEmail is not manager email. It is the message!
            // And do not think. It is all MATRIX.
            new EmployeeAsynTasks(this, myIdInBase, message, AsynTaskForEmployee.SEND_SPY_STATUS).execute();
        }

        //endTODO-->
    }
}
