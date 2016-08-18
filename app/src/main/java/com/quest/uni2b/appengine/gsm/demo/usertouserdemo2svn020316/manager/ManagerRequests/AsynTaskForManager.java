package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.ManagerRequests;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.operator.myapplication.backend.managerManagerApi.ManagerManagerApi;
import com.example.operator.myapplication.backend.managerManagerApi.model.ManagerManager;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.GCMConstants.MessageConstant;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.EmployeeDataWork.EmployeeOfM;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.EmployeeDataWork.EmployeeRequest;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.EmployeeDataWork.EmployeeRequestsActivity;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.IfOnlineEControl.AlarmServiceReceiver;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.ManagerCabinetListActivity;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.ManagerMap.UserMapsActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Operator on 17.03.2016.
 */
public class AsynTaskForManager extends AsyncTask<Void, Void, List<String>> {

    private static ManagerManagerApi managerApi = null;
    private  AlarmServiceReceiver alarmServiceReceiver;
    private GoogleCloudMessaging gcm;
    private EmployeeRequestsActivity employeeRequestsActivity;
    private ManagerCabinetListActivity activityReload;
    private Long managerId;
    private AsynTaskForManagerEnum task;
    private ArrayList<String> employeeEmails, getEmployeeRequest;
    private ManagerManager container;
    private String message;
    UserMapsActivity userMapsActivity;
    private String employeeEmail;
    //For GET_MANAGER asyn task
    private ManagerManager resultManager;
    private static final String SENDER_ID = "414291776712";
    private boolean result = true;
    private String msg, deleteEmail, setRegId;


    //<!--10june16_15:30
    //Constructor for DELETE_LIST_E_FROM_FRIEND request
    public  AsynTaskForManager (ManagerCabinetListActivity activity, Long managerId, ArrayList<String> employeeEmails, AsynTaskForManagerEnum enumTask) {
        this.activityReload = activity;
        this.managerId = managerId;
        this.employeeEmails = employeeEmails;
        this.task = enumTask;
    }

    //Constructor deleteEmployee
    // parameters: MCabLActvt actvt, Employee email, manager id , asynTask deleteFromE
    public AsynTaskForManager(ManagerCabinetListActivity activity,Long managerId,String employeeEmail, AsynTaskForManagerEnum enumTask ) {
    this.activityReload=activity;
    this.managerId=managerId;
    this.task=enumTask;
    this.employeeEmail = employeeEmail;
    }


    //For delete from requests
    public  AsynTaskForManager ( EmployeeRequestsActivity employeeRequestsActivity,Long managerId,String deleteEmail, AsynTaskForManagerEnum enumTask) {
        this.employeeRequestsActivity = employeeRequestsActivity;
        this.managerId = managerId;
        this.deleteEmail = deleteEmail;
        this.task = enumTask;
        //this.message =message;
    }


   //Constructor for spying asyngTask  with manager id & user map context& email of employee  for toast
   //Also for GET_LOCATION TASK
    public  AsynTaskForManager (UserMapsActivity userMapsActivity , Long managerId,String employeeEmail, AsynTaskForManagerEnum enumTask) {
        this.userMapsActivity = userMapsActivity;
        this.managerId = managerId;
        this.employeeEmail = employeeEmail;
        this.task = enumTask;
        //this.message =message;
    }


    public  AsynTaskForManager (AlarmServiceReceiver alarmServiceReceiver , Long managerId, String employeeEmail, AsynTaskForManagerEnum enumTask) {
        this. alarmServiceReceiver = alarmServiceReceiver;
        this.managerId = managerId;
        this.employeeEmail = employeeEmail;
        this.task = enumTask;
        //this.message =message;
    }


    public  AsynTaskForManager (UserMapsActivity userMapsActivity, Long managerId, ArrayList<String> employeeEmails,String message, AsynTaskForManagerEnum enumTask) {
        this.userMapsActivity = userMapsActivity;
        this.managerId = managerId;
        this.employeeEmails = employeeEmails;
        this.task = enumTask;
        this.message =message;
    }


    public  AsynTaskForManager (EmployeeRequestsActivity activity, Long managerId, ArrayList<String> employeeEmails, AsynTaskForManagerEnum enumTask) {
        this.employeeRequestsActivity = activity;
        this.managerId = managerId;
        this.employeeEmails = employeeEmails;
        this.task = enumTask;
        //this.message =message;
    }

