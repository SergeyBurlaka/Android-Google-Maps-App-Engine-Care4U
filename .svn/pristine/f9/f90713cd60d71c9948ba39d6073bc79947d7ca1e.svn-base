package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.operator.myapplication.backend.managerManagerApi.ManagerManagerApi;
import com.example.operator.myapplication.backend.managerManagerApi.model.ManagerManager;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Operator on 07.04.2016.
 */
public class AlreadyExistAsynTask extends AsyncTask<Void, Void, String> {
    private static ManagerManagerApi managerApi = null;
    private GoogleCloudMessaging gcm;
    //  private ManagerRegistrationActivity context;
    private Main2Activity contextMain2Activity;
    private ManagerManager manager;
    private  GoogleAccountCredential credential;
    private  ManagerManager result;
    final String TAG = "myLogsASYNASYNAYN";


    public CharacterExist getCharacter() {
        return character;
    }

    public void setNullCharacter() {
        character = null;
    }


    private CharacterExist character;

    private Boolean isExist=true;



    public Boolean getIsExist() {
        return isExist;
    }









    // TODO_: change to your own sender ID to Google Developers Console project number, as per instructions above
    private static final String SENDER_ID = "414291776712";




    public AlreadyExistAsynTask(Main2Activity contextMain2Activity, GoogleAccountCredential credential) {
        this.contextMain2Activity = contextMain2Activity;
        this.credential = credential;

    }


    @Override
    protected String doInBackground(Void... params) {
        if (managerApi == null) {

           // Toast.makeText(contextMain2Activity, "became already exist request: "+credential.getSelectedAccountName(), Toast.LENGTH_LONG).show();

            Log.i(TAG,  "became already exist request: "+credential.getSelectedAccountName());

            ManagerManagerApi.Builder builder = new ManagerManagerApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), credential)
                    .setRootUrl("https://sincere-baton-123818.appspot.com/_ah/api/");

            managerApi = builder.build();
        }
        String msg = "";



        //String msg = "";
        try {
            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(contextMain2Activity);
            }
            String regId = gcm.register(SENDER_ID);
            msg = "Device registered, registration ID=" + regId;

            // You should send the registration ID to your server over HTTP,
            // so it can use GCM/HTTP or CCS to send messages to your app.
            // The request to your server should be authenticated if your app
            // is using accounts.


          //  manager.setRegId(regId);// pass device GCM regId to employee

           // msgRegId= "Device registered, registration ID=" + regId;

            // You should send the registration ID to your server over HTTP,
            // so it can use GCM/HTTP or CCS to send messages to your app.
            // The request to your server should be authenticated if your app
            // is using accounts.
            // Toast.makeText(context, msgRegId, Toast.LENGTH_LONG).show();
            if (regId== null){msg = "Error: "; return msg;}

        //work with Api server
       // try {

            //<!---09june16 22: 45
            //TODO_d+??: set regId of device to alreadyEx method for checking device. Parameter: String regId

            Log.i(TAG,  "managerApi.alreadyExists().execute(); "+credential.getSelectedAccountName());
            result = managerApi.alreadyExists(regId).execute();//save employee in cloud data store
          //<--


            msg ="ok"+msg;


        } catch (IOException ex) {
            ex.printStackTrace();
            msg = "Error: " + ex.getMessage();
            isExist = false;

        }
        return msg;
    }

    @Override
    protected void onPostExecute(String msg) {

       // Toast.makeText(contextMain2Activity, msg+"is manager: "+result.getIsManager(), Toast.LENGTH_LONG).show();
        Logger.getLogger("REGISTRATION").log(Level.INFO, msg);


        // put registration info in Shared Preference - if first time sign google with new account

        if (isExist){


            if (result.getIsManager()) {
                character = CharacterExist.EXIST_MANAGER;
                //and
                //save id in SharedPreferences
                SharedPreferences pref = contextMain2Activity.getApplicationContext().getSharedPreferences("info", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("manager id", result.getId().toString());

//finally, when you are done saving the values, call the commit() method.
                editor.commit();
                //saved it.




            }else{

                character = CharacterExist.EXIST_EMPLOYEE;
                //and
                //save id in SharedPreferences
                SharedPreferences pref = contextMain2Activity.getApplicationContext().getSharedPreferences("info", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("employee id", result.getId().toString());
                editor.commit();



            }

        }

        //zero out of managerApi, for new  defining of  ManagerManagerApi.Builder builder in new new AlreadyExistAsynTask
        managerApi = null;

        contextMain2Activity.onPostExecuteAlreExist();


    }




}