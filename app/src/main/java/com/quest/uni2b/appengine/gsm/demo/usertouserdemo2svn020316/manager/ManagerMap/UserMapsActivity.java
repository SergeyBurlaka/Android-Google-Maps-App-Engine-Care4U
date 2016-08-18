package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.ManagerMap;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.amlcurran.showcaseview.OnShowcaseEventListener;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
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
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.R;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.EmployeeMap.CircleLabel;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.GCMConstants.MessageConstant;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.controller.MessagesService.CreateSpyMessage;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.controller.MessagesService.MessageAgent;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.EmployeeDataWork.EmployeeLocation;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.IfOnlineEControl.AlarmServiceReceiver;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.ManagerCabinetListActivity;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.ManagerRequests.AsynTaskForManager;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.ManagerRequests.AsynTaskForManagerEnum;

import java.util.ArrayList;

import static com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.ManagerMap.State4Commit.ON_DISABLE;
import static com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.ManagerMap.State4Commit.ON_STOP;

//import android.widget.Toast;

// import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.CircleLabel;

public class UserMapsActivity extends ActionBarActivity implements GoogleMap.OnMapClickListener, OnMapReadyCallback, com.google.android.gms.location.LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private   CreateSpyMessage createSpyMessage;
    EmployeeLocation employeeLocation ;
    private GoogleApiClient mGoogleApiClient = null;
    private LocationRequest mLocationRequest;
    private CircleLabel circleLabel;
    private Bundle user;
    private  double latitude;
    private  double longitude;
    Location mCurrentLocation;
    Marker mMarker = null;
    private  MarkerOptions markerEmployeeLoc;
    private int openMapFrom;
    String employeeSelected;
    /*
     SharedPreferences fields
   */
    private SharedPreferences sharedGetCircle, settings, sharedGetStatus;;
    private SharedPreferences.Editor editorStatusSpy,saveCircleLblShared;
    private SharedPreferences spIsActive;
    private boolean  tips = false;
    EditText getRadius ;
    private double getDoubleRadius;
    TextInputLayout inputLayoutName;
    private  final double SOME_HORIZONTAL_DISTANCE = 0.0007, SOME_VERTICAL_DISTANCE = 0.0002;
    MenuItem itemSpy, itemStop,/* itemRefresh,*/ itemHelp;
    TextView employeeNameText;
    //For detect if opened first time to show tips study
    //I create sharedGetCircle preference flag in settings
    final String PREFS_NAME = "MyPrefsFile";
    private boolean isTipStarted = false;
    //For get circle from memory
    CircleLabel circleLabel4E;
    ProgressBar progressBar;
    private Thread threadTryGetLocation;
    private ProgressDialog dialogStartSpyProgress;
   //Work around with fight  notification from employee when spy stop, or disable
    public  static volatile boolean noMore4Notification = false;
    // For Factory Method Pattern
    ToolbarFactoryMethod toolbarFactory;
    Toolbar4Map toolbarStartFace;
    Toolbar4Map toolbarOnShowTip;
    Toolbar4Map toolbarViewFailGetLoc;
    Toolbar4Map toolbarOnGetLocationWin;
    // For Strategy Pattern
    GetCircle4Map getCircle4Map;
    Circle circleAreaMarker, oldCircle;
    GoogleMap mMap;
    private Marker markerDraggable, markerEmployee;
    C4Draggable circle4Draggable;
    MenuItem itemAlertIndicator;

    private FloatingActionButton myFab;
    TextView textWithProgress;
    //when click refresh floating button
    //start new wait thread
    private Thread threadWait4Refresh;
    private LatLng currLocOnConnected;
    private AlarmServiceReceiver employeeControl;
    private SharedPreferences.Editor alarmShPEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        spIsActive = getSharedPreferences(MessageConstant.MANAGER_INFO, MODE_PRIVATE);
        saveCircleLblShared = getSharedPreferences(employeeSelected, MODE_PRIVATE).edit();
        sharedGetCircle = getSharedPreferences(employeeSelected,MODE_PRIVATE);
        sharedGetStatus = getSharedPreferences(MessageConstant.STATUS_SPY_VIEW,MODE_PRIVATE);
        editorStatusSpy = getSharedPreferences(MessageConstant.STATUS_SPY_VIEW , MODE_PRIVATE).edit();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_user_maps);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getExtrasToUser();

        progressBar = (ProgressBar) findViewById(R.id.pbHeaderProgress);
        textWithProgress = (TextView) findViewById(R.id.loadText);

        employeeNameText= (TextView) findViewById(android.R.id.text1);
        employeeNameText.setText(employeeSelected);
        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name_for_radius);

        //get link to edit Text for get radius
        getRadius = (EditText) findViewById(R.id.inputRadius);
        getRadius.addTextChangedListener(new TextWatcher(){

            //Change dynamically circle
            public void afterTextChanged(Editable s) {
                String textRadius =  getRadius.getText().toString();
                if (textRadius.matches(""))return;
                // getDoubleRadius = Double.parseDouble(textRadius);
                getDoubleRadius = new Double(textRadius);
                if (circleAreaMarker != null) circleAreaMarker.setRadius(getDoubleRadius);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){

            }
        });

        myFab = (FloatingActionButton)  findViewById(R.id.myFloatRefresh4Map);
        myFab.setOnClickListener(new View.OnClickListener() {
            public Marker temp4elMarker;
            public void onClick(View v) {
                new AsynTaskForManager(UserMapsActivity.this, user.getLong("mamagerId"), employeeSelected ,AsynTaskForManagerEnum.GET_LOCATION).execute();
                //<!-- 28.07.2016  12:15
                //WaitRefreshThread -> wait for 3 sec
                //Set link to marker in new variableDelete
                //After start indicate thread, delete marker using variableDelete
                wait4DataRefreshT ();
            }

            private void wait4DataRefreshT() {
                temp4elMarker = mMarker;
                if ( threadWait4Refresh!=null &&  threadWait4Refresh.isAlive())return;
                threadWait4Refresh = new Thread() {


                    @Override
                    public void run() {
                        try {
                            while (true) {
                                sleep(3000);
                                if(Thread.currentThread().isInterrupted())return;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        createFacePalm();
                                    }});
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                threadWait4Refresh.start();
            }

            void createFacePalm() {
                if (toolbarOnGetLocationWin != null)
                {
                    //Create mix of different toolbar state
                    if(toolbarViewFailGetLoc == null) toolbarViewFailGetLoc = toolbarFactory.getToolbar(new ToolbarGetLocationFail(UserMapsActivity.this));
                    toolbarViewFailGetLoc.defineSpyingItem();
                    toolbarStartFace.startIndicateThread();
                }
                if (temp4elMarker !=null) temp4elMarker.remove();
            }
        });
        //if opened from notification
       if(openMapFrom == MessageConstant.FROM_NOTIFICATION){
           createEmployeeLocationObject ();
       }

        //create map view
        MapFragment mapFragment = (MapFragment) getFragmentManager()    //get map fragment
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

       //create connection to google map api
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)    //set properties for location request
                .setFastestInterval(1000)
                .setInterval(5000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        mGoogleApiClient = new GoogleApiClient.Builder(this) //set properties for google api client
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        //Init markerDraggable  for  employee
        markerEmployeeLoc =  new MarkerOptions()
               .icon(BitmapDescriptorFactory.fromBitmap(writeTextOnDrawable(R.mipmap.human_i, "" )));
        IntentFilter filterRefresh = new IntentFilter(MessageAgent.GET_E_LOCATION_ACTION);
        filterRefresh.addAction(MessageConstant.ACTION_START_SPY );

        // 26.07.2016
        //for dynamically getting data about employee location
        registerReceiver(broadcastReceiver, filterRefresh);
    }


    /*
    *    Extras employee data from Intent  on Create
    * */
    public void getExtrasToUser() {
        user = getIntent().getExtras();
        //get status  from Extras to detect if opened from notification
        openMapFrom = user.getInt(MessageConstant.OPEN_USER_MAP_FROM);
        //get employee selected email
        employeeSelected =  user.getString(MessageConstant.EMPLOYEE_LOC_EMAIL);
    }


    private void createEmployeeLocationObject (){
        employeeLocation = new EmployeeLocation();
        employeeLocation.setCircle1latitude(user.getDouble(MessageConstant.CIRCLE_LATITUDE ));
        employeeLocation.setCircleLongitude(user.getDouble(MessageConstant.CIRCLE_LONGITUDE));
        employeeLocation.setRadius(user.getDouble(MessageConstant.CIRCLE_RADIUS));
        employeeLocation.setEmployeeLatitude(user.getDouble(MessageConstant.EMPLOYEE_LOC_LATITUDE));
        employeeLocation.setEmployeeLongitude(user.getDouble(MessageConstant.EMPLOYEE_lOC_LONGITUDE));
    }


    /*
       Create marker - icon in map
       on Create
   */
    private Bitmap writeTextOnDrawable(int drawableId, String text) {

        Bitmap bm = BitmapFactory.decodeResource(getResources(), drawableId)
                .copy(Bitmap.Config.ARGB_4444, true);
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


    /*
    *    Activity methods
    * */
    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }


    @Override
    public void onBackPressed()
    {
        //Save in editorStatusSpy lat long & radius
        //Shut all threads because out of memory error
        shutAllThreads ();
        if ( MessageConstant.NO_SPY == getStatus ())  getCircle4Map.saveCircleLabel(circleAreaMarker);
        if(!spIsActive.getBoolean( "active", false))  {
            Intent cabinetManagerAct = new Intent(this, ManagerCabinetListActivity.class );
            cabinetManagerAct.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finish();
            startActivity(cabinetManagerAct );
        }else {
            finish();
        }
    }


    /**
     *   shut All Threads  on Back Pressed
     */
    private void shutAllThreads() {
        toolbarStartFace.setInterruptAnimateLoadText(true);
        threadTryGetLocation.interrupt();
        toolbarStartFace.getThread().interrupt();
        if (threadWait4Refresh!=null) threadWait4Refresh.interrupt();
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
        unregisterReceiver(broadcastReceiver);
        if( mGoogleApiClient != null && mGoogleApiClient.isConnected() ) {
            mGoogleApiClient.disconnect();
        }
    }


    /**
     *  Create Toolbar menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_map, menu);
        itemSpy = menu.findItem(R.id.spy_for_employee);
        itemStop = menu.findItem(R.id.stop_spy);
       // itemRefresh = menu.findItem(R.id.myRefreshMap);
        itemHelp = menu.findItem(R.id.study);
        itemAlertIndicator = menu.findItem(R.id.fail_loc_indicator);

        //<!--- 19.07.2016
        //Set all item false
       // Using Pattern Factory method for create different views of toolbar
        toolbarFactory = new ToolbarFactoryMethod(UserMapsActivity.this);
        //Now creating toolbar in starting position
        toolbarStartFace = toolbarFactory.getToolbar(new ToolbarStart(this));
        toolbarStartFace.createFace();
       // toolbarStartFace.setRadiusFAndNameFViews();
        //toolbarStartFace.startIndicateThread();
        return true;
    }


    /*
        Listener for item in toolbarFactory
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){

            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.stop_spy:
                someProcess(ON_STOP );
                refreshCircle();
                toolbarStartFace.toggleSpyItem();
                ManagerCabinetListActivity.flagNewRequest = true;
                new AsynTaskForManager(this, user.getLong("mamagerId"), employeeSelected ,AsynTaskForManagerEnum.STOP_SPY).execute();
                // your action goes here
                return true;

            case R.id.spy_for_employee:
                someProcess( State4Commit.ON_START);
                new AsynTaskForManager(this,user.getLong("mamagerId"),fillEmails(), createSpyMessage(), AsynTaskForManagerEnum.SPY).execute();
                setProgressOnItem();
                return true;

            /*case R.id.myRefreshMap:
                itemRefresh.setActionView(R.layout.actionbar_progress_for_refresg_item);
                new AsynTaskForManager(this, user.getLong("mamagerId"), employeeSelected ,AsynTaskForManagerEnum.GET_LOCATION).execute();
                toolbarStartFace.load4ItemRefresh();
                return true;*/

            case R.id.study:
                showTips();
                return true;

            //my little cheate
            case R.id.forDebug:
                settings = getSharedPreferences(PREFS_NAME, 0);
                settings.edit().putBoolean("my_first_time", true).commit();
                saveCircleLblShared.clear();
                saveCircleLblShared.commit();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
     *  Create Progress dialog (wait for start spy) on Options Item Selected
     *  on click R.id.spy_for_employee:
     */
    private void setProgressOnItem(){
        dialogStartSpyProgress = new ProgressDialog(UserMapsActivity.this);
        dialogStartSpyProgress.setMessage("Loading...");
        dialogStartSpyProgress.setCancelable(false);
        dialogStartSpyProgress.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                onDisableClick();
            }
        });

        dialogStartSpyProgress.setButton (DialogInterface.BUTTON_NEUTRAL, "Home", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // dialog.dismiss();
                onBackPressed();
            }
        });
        dialogStartSpyProgress.show();
    }


    private void onDisableClick() {
        someProcess(ON_DISABLE);
        ManagerCabinetListActivity.flagNewRequest = true;
        new AsynTaskForManager(this, user.getLong("mamagerId"), employeeSelected ,AsynTaskForManagerEnum.STOP_SPY).execute();
    }


     /*
    *   Help method for on Options Item Selected
    */
    private void someProcess(State4Commit state4Commit) {
        switch (state4Commit){

            case ON_STOP:
                //<!--- 29.07.2016
                //Delete from inListOfSpyEmployees in ShPr
                //StopEmployeeCService (this);
                stopEmployeeCService (this);

            case ON_DISABLE:
                // work around for no getting spare notifications
                noMore4Notification = true;
                saveCircleLblShared.putInt(MessageConstant.STOP_NOTIFICATIONS,  MessageConstant.ENABLE );
                saveCircleLblShared.putInt(MessageConstant.CLICKED_SPY,  MessageConstant.DISABLE );
                saveCircleLblShared.commit();
                editorStatusSpy.putInt(employeeSelected, MessageConstant.NO_SPY  );
                editorStatusSpy.commit();
                break;

            case ON_START:
                noMore4Notification = false;
                saveCircleLblShared.putInt(MessageConstant.STOP_NOTIFICATIONS,  MessageConstant.DISABLE );
                saveCircleLblShared.putInt(MessageConstant.CLICKED_SPY,  MessageConstant.CLICKED );
                saveCircleLblShared.commit();
                if (circleAreaMarker == null)break;
                circleLabel = new CircleLabel();
                circleLabel.setLatitude( circleAreaMarker.getCenter().latitude);
                circleLabel.setLongitude( circleAreaMarker.getCenter().longitude);
                circleLabel.setRadius( circleAreaMarker.getRadius());
                break;
        }
    }


    private void stopEmployeeCService(UserMapsActivity userMapsActivity) {
            employeeControl = new AlarmServiceReceiver();
            employeeControl.stopControl(this);
    }


    /*
   *    Set circle on stop spy item click on Options Item Selected
   *     on R.id.stop_spy:
   **/
    private void refreshCircle() {
        if (oldCircle!=null) {
            circleAreaMarker = oldCircle;
            markerDraggable = mMap.addMarker(new MarkerOptions()
                    .position(oldCircle.getCenter())
                    .draggable(true)
            );
            // getCircle4Map.executeSetCircle(new CInSomePosition(this, oldCircle.getCenter(),oldCircle.getRadius()));
            //setCircleMarkerInSomePosition (mMap, oldCircle.getCenter(),oldCircle.getRadius() );
            setRadiusInputField ();
        }
    }


    private void setRadiusInputField() {
        getRadius.setVisibility(View.VISIBLE);
        inputLayoutName.setVisibility(View.VISIBLE);
        getRadius.setText(String.valueOf((int) oldCircle.getRadius() ));
        getRadius.setSelection(getRadius.getText().length());
    }


    /*
  *   Help methods for make spy request from manager map activity on Options Item Selected
  *   on R.id.spy_for_employee:
  */
    private ArrayList<String> fillEmails() {
        ArrayList<String> listStrEmplEmail = new ArrayList<>();
        listStrEmplEmail.add(employeeSelected);// employeeEmailStr
        return listStrEmplEmail;
    }


    private String createSpyMessage (){
        createSpyMessage = new CreateSpyMessage(circleLabel,"@Manager");
        String SPYmessage = createSpyMessage.getSpyMessage();
        return SPYmessage;
    }


    /*
    *    Create Map
     */
    //TODO create onMapReady ()
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        //get our location
        map.setMyLocationEnabled(true);
        setCircleOnMapReady ();
        map.setOnMapClickListener(this);
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            public boolean onMarkerClick(Marker marker) {
                onMapClick(marker.getPosition());
                return true;
            }
        });
        tryGetLocationThreads ();
    }


    /*
    *    Try get location for employee (for 3 time in separate thread )
    *    on Map Ready
    */
    private void tryGetLocationThreads (){

        new AsynTaskForManager(this, user.getLong("mamagerId"),employeeSelected ,AsynTaskForManagerEnum.GET_LOCATION).execute();

        threadTryGetLocation = new Thread() {
            @Override
            public void run() {
                try {
                    int i =0;
                    while(i<2&&!Thread.currentThread().isInterrupted()) {
                        sleep(3000);
                        //TODO_ check flag here
                        if(Thread.currentThread().isInterrupted())return;
                        new AsynTaskForManager(UserMapsActivity.this, user.getLong("mamagerId"),employeeSelected ,AsynTaskForManagerEnum.GET_LOCATION).execute();
                        i++;
                    }
                    if(Thread.currentThread().isInterrupted())return;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //In case of fail to get employee location app set fail alert indicator in toolbar
                            toolbarViewFailGetLoc = toolbarFactory.getToolbar(new ToolbarGetLocationFail(UserMapsActivity.this));
                            toolbarViewFailGetLoc.createFace();
                            //toolbarStartFace.setRadiusFAndNameFViews();
                            if(getStatus() == MessageConstant.NO_SPY &&MessageConstant.CLICKED == sharedGetCircle.getInt (MessageConstant.CLICKED_SPY, MessageConstant.DISABLE ))setProgressOnItem ();
                            // TODO_: 21.07.2016 ->
                        }});

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        threadTryGetLocation.start();

    }


    /*
    *   Set  circle for employee on Map Ready
    */
    private void setCircleOnMapReady() {
        //*** Using strategy Pattern.
        // *** Create base system for use different algorithms to set circle on map
        getCircle4Map = new GetCircle4Map(this);
        //*** Our algorithm for setup draggable circle on map in begin
        circle4Draggable = new C4Draggable(this);

        if (getStatus() == MessageConstant.NO_SPY) {
            // * For executing strategy pattern we use "circle4Draggable" algorithm
            // to set new circle on map from shared preference
            circleAreaMarker = getCircle4Map.executeSetCircle(circle4Draggable);
            setOutputValueOfDraggableCircle();
        }//setDraggableMarkerAndCircle(map);
        else   oldCircle = getCircle4Map.executeSetCircle(new C4Spy(this)); //setSpyCircle(map);
    }


    /*
      *    Set draggable circle radius value, after set this circle on map
      */
    private void setOutputValueOfDraggableCircle() {
        markerDraggable = circle4Draggable.getMarker();
        circleLabel4E = circle4Draggable.getCircleLabel4E();
        getRadius.setText(String.valueOf((int)circleLabel4E.getRadius()));
        getRadius.setSelection(getRadius.getText().length());
    }


    @Override
    public void onMapClick(LatLng latLng) {
        if (markerDraggable == null)return;
        markerDraggable.setPosition(latLng);
        circleAreaMarker.setCenter(latLng);
        if (tips){
            findViewById(R.id.tipMapImageFinger).setVisibility(View.INVISIBLE);
            //  findViewById(R.id.tipsMapTextFinger).setVisibility(View.INVISIBLE);
            findViewById( R.id.tipArrowImageFinger).setVisibility(View.INVISIBLE);
            tips = false;
        }
    }


    @Override
    public void onLocationChanged(Location location) {
    }


    public void onConnectionFailed(ConnectionResult arg0){

    }


    public void onConnected(Bundle bundle){

        mCurrentLocation = LocationServices
                .FusedLocationApi
                .getLastLocation( mGoogleApiClient );

        if (!getCameraPosition()) return;
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(currLocOnConnected)
                //.zoom(map.getCameraPosition().zoom)
                .zoom(17)
                //.bearing(targetBearing)
                //.tilt(20)
                .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.animateCamera(cameraUpdate);
    }


      /*
    *   Help methods 4 moving camera  on  Connected
    **/
    public boolean getCameraPosition() {
        if (mCurrentLocation == null){
            if( isCircleAreaMarkerInZero())return false;//do not move camera
            currLocOnConnected = getCenter ();return true;// move camera to center of circle
        }else {
            currLocOnConnected = new LatLng(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude() );
            if(isCircleAreaMarkerInZero() ){circleAreaMarker.setCenter(currLocOnConnected);markerDraggable.setPosition(currLocOnConnected);} //set circle in center of my position
            return true;
            }
        }


    public LatLng getCenter() {
        if(circleAreaMarker!=null)  return  circleAreaMarker.getCenter();
        if (oldCircle!= null) return oldCircle.getCenter();
        return new LatLng (0,0);
    }


    public void onConnectionSuspended(int arg0){
    }


   /*
    *    BroadcastReceiver for interactive getting employee location
    *
    */
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
             public AlertDialog myDialog;

        @Override
        public void onReceive(Context context, Intent intent) {
            //updateDate(intent);
            String action = intent.getAction();
            switch (action){

                case MessageAgent.GET_E_LOCATION_ACTION:
                    if(!read4ControlGPS(intent)){
                        //todo_ stop thread
                       if (threadTryGetLocation.isAlive()) threadTryGetLocation.interrupt();
                        latitude = intent.getExtras().getDouble("latitude");
                        longitude = intent.getExtras().getDouble("longitude");
                        setUpMap();
                    }
                    break;

                case MessageConstant.ACTION_START_SPY:

                    if (noMore4Notification) return;

                    new AsynTaskForManager(UserMapsActivity.this, user.getLong("mamagerId"),employeeSelected ,AsynTaskForManagerEnum.GET_LOCATION).execute();

                    if (intent.getExtras().getBoolean("justRefresh", true)) return;

                    dialogStartSpyProgress.dismiss();
                    itemSpy.setVisible(false);
                    itemStop.setVisible(true);

                    markerDraggable.remove();

                        oldCircle = mMap.addCircle(new CircleOptions()
                                .center(circleAreaMarker.getCenter())
                                .radius(circleAreaMarker.getRadius())
                                .strokeColor(Color.BLACK)
                                .fillColor(ContextCompat.getColor(UserMapsActivity.this, R.color.outside_color_pink))
                                // Border width of the circle
                                .strokeWidth(2)
                        );

                    //Remove draggable circle because user
                    // set circle to right position.
                    // So when spy, circle must be fixed in one position (not draggable)
                    circleAreaMarker.remove();
                    getRadius.setVisibility(View.GONE);
                break;
            }
        }


        //get data location from brodcasr receiver
        private void setUpMap() {
            if (threadWait4Refresh!=null) threadWait4Refresh.interrupt();
           //Delete alert indicator in toolbar if toolbarViewFailGetLoc object not null
            toolbarStartFace.onTransformerToolbar();

            //Using Pattern Factory method for create different views of toolbars
            //Now creating toolbar when we get successfully employee location
            if( toolbarOnGetLocationWin == null) toolbarOnGetLocationWin = toolbarFactory.getToolbar( new ToolbarGetLocationWin(UserMapsActivity.this));//.createFace();
            toolbarOnGetLocationWin.createFace();
            //toolbarViewFailGetLoc.setRadiusFAndNameFViews();

            //for moving employee markerDraggable to new position
            if(mMarker!=null)mMarker.remove();
            mMarker = mMap.addMarker(markerEmployeeLoc.position(new LatLng(latitude, longitude)).title(employeeSelected));

            cameraMoveOnSetUpMAp();
            showTipsIf1Time ();
        }


        //Showing tips when user opened map first time
        private void showTipsIf1Time() {
            settings = getSharedPreferences(PREFS_NAME, 0);
            if (settings.getBoolean("my_first_time", true)&&!isTipStarted) {
                isTipStarted = true;
                setMarkerCircleInFirstTime();
               // firstTime = false;
                showTips();
            }
        }


        //Camera move to employee location
        private void cameraMoveOnSetUpMAp() {
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(latitude  , longitude))
                    //.zoom(mMap.getCameraPosition().zoom)
                    .zoom(17)
                    //.bearing(targetBearing)
                    //.tilt(20)
                    .build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
            mMap.animateCamera(cameraUpdate);
        }


        ///It works when employee GPS disable
        private boolean read4ControlGPS( Intent intent) {
            int status4Control;
            // Get status about kind for alert
            //Show alert
            status4Control = intent.getExtras().getInt("status4Alert",0);
            if (status4Control == 0) return false;
            createStateAlert(status4Control);
            return true;
        }


        private void setMarkerCircleInFirstTime() {
            if (circleAreaMarker == null) return;
            if (isSetMarkerCircleSecondary ()){
                markerDraggable.setPosition(new LatLng(latitude, longitude));
                circleAreaMarker.setCenter(new LatLng(latitude, longitude));
            }
        }


        private boolean isSetMarkerCircleSecondary() {
            //TODO_CircleSetdoneTotalFrom3h>> 4
            if (circleAreaMarker.getCenter().latitude != 0) return false;
            return true;
        }


        /*
        *   Show alert dialogs
        */
        private void createStateAlert(int status4Control) {
            StringBuilder sb = new StringBuilder();
            //<!--- 27.07.2016
            // Set alert indicator if toolbarOnWin != null
            //Delete employee
            createFacePalm ();
                switch (status4Control) {

                case MessageConstant.GPS_DISABLE:
                    // <!-- 28.07.2016 12 :20
                    //stopWaitRefreshThread
                    if (threadWait4Refresh!=null) threadWait4Refresh.interrupt();
                    sb.append("   Failed to get location."+"\n");
                    sb.append(employeeSelected);
                    sb.append(" has disabled GPS."+"\n"+ "   Don't worry. App had already asked him to toggle GPS on.");
                    buildDialog (sb.toString());
                    break;

                case MessageConstant.SPY_DID_NOT_START:
                    sb.append("  Sorry, spy didn't start. Failed to get location."+"\n");
                    sb.append(employeeSelected);
                    sb.append(" has disabled GP"+"\n"+ "   Don't worry. App had already asked him to toggle GPS on.");
                    buildDialog (sb.toString());
                    // <!-- 28.07.2016 12 :50
                    //Abort dismiss
                    if (dialogStartSpyProgress != null)
                    dialogStartSpyProgress.dismiss();
                    break;

                //<!--- 27.07.2016
                    case MessageConstant.TURN_GPS_DISABLE:
                        sb.append("   Spy is not working"+"\n");
                        sb.append(employeeSelected);
                        sb.append(" has disabled GPS."+"\n"+ "   Don't worry. App had already asked him to toggle GPS on.");
                        buildDialog (sb.toString());
                        break;

                    case MessageConstant.TURN_GPS_ENABLE:
                        sb.append("It's all ok. ");
                        sb.append(employeeSelected);
                        sb.append(" has just enabled GPS! Spy  continues working." );
                        buildDialog (sb.toString());
                        new AsynTaskForManager(UserMapsActivity.this, user.getLong("mamagerId"), employeeSelected ,AsynTaskForManagerEnum.GET_LOCATION).execute();
                        break;

                    case MessageConstant.WIN_GET_LOC:
                        sb.append("It's all ok. ");
                        sb.append(employeeSelected);
                        sb.append(" has just enabled GPS! " );
                        buildDialog (sb.toString());
                        new AsynTaskForManager(UserMapsActivity.this, user.getLong("mamagerId"), employeeSelected ,AsynTaskForManagerEnum.GET_LOCATION).execute();
                        break;
            }
        }


        void createFacePalm() {
            if (toolbarOnGetLocationWin != null)
            {
                //Create hot mix of different toolbar state
                if(toolbarViewFailGetLoc == null) toolbarViewFailGetLoc = toolbarFactory.getToolbar(new ToolbarGetLocationFail(UserMapsActivity.this));
                toolbarViewFailGetLoc.defineSpyingItem();
                toolbarStartFace.startIndicateThread();
            }
            if(mMarker!=null)mMarker.remove();
        }


        private void buildDialog(String message) {
            if( myDialog != null && myDialog.isShowing() ) return;
            AlertDialog.Builder builder = new AlertDialog.Builder(UserMapsActivity.this);
            builder.setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                        }
                    });
             myDialog = builder.create();
            myDialog.show();
        }
    };


       /*
       *    For showing tips
       */
    private void showTips (){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                /*
                  Before show tips make some preparation
                 */
                toolbarOnShowTip = toolbarFactory.getToolbar( new ToolbarShowTips(UserMapsActivity.this));
                toolbarOnShowTip.createFaces(ToolbarFacesState.ON_START_TIP);
                //toolbarOnShowTip.setRadiusFAndNameFViews(ToolbarFacesState.ON_START_TIP);
                tips = true;

                findViewById(R.id.inputRadius).setVisibility(View.INVISIBLE);
                findViewById(R.id.input_layout_name_for_radius).setVisibility(View.INVISIBLE);
                //showing tips image
                findViewById(R.id.tipMapImageFinger).setVisibility(View.VISIBLE);
                // TextView txt =(TextView) findViewById(R.id.tipsMapTextFinger);
                //  txt.setVisibility(View.VISIBLE);
                findViewById( R.id.tipArrowImageFinger).setVisibility(View.VISIBLE);
                //move camera for better sowing our tis
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(latitude + SOME_VERTICAL_DISTANCE , longitude + SOME_HORIZONTAL_DISTANCE))
                        //.zoom(mMap.getCameraPosition().zoom)
                        .zoom(17)
                        //.bearing(targetBearing)
                        //.tilt(20)
                        .build();
                CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                mMap.animateCamera(cameraUpdate);

              /*
                  Begin demonstrate our tips show
               */
                TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
                paint.setTextSize(getResources().getDimension(R.dimen.abc_text_size_large_material));
                TextPaint title = new TextPaint(Paint.ANTI_ALIAS_FLAG);
                title.setTextSize(getResources().getDimension(R.dimen.abc_text_size_display_1_material));
                title.setUnderlineText(true);

                //   MultiEventListener multiEventListener = new MultiEventListener(new LogToTextListener(eventLog), new ShakeButtonListener(customButton));
                ShowcaseView showcaseView = new ShowcaseView.Builder(UserMapsActivity.this)
                        .withNewStyleShowcase()
                        .setTarget(new ViewTarget(R.id.tipMapImageFinger, UserMapsActivity.this))
                        .setContentTextPaint(paint)
                        .setContentTitle(R.string.title_single_shot)
                        .setContentText(R.string.R_string_desc_single_shot)
                        .setContentTitlePaint(title)
                        .setStyle(R.style.CustomShowcaseTheme3)
                        // .singleShot(42)
                        .setShowcaseEventListener(new FirstTipHideListener(UserMapsActivity.this))
                        .build();
                showcaseView.setDetailTextAlignment(Layout.Alignment.ALIGN_CENTER);
                showcaseView.setTitleTextAlignment(Layout.Alignment.ALIGN_CENTER);
                showcaseView.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);

                // Actions to do after 10 seconds
            }
        }, 3000);

    }


    public boolean isCircleAreaMarkerInZero() {
       return  (circleAreaMarker!=null&&circleAreaMarker.getCenter().latitude ==0&&circleAreaMarker.getCenter().longitude ==0);
    }


    /*
        Class for help to demonstrate tips study show
     */
    public class FirstTipHideListener implements OnShowcaseEventListener {
        private Context context;
        FirstTipHideListener (Context context) {
            this.context = context;
        }


        @Override
        public void onShowcaseViewHide(ShowcaseView showcaseView) {

            toolbarOnShowTip.createFaces(ToolbarFacesState.SET_RADIUS_TIP);
           // toolbarOnShowTip.setRadiusFAndNameFViews(ToolbarFacesState.SET_RADIUS_TIP);
            showNextTip ();
        }


        private void showNextTip (){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {

                    TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
                    paint.setTextSize(getResources().getDimension(R.dimen.abc_text_size_large_material));
                    //paint.setStrikeThruText(true);
                    //paint.setColor(Color.RED);
                    //   paint.setTypeface(Typeface.createFromAsset(getAssets(), "RobotoSlab-Regular.ttf"));

                    TextPaint title = new TextPaint(Paint.ANTI_ALIAS_FLAG);
                    title.setTextSize(getResources().getDimension(R.dimen.abc_text_size_display_1_material));
                    title.setUnderlineText(true);


                    ShowcaseView showcaseView = new ShowcaseView.Builder(UserMapsActivity.this)
                            .withNewStyleShowcase()
                            .setTarget(new ViewTarget(R.id.inputRadius, UserMapsActivity.this))
                            .setContentTextPaint(paint)
                            .setContentTitle(R.string.title_for_set_radius_tip)
                            .setContentText(R.string.single_shot_for_set_radius_tip)
                            .setContentTitlePaint(title)
                            .setStyle(R.style.CustomShowcaseTheme3)
                            // .singleShot(42)
                            .setShowcaseEventListener(new  OnShowcaseEventListener (){
                                @Override
                                public void onShowcaseViewHide(ShowcaseView showcaseView) {

                                }

                                @Override
                                public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
                                    showLastTip ();

                                }

                                @Override
                                public void onShowcaseViewShow(ShowcaseView showcaseView) {

                                }

                                @Override
                                public void onShowcaseViewTouchBlocked(MotionEvent motionEvent) {

                                }
                            })
                            .build();

                    showcaseView.setDetailTextAlignment(Layout.Alignment.ALIGN_CENTER);
                    showcaseView.setTitleTextAlignment(Layout.Alignment.ALIGN_CENTER);
                    showcaseView.forceTextPosition(ShowcaseView.BELOW_SHOWCASE);

                }
            }, 1000);


        }


        private void showLastTip () {

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    ViewTarget viewTarget;
                    int titleStr = 0, text = 0;

                  toolbarOnShowTip.createFaces(ToolbarFacesState.START_SPY_TIP);
                    //toolbarOnShowTip.setRadiusFAndNameFViews(ToolbarFacesState.START_SPY_TIP);

                   if(getStatus()!=MessageConstant.NO_SPY){
                       viewTarget=  new ViewTarget(R.id.stop_spy, UserMapsActivity.this);
                   }else{
                       viewTarget= new ViewTarget(R.id.spy_for_employee, UserMapsActivity.this);

                   }

                    //<!--- 11:47 07 jule 16
                    ///TODO_done_all_from3h >>6*---*
                    //TODO_done 6task from 1h  set showCast View  instead of easy dialog
                    TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
                    paint.setTextSize(getResources().getDimension(R.dimen.abc_text_size_large_material));
                    //paint.setStrikeThruText(true);
                    //paint.setColor(Color.RED);
                    //   paint.setTypeface(Typeface.createFromAsset(getAssets(), "RobotoSlab-Regular.ttf"));
                    TextPaint title = new TextPaint(Paint.ANTI_ALIAS_FLAG);
                    title.setTextSize(getResources().getDimension(R.dimen.abc_text_size_display_1_material));
                    title.setUnderlineText(true);

                    ShowcaseView showcaseView = new ShowcaseView.Builder(UserMapsActivity.this)
                            .withNewStyleShowcase()
                            .setTarget(viewTarget )
                            .setContentTextPaint(paint)
                            .setContentTitle(R.string.title_for_begin_spy )
                            .setContentText( R.string.single_shot_for_begin_spy)
                            .setContentTitlePaint(title)
                            .setStyle(R.style.CustomShowcaseTheme3)
                            // .singleShot(42)
                            .setShowcaseEventListener(new  OnShowcaseEventListener (){
                                @Override
                                public void onShowcaseViewHide(ShowcaseView showcaseView) {

                                }

                                @Override
                                public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        public void run() {

                                            // Actions to do after 10 seconds
                                        }
                                    }, 1000);

                                }


                                @Override
                                public void onShowcaseViewShow(ShowcaseView showcaseView) {

                                }

                                @Override
                                public void onShowcaseViewTouchBlocked(MotionEvent motionEvent) {

                                }
                            })
                            .build();

                    showcaseView.setDetailTextAlignment(Layout.Alignment.ALIGN_CENTER);
                    showcaseView.setTitleTextAlignment(Layout.Alignment.ALIGN_CENTER);
                    showcaseView.forceTextPosition(ShowcaseView.BELOW_SHOWCASE);

                }
            }, 1000);

            safeFlagFirstTimeEnd ();
        }


        private void safeFlagFirstTimeEnd (){
            //<!--- 11:22 07jule 16
           //TODO_done_all_from3h 1>>*------*
           //TODO_done create editorStatusSpy sh_prf and safe flag (tips showed ) in name of manager
           // record the fact that the app has been started at least once
           settings.edit().putBoolean("my_first_time", false).commit();
       }


        @Override
        public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
            // Toast.makeText(context,"Showcase hidden", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onShowcaseViewShow(ShowcaseView showcaseView) {
            //  Toast.makeText(context,"Showcase shown", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onShowcaseViewTouchBlocked(MotionEvent motionEvent) {
            //append("Touch blocked: x: " + motionEvent.getX() + " y: " + motionEvent.getY());
            //  Toast.makeText(context,"Touch blocked: x: " + motionEvent.getX() + " y: " + motionEvent.getY(), Toast.LENGTH_SHORT).show();
        }
    }


    /*
        To detect status of app work is it spy for employee or not
    */
    private int getStatus (){
        //sharedGetCircle = getSharedPreferences(MessageConstant.STATUS_SPY_VIEW,MODE_PRIVATE);
        int statusSpy = sharedGetStatus.getInt( employeeSelected, MessageConstant.NO_SPY);
        return statusSpy;
    }
}

