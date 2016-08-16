package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.ShowNotifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;

import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.EmployeeMap.TurnGPSFromPromptActivity;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.R;

import java.util.Calendar;

/**
 * Created by Operator on 12.07.2016.
 */
public class TurnGPSNotification {
    //TODO_done+02/07/16 show notification with message string as parameter
    //without new intent
    private static final int NOTIFY_ID = 105;
    private static String string_temp;
    private static long myIdInBase;

    private Context context;




    public TurnGPSNotification(Context context){

        this.context = context;

    }


    public  void ShowNotification (){

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //Intent notificationIntent = new Intent();
        Intent mapIntent = new Intent(context, TurnGPSFromPromptActivity.class);

        mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                (int) Calendar.getInstance().getTimeInMillis(),  mapIntent,
                0);


        Resources res = context.getResources();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        builder.setContentIntent(contentIntent)
                //.setSmallIcon(android.R.drawable.ic_dialog_email)
                .setSmallIcon(R.mipmap.letter)
                // большая картинка
                .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.care_u))
                //.setTicker(res.getString(R.string.warning)) // текст в строке состояния
                .setTicker("Please, enable GPS. Care4U.")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                //.setContentTitle(res.getString(R.string.notifytitle)) // Заголовок уведомления
                .setContentTitle("Care4U")
                //.setContentText(res.getString(R.string.notifytext))
                .setContentText(
                        "Please, enable GPS.") // Текст уведомления

                .setSound(soundUri)
                //.addAction(R.drawable.map_marker, "show map", contentIntent).setAutoCancel(true)
                .build();

        // убираем уведомление, когда его выбрали
        //builder.flag|= Notification.FLAG_AUTO_CANCEL;


        // Notification notification = builder.getNotification(); // до API 16
        Notification notification = builder.build();
        //notification.flags = notification.flags | Notification.FLAG_INSISTENT;
        //notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.sound = soundUri;

        // notification.number = amountReq;



        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFY_ID, notification);

    }


}
