package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.gps;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.R;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.AsynTaskForEmployee;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.EmployeeAsynTasks;

public class TurnGPSFromPromptActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    GoogleApiClient googleApiClient;
    LocationSettingsRequest.Builder builder;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    private long myIdInBase;
    private Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turn_gpsfrom_prompt);


        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarTurnGPS);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);



        // Display icon in the toolbar

        // getSupportActionBar().setLogo(R.drawable.babycare);
        // Display icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.care_i);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        getSupportActionBar().setTitle("Care4U");


        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(TurnGPSFromPromptActivity.this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(TurnGPSFromPromptActivity.this)
                    .addOnConnectionFailedListener(TurnGPSFromPromptActivity.this).build();
            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            //**************************
            builder.setAlwaysShow(true); //this is the key ingredient
            //***
            }





    }



        //endTODO-->



    @Override
    public void onConnected(Bundle bundle) {


        changeLocSettFromPrompt ();

    }



    @Override
    public void onConnectionSuspended(int i) {

    }

    protected void changeLocSettFromPrompt (){


        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(TurnGPSFromPromptActivity.this, REQUEST_CHECK_SETTINGS);

                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });




 }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }



    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }



    @Override
    public void onStop() {
        super.onStop();
        if(googleApiClient != null && googleApiClient.isConnected() ) {
            googleApiClient.disconnect();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if( googleApiClient != null && googleApiClient.isConnected() ) {
            googleApiClient.disconnect();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
// Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                       // startLocationUpdates();
                      //  Toast.makeText(this, "RESULT_OK", Toast.LENGTH_LONG).show();

                       // sendMyLocationToM ();
                        disconnectGoogleApi();
                        finish();

                        break;
                    case Activity.RESULT_CANCELED:
                        //settingsrequest();//keep asking if imp or do whatever
                       //  Toast.makeText(this, "RESULT_CANCELED", Toast.LENGTH_LONG).show();
                        disconnectGoogleApi();
                        finish();

                        break;
                }
                break;
        }
}



    private void sendMyLocationToM (){

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (mLastLocation != null) {
            double [] locArray  =  new  double[]{mLastLocation.getLatitude(), mLastLocation.getLongitude()};
            //  Toast.makeText(this, String.valueOf(mLastLocation.getLatitude())+"long:" +String.valueOf(mLastLocation.getLongitude()), Toast.LENGTH_LONG).show();
                if (getMyId()) {
                    //Make request to server for task sending message to manager client with employee location
                    new EmployeeAsynTasks(myIdInBase, locArray, AsynTaskForEmployee.TAKE_LOCATION).execute();
                }



    }

}


    private void disconnectGoogleApi() {
        if (googleApiClient  != null && googleApiClient .isConnected()) {
            googleApiClient .disconnect();
        }
    }


    private boolean getMyId (){

        //Get id inf (id in base on server) from Sh. Prf.
        // Idea is this data of personal unique id in base of server let you to make request to server.
        // This id info had saved during registration, or when you signed in to app with google+.
        //get SharedPreferences from getSharedPreferences("name_file", MODE_PRIVATE)
        SharedPreferences shared = getSharedPreferences("info",MODE_PRIVATE);
        //Using getXXX- with XX is type date you wrote to file "name_file"
         String string_temp = shared.getString("employee id","");
        if ( string_temp ==""){
            // Toast.makeText(this, "You have not employee acc!", Toast.LENGTH_LONG).show();
            // You can not to use buttons if you have not id info in Sh. Pref.
                    return false;
        }else {

                return true;
        }
    }





}






