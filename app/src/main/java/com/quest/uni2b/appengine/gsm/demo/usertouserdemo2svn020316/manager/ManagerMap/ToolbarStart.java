package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.ManagerMap;

import android.os.Handler;
import android.view.View;

/**
 * Created by Operator on 21.07.2016.
 */
public class ToolbarStart extends Toolbar4Map {

    private final String employeeName;

   /* public Thread getThread() {
        return threadForIndicate;
    }

    Thread threadForIndicate;*/


    private Handler handler;


    ToolbarStart(UserMapsActivity context) {
        super(context);
        this.employeeName = context.employeeSelected;
    }


    @Override
    void createFace() {

        itemSpy.setVisible(false);
        itemStop.setVisible(false);
        //itemRefresh.setVisible(false);
        itemHelp.setVisible(false);
        // <!--- 25.07.2016
        //TODO_+ set invisible new item- indicator
        itemAlertIndicator.setVisible(false);
        animatedText();

        setRadiusFAndNameFViews();
       // loadTextGetLoc.setText("Getting "+employeeName+" location.");
        startIndicateThread();

    }

    private void animatedText() {
        handler = new Handler();

        for (int i = 100; i <= 6000; i=i+100) {
            if (interruptAnimateLoadText) return;
            final int finalI = i;
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    if (finalI % 300 == 0) {
                        loadTextGetLoc.setText("Request for "+employeeName+"\n"+"    current location.");
                    } else if (finalI % 200 == 0) {
                        loadTextGetLoc.setText("Request for "+employeeName+"\n"+"    current location..");
                    } else if (finalI % 100 == 0) {
                        loadTextGetLoc.setText("Request for "+employeeName+"\n"+"    current location...");
                    }
                }
            }, i);
        }
    }



    @Override
     void setRadiusFAndNameFViews() {
        //"Input Radius" & "Name Field"
        getRadius.setVisibility(View.GONE);
        inputLayoutName.setVisibility(View.GONE);
        employeeNameText.setVisibility(View.GONE);
        // getRadius.setVisibility(View.VISIBLE);
        // inputLayoutName.setVisibility(View.VISIBLE);
        //<--
    }


    @Override
    void setRadiusFAndNameFViews(ToolbarFacesState state) {

    }

    @Override
    void onTransformerToolbar() {
        stopIndicateAlert ();
    }



    //Blinking of itemAlertIndicator
    @Override
    void startIndicateThread() {
        // itemRefresh.setActionView();
        if (threadForIndicate!=null && threadForIndicate.isAlive())return;
        threadForIndicate = new Thread() {
            public boolean theBoolean;

            @Override
            public void run() {

                try {
                    theBoolean = true;
                    while (true) {

                        sleep(500);
                        if (Thread.currentThread().isInterrupted()) break;
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                itemAlertIndicator.setVisible(theBoolean);

                            }
                        });

                        theBoolean = !theBoolean;

                    }

                    itemAlertIndicator.setVisible(false);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        threadForIndicate.start();


    }

    void stopIndicateAlert (){

        if ( threadForIndicate != null) threadForIndicate.interrupt();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                itemAlertIndicator.setVisible(false);
            }
        }, 500);

    }


    @Override
    void createFaces(ToolbarFacesState state) {

    }

    @Override
    void defineSpyingItem() {

    }

}
