package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.controller.MessagesService;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.Constants4Emploee.EmplConst4ShPrfOrIntent;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.EmployeeMap.CircleLabel;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.EmployeeMap.GetLocationService;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.SpyViaGPSService.GPSService;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.SpyViaGeofence.GeofencingService;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.GCMConstants.MessageConstant;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.ShowNotifications.ShowControl4E;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.ShowNotifications.ShowNewEReqToM;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.ShowNotifications.ShowSpyNotifToEmployee;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.ShowNotifications.ShowToEReqInfo;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.ShowNotifications.ShowToManagerNotifications;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.controller.GcmIntentService;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.controller.MyServiseSetMarker;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.EmployeeDataWork.EmployeeLocation;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.ManagerCabinetListActivity;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.ManagerMap.UserMapsActivity;

import org.json.JSONException;
import org.json.JSONObject;

//import android.widget.Toast;

//import android.widget.Toast;

/**
 * Created by Operator on 24.03.2016.
 */
public class MessageAgent {

    private String message;
    private JSONObject reader;
    private int kindMessage;
    private static final String TAG = "MyActivity";
    private String emailFrom;
    private ParseSpyMessage parseSpyMessage;
    private GcmIntentService context;
    private static final int NOTIFY_ID = 101;
    private long myIdInBase = 0;
    //for work with servise to put marker
    private MyServiseSetMarker myService;
    private boolean bound = false;
    //
    public static final String GET_E_LOCATION_ACTION = "com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.Manager";
    //public static final String ACTION_START_SPY = "com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.GCM.notifications";
    private Intent intent;
    //for new_e_req
    private  int amountReq;
    private ShowSpyNotifToEmployee spyNotifToEmployee;
    private EmployeeLocation employeeLocation;
    //for disable air mode
    private final String COMMAND_FLIGHT_MODE_1 = "settings put global airplane_mode_on";
    private final String COMMAND_FLIGHT_MODE_2 = "am broadcast -a android.intent.action.AIRPLANE_MODE --ez state";
    private ParseEmployeeOutsideMessage parseEmployeeOutsideMessage;
    //for control GPS and FLIGHT MODE IN EMPLOYEE
    private int status4Control;
    private SharedPreferences  sharedGetStatus;
    private SharedPreferences.Editor saveStatusRequestLoc;
    //For check isOnlineOffline employee
    private SharedPreferences sp4Emploee;
    private SharedPreferences.Editor sp4EmploeeEdit;


    public MessageAgent(String message, GcmIntentService context) {
                this.context = context;
                this.message = message;
            }


