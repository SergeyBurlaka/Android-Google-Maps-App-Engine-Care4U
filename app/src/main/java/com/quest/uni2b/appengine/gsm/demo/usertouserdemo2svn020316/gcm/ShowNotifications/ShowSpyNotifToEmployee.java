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

import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.Constants4Emploee.EmplConst4ShPrfOrIntent;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.EmployeeMap.CircleLabel;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.EmployeeMap.EmployeeMapActivity;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.GCMConstants.MessageConstant;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.R;

import java.util.Calendar;

/**
 * Created by Operator on 21.06.2016.
 */
public class ShowSpyNotifToEmployee {

    private Context context;




    // Идентификатор уведомления
  //  private static final int NOTIFY_ID = 101;



    CircleLabel circleLabel;




    public ShowSpyNotifToEmployee(Context context,CircleLabel circleLabel) {
        this.context = context;
        this.circleLabel= circleLabel;

    }

    public void showNotification (){
        // context = getApplicationContext();
       // Log.v(TAG, "kkkindex=" );

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //Intent notificationIntent = new Intent();
        Intent employeeMap = new Intent(context, EmployeeMapActivity.class);

        //<!---21june16
        //TODO_d+: 1
        //TODO_d+: putExtra latitude longitude & other
        employeeMap.putExtra(MessageConstant.CIRCLE_LONGITUDE,circleLabel.getLongitude() );
        employeeMap.putExtra(MessageConstant.CIRCLE_LATITUDE, circleLabel.getLatitude());
        employeeMap.putExtra(MessageConstant.CIRCLE_RADIUS, circleLabel.getRadius());


        //<!--- 14:34 21june 16
        //TODOd+from2h_total 1>>>
        //TODO_d+from1:50 set to EXTRA open map from notification

        employeeMap.putExtra(EmplConst4ShPrfOrIntent.OPEN_MAP_FROM , EmplConst4ShPrfOrIntent.FROM_NOTIFICATION);

        //-->

        employeeMap.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                (int) Calendar.getInstance().getTimeInMillis(),  employeeMap,
                0);

        //Assign inbox style notification
        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("    Hi, i am your boss. Click this message. You will see a marker with the circular area in the map. " +"\n"+"\n"+
                "        Note the rule:");
        bigText.setBigContentTitle("Care4U");
        bigText.setSummaryText("U can't go throw away from the circular area.");


        //IconButton iconButtonMessages = (IconButton) badgeLayout.findViewById(R.id.bad ge_icon_button)

        Resources res = context.getResources();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);


        // View target = context.findViewById(R.id.target_view);
        // BadgeView badge = new BadgeView(context);
        //  badge.setText("1");
        //  badge.show();
        //   badge.buildDrawingCache();
        //  int num = Integer.valueOf(badge.getText().toString());

        //int a = 8;

        // BitmapFactory.

        // ImageView image = (ImageView) context.findViewById(R.id.image_view);


        // View rootView = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);


      /*
       View view = View.inflate(context, R.layout.contact_item, null);
      ImageView myImage = (ImageView) view.findViewById(R.id.actionLogo);

       view.setDrawingCacheEnabled(true);

       view.buildDrawingCache();
       ByteArrayOutputStream stream = new ByteArrayOutputStream();
       Bitmap bm = view.getDrawingCache();

       bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
       byte[] byteArray = stream.toByteArray();

       */


        builder.setContentIntent(contentIntent)
                //.setSmallIcon(num)

                // .setSmallIcon(android.R.drawable.ic_dialog_email)
                .setSmallIcon(R.mipmap.letter)
                // большая картинка
                // .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.map_marker))
                .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.care_u))
                //.setTicker(res.getString(R.string.warning)) // текст в строке состояния

                .setTicker("Became monitor your GPS Location. Care4U")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                //.setContentTitle(res.getString(R.string.notifytitle)) // Заголовок уведомления
                .setContentTitle("Care4U")
                //.setContentText(res.getString(R.string.notifytext))
                .setContentText(
                        "Became monitor your GPS Location.") // Текст уведомления
                .setStyle(bigText)
                .setSound(soundUri)
                //.addAction(R.drawable.map_marker, "show map", contentIntent).setAutoCancel(true)
                .build();

        // убираем уведомление, когда его выбрали
        //builder.flag|= Notification.FLAG_AUTO_CANCEL;


        // Notification notification = builder.getNotification(); // до API 16
        Notification notification = builder.build();
        //  notification.number = 3;


        notification.flags = notification.flags | Notification.FLAG_INSISTENT;
        //notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.sound = soundUri;



        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(EmplConst4ShPrfOrIntent.NOTIFY_SPY_FOR_EMPLOYEE_ID, notification);

    }






}
