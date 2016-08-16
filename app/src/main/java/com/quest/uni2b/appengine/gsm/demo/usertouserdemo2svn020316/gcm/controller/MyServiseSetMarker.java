package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.controller;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Operator on 14.04.2016.
 */
public class MyServiseSetMarker extends Service {


        public static final String BROADCAST_ACTION = "com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.Manager";
        private Intent intent;
       // private final Handler handler = new Handler();

        @Override public void onCreate() {

        intent = new Intent(BROADCAST_ACTION);
    }


    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("FFFFF", "onStartCommand");
        //someTask();
        //intent.putExtra("time", new Date().toLocaleString());
        //intent.putExtra("counter", String.valueOf(counter));
        sendBroadcast(intent);
        stopService(intent);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {


        return null;
    }




}

