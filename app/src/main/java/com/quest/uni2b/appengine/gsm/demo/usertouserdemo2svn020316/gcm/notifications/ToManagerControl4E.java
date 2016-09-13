package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.notifications;

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
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.constants.MessageConstant;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.map.UserMapsActivity;

import java.util.Calendar;

/**
 * Created by Operator on 14.07.2016.
 */
public class ToManagerControl4E {

    private  final int NOTIFY_ID = 4000;
    private String string_temp;
    private SharedPreferences.Editor editor;
    private String emailFrom;
    private int status4Control;
    private Context context;


    public ToManagerControl4E(int status4Control, String emailFrom) {
        this.emailFrom = emailFrom;
        this.status4Control = status4Control;
    }


    public  void ShowNotification (Context context){
        this.context = context;
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        if(isReturnFromShowNotification()) return;
        Intent mapIntent = new Intent(context, UserMapsActivity.class);
        mapIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        mapIntent.putExtra("mamagerId",  getMyId());
        mapIntent.putExtra(MessageConstant.EMPLOYEE_LOC_EMAIL, emailFrom);
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                (int) Calendar.getInstance().getTimeInMillis(),  mapIntent, 0);
        switch (status4Control) {

            /*case MessageConstant.GPS_DISABLE:

                bigText.bigText( "      Fail get location "+"\n" + emailFrom + " has disabled GPS. "
                        );
                break;

            case MessageConstant.SPY_DID_NOT_START:

                bigText.bigText( "      Spy didn't start."+"\n" + emailFrom + " has disabled GPS. "
                );
                break;*/

            // <!-- 27.07.2016  20:55
            //TODO_aborted >>1
            //TODO_aborted case MesC.GPS_TURN_DISABLE
            case MessageConstant.TURN_GPS_DISABLE:
                bigText.bigText( "Spy is not working. But yoy don't need to stop it. Spy  begin when "+emailFrom+" will turn gps on.");
                break;
            //TODO_aborted case MessageConstant.GPS_ENABLE
            case MessageConstant.TURN_GPS_ENABLE:
                break;
        }

        //bigText.bigText("EmployeeOfM is outside!");
        bigText.setBigContentTitle("Care4U. " +emailFrom);
        //bigText.setSummaryText(" H to toggle GPS on.");
        Resources res = context.getResources();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        switch (status4Control) {
            /*case MessageConstant.GPS_DISABLE:

                builder.setSmallIcon(R.mipmap.sad_i)
                        .setTicker( "Disabled GPS! Care4U.")
                        .setContentText("Failed. Disabled GPS"); // Текст уведомления
                break;
            case MessageConstant.SPY_DID_NOT_START:

                builder.setSmallIcon(R.mipmap.sad_i)
                        .setTicker( "Disabled GPS! Care4U.")
                        .setContentText("Spy didn't start. Disabled GPS"); // Текст уведомления;
                break;*/
            // <!-- 27.07.2016  20:55
            //TODO_d+50 case MesC.GPS_TURN_DISABLE
            //TODO_d+50 open_Map_Intent
            case MessageConstant.TURN_GPS_DISABLE:
                builder.setSmallIcon(R.mipmap.sad_i)
                        .setTicker( "Spy is not working.")
                        .setContentText("disabled GPS. Spy is not working."); // Текст уведомления
                break;
            //TODO_d+50 case MessageConstant.GPS_ENABLE
            //TODO_d+50 open_Map_Intent
            case MessageConstant.TURN_GPS_ENABLE:

                builder.setSmallIcon(R.mipmap.smille_i)
                        .setTicker( "Spy is working")
                        .setContentText("toggle GPS on. Spy is working ");
                break;
        }

        builder.setContentIntent(contentIntent);
        builder.setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.care_u))
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle("Care4U "+emailFrom)
                .setSound(soundUri)
                .build();
        Notification notification = builder.build();
        notification.sound = soundUri;
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFY_ID, notification);
    }



    private boolean isReturnFromShowNotification() {
        switch (status4Control) {
            case MessageConstant.GPS_DISABLE:
                return true;
            case MessageConstant.SPY_DID_NOT_START:
               return true;
        }
        return false;
    }
    public long getMyId() {
          long myIdInBase = 0;
        SharedPreferences shared = context.getSharedPreferences("info", context.MODE_PRIVATE);
        //Using getXXX- with XX is type date you wrote to file "name_file"
        string_temp = shared.getString("manager id","");
        if ( string_temp =="") {
            // _1 Toast.makeText(context, "You have not manager acc!", Toast.LENGTH_LONG).show();
        }else{
            myIdInBase = Long.parseLong(string_temp);}
        return  myIdInBase;
    }
}



