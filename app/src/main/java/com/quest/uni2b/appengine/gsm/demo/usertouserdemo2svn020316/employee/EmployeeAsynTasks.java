package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.operator.myapplication.backend.employeeApi.EmployeeApi;
import com.example.operator.myapplication.backend.employeeApi.model.Employee;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

/**
 * Created by Operator on 10.03.2016.
 */
public class EmployeeAsynTasks  extends AsyncTask<Void, Void, String> {

    //private GoogleCloudMessaging gcm;
    private static EmployeeApi employeeApi = null;
    private GoogleCloudMessaging gcm;

    //reference to activity from which we make request
    private Context context;

    private Employee employee;
    private Long employeeeId;

    //Enum class
    private AsynTaskForEmployee task;
    private String managerEmail;
    private double [] dataLocation = new double[]{0,0};
    // TODO_: change to your own sender ID to Google Developers Console project number, as per instructions above
    private static final String SENDER_ID = "414291776712";






    //for creating response from request giving emploee location now
    //AsynTask = TAKE_LOCATION
    public EmployeeAsynTasks (Context context, Long employeeId,double[] datalocation,AsynTaskForEmployee task) {
        this.context = context;
        this.employeeeId = employeeId;
        this.task = task;
        this.dataLocation = datalocation;


    }

    //for creating response from request giving emploee location now
    //AsynTask = TAKE_LOCATION
    public EmployeeAsynTasks ( Long employeeId,double[] datalocation,AsynTaskForEmployee task) {

        this.employeeeId = employeeId;
        this.task = task;
        this.dataLocation = datalocation;


    }







    // CHECK_INFO task
    public EmployeeAsynTasks (Context context, Long employeeId,AsynTaskForEmployee task) {
        this.context = context;
        this.employeeeId = employeeId;
        this.task = task;


    }

    //For set new reg id for new another devices
    //SEND_SPY_STATUS, GET_Hired task
    //need manager email
    public EmployeeAsynTasks (Context context, Long employeeId,String someString, AsynTaskForEmployee task) {
        this.context = context;
        this.employeeeId = employeeId;
        this.task = task;
        //also some setRegId str
        this.managerEmail=someString;

    }



    @Override
    protected String doInBackground(Void... params) {
        if (employeeApi == null) {
            EmployeeApi.Builder builder = new EmployeeApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://sincere-baton-123818.appspot.com/_ah/api/");

            employeeApi = builder.build();
        }

        String msg = "";

        switch (task) {
            case SOS:

            try {
                employeeApi.hELP(employeeeId).execute();//save employee in cloud data store
            } catch (IOException e) {
                e.printStackTrace();
             //   msg = "Error: " + e.getMessage();
                msg = "unknown error";

            }
                break;
            case GET_Hired:

                try {
                    employeeApi.hired(employeeeId,managerEmail).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                  //  msg = "Error: " + e.getMessage();
                    msg = "Error: email is invalid";
                }

                break;
            case CHECK_INFO:
                try {
                    employeeApi.getEmployesTest(employeeeId).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                    msg = "Error: " + e.getMessage();
                }

                break;


            case SEND_SPY_STATUS:

                try {

                    // SEND_SPY_STATUS is not outside message.
                    // It is for  sending different information about employee life.
                     //ManagerEmail is not manager email. It is the message!
                    // And do not think. It is all MATRIX.
                    employeeApi.outside(employeeeId,managerEmail ).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case TAKE_LOCATION:

                //employeeApi.sendEmployeetLocation(dataLocation, employeeeId[0],employeeeId[1] )
                try {
                    employeeApi.sendEmployeetLocation(employeeeId, dataLocation[0],dataLocation[1]).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case SET_NEW_REG_ID:

                String msgRegId = "";

                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    String regId = null;
                    try {
                        regId = gcm.register(SENDER_ID);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    msgRegId= "Device registered, registration ID=" + regId;

                    // You should send the registration ID to your server over HTTP,
                    // so it can use GCM/HTTP or CCS to send messages to your app.
                    // The request to your server should be authenticated if your app
                    // is using accounts.
                   // Toast.makeText(context, msgRegId, Toast.LENGTH_LONG).show();
                    if (regId== null){msg = "Error: "; break;}
                    employeeApi.setEmployeeRegId(employeeeId, regId).execute();
                    msg = "";
                } catch (IOException e) {
                    e.printStackTrace();
                    msg = "Error: " + e.getMessage();
                }

                break;

        }
        return msg;
    }

    @Override
    protected void onPostExecute(String msg) {
        if (!msg.matches(""))
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

    }
}



