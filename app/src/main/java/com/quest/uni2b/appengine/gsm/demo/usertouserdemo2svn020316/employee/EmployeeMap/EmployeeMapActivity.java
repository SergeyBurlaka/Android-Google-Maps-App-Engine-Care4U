package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.EmployeeMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.EmployeeCabinetActivity;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.Constants4Emploee.EmplConst4ShPrfOrIntent;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.GCMConstants.MessageConstant;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.R;

//import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.CircleLabel;

public class EmployeeMapActivity extends ActionBarActivity implements /*View.OnClickListener,*/ OnMapReadyCallback,/* com.google.android.gms.location.LocationListener,*/ GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private Marker marker, markerMyLoc = null;
    private Circle circle;
    private EditText radius;
    private final int earthRadius = 6371; //NOTE: this is in kilometers
    private GoogleApiClient mGoogleApiClient = null;
    private LocationRequest mLocationRequest;
   // CircleLabel circleLabel;
   // Bundle user;
    public TextView text;
    private CountDownTimer countDownTimer;
    private final long startTime = 30 * 1000;

    private final long interval = 1 * 1000;
    private static final int NOTIFY_ID = 102;
    private boolean flagOutside = true ;
    public static final String NOTIFICATION_ID = "NOTIFICATION_ID";
    Location tempLocation;
    private Location mCurrentLocation;


    //for getting data location of circle
    private  CircleLabel circleLabel;

    private  MarkerOptions myLocation;

    private Bundle bundleForMap;


    private int openFromStatus;

    private SharedPreferences.Editor editorForCircle;

    private SharedPreferences shared;

    private SharedPreferences spForActiveActivityDetect;

   // private static final String PF_TAG = "ShowFullQuoteFragment";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        spForActiveActivityDetect = getSharedPreferences(EmplConst4ShPrfOrIntent.EMPLOYEE_INFO, MODE_PRIVATE);

        // fix screen orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        /*
        Log.v("H77", "kkkindex=");
        //to close notification after open activity
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(getIntent().getIntExtra(NOTIFICATION_ID, -1));
        //notificationManager.cancel( getIntent().getIntExtra("NOTIFICATION_ID", -1));

            */
        setContentView(R.layout.activity_employee_map);
        // Button listeners
       // findViewById(R.id.sosButtonFromMap).setOnClickListener(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
       // text = (TextView) this.findViewById(R.id.timer);

        //  When employee is out of circle, countdown begin. When time is over,
        // manager get notification "employee is out circle"
        //countDownTimer = new MyCountDownTimer(startTime, interval);
        // String quote = (String)getActivity().getIntent().getStringExtra(PollingService.INTENT_KEY);
        //getIntent().getIntExtra
        //user.getDouble("latitude");
        // user.getDouble("longitude");
        //user.getDouble("radius");
        //-->
       // notificationManager.cancel(NOTIFY_ID);
      //  radius = (EditText) findViewById(R.id.radius); //radius for circle

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        // Display icon in the toolbar

        // getSupportActionBar().setLogo(R.drawable.babycare);
        // Display icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.care_i);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        getSupportActionBar().setTitle("Care4U");


        MapFragment mapFragment = (MapFragment) getFragmentManager()    //get map fragment
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

       /*
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)    //set properties for location request
                .setFastestInterval(1000*10)
                .setInterval(5000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
                */

        mGoogleApiClient = new GoogleApiClient.Builder(this) //set properties for google api client
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
       // mGoogleApiClient.connect(); //connect to google api

        //TODO_done+22june16: set marker with you current location
        myLocation =  new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromBitmap(writeTextOnDrawable(R.mipmap.human_i, "" )));

        //get some data from Intent
        bundleForMap = getIntent().getExtras();

        openFromStatus =  bundleForMap.getInt(EmplConst4ShPrfOrIntent.OPEN_MAP_FROM);

        if (openFromStatus == EmplConst4ShPrfOrIntent.FROM_NOTIFICATION) {
            getNewCircleLabelFromNotification ();
        }

    }


    @Override
    public void onBackPressed()
    {

        if(spForActiveActivityDetect.getBoolean( "active", false) == false) {
            Intent cabinetManagerAct = new Intent(this, EmployeeCabinetActivity.class );
            finish();
            startActivity(cabinetManagerAct );

        }else {

            //only finish
            finish();
        }

    }


    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }


    @Override
    public void onStop() {
        super.onStop();
        if( mGoogleApiClient != null && mGoogleApiClient.isConnected() ) {
            mGoogleApiClient.disconnect();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if( mGoogleApiClient != null && mGoogleApiClient.isConnected() ) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap map) {

        mMap = map;
        //get our location
        //mMap = map;
        map.setMyLocationEnabled(true);



       //TODO_d+from2h 1>>
        //TODO_d+ check if map opened from notification

        if (openFromStatus == EmplConst4ShPrfOrIntent.FROM_NOTIFICATION)
        {
            addCircle(map);
        }

        else {

            if ( getSpyStatus() == EmplConst4ShPrfOrIntent.SPY) {
                getNewCircleLabelFromShPrfnce();
                addCircle(map);
            }
        }

    }



    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_employee_map, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){

            case android.R.id.home:


                if(spForActiveActivityDetect.getBoolean( "active", false) == false) {
                    Intent cabinetManagerAct = new Intent(this, EmployeeCabinetActivity.class );
                    finish();
                    startActivity(cabinetManagerAct );

                }else {

                    //only finish
                    finish();
                }

                return true;
            case R.id.refreshEmployeeMap:

                reloadActivity();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void reloadActivity (){

       // ManagerCabinetListActivity.flagNewRequest = true;
        Intent intent = getIntent();
        finish();
        startActivity(intent);


    }



    private int getSpyStatus (){
        shared = getSharedPreferences(EmplConst4ShPrfOrIntent.STATUS_SPY, MODE_PRIVATE );
        int statusSpy = shared.getInt(EmplConst4ShPrfOrIntent.STATUS_SPY, EmplConst4ShPrfOrIntent.NO_SPY);
        return statusSpy;
    }

    private void getNewCircleLabelFromNotification (){

        circleLabel = new CircleLabel();


        //<!---21june16
        //TODO_d+: 1
        //TODO_done+: get data from intent

        Intent myIntent = getIntent();
        circleLabel.setLatitude( myIntent.getDoubleExtra(MessageConstant.CIRCLE_LATITUDE, 0));
        circleLabel.setLongitude( myIntent.getDoubleExtra(MessageConstant.CIRCLE_LONGITUDE, 0));
        circleLabel.setRadius( myIntent.getDoubleExtra(MessageConstant.CIRCLE_RADIUS, 0));


    }


    private void getNewCircleLabelFromShPrfnce (){

        circleLabel = new CircleLabel();
        //TODO_d+from2h 3>>
        // TODO_d+ check map opened from cabinet
        // TODO_d+  if map opened from cabinet {...

        //<!--- 22june16 14:09
        //TODO_d+from2h_total 4>>
        //TODO_d+ check status if employee SPY - set circle

       // SharedPreferences shared = getSharedPreferences("info",MODE_PRIVATE);
        //Using getXXX- with XX is type date you wrote to file "name_file"
        // String string_temp = shared.getString("employee id","");

        shared = getSharedPreferences(EmplConst4ShPrfOrIntent.CIRCLE_LOCATION,MODE_PRIVATE);
        //Using getXXX- with XX is type date you wrote to file "name_file"
        circleLabel.setLatitude(Double.longBitsToDouble(shared.getLong(EmplConst4ShPrfOrIntent.CIRCLE_LATITUDE, 0))) ;
        circleLabel.setLongitude(Double.longBitsToDouble(shared.getLong(EmplConst4ShPrfOrIntent.CIRCLE_LONGITUDE, 0)));
        circleLabel.setRadius(Double.longBitsToDouble(shared.getLong(EmplConst4ShPrfOrIntent.CIRCLE_RADIUS, 0))); ;
    }


    private void addCircle(GoogleMap map){


        //Set marker in the center of circle. Employeee must be in circle.
        // set marker (will be a center to circle)
        marker = map.addMarker(new MarkerOptions()
                .position(new LatLng(circleLabel.getLatitude(), circleLabel.getLongitude()))

        );
        // draw circle on map
        //

        circle = map.addCircle(new CircleOptions()
                .center(new LatLng(circleLabel.getLatitude(), circleLabel.getLongitude()))
                .radius(circleLabel.getRadius())
                .strokeColor(Color.BLACK)

                // Fill color of the circle
                // 0x represents, this is an hexadecimal code
                // 55 represents percentage of transparency. For 100% transparency, specify 00.
                // For 0% transparency ( ie, opaque ) , specify ff
                // The remaining 6 characters(00ff00) specify the fill color
                .fillColor(0x5500ff00)

                // Border width of the circle
                .strokeWidth(2)

        );



        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target( marker.getPosition())
                //.zoom(map.getCameraPosition().zoom)
                .zoom(17)
                //.bearing(targetBearing)
                //.tilt(20)
                .build();

        // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mMarker.getPosition(), 14));

        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        map.animateCamera(cameraUpdate);



    }




    public void onConnectionFailed(ConnectionResult arg0){

    }


    public void onConnected(Bundle bundle){

       // Toast.makeText(getApplicationContext(),"connected", Toast.LENGTH_SHORT).show();

       /*
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);*/


        mCurrentLocation = LocationServices
                .FusedLocationApi
                .getLastLocation( mGoogleApiClient );


        if(markerMyLoc!=null)markerMyLoc.remove();

      markerMyLoc = mMap.addMarker(myLocation.position(new LatLng(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude())).title("I'm"));
        // here is marker Adding code


        //-->

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(markerMyLoc.getPosition())
                //.zoom(mMap.getCameraPosition().zoom)
                .zoom(17)
                //.bearing(targetBearing)
                //.tilt(20)
                .build();

        // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mMarker.getPosition(), 14));

        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.animateCamera(cameraUpdate);


    }


    /*
    *    for creating icon - marker in map
    *
     */
    private Bitmap writeTextOnDrawable(int drawableId, String text) {

        Bitmap bm = BitmapFactory.decodeResource(getResources(), drawableId)
                .copy(Bitmap.Config.ARGB_8888, true);

        Typeface tf = Typeface.create("Helvetica", Typeface.BOLD);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        paint.setTypeface(tf);
        paint.setFakeBoldText(true);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(convertToPixels(this, 11));

        Rect textRect = new Rect();
        //paint.getTextBounds(text, 0, text.length(), textRect);

        Canvas canvas = new Canvas(bm);
        // canvas.drawText(text, 0, paint.getTextBounds(text, 0, text.length(), textRect), paint);

        //If the text is bigger than the canvas , reduce the font size
        if(textRect.width() >= (canvas.getWidth() - 4))     //the padding on either sides is considered as 4, so as to appropriately fit in the text
            paint.setTextSize(convertToPixels(this, 7));        //Scaling needs to be used for different dpi's

        //Calculate the positions
        int xPos = (canvas.getWidth() / 2) - 2;     //-2 is for regulating the x position offset

        //"- ((paint.descent() + paint.ascent()) / 2)" is the distance from the baseline to the center.
        int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2)) ;

        canvas.drawText(text, xPos, yPos, paint);

        return  bm;
    }


    public static int convertToPixels(Context context, int nDP)
    {
        final float conversionScale = context.getResources().getDisplayMetrics().density;

        return (int) ((nDP * conversionScale) + 0.5f) ;

    }


    public void onConnectionSuspended(int arg0){

    }




}


