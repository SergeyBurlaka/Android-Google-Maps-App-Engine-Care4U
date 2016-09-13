package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.gps;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.AsynTaskForEmployee;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.constants.EmplConst4ShPrfOrIntent;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.EmployeeAsynTasks;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.constants.MessageConstant;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.notifications.ToEmployeeTurnGPS;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.onreceive.service.creator.CreateControlMessageToM;

//import android.widget.Toast;

/**
 * Created by Operator on 26.05.2016.
 */

public class GetLocationService extends Service implements ConnectionCallbacks, OnConnectionFailedListener {

    Location location;
    private long myIdInBase;
    Intent intent;
    LocationRequest locationRequest;
    private Thread threadForIndicate;
    static volatile boolean stopSendNotification = false;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    final String LOG_TAG = "myLogs";
    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    protected static final String TAG = "basic-location-sample";
    LocationSettingsRequest.Builder builder;

    private SharedPreferences.Editor saveStatusRequestLoc;

    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "MyService onCreate");

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "MyService onDestroy");

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }

    }

    public int onStartCommand(Intent intent, int flags, int startId) {
       // Log.d(LOG_TAG, "MyService onStartCommand");

       //  Toast.makeText(this, "MyService onStartCommand", Toast.LENGTH_LONG).show();
        myIdInBase = intent.getLongExtra("id", 0);
        mGoogleApiClient.connect();

        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {

        //If GPS disable send notification to manager and employee too
        if (isGPSDisable()) {disconnectGoogleApi();raiseTheAlarm (); stopSelf(); return;}

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(mLastLocation == null){ disconnectGoogleApi(); stopSelf(); return;}

        double[] locArray = new double[]{mLastLocation.getLatitude(), mLastLocation.getLongitude()};
        //<!-- 28.07.2016
        //TODO_done_form1hour_of_debug_in15:00 >> ----1.1----key
        //TODO_+from30min set in shPrfrnc boolean reqToEmplLoc false
        saveStatusRequestLoc = getSharedPreferences(EmplConst4ShPrfOrIntent.INFO_REQ_LOC, this.MODE_PRIVATE).edit();
        saveStatusRequestLoc.putBoolean(EmplConst4ShPrfOrIntent.REQ_ACTIVE_LOC, false).commit();
        //-->

        //Make request to server for task sending message to manager client with employee location
        new EmployeeAsynTasks(myIdInBase, locArray, AsynTaskForEmployee.TAKE_LOCATION).execute(); disconnectGoogleApi(); stopSelf();


    }


    private boolean isGPSDisable() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) return true;
        else return  false;
    }

    private void raiseTheAlarm() {
        //<!-- 12 jule 16 17:15
        //TODO_d+from26min create notification turn GPS


        //If requests comes to often, we must set some 15 cek block
        //Because notification also shows to often

        if (stopSendNotification) return;
        blockNotificationSomeTimeThread();

        //Send messages to Manager & to Employee
        sendNotificationToM();
        ToEmployeeTurnGPS gpsNotification = new ToEmployeeTurnGPS(this); gpsNotification.ShowNotification();

    }


    private void disconnectGoogleApi() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }


    private void blockNotificationSomeTimeThread() {
        stopSendNotification = true;
        // itemRefresh.setActionView();

        threadForIndicate = new Thread() {
            @Override
            public void run() {

                try {
                    sleep(10000);
                    stopSendNotification = false;

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        threadForIndicate.start();


    }


    private void sendNotificationToM() {
        //<!-- 14 jule 16
        //TODO_DONE_ALL_FROM_TOTAL_TIME_3H >> *---1--*
        //TODO_done+19min send notification to manager GPS is disable
        //TODO_done+19min we ask employee toggle GPS on please wait, or connect with employee
        //Began to send notification to manager  that employee is out
        // get info for making asyn request to server
        SharedPreferences shared = getSharedPreferences("info", MODE_PRIVATE);
        //Using getXXX- with XX is type date you wrote to file "name_file"
        String string_temp = shared.getString("employee id", "");

        if (string_temp != "") {

            Long myIdInBase = Long.parseLong(string_temp);

            // taking loc data in our message in JSON FORMAT
            CreateControlMessageToM createMessage = new CreateControlMessageToM("@MATRIX_IS_EXIST", MessageConstant.GPS_DISABLE);
            String message = createMessage.getSpyMessage();
            // SEND_SPY_STATUS is not outside message.
            // It is for  sending different information about employee life.
            //ManagerEmail is not manager email. It is the message!
            // And do not think. It is all MATRIX.
            new EmployeeAsynTasks(this, myIdInBase, message, AsynTaskForEmployee.SEND_SPY_STATUS).execute();
        }

        //endTODO-->
    }


    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());

        //Toast.makeText(this, "MyService onConnectionFailed", Toast.LENGTH_LONG).show();

        stopSelf();
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }


    }







