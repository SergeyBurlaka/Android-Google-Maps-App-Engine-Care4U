package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.operator.myapplication.backend.employeeApi.EmployeeApi;
import com.example.operator.myapplication.backend.employeeApi.model.Employee;
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
public class EmployeeRegistrationASynTask extends AsyncTask<Void, Void, String> {
private static EmployeeApi employeeApi = null;
private GoogleCloudMessaging gcm;
//private EmployeeRegistrationActivity context;
private Main2Activity context;
private Employee employee;
private SharedPreferences pref;
 private GoogleAccountCredential credential;
    private Boolean result = true;
private   Employee resultEmployee;
// TODO_: change to your own sender ID to Google Developers Console project number, as per instructions above
private static final String SENDER_ID = "414291776712";

public EmployeeRegistrationASynTask (Main2Activity context, GoogleAccountCredential credential) {
        this.context = context;
        this.employee = new Employee();
        this.credential = credential;
        }

@Override
protected String doInBackground(Void... params) {
        if (employeeApi == null) {
        EmployeeApi.Builder builder = new EmployeeApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), credential)
        .setRootUrl("https://sincere-baton-123818.appspot.com/_ah/api/");

        employeeApi = builder.build();
        }

        String msg = "";
        try {
        if (gcm == null) {
        gcm = GoogleCloudMessaging.getInstance(context);
        }
        String regId = gcm.register(SENDER_ID);
        msg = "Device registered, registration ID=" + regId;

        // You should send the registration ID to your server over HTTP,
        // so it can use GCM/HTTP or CCS to send messages to your app.
        // The request to your server should be authenticated if your app
        // is using accounts.


        employee.setRegId(regId);// pass device GCM regId to employee

           //work with Api server
         resultEmployee = employeeApi.insert(employee).execute();//save employee in cloud data store



        } catch (IOException ex) {
        ex.printStackTrace();
        msg = "Error: " + ex.getMessage();
            result =false;
        }
        return msg;
        }

@Override
protected void onPostExecute(String msg) {

    if (result){

        //and
        //save id in SharedPreferences
        pref = context.getApplicationContext().getSharedPreferences("info", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("employee id", resultEmployee.getId().toString());
//finally, when you are done saving the values, call the commit() method.
        editor.commit();
        //saved it.
        context.startEmployeeCabinetActivity();


    }
      //  Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        Logger.getLogger("REGISTRATION").log(Level.INFO, msg);

    context.stopDialog();
        }
        }