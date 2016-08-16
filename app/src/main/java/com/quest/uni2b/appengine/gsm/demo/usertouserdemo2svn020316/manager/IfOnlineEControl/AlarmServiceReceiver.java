package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.IfOnlineEControl;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.GCMConstants.MessageConstant;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.ManagerRequests.AsynTaskForManager;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.ManagerRequests.AsynTaskForManagerEnum;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Operator on 29.07.2016.
 */
public class AlarmServiceReceiver extends BroadcastReceiver {

    private String string_temp;
    private long myIdInBase;
    private Set<String> listOfEmployees;
    private SharedPreferences getSpyEmployeeFromList;
    private SharedPreferences sp4Emploee;

    public Context getContext() {
        return context;
    }

    private   Context context;

    private  SharedPreferences.Editor sp4EmployeeEdit;

    @Override
    public void onReceive(Context context, Intent intent) {
       // if (isPresent == -1 ) {isPresent = 1; return;}

       this.context = context;
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();


        //Open onReceiver from getting employee location message in MessageAgent
        if(isOpenFromInterrupt(intent, context )){wl.release();return;}



        listOfEmployees = getListOfSpyEmployees (context);
        //Monitoring employee's present in online
        //Check spy indicator. Is it online?
        for (String employeeEmail : listOfEmployees) {
            if ( checkIsPresent(employeeEmail, context) == MessageConstant.IS_PRESENT_FALSE ){ Toast.makeText(context,employeeEmail+" is offline.",Toast.LENGTH_SHORT).show();return;}
            Toast.makeText(context,employeeEmail+" is online.",Toast.LENGTH_SHORT).show();
        }

        //Send new requests to continue spy monitor
        for (String employeeEmail : listOfEmployees) {
          //For set check spy indicator in offline state
          toggleShPr4EPresent(employeeEmail, false, context);
          new AsynTaskForManager(AlarmServiceReceiver.this, getId(context), employeeEmail, AsynTaskForManagerEnum.GET_LOCATION).execute();
        }
            wl.release();

          //TODO_ if false create notification "badRequest from employee".
          // "Employee  disappeared from app's radar"
          //TODO_>> 2.0
          //TODO_ set in shPr status inside (and save current status in tempStatusSh Pref)
          //TODO_ set in ShPr status "DISAPPEARED" ->1
          //TODO_ 2.1
          //TODO_ if win check status if  "DISAPPEARED" ->1 change to -->0
          //TODO_ + set in shPr status from tempStatusSh Pref)
    }

    /*
    *   Monitoring of employee state
    **/
    private boolean isOpenFromInterrupt(Intent intent, Context context) {
        String getEEmail4mIntent = intent.getExtras().getString("employeeE");
        // Did opened receiver, in interrupt.
        // When got message successfully from employee in agent service.
        //And set state online in spy indicator for employee
        if (getEEmail4mIntent!= null){toggleShPr4EPresent(getEEmail4mIntent, true, context);return true;} return false;
    }

    public  Set<String> getListOfSpyEmployees(Context context) {
        //<!--- 29.07.2016
        getSpyEmployeeFromList = context.getSharedPreferences(MessageConstant.SPY_EMPLOYEE_LIST, context.MODE_PRIVATE);
        Set<String> setOfEEmails  = getSpyEmployeeFromList.getStringSet(MessageConstant.SPY_EMPLOYEE_LIST,null  );
        if (setOfEEmails == null){ setOfEEmails = new HashSet<>();setOfEEmails.add("Null Neo from Null Matrix");}
        return setOfEEmails;
    }

    private int checkIsPresent(String employeeEmail, Context context ) {
        sp4Emploee =  PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
       // sp4Emploee = context.getSharedPreferences(employeeEmail, context.MODE_PRIVATE);
        int  isPresent = sp4Emploee.getInt(employeeEmail, 0);
       // Toast.makeText(context, " batman(^^()^^)check "+isPresent,Toast.LENGTH_SHORT).show();
        return isPresent;
    }

    public   void toggleShPr4EPresent(String employeeEmail, boolean isPresent, Context context) {
        if(sp4EmployeeEdit == null) sp4EmployeeEdit=  PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).edit();

        if (isPresent){
           // Toast.makeText(context, "set true "+employeeEmail, Toast.LENGTH_LONG).show();
            sp4EmployeeEdit.putInt(employeeEmail, 1988  );
            sp4EmployeeEdit.commit();

        }else{
           // Toast.makeText(context, "set false "+employeeEmail, Toast.LENGTH_LONG).show();
            sp4EmployeeEdit.putInt(employeeEmail, MessageConstant.IS_PRESENT_FALSE  );
            sp4EmployeeEdit.commit();
        }
    }



    /*
    *    Starting alarm spy employee service
    */
    public void startControl(Context context) {

        //Toast.makeText(context, "Alarm monitoring on start ",Toast.LENGTH_SHORT).show();

        setEPresentTrueInStart ( context);

        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, AlarmServiceReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 60000, pi); // Millisec * Second * Minute
    }

    private void setEPresentTrueInStart(Context context) {
        listOfEmployees = getListOfSpyEmployees (context);
        for (String employeeEmail : listOfEmployees) {
            toggleShPr4EPresent(employeeEmail, true, context);
        }
    }



    /*
     *    Stopping alarm spy employee service
     */
    public void stopControl (Context context) {
        Intent intent = new Intent(context, AlarmServiceReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    public Long getId(Context context) {
        SharedPreferences shared = context.getSharedPreferences("info",context.MODE_PRIVATE);
        //Using getXXX- with XX is type date you wrote to file "name_file"
        string_temp = shared.getString("manager id","");

        if ( string_temp =="") {
            //  Toast.makeText(this, "You have not manager acc!", Toast.LENGTH_LONG).show();
        }else {
            //convert id to Long
            myIdInBase = Long.parseLong(string_temp);
            return myIdInBase;

        }
        return 0L;
    }

}