      //Asyn task for new ManagerCabinetListActivity for getting list of employees emails
     // for task GET_MANAGER
    // for task GET_REQUEST
    // also For set new reg id for new another devices
    public AsynTaskForManager (ManagerCabinetListActivity activity, Long managerId, AsynTaskForManagerEnum enumTask){
        this.activityReload = activity;
        this.managerId = managerId;
        this.task = enumTask;

    }


    @Override
    protected List<String> doInBackground(Void... params) {
        if ( managerApi == null) {
            ManagerManagerApi.Builder builder = new ManagerManagerApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://sincere-baton-123818.appspot.com/_ah/api/");
            managerApi = builder.build();
        }
       msg = "";
        List<String> ls;
        switch (task) {
            case CONFIRM:
            try {
                managerApi.confirm(managerId, employeeEmails).execute();//save employee in cloud data store
                //msg = "Add employee successfully";
                msg = "";
            } catch (IOException e) {
                e.printStackTrace();
               // msg = "Error: " + e.getMessage();
                msg = "Unknown error. Try again.";
            }
               // return null ;
            break;

            case GET_REQUEST:
                try {
                  container= managerApi.getEmployeeRequest(managerId).execute();//save employee in cloud data store
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (container !=null) {
                    return container.getListEmails();
                }else {
                    break;
                }

            case GET_CHECK:
                try {
                    managerApi.chekingId(managerId).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            //Sends special spy message with kind SPY = 7
            case SPY:
                //DEMO LIST
                List <String> emailList = new ArrayList<>();
                try {
                    //DEMO execute
                    managerApi.sPY(managerId, employeeEmails , message).execute();
                    //msg = "Send spy message";
                } catch (IOException e) {
                    e.printStackTrace();
                    //msg = "Error: " + e.getMessage();
                    msg = "Unknown error. Try again.";
                }
                break;

            case GET_LOCATION:
                try {
                    managerApi.giveEmployeetLocation(employeeEmail,managerId).execute();
                   // msg = "Send req message";
                    msg ="";
                } catch (IOException e) {
                    e.printStackTrace();
                   // msg = "Error: " + e.getMessage();
                    msg = "Unknown error. Try again.";
                }
                break;

            case GET_MANAGER:
                try {
                    resultManager = managerApi.getManager(managerId ).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                    //msg = "Error: " + e.getMessage();
                    msg = "Unknown error. Try again.";
                    result = false;
                }
                break;

            case DELETE_REQUEST:
                try {
                    managerApi.deleteRequest(managerId , deleteEmail ).execute();
                   // msg = "Delete employee successfully";
                    msg = "";
                } catch (IOException e) {
                    e.printStackTrace();
                   // msg = "Error: " + e.getMessage();
                    msg = "Unknown error. Try again.";
                }
                break;

            case SET_NEW_REG_ID:
                String msgRegId = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(activityReload);
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
                    managerApi.setManagerRegId(managerId, regId ).execute();
                    //msg = "registrate new device"+regId;
                    msg = "";
                } catch (IOException e) {
                    e.printStackTrace();
                    //msg = "Error: " + e.getMessage();
                    msg = "Unknown error. Try again.";
                }
                break;
            //08june16_14_55:
            // AsynTask to server delete Employee from list
            case DELETE_E_FROM_FRIEND:
                try {
                    managerApi.deleteEFromFriend(managerId, employeeEmail).execute();
                   // msg = "delete employee";
                    msg = "";
                } catch (IOException e) {
                    e.printStackTrace();
                   // msg = "Error: " + e.getMessage();
                    msg ="Unknown error. Try again.";
                }
                break;

            //<!---10june16_15:30
           //Request to server with parameters: managerId & list of employees
            case DELETE_LIST_E_FROM_FRIEND:
                try {
                    managerApi.deleteListEFromFriend(managerId, employeeEmails ).execute();
                   // msg = "delete list of employees";
                    msg = "";
                } catch (IOException e) {
                    e.printStackTrace();
                    //msg = "Error: " + e.getMessage();
                    msg = "Unknown error. Try again.";
                }
                break;

            //<!---18:00 17june16
            //AsynTask request to server stop spying
            // Just need manager id & email of employee for request
            case STOP_SPY:
                try {
                    managerApi.stopSpy(managerId,employeeEmail).execute();
                    //msg = "stop_spy_for_employee";
                    msg="";
                } catch (IOException e) {
                    e.printStackTrace();
                    //msg = "Error: " + e.getMessage();
                    //"unknown error"
                    msg = "Unknown error. Try again.";
                }
                break;
        }
         ls = new ArrayList<>();
            ls.add(msg);
            return ls;
    }


    @Override
    protected void onPostExecute(List<String> str) {
        switch (task) {
            case GET_REQUEST:
                EmployeeRequest employeeReq;
                //List products = activity.getProducts();
                List products = activityReload.getProducts();
                //new new new matrix reload matrix reload
                //fill our list of employee requests
                if ((str != null) && !str.isEmpty()) {
                    for (String fillEmployeeEmail : str) {
                        employeeReq = new EmployeeRequest(fillEmployeeEmail, false);
                        if (!products.contains(employeeReq)) products.add(employeeReq);
                    }
                    activityReload.showBadge();
                    //Toast.makeText(activityReload, "i've got it!", Toast.LENGTH_LONG).show();
                } else
                   // Toast.makeText(activityReload, "You have not employees request!", Toast.LENGTH_LONG).show();
                break;

            case CONFIRM:
                //activity.clearList();
                for (String mssg : str) {
                    //employeeReq= new EmployeeRequest(fillEmployeeEmail, false);
                    if (!msg.matches(""))
                    Toast.makeText(employeeRequestsActivity, mssg, Toast.LENGTH_LONG).show();
                        if ( ManagerCabinetListActivity.users!=null){
                           ManagerCabinetListActivity.flagNewRequest = true;
                        }
                }
                break;

            case DELETE_REQUEST:
                //activity.clearList();
                for (String mssg : str) {
                    //employeeReq= new EmployeeRequest(fillEmployeeEmail, false);
                    if (!msg.matches(""))
                    Toast.makeText(employeeRequestsActivity, mssg, Toast.LENGTH_LONG).show();
                    if ( ManagerCabinetListActivity.users!=null){
                        ManagerCabinetListActivity.flagNewRequest = true;
                    }
                }
                break;

            case GET_LOCATION:
                if ((str != null) && !str.isEmpty()) {
                    for (String mssg : str) {
                        if (!msg.matches(""))
                            if (userMapsActivity!=null) Toast.makeText(userMapsActivity, mssg, Toast.LENGTH_LONG).show();
                            if(alarmServiceReceiver!= null) Toast.makeText(alarmServiceReceiver.getContext(), mssg, Toast.LENGTH_LONG).show();
                    }
                }
                // msg = "";
                break;

            case SPY:
                if ((str != null) && !str.isEmpty()) {
                    for (String mssg : str) {
                        //employeeReq= new EmployeeRequest(fillEmployeeEmail, false);
                        if (!msg.matches(""))
                        Toast.makeText(userMapsActivity, mssg, Toast.LENGTH_LONG).show();
                    }
                    break;
                }

            case GET_MANAGER:
                if (result) {
                    SharedPreferences pref = activityReload.getSharedPreferences(MessageConstant.STATUS_SPY_VIEW, activityReload.MODE_PRIVATE);
                //    Toast.makeText(activityReload, resultManager.getEmail(), Toast.LENGTH_LONG).show();
                    if (resultManager.getListEmails() ==null) return;
                    List<String> emails;
                    //fill list of employees
                    emails =  resultManager.getListEmails();
                    ManagerCabinetListActivity.users = new ArrayList<>();
                    String [] itemAlert = new String [emails.size()];
                    int i = 0;
                    for (String email : emails){
                         //<!---13:40 17june16
                        //Get spying status from shared preference
                        //set it in EmployeeOFM
                        int statusSpy = pref.getInt(email,MessageConstant.NO_SPY);
                        ManagerCabinetListActivity.users.add(new EmployeeOfM(email,statusSpy));
                        //create array for alert dialog for delete contact
                        itemAlert [i] = email;
                        i++;
                    }
                    //set our list of contacts for alert dialog
                    activityReload.setItemAlertD( itemAlert);
                    activityReload.createUsersRecycleViewList();
                }else {
                    if (!msg.matches(""))
                 Toast.makeText(activityReload, msg, Toast.LENGTH_LONG).show();
                }
                break;

            case SET_NEW_REG_ID:
              //  Toast.makeText(activityReload, msg, Toast.LENGTH_LONG).show();
                break;

            case DELETE_LIST_E_FROM_FRIEND:
            case DELETE_E_FROM_FRIEND:
                if (!msg.matches(""))
                Toast.makeText(activityReload, msg, Toast.LENGTH_LONG).show();
                    activityReload.reloadActivity();
                break;

            case STOP_SPY:
                if (!msg.matches(""))
              Toast.makeText(userMapsActivity, msg, Toast.LENGTH_LONG).show();
                break;
        }
    }
}