    public void processMessage(){
            try {
                JSONObject reader = new JSONObject(message);
                //get detect constant
                kindMessage = reader.getInt("kind");
                emailFrom = reader.getString("from");
                //ask different task, detected via constant
                distributeTask(kindMessage, reader);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void distributeTask (int kindMessage, JSONObject reader) {
        String tempMessage;
       //JSONObject readerInTask;
        // readerInTask = new JSONObject(message);
        //kindMessage is detecting constant
        switch (kindMessage){
       //*** Message to employee
        case MessageConstant.KIND_SPY:
            saveStatusRequestLoc = context.getSharedPreferences(EmplConst4ShPrfOrIntent.INFO_REQ_LOC, context.MODE_PRIVATE).edit();
            saveStatusRequestLoc.putBoolean(EmplConst4ShPrfOrIntent.REQ_ACTIVE_LOC, true).commit();
            try {
                parseSpyMessage = new ParseSpyMessage(reader.getJSONObject("data"),context);
                //get data about circle - label  from manager message into special object
                CircleLabel circleLabel= parseSpyMessage.getСircle();
                startGPSService (reader.getJSONObject("data"));
                ShowSpyNotifToEmployee spyNotifToEmployee = new ShowSpyNotifToEmployee ( context,circleLabel );
                spyNotifToEmployee.showNotification();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            break;

        case MessageConstant.SIMPLE_MESSAGE:
            try {
              tempMessage =  reader.getString("text");
             //   Toast.makeText(context,tempMessage, Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            break;

       //*** Message to manager
        //TODO_aborted set new name EXIT_ENTER_MESSAGE
            case MessageConstant.OUTSIDE_MESSAGE:
                try {
                        ShowToManagerNotifications showToManager = new ShowToManagerNotifications( parseMessage ( reader));
                    if ( isProblem4GPS(reader)||UserMapsActivity.noMore4Notification||getStatusSpy())return;
                   // if(UserMapsActivity.noMore4Notification)return;
                        showToManager.ShowNotification(context);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            //Give_location = "8"

            //*** Message to employee
            // with request give my location to manager
            //After that as Employee have got this notification, app is calling method getGPSLocation()
            //And send data to manager
            case MessageConstant.GIVE_LOCATION:
                //<!-- 28.07.2016 12:25
                // save in "info" shared Preference requestToEmployeeLoc boolean true
                saveStatusRequestLoc = context.getSharedPreferences(EmplConst4ShPrfOrIntent.INFO_REQ_LOC, context.MODE_PRIVATE).edit();
                saveStatusRequestLoc.putBoolean(EmplConst4ShPrfOrIntent.REQ_ACTIVE_LOC, true).commit();
                SharedPreferences shared = context.getSharedPreferences("info",Context.MODE_PRIVATE);
                //Using getXXX- with XX is type date you wrote to file "name_file"
                String string_temp = shared.getString("employee id", "");
                if ( string_temp ==""){
                 //   Toast.makeText(context, "You have not employee acc!", Toast.LENGTH_LONG).show();
                }else {
                    myIdInBase = Long.parseLong(string_temp);
                }
                /*
                //Make request to server for task sending message to manager client with employee location
                new EmployeeAsynTasks(context, myIdInBase,  getGPSLocation(), AsynTaskForEmployee.TAKE_LOCATION).execute();
                    */
                getGPSLocation();
                break;

            //*** Message to manager
            // info about employee current position on map
            case  MessageConstant.TAKE_LOCATION:
                try {
                    double latitude =  reader.getDouble("latitude");
                    double longitude = reader.getDouble("longitude");
                   // Toast.makeText(context, (int) latitude +"/"+(int) longitude, Toast.LENGTH_LONG).show();
                    setMarker(latitude,longitude);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            //<!--
            //It opens manager cabinet activity.
            //Also it haves some message. For example :"you have 2 new request"
            //New notification rewrites old, because the same notification id
            case MessageConstant.NEW_EMPLOYEE_REQUEST:
                try {
                    amountReq = reader.getInt("amount requests");
                //    Toast.makeText(context,"I have new requestsn " + amountReq+" From "+emailFrom, Toast.LENGTH_LONG).show();
                        ShowNewEReqToM showNewEReqToM = new ShowNewEReqToM( context, emailFrom, amountReq);
                        showNewEReqToM.ShowNotification();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            //<!---
            //show notification - you are confirmed, or you are deleted from request
            case MessageConstant.INFO_TO_EMPLOYEE_REQ:

        String tempMssg = "NONE";
                try {
                   tempMssg= reader.getString("message to employee");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ShowToEReqInfo showToEReqInfo = new ShowToEReqInfo(context,emailFrom,tempMssg);
                showToEReqInfo.ShowNotification();
                break;

            //<!---18:00 17june16
            //just stop spy service in employee client
            case MessageConstant.STOP_SPY:
                //<!--14 40 23june 16
                context.stopService(new Intent(context,GPSService.class));
                break;
    }
    }


    private void reportProblem() {
        //skip parsing and go  to show notification
        // show special notification using ShowControl4E//
        ShowControl4E showToManager = new ShowControl4E(status4Control, emailFrom );
        showToManager.ShowNotification(context);
        // send to broadcast receiver for show alert dialog in user map
        intent = new Intent(GET_E_LOCATION_ACTION);
        intent.putExtra("status4Alert", status4Control);
        context.sendBroadcast(intent);
    }


    private EmployeeLocation parseMessage(JSONObject reader) throws JSONException {
        //parse message
        //for getting new status in  employees recycler view
        ManagerCabinetListActivity.flagNewRequest =true;
        parseEmployeeOutsideMessage = new ParseEmployeeOutsideMessage(reader.getJSONObject("data"), context,emailFrom);
        employeeLocation =    parseEmployeeOutsideMessage.getСircle();
        //detect how employee
        employeeLocation.setEmployeeEmail(emailFrom);
        //  EmployeeLocation.employeeEmail = emailFrom;
        return employeeLocation;
    }


    private boolean isProblem4GPS(JSONObject reader) {
        //<!---
        // 14.07.2016
        try {
            JSONObject data = reader.getJSONObject("data");
              status4Control = data.getInt("status_spy"  );
            //it must be from 4000 to 4999
            if(1 == (status4Control/MessageConstant.GPS_FACE_CONTROL)) {
                reportProblem ();
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
            return  false;
    }


    private void startGPSService (JSONObject data) throws JSONException {
        //just in case service is running
        context.stopService(new Intent(context,GPSService.class));
        Intent  intent1 = new Intent(context, GPSService.class);
        Bundle params = new Bundle();
        //params.putDouble
        params.putDouble("latitude",data.getDouble("latitude"));
        params.putDouble("longitude", data.getDouble("longitude") );
        params.putDouble( "radius", data.getDouble("radius"));
        intent1.putExtras(params);
        context.startService(intent1);
    }


    private void startGeofencingService (){
       // Toast.makeText(context,"connected", Toast.LENGTH_SHORT).show();
        Intent geofencingService = new Intent(context, GeofencingService.class);
        geofencingService.setAction(String.valueOf(Math.random()));
        geofencingService.putExtra(GeofencingService.EXTRA_ACTION, GeofencingService.Action.ADD);
        geofencingService.putExtra(GeofencingService.EXTRA_GEOFENCE,  parseSpyMessage.getMyGeofence());
        context.startService(geofencingService);
      //  context.startService(new Intent(context,GPSService.class));
    }


    private void setMarker(double latitude, double longitude) {
       // Intent intent = new Intent(context, MyServiseSetMarker.class);
       // bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
       //UserMapsActivity context2 = context;
        intent = new Intent(GET_E_LOCATION_ACTION);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        intent.putExtra("employeeE", emailFrom );
        context.sendBroadcast(intent);
        //context.startService(new Intent(context, MyServiseSetMarker.class));
    }


    private void getGPSLocation(){
        Intent  i = new Intent(context, GetLocationService.class);
        i.putExtra("id", myIdInBase);
        context.startService(i);
    }

    /**
     * Gets the state of Airplane Mode.
     *
     * @param context
     * @return true if enabled.
     */
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isAirplaneModeOn(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.System.getInt(context.getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON, 0) != 0;
        } else {
            return Settings.Global.getInt(context.getContentResolver(),
                    Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
        }
    }


   /* public void changedAirMode () {
            try {
                Intent intent = new Intent(android.provider.Settings.ACTION_AIRPLANE_MODE_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                try {
                    Intent intent = new Intent("android.settings.WIRELESS_SETTINGS");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                   // Toast.makeText(context, "not_able_set_airplane", Toast.LENGTH_SHORT).show();
                }
            }
        }*/


    public boolean getStatusSpy() {
       SharedPreferences sharedGetCircle = context.getSharedPreferences(emailFrom,context.MODE_PRIVATE);
        if (MessageConstant.ENABLE == sharedGetCircle.getInt(MessageConstant.STOP_NOTIFICATIONS,MessageConstant.DISABLE)) return true;
        return false;
    }

}












