package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.ManagerMap;

import android.content.SharedPreferences;
import android.view.View;

import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.GCMConstants.MessageConstant;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.R;

/**
 * Created by Operator on 21.07.2016.
 */
public class ToolbarShowTips extends  Toolbar4Map{

    ToolbarShowTips(UserMapsActivity context) {
        super(context);
    }

    @Override
    void createFace() {
    }


    @Override
    void createFaces(ToolbarFacesState state) {

        switch ( state) {

            case ON_START_TIP:
                itemSpy.setVisible(false);
                itemStop.setVisible(false);
                itemHelp.setVisible(false);
                //set invisible some interface elements for better sowing our tips
                setRadiusFAndNameFViews(ToolbarFacesState.ON_START_TIP);
                break;

            case SET_RADIUS_TIP:
                setRadiusFAndNameFViews(ToolbarFacesState.SET_RADIUS_TIP);
                break;

            case START_SPY_TIP:
                //setSpyOrStopItemFromBroadcast();
                defineSpyingItem();
                itemHelp.setVisible(true);
                break;
        }
    }


    @Override
    void setRadiusFAndNameFViews() {
    }


    @Override
    void setRadiusFAndNameFViews(ToolbarFacesState state) {
        switch ( state) {
            case ON_START_TIP:
                employeeNameText.setVisibility(View.INVISIBLE);
                getRadius.setVisibility(View.INVISIBLE);
                inputLayoutName.setVisibility(View.INVISIBLE);
                break;

            case SET_RADIUS_TIP:
                employeeNameText.setVisibility(View.VISIBLE);
                getRadius.setVisibility(View.VISIBLE);
                inputLayoutName.setVisibility(View.VISIBLE);
                break;

            case START_SPY_TIP:
                break;
        }
    }


    @Override
    void defineSpyingItem() {
        //TODO_done just check status if spy set visible stop or set visible spyItem
        SharedPreferences pref =context.getSharedPreferences(MessageConstant.STATUS_SPY_VIEW, context.MODE_PRIVATE);
        int statusSpy = pref.getInt(employeeSelected,MessageConstant.NO_SPY);

        if (statusSpy == MessageConstant.NO_SPY){
            itemStop.setVisible(false);
            itemSpy.setVisible(true);
            itemSpy.setIcon(R.mipmap.eye_wh_i);
        }else{
            itemStop.setVisible(true);
            itemSpy.setVisible(false);
        }
    }


    @Override
    void onTransformerToolbar() {

    }


    @Override
    void startIndicateThread() {

    }
}
