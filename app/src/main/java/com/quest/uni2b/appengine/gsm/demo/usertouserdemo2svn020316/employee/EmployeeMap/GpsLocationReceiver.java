package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.EmployeeMap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;

import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.AsynTaskForEmployee;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.Constants4Emploee.EmplConst4ShPrfOrIntent;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.EmployeeAsynTasks;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.GCMConstants.MessageConstant;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.ShowNotifications.TurnGPSNotification;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.controller.MessagesService.ControlMessageToM;

/**
 * Created by Operator on 12.07.2016.
 */
public class GpsLocationReceiver extends BroadcastReceiver {

    private long myIdInBase = 0;
    private SharedPreferences shared;
    private Context context;
    private Intent intent;
    private SharedPreferences getStatusRequest;
    private LocationManager locationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        constructorOnReceive(context, intent);
        if (!providerLocationChanged()) return;

        if (EmplConst4ShPrfOrIntent.SPY == getSpyStatus()) checkGPS();
        else {


            //<!-- 28.07.2016
            //TODO_done_form1hour_of_debug_in15:00 >>----1.1----------
            //TODO_+from50min  if(providerLocationChanged() -->
            //TODO_+from50min and statusReqtoELoc -true?  --> checkGPS --> ifEnable  sendGoodMessage
            //TODO_+from50min and statusReqEmploy_SPY true? -->checkGPS --> ifEnable sendGoodMEssg

            // getStatusRequest = context.getSharedPreferences(EmplConst4ShPrfOrIntent.INFO_REQ_SPY, context.MODE_PRIVATE);

            /*if( getStatusRequest.getBoolean(EmplConst4ShPrfOrIntent.REQ_ACTIVE_SPY, false)&&!isGPSDisable()) {
                sendNotificationToM(MessageConstant.TURN_GPS_SPY);;
                return;}*/

            getStatusRequest = context.getSharedPreferences(EmplConst4ShPrfOrIntent.INFO_REQ_LOC, context.MODE_PRIVATE);
            if (getStatusRequest.getBoolean(EmplConst4ShPrfOrIntent.REQ_ACTIVE_LOC, false) && !isGPSDisable())
                sendNotificationToM(MessageConstant.WIN_GET_LOC);

        }

        //stopUsingGPS();

        //googleMap.setMyLocationEnabled(false);
        //locationManager.removeUpdates();

       /* if( mGoogleApiClient != null && mGoogleApiClient.isConnected() ) {
            mGoogleApiClient.disconnect();
        }*/

    }

    private void constructorOnReceive(Context context, Intent intent) {
        this.intent = intent;
        this.context = context;
    }

    private void checkGPS() {
        if (isGPSDisable()) raiseTheAlarm();
        else {

            // <!--27.07.2016
            //TODO_+ send good message to M
            //TODO_+ MessageConstant.EnableGps
            sendNotificationToM(MessageConstant.TURN_GPS_ENABLE);

        }
    }


    private boolean providerLocationChanged() {
        if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) return true;
        else return false;
    }


    private boolean isGPSDisable() {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) return true;
        else return false;
    }

    private void raiseTheAlarm() {
        //<!-- 12 jule 16 17:15
        //TODO_d+from26min create notification turn GPS

        //If requests comes to often, we must set some 15 cek block
        //Because notification also shows to often
        sendNotificationToM(MessageConstant.TURN_GPS_DISABLE);
        TurnGPSNotification gpsNotification = new TurnGPSNotification(context);
        gpsNotification.ShowNotification();

    }

    private void sendNotificationToM(int messageToManager) {

        //Began to send notification to locationManager  that employee is out
        // get info for making asyn request to server
        SharedPreferences shared = context.getSharedPreferences("info", context.MODE_PRIVATE);
        String string_temp = shared.getString("employee id", "");

        if (string_temp != "") {
            Long myIdInBase = Long.parseLong(string_temp);

            // taking loc data in our message in JSON FORMAT

            // <!-- 27.07.2016
            //TODO_+ make MessageConstant.TURN_GPS_DISABLE
            ControlMessageToM createMessage = new ControlMessageToM("@MATRIX_IS_EXIST", messageToManager);
            String message = createMessage.getSpyMessage();

            // SEND_SPY_STATUS is not outside message.
            // It is for  sending different information about employee life.
            //ManagerEmail is not locationManager email. It is the message!
            // And do not think. It is all MATRIX.
            new EmployeeAsynTasks(context, myIdInBase, message, AsynTaskForEmployee.SEND_SPY_STATUS).execute();
        }
    }

    private int getSpyStatus() {
        shared = context.getSharedPreferences(EmplConst4ShPrfOrIntent.STATUS_SPY, context.MODE_PRIVATE);
        int statusSpy = shared.getInt(EmplConst4ShPrfOrIntent.STATUS_SPY, EmplConst4ShPrfOrIntent.NO_SPY);
        return statusSpy;
    }


    /**
     * Stop using GPS
     * Calling this function will stop using GPS in your app
     */
    public void stopUsingGPS() {
        if (locationManager != null) {
           // locationManager.removeUpdates(intent);

        }
    }
}