package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.geofence;

/**
 * Created by Operator on 13.06.2016.
 */

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.Main2Activity;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.R;

import java.util.List;

//import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.MainActivity;

public class ReceiveTransitionsIntentService extends IntentService {

    public static final String TRANSITION_INTENT_SERVICE = "TransitionsService";

    public ReceiveTransitionsIntentService() {
        super(TRANSITION_INTENT_SERVICE);
    }


   //Последняя часть приложения – это IntentService,
    // который запускается при пересечении границы геозоны пользователем устройства.
    // Все действия выполняются в onHandleIntent:

    @Override
    protected void onHandleIntent(Intent intent) {


        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            Log.e(TRANSITION_INTENT_SERVICE, "Location Services error: " + geofencingEvent.getErrorCode());
          //  Toast.makeText(this,"Location Services error: ", Toast.LENGTH_SHORT).show();
            return;
        }

       // Затем получаем тип перемещения и список сработавших геозон

        //int transitionType --> Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT
        int transitionType = geofencingEvent.getGeofenceTransition();

        //список сработавших геозон

        List<Geofence> triggeredGeofences = geofencingEvent.getTriggeringGeofences();

        for (Geofence geofence : triggeredGeofences) {
            Log.d("GEO", "onHandle:" + geofence.getRequestId());
           // Toast.makeText(this,"onHandle:" + geofence.getRequestId(), Toast.LENGTH_SHORT).show();
            processGeofence(geofence, transitionType);

            //TODO_accepted send notifications to manager : employe enter or exit of circle ( transitionType)



        }
    }

    private void processGeofence(Geofence geofence, int transitionType) {

        //<!--- 14:30 23june16
        //TODO_aborted 1>>>
        //TODO_aborted: SEND OUT message to manager just like from gps service sending


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext());

        PendingIntent openActivityIntetnt = PendingIntent.getActivity(this, 0, new Intent(this, Main2Activity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        int id = Integer.parseInt(geofence.getRequestId());


       //Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT
        String transitionTypeString = getTransitionTypeString(transitionType);

        notificationBuilder
                .setSmallIcon(R.mipmap.siren_2)
                .setContentTitle("Geofence id: " + id)
                .setContentText("Transition type: " + transitionTypeString)
                .setVibrate(new long[]{500, 500})
                .setContentIntent(openActivityIntetnt)
                .setAutoCancel(true);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(transitionType * 100 + id, notificationBuilder.build());

        Log.d("GEO", String.format("notification built:%d %s", id, transitionTypeString));
       // Toast.makeText(this, String.format("notification built:%d %s", id, transitionTypeString), Toast.LENGTH_SHORT).show();

    }

    private String getTransitionTypeString(int transitionType) {
        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                return "enter";
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                return "exit";
            case Geofence.GEOFENCE_TRANSITION_DWELL:
                return "dwell";
            default:
                return "unknown";
        }
    }


}