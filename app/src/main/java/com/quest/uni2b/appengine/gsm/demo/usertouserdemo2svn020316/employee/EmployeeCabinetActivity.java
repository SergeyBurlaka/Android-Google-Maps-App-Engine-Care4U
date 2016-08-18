package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.Constants4Emploee.EmplConst4ShPrfOrIntent;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.employee.EmployeeMap.EmployeeMapActivity;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.Main2Activity;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.R;

public class EmployeeCabinetActivity extends AppCompatActivity implements View.OnClickListener, KeyEvent.Callback, Runnable {

    private GoogleCloudMessaging gcm;
    private static final String SENDER_ID = "414291776712";
    private String string_temp, managerEmailStr;
    private long myIdInBase;
    private EditText managerEmail;
    private static final int NOTIFY_ID = 101;
    private Thread t = new Thread(this);
    private Handler h;
    private SharedPreferences sPref;
    private TextView nik;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Store our shared preference
        SharedPreferences sp = getSharedPreferences(EmplConst4ShPrfOrIntent.EMPLOYEE_INFO, MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putBoolean("active", true);
        ed.commit();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_employee_cabinet);
        h = new Handler();
        nik = (TextView) findViewById(R.id.textViewNik);
        managerEmail =  (EditText) findViewById(R.id.getManagerEmailTxt);
        Intent intent = getIntent();
        if (!(intent.getStringExtra("nik") ==null))
        nik.append(intent.getStringExtra("nik") );

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarEmployeeCabinet);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        // getSupportActionBar().setLogo(R.drawable.babycare);
        // Display icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.care_i);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("Care4U");

        //Get id inf (id in base on server) from Sh. Prf.
        // Idea is this data of personal unique id in base of server let you to make request to server.
        // This id info had saved during registration, or when you signed in to app with google+.
        //get SharedPreferences from getSharedPreferences("name_file", MODE_PRIVATE)
        SharedPreferences shared = getSharedPreferences("info",MODE_PRIVATE);
        //Using getXXX- with XX is type date you wrote to file "name_file"
        string_temp = shared.getString("employee id","");
        if ( string_temp ==""){
           // Toast.makeText(this, "You have not employee acc!", Toast.LENGTH_LONG).show();
            // You can not to use buttons if you have not id info in Sh. Pref.
        }else {
            //id is Long variable
            myIdInBase = Long.parseLong(string_temp);
            findViewById(R.id.getHiredButton).setOnClickListener(this);
            findViewById(R.id.SOSButton).setOnClickListener(this);
           // findViewById(R.id.checkEmployeeInfoButtn).setOnClickListener(this);
        }
    }


    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_employee, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){

            case R.id.action_out_employee:
                sPref = getSharedPreferences("enter", MODE_PRIVATE);
                SharedPreferences.Editor editor = sPref.edit();
                editor.putBoolean("IsIn", false);
                editor.commit();
                Intent newI = new Intent( this, Main2Activity.class);
                newI.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(newI);
               // finish();
                // your action goes here
                return true;

    //<!--- 14:39 22 june 16
    //set onOptin Selected
    //set listener to map_item in toolbar
    // this item will open map
    // but set status from cabinet opened to shared preference before
            case R.id.mapOpen:
                Intent mapIntent = new Intent(this, EmployeeMapActivity.class);
                mapIntent.putExtra(EmplConst4ShPrfOrIntent.OPEN_MAP_FROM , EmplConst4ShPrfOrIntent.FROM_CABINET);
                startActivity(mapIntent);
                return  true;
            case R.id.action_settings_employee:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.getHiredButton:
                if (checkEmail()) return;
                new EmployeeAsynTasks(this, myIdInBase, managerEmailStr,AsynTaskForEmployee.GET_Hired).execute();
                break;

            case R.id.SOSButton:
                //if (checkEmail()) return;
                //managerEmailStr = managerEmail.getText().toString();
                // ShowNotification ();
                new EmployeeAsynTasks(this, myIdInBase,AsynTaskForEmployee.SOS).execute();
                try {
                    call();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
        }

    }


    private boolean checkEmail (){
        managerEmailStr = managerEmail.getText().toString();
        if (managerEmailStr.matches("")){
            Toast.makeText(this, "Enter manager email!", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }


    private void call() throws InterruptedException {
        if (t == null){
            Thread t = new Thread(this);
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:123456"));
            startActivity(intent);
            h.postDelayed(t, 2000);
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:123456"));
            startActivity(intent);
            h.postDelayed(t, 2000);

        }



    }


    @Override
    public void run() {
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP)){
            try {
                call();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Store our shared preference
        SharedPreferences sp = getSharedPreferences(EmplConst4ShPrfOrIntent.EMPLOYEE_INFO, MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putBoolean("active", false);
        ed.commit();

    }

}




