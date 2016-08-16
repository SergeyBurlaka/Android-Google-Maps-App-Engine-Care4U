package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.EmployeeListView;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.ManagerCabinetListActivity;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.ManagerRequests.AsynTaskForManager;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.ManagerRequests.AsynTaskForManagerEnum;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.EmployeeDataWork.EmployeeOfM;

/**
 * Created by Operator on 17.05.2016.
 */
public class RecyclerClickListener implements DataManager.OnItemClickListener {
        private ManagerCabinetListActivity activity;
        private  Long managerId;
  //  private  boolean deleteEmployeeBoolean;
    //<!---
    //TODO_done+: create constructor with parametr CabinLActvt actvt , +managerId

    public RecyclerClickListener (ManagerCabinetListActivity activity, Long managerId){
        this.activity = activity;
        this.managerId = managerId;

    }


    //<--


    @Override
    public void onItemClick(EmployeeOfM item) {

        //<!---09/june/16_
        //TODO_done10min : create alert dialog do u wante delete employee y/n?      t/15:05
        //TODO_done+1min: create new asynTAsk for manager with task deleteEmployee From Friend  t/15:15
        //TODO_done+4min reload cabinet manager list activity  t15:17

        createAlertDialog (item);





        //<--


    }


        private void createAlertDialog (final  EmployeeOfM item){

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    activity);

            // set title
            alertDialogBuilder.setTitle("Your Title");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Click yes to exit!")
                    .setCancelable(false)
                    .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, close
                            // current activity
                           // MainActivity.this.finish();

                            new AsynTaskForManager(activity, managerId, item.getUserEmail(), AsynTaskForManagerEnum.DELETE_E_FROM_FRIEND  ).execute();


                           // deleteEmployeeBoolean = true;
                            dialog.cancel();

                        }
                    })
                    .setNegativeButton("No",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                          //  deleteEmployeeBoolean = false;
                            dialog.cancel();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();

        }




}