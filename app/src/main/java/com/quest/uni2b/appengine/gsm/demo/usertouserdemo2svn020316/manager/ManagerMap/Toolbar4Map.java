package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.ManagerMap;

import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.model.Circle;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.GCMConstants.MessageConstant;

/**
 * Created by Operator on 21.07.2016.
 *   Using Factory pattern.
 */
public abstract  class Toolbar4Map {

    TextView loadTextGetLoc;
    UserMapsActivity context;
    MenuItem itemSpy, itemStop,/* itemRefresh ,*/ itemHelp;
    EditText getRadius ;
    TextInputLayout inputLayoutName;
    Circle circleAreaMarker;
    TextView employeeNameText;
    ProgressBar progressBar;
    String employeeSelected;
    SharedPreferences sharedGetCircle;
    Thread threadForFewSeconds;
    SharedPreferences sharedGetStatus;;
    MenuItem itemAlertIndicator;
    Thread threadForIndicate;

    public void setInterruptAnimateLoadText(boolean interruptAnimateLoadText) {
        this.interruptAnimateLoadText = interruptAnimateLoadText;
    }

    boolean interruptAnimateLoadText = false;

    Toolbar4Map(UserMapsActivity context){
        this.itemAlertIndicator  = context.itemAlertIndicator;
        this.context = context;
        this.itemSpy = context.itemSpy;
        this.itemStop = context.itemStop;
        this.itemHelp = context.itemHelp;
        //this.itemRefresh = context.itemRefresh;
        this.getRadius = context.getRadius;
        this.inputLayoutName = context.inputLayoutName;
        this.circleAreaMarker = context.circleAreaMarker;
        this.employeeNameText = context.employeeNameText;
        this.loadTextGetLoc = context.textWithProgress;

        this.progressBar = context.progressBar;
        this.employeeSelected = context.employeeSelected;
        sharedGetStatus = context.getSharedPreferences(MessageConstant.STATUS_SPY_VIEW,context.MODE_PRIVATE);
        sharedGetCircle = context.getSharedPreferences(employeeSelected,context.MODE_PRIVATE);
    }

    abstract void createFace ();

    abstract void createFaces(ToolbarFacesState state) ;

    abstract void setRadiusFAndNameFViews();

    abstract void setRadiusFAndNameFViews(ToolbarFacesState state);

    abstract void defineSpyingItem();

    abstract void onTransformerToolbar ();

    abstract void startIndicateThread();


    public Thread getThread() {
        return threadForIndicate;
    }


    /*
   *    Progress bar
   **/
     void onStopProgressBar(){
        //<!-- 18.07.2016
        //TODO_ stop bar
        //TODO_ set visible of name / set radius
        // setProgressBarIndeterminateVisibility(false);
        interruptAnimateLoadText = true;
        progressBar.setVisibility(View.GONE);
        loadTextGetLoc.setVisibility(View.GONE);
        //itemRefresh.setVisible(true);
        // setSpyOrStopItemInCreate();
    }


    /*
    *   Things for items
    */
    /*
     void load4ItemRefresh() {

        // itemRefresh.setActionView();

        threadForFewSeconds = new Thread() {
            @Override
            public void run() {

                try {
                    sleep(3000);
                    if(Thread.currentThread().isInterrupted())return;
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            itemRefresh.setActionView(null);;


                        }});

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        threadForFewSeconds.start();

    }*/



     void toggleSpyItem (){
        if(itemSpy.isVisible()){
            itemStop.setVisible(true);
            itemSpy.setVisible(false);
        }else {
            itemStop.setVisible(false);
            itemSpy.setVisible(true);
        }
    }


    /*
    *   Shared Preference help
    * */
     int getStatus (){
        //sharedGetCircle = getSharedPreferences(MessageConstant.STATUS_SPY_VIEW,MODE_PRIVATE);
        int statusSpy = sharedGetStatus.getInt( employeeSelected, MessageConstant.NO_SPY);
        return statusSpy;
    }

}