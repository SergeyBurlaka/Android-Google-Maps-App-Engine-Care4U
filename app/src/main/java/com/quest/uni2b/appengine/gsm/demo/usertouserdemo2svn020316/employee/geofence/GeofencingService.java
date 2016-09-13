package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.geofence;

/**
 * Created by Operator on 13.06.2016.
 */
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GeofencingService extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static final String EXTRA_REQUEST_IDS = "requestId";
    public static final String EXTRA_GEOFENCE = "geofence";
    public static final String EXTRA_ACTION = "action";

    private List<Geofence> mGeofenceListsToAdd = new ArrayList<>();
    private GoogleApiClient mGoogleApiClient;
    private Action mAction;

   // private int transitionType;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("GEO", "Location service started");

        mAction = (Action) intent.getSerializableExtra(EXTRA_ACTION);

        if (mAction == Action.ADD) {

            //get object MyGeofence witha longitude, latitude & radius fom intent
            MyGeofence newGeofence = (MyGeofence) intent.getSerializableExtra(EXTRA_GEOFENCE);

            //type of indicate type ENTER or EXIT of circle
          //  transitionType = newGeofence.getTransitionType();


          // builder Geofence (Geofence.Builder()...build())
            //MyGeofence -->build()--> Geofence -->addTo(Geofence)List()-->mGeofenceListsToAdd
            mGeofenceListsToAdd.add(newGeofence.toGeofence());
        }


       //initialize Google client and wait for connection with google services
        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API)
                .addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
        mGoogleApiClient.connect();

        return super.onStartCommand(intent, flags, startId);
    }




    //После того как LocationClient подключится,
    // сработает onConnected колбэк интерфейса GooglePlayServicesClient.ConnectionCallbacks.
    // В нем мы выполняем добавление либо удаление в зависимости от текущего типа действия:
    @Override
    public void onConnected(Bundle bundle) {
        Log.d("GEO", "Location client connected");

        //add geofence circle
        if (mAction == Action.ADD) {

           //
            //
            // *first
            //build our  GeofencingRequest
            //В onStartCommand мы получаем тип операции (ADD или REMOVE)
            // и вытягиваем необходимые для выполнения этого действия данные.
            // После этого инициализируем и запускаем LocationClient:

            //<--14june16
            //TODO_done+: make ENTER || EXIT  - in one time

            GeofencingRequest.Builder builder = new GeofencingRequest.Builder();


           // Toast.makeText(this, "Location client adds geofence", Toast.LENGTH_SHORT).show();
            Log.d("GEO", "Location client adds geofence");

           /* builder.setInitialTrigger(
                    transitionType == Geofence.GEOFENCE_TRANSITION_ENTER ? GeofencingRequest
                            .INITIAL_TRIGGER_ENTER : GeofencingRequest.INITIAL_TRIGGER_EXIT);*/

            builder.setInitialTrigger(
                            GeofencingRequest.INITIAL_TRIGGER_EXIT |GeofencingRequest.INITIAL_TRIGGER_ENTER);

           // add list of geofence
            builder.addGeofences(mGeofenceListsToAdd);

            //add to build our (GeofenceReq)builder
            GeofencingRequest build = builder.build();


            //*second
            //add (GeofenceReq)build, mGoogleApiClient and  PendingIntent to geofence Api
            //Как мы видим, addGeofences одним из параметров требует PendingIntent,
            // который сработает при перемещении.
            // В нашем случае PendingIntent будет запускать IntentService:
            LocationServices.GeofencingApi.addGeofences(mGoogleApiClient, build,
                    getPendingIntent())
                    .setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            if (status.isSuccess()) {

                                String msg = "Geofences added: " + status.getStatusMessage();
                                Log.e("GEO", msg);
                              //  Toast.makeText(GeofencingService.this, msg, Toast.LENGTH_SHORT)
                               //         .show();
                            }
                            GeofencingService.this.onResult(status);
                        }
                    });

        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e("GEO", "onConnectionSuspended i = " + i);
    }


        // В нашем случае PendingIntent будет запускать IntentService:
    private PendingIntent getPendingIntent() {
        Intent transitionService = new Intent(this, ReceiveTransitionsIntentService.class);
        return PendingIntent
                .getService(this, 0, transitionService, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("GEO", "Location client connection failed: " + connectionResult.getErrorCode());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d("GEO", "Location service destroyed");
        super.onDestroy();
    }



    //у нас срабатывают onResult
    // в которых мы отключаемся от LocationClient и останавливаем сервис:
    //
    public void onResult(@NonNull Status status) {

      //  Toast.makeText(this, "Geofences onResult", Toast.LENGTH_SHORT).show();
        Log.d("GEO", "Geofences onResult" + status.toString());
        if (status.isSuccess()) {
            mGoogleApiClient.disconnect();
            stopSelf();
        } else {
            String text = "Error while geofence: " + status.getStatusMessage();
            Log.e("GEO", text);
          //  Toast.makeText(GeofencingService.this, text, Toast.LENGTH_SHORT).show();
        }

    }

    public enum Action implements Serializable {ADD, REMOVE}

}