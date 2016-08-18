package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.AlertDialogRadio.AlertPositiveListener;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.EmployeeCabinetActivity;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.EmployeeRegistrationASynTask;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.ManagerCabinetListActivity;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.ManagerRequests.ManagerRegistratioinAsynTask;


public class Main2Activity extends AppCompatActivity implements AlertPositiveListener {

    private  static final String MANAGER =  "Manager";
    private static  final  String EMPLOYE =  "EmployeeOfM";
    private GoogleAccountCredential credential;
    private static final int REQUEST_ACCOUNT_PICKER = 2;
    private static final String WEB_CLIENT_ID = "486079181368-go992i9mqnb9mutkt5medj693rovn4ru.apps.googleusercontent.com";
    private SharedPreferences settings;
    private static final String PREF_ACCOUNT_NAME = "PREF_ACCOUNT_NAME";
    private String acc_name_tmp;
    /** Stores the selected item's position */
    private  int position = 0;
    private ProgressDialog dialog;
    private SharedPreferences sPref, sPrefNikEmployee;
    private  AlreadyExistAsynTask existAsynTask;
    final String TAG = "myLogsM2A";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sPrefNikEmployee = getSharedPreferences("EmployeeNik", MODE_PRIVATE);
        // get status from shared preference and skip this activity
        sPref = getSharedPreferences("enter", MODE_PRIVATE);
        if (sPref.getBoolean("IsIn", false)) {
            Log.i(TAG, "sP_is in "+sPref.getBoolean("IsIn", false));
            if (sPref.getBoolean ("IsManager", false)){
                Intent newi = new Intent(this, ManagerCabinetListActivity.class );
                //employeeCabinet.putExtra("nik", credential.getSelectedAccountName());
                finish();
                startActivity(newi);
                //make finish for mai2A
               // finish();
                return;
            }else {
                Intent newi = new Intent(this, EmployeeCabinetActivity.class );
                //sPrefNikEmployee.getString("nik", credential.getSelectedAccountName());
                newi.putExtra("nik",  sPrefNikEmployee.getString("nik","Nik"));
                finish();
                startActivity(newi);
               // finish();
                return;
            }
            //return;
        };
        Log.i(TAG, "sP_is in " + sPref.getBoolean("IsIn", false));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main2);
        Log.i(TAG, "ONCreate");
        settings = getSharedPreferences("Spy_app_sett", Activity.MODE_PRIVATE);
        credential = GoogleAccountCredential.usingAudience(this,
                "server:client_id:" + WEB_CLIENT_ID );
        Button button = (Button) findViewById(R.id.btnGplus);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                chooseAccount();
            }
        });
    }


    @Override
    protected void onStart(){
        super.onStart();
    }


    private void chooseCharacter(){
        /** Getting the fragment manager */
        FragmentManager manager = getFragmentManager();
        /** Instantiating the DialogFragment class */
        AlertDialogRadio alert = new AlertDialogRadio();
        //alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        /** Creating a bundle object to store the selected item's index */
        Bundle b  = new Bundle();
        /** Storing the selected item's index in the bundle object */
        b.putInt("position", position);
        /** Setting the bundle object to the dialog fragment object */
        alert.setArguments(b);
        /** Creating the dialog fragment object, which will in turn open the alert dialog window */
        alert.show(manager, "alert_dialog_radio");
    }


    private void chooseAccount() {
        startActivityForResult(credential.newChooseAccountIntent(),
                REQUEST_ACCOUNT_PICKER);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_ACCOUNT_PICKER:
                if (data != null && data.getExtras() != null) {
                    String accountName = data.getExtras().getString(
                            AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        credential.setSelectedAccountName(accountName);
                        dialog = ProgressDialog.show(Main2Activity.this, "",
                                "Loading. Please wait...", true);
                       // chooseCharacter();
                         acc_name_tmp = accountName;
                        //save choice in ShPrf
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.commit();
                        // User is authorized.
                        existAsynTask = new AlreadyExistAsynTask(this, credential);
                        existAsynTask.execute();
                    }
                }
                break;
        }
    }


    public void onPostExecuteAlreExist(){
        if(!existAsynTask.getIsExist()) {
            dialog.dismiss();
            chooseCharacter();
            return;
        }

        switch (existAsynTask.getCharacter()){

            case EXIST_MANAGER:

                Log.i(TAG, " EXIST_MANAGER");

                startManagerCabinetActivity();
               // existAsynTask.getCharacter() = null;
                existAsynTask.setNullCharacter();


                break;
            case EXIST_EMPLOYEE :

                Log.i(TAG, "EXIST_EMPLOYEE ");

                startEmployeeCabinetActivity();

                existAsynTask.setNullCharacter();

                break;


        }


    }


    @Override
    public void onPositiveClick(int position) {
        this.position = position;
        /** Getting the reference of the textview from the main layout */
       // TextView tv = (TextView) findViewById(R.id.tv_android);
        /** Setting the selected android version in the textview */
        //tv.setText("Your Choice : " +
      String temp_str = Character.code[this.position];
       // Toast.makeText(this,temp_str+"/"+acc_name_tmp, Toast.LENGTH_LONG).show();
        switch (Character.code[this.position]) {
            case MANAGER:
               dialog = ProgressDialog.show(Main2Activity.this, "",
                        "Loading. Please wait...", true);
                new ManagerRegistratioinAsynTask(this, credential).execute();
                break;
            case EMPLOYE:
                dialog = ProgressDialog.show(Main2Activity.this, "",
                        "Loading. Please wait...", true);
                new EmployeeRegistrationASynTask(this, credential).execute();
                break;
        }

    }


    public void startManagerCabinetActivity(){
        SharedPreferences.Editor editor = sPref.edit();
        editor.putBoolean("IsIn", true);
        editor.commit();
        editor.putBoolean("IsManager",true);
        editor.commit();
        Intent managerReloadCabinet = new Intent(this, ManagerCabinetListActivity.class);
        managerReloadCabinet.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        dialog.dismiss();
        finish();
        startActivity(managerReloadCabinet);
    }


    public void startEmployeeCabinetActivity() {
        SharedPreferences.Editor editorNik = sPrefNikEmployee.edit();
        editorNik.putString("nik", credential.getSelectedAccountName());
        editorNik.commit();

        SharedPreferences.Editor editor = sPref.edit();
        editor.putBoolean("IsIn", true);
        editor.putBoolean("IsManager", false);
        editor.commit();

        Intent employeeCabinet = new Intent(this, EmployeeCabinetActivity.class);
        employeeCabinet.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        employeeCabinet.putExtra("nik", credential.getSelectedAccountName());
        dialog.dismiss();
        finish();
        startActivity(employeeCabinet);
    }


    public void stopDialog() {
        dialog.dismiss();
    }

}
