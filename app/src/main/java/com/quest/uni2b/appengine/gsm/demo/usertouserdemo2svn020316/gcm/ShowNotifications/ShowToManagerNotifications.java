package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.ShowNotifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;

import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.R;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.GCMConstants.MessageConstant;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.EmployeeDataWork.EmployeeLocation;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.IfOnlineEControl.AlarmServiceReceiver;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.ManagerMap.UserMapsActivity;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Operator on 01.04.2016.
 */
public class ShowToManagerNotifications {

    private  final int NOTIFY_ID = 103;
    private String string_temp;
    private  long myIdInBase;
    private SharedPreferences.Editor editor;
    private String emailFrom;
    private Intent intent;
    private SharedPreferences.Editor saveCircleLblShared;
    private SharedPreferences.Editor saveSpyEmployeeToList;
    private SharedPreferences getSpyEmployeeFromList;
    private AlarmServiceReceiver employeeControl;
    private Context context;


    public ShowToManagerNotifications(EmployeeLocation employeeLocation) {
        this.employeeLocation = employeeLocation;
    }


    private EmployeeLocation employeeLocation;


    //<!---16:11 16june 16
    //show different status of employee spying: ENTER EXIT or BEGIN
   public  void ShowNotification (Context context){
        this.context = context;
       //for save list of spy employee
       saveSpyEmployeeToList = context.getSharedPreferences(MessageConstant.SPY_EMPLOYEE_LIST, context.MODE_PRIVATE).edit();
       getSpyEmployeeFromList = context.getSharedPreferences(MessageConstant.SPY_EMPLOYEE_LIST, context.MODE_PRIVATE);
       emailFrom = employeeLocation.getEmployeeEmail();
       editor = context.getSharedPreferences(MessageConstant.STATUS_SPY_VIEW , context.MODE_PRIVATE).edit();
       saveCircleLblShared = context.getSharedPreferences(emailFrom, context.MODE_PRIVATE).edit();
       SharedPreferences shared = context.getSharedPreferences("info", context.MODE_PRIVATE);
       //Using getXXX- with XX is type date you wrote to file "name_file"
       string_temp = shared.getString("manager id","");
       if ( string_temp =="") {
         //  Toast.makeText(context, "You have not manager acc!", Toast.LENGTH_LONG).show();
       }else{
           myIdInBase = Long.parseLong(string_temp);}
           Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            //Intent notificationIntent = new Intent();
            Intent mapIntent = new Intent(context, UserMapsActivity.class);
            //mapIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mapIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            mapIntent.putExtra("mamagerId",  myIdInBase);
            mapIntent.putExtra(MessageConstant.EMPLOYEE_LOC_EMAIL, employeeLocation.getEmployeeEmail());
            PendingIntent contentIntent = PendingIntent.getActivity(context,
                (int) Calendar.getInstance().getTimeInMillis(),  mapIntent,
                0);

        //Assign inbox style notification
        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
      // Spannable sb = new SpannableString("exit");

       switch (employeeLocation.getStatusConst()) {
           case MessageConstant.EXIT_CIRCLE:
               bigText.bigText( "   Alarm!!!"+"\n" + emailFrom + " exit the circular area, that you created."
                       +"\n"+" He isn't in Safe Zone, now!");
               sendToBroadcastReceiverRefreshLoc(context, true );
               break;

           case MessageConstant.ENTER_CIRCLE:
               bigText.bigText( "Cancel alarm =) "+"\n" + emailFrom +" enter the circular area, that you created."
                       +"\n"+" He is in Safe Zone, now.");
               sendToBroadcastReceiverRefreshLoc(context, true );
               break;

            case MessageConstant.BEGIN_OUTSIDE:
                saveCircleLblShared.putInt(MessageConstant.CLICKED_SPY,  MessageConstant.DISABLE );
                saveCircleLblShared.commit();
                sendToBroadcastReceiverRefreshLoc(context, false );
                bigText.bigText( "Spy is begin. But terrible happened."+ "\n"+emailFrom +  " is outside the circular area, that you created.");
                break;

            case MessageConstant.BEGIN_INSIDE:
                saveCircleLblShared.putInt(MessageConstant.CLICKED_SPY,  MessageConstant.DISABLE );
                saveCircleLblShared.commit();
                sendToBroadcastReceiverRefreshLoc(context, false);
                //--->
                bigText.bigText( "Spy is begin. "+"\n"+emailFrom +" is inside the circular area, that you created.");
                break;
       }

       // bigText.bigText("EmployeeOfM is outside!");
        bigText.setBigContentTitle("Care4U");
        bigText.setSummaryText(" See details");
        Resources res = context.getResources();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentIntent(contentIntent);
                //.setSmallIcon(android.R.drawable.ic_dialog_email)
                //.setSmallIcon(R.mipmap.letter)
                //.setSmallIcon(R.mipmap.siren)
       switch (employeeLocation.getStatusConst()) {
           case MessageConstant.BEGIN_OUTSIDE:
           case MessageConstant.EXIT_CIRCLE:
               //<!---13:45 17june16
               //set status in shared preference for showing it in view in  manager cabinet list:
               editor.putInt(emailFrom, MessageConstant.EXIT_CIRCLE  );
               //end  commit
               editor.commit();

           builder.setSmallIcon(R.mipmap.siren_2)
                   .setTicker(emailFrom + " is outside! Care4U.")
                   .setContentText(emailFrom + " exit the circular area!"); // Текст уведомления;
            break;
           case MessageConstant.ENTER_CIRCLE:
           case MessageConstant.BEGIN_INSIDE:
               //<!---13:45 17june16
               //set status in shared preference for showing it in view in  manager cabinet list:
               editor.putInt(emailFrom, MessageConstant.ENTER_CIRCLE );
               editor.commit();
               builder.setSmallIcon(R.mipmap.smille_i )
                       .setTicker(emailFrom + " is inside! Care4U.")
               .setContentText(emailFrom + " enter the circular area");
               break;
       }

               // .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.map_marker))
       builder.setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.care_u))
                        //.setTicker(res.getString(R.string.warning))
                //.setTicker("Employee is out! Care4U.")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                        //.setContentTitle(res.getString(R.string.notifytitle))
                .setContentTitle("Care4U")
                        //.setContentText(res.getString(R.string.notifytext))
                //.setContentText(
                //       "EmployeeOfM is outside!") // Текст уведомления
                .setStyle(bigText)
                .setSound(soundUri)
                        //.addAction(R.drawable.map_marker, "show map", contentIntent).setAutoCancel(true)
                .build();
        Notification notification = builder.build();
        notification.flags = notification.flags | Notification.FLAG_INSISTENT;
        //notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.sound = soundUri;
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFY_ID, notification);
    }



    private void startEmployeeControlService(Context context) {
         employeeControl = new AlarmServiceReceiver();
        employeeControl.startControl(context);
    }


    //<!--- 29.07.2016
    private void saveInSpyEmployeeList() {
      //  Toast.makeText(context, "Begin save ###"+emailFrom,Toast.LENGTH_SHORT).show();
        Set <String> employeeList=null;
         employeeList = getSpyEmployeeFromList.getStringSet(MessageConstant.SPY_EMPLOYEE_LIST, null );
        if (employeeList == null)  employeeList = new HashSet<>();
           // Toast.makeText(context, "get from util "+emailFrom,Toast.LENGTH_SHORT).show();}
        employeeList.add(emailFrom);
        saveSpyEmployeeToList.putStringSet (MessageConstant.SPY_EMPLOYEE_LIST, employeeList);
        saveSpyEmployeeToList.commit();
    }


    private void sendToBroadcastReceiverRefreshLoc(Context context, Boolean onlyRefresh) {
        // 26.07.2016
        // send to user map challenge to refresh employee location
        intent = new Intent(MessageConstant.ACTION_START_SPY );

        //set status only refresh?
        intent.putExtra("justRefresh", onlyRefresh);
        ///intent.putExtra("longitude", longitude);

        //  intent.putExtra("status4Alert", status4Control);
        context.sendBroadcast(intent);
    }
}
