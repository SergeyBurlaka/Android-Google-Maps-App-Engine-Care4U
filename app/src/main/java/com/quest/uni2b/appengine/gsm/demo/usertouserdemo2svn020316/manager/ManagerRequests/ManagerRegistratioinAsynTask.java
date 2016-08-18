package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.ManagerRequests;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.operator.myapplication.backend.managerManagerApi.ManagerManagerApi;
import com.example.operator.myapplication.backend.managerManagerApi.model.ManagerManager;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.Main2Activity;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Operator on 10.03.2016.
 */
public class ManagerRegistratioinAsynTask extends AsyncTask<Void, Void, String> {

    private static ManagerManagerApi managerApi = null;
    private GoogleCloudMessaging gcm;
    private Main2Activity contextMain2Activity;
    private ManagerManager manager;
    private  GoogleAccountCredential credential;
    private Boolean result = true;
    private  ManagerManager resultEmployee;
    private static final String SENDER_ID = "414291776712";


    public ManagerRegistratioinAsynTask (Main2Activity contextMain2Activity,  GoogleAccountCredential credential) {
        this.contextMain2Activity= contextMain2Activity;
        this.credential = credential;
        this.manager = new ManagerManager();
    }


    @Override
    protected String doInBackground(Void... params) {
        if (managerApi == null) {
            ManagerManagerApi.Builder builder = new ManagerManagerApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), credential)
                    .setRootUrl("https://sincere-baton-123818.appspot.com/_ah/api/");
            managerApi = builder.build();
        }
        String msg = "";
        try {
            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(contextMain2Activity);
            }
            String regId = gcm.register(SENDER_ID);
            msg = "Device registered, registration ID=" + regId;
            manager.setRegId(regId);// pass device GCM regId to employee
            //work with Api server
           resultEmployee = managerApi.insert(manager).execute();//save employee in cloud data store
        } catch (IOException ex) {
            ex.printStackTrace();
            msg = "Error: " + ex.getMessage();
            result = false;
        }
        return msg;
    }


    @Override
    protected void onPostExecute(String msg) {
        if (result){
            //save id in SharedPreferences
            SharedPreferences pref = contextMain2Activity.getApplicationContext().getSharedPreferences("info", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("manager id", resultEmployee.getId().toString());
            editor.commit();
            contextMain2Activity.startManagerCabinetActivity();
        }
        Logger.getLogger("REGISTRATION").log(Level.INFO, msg);
        contextMain2Activity.stopDialog();
    }
}