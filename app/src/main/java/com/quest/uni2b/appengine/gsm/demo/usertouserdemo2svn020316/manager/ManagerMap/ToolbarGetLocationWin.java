package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.ManagerMap;

import android.content.SharedPreferences;
import android.view.View;

import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.GCMConstants.MessageConstant;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.R;

/**
 * Created by Operator on 21.07.2016.
 */
public class ToolbarGetLocationWin extends Toolbar4Map {



    ToolbarGetLocationWin(UserMapsActivity context) {
        super(context);

    }

    @Override
    void createFace() {

        onStopProgressBar();
        defineSpyingItem();
        /*if(threadForFewSeconds!=null){
            threadForFewSeconds.interrupt();
            //itemRefresh.setActionView(null);;
        }*/
       // itemAlertIndicator.setVisible(false);
       itemHelp.setVisible(true);
        //TODO_ delete this

        setRadiusFAndNameFViews();
    }


    @Override
    void setRadiusFAndNameFViews() {
        employeeNameText.setVisibility(View.VISIBLE);
        if ( MessageConstant.NO_SPY == getStatus()){
            getRadius.setVisibility(View.VISIBLE);
            inputLayoutName.setVisibility(View.VISIBLE);
        }
    }

    @Override
    void setRadiusFAndNameFViews(ToolbarFacesState state) {

    }

    @Override
    void createFaces(ToolbarFacesState state) {

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
