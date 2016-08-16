package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.GCMConstants.MessageConstant;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.Main2Activity;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.EmployeeDataWork.EmployeeOfM;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.EmployeeDataWork.EmployeeRequest;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.EmployeeDataWork.EmployeeRequestsActivity;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.ManagerRequests.AsynTaskForManager;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.ManagerRequests.AsynTaskForManagerEnum;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.EmployeeListView.DataManager;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.EmployeeListView.RecyclerClickListener;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.R;
import com.readystatesoftware.viewbadger.BadgeView;

import java.util.ArrayList;
import java.util.Arrays;

public class ManagerCabinetListActivity extends AppCompatActivity {

    private GoogleCloudMessaging gcm;
    private static final String SENDER_ID = "414291776712";

    private String string_temp;
    //have array list of users
    //public static ArrayList<String> emails;

    public static ArrayList<EmployeeOfM> users;

    //refresh after confirm
    public static boolean flagNewRequest = false;


    public static String currentUserName;

    public static RecyclerView rv;
    public static DataManager dataManager;

    private long myIdInBase;

    //for open map
    public EmployeeOfM user;

    private SharedPreferences sPref;


    private ArrayList<EmployeeRequest> employeeRequests = new ArrayList<EmployeeRequest>();


    public String[] getItemAlertD() {
        return itemAlertD;
    }

    public void setItemAlertD(String[] itemAlertD) {
        this.itemAlertD = itemAlertD;
    }

    private String[] itemAlertD = {"NONE","NONE"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Store our shared preference
        SharedPreferences sp = getSharedPreferences(MessageConstant.MANAGER_INFO, MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putBoolean("active", true);
        ed.commit();


        //cbSelect = (CheckBox) v.findViewById(R.id.cbSelect);


        setContentView(R.layout.activity_manager_cabinet_list);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.managerToolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);



        // Display icon in the toolbar

       // getSupportActionBar().setLogo(R.drawable.babycare);
        // Display icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.care_i);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

       getSupportActionBar().setTitle("Care4U");



        SharedPreferences shared = getSharedPreferences("info",MODE_PRIVATE);
        //Using getXXX- with XX is type date you wrote to file "name_file"
        string_temp = shared.getString("manager id","");

        if ( string_temp =="") {
          //  Toast.makeText(this, "You have not manager acc!", Toast.LENGTH_LONG).show();
        }else {
            //convert id to Long
            myIdInBase = Long.parseLong(string_temp);

            //get my employees from server
            if (users==null||flagNewRequest == true) {


                new AsynTaskForManager(this, myIdInBase, AsynTaskForManagerEnum.GET_MANAGER).execute();
                flagNewRequest = false;


            }else{

                createUsersRecycleViewList ();
            }

           // dataManager = new DataManager();

            //get request of employee from server
          new AsynTaskForManager(this,myIdInBase, AsynTaskForManagerEnum.GET_REQUEST ).execute();


        }


       /*
        // Floating Action Button
        android.support.design.widget.FloatingActionButton fab = (android.support.design.widget.FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linkNewUser();
            }
        });

        */
        //create alert dialog for delete contact

    }


    public void createAlertDialog (final String [] items){

        Dialog dialog;
        // final String[] items = {" PHP", " JAVA", " JSON", " C#", " Objective-C"};
        final ArrayList <Integer> itemsSelected = new ArrayList();


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Languages you know : ");
        builder.setMultiChoiceItems(items, null,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedItemId,
                                        boolean isSelected) {
                        if (isSelected) {
                            itemsSelected.add(selectedItemId);
                        } else if (itemsSelected.contains(selectedItemId)) {
                            itemsSelected.remove(Integer.valueOf(selectedItemId));
                        }
                    }
                })
                .setPositiveButton("Done!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Your logic when OK button is clicked

                        deleteFromFriend (itemsSelected, items);


                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        dialog = builder.create();
        dialog.show();


    }

        //<!--- 10june16_15:40
        //TODO_d+??: make method for deleting some employees. They are selected via checkbox in alert dialog
        // int selectedItemId  - is position in recycler view
        //ArrayList itemsSelected - list of number position of selected employees
   public void deleteFromFriend (ArrayList <Integer> itemsSelected, String [] items ){

       //<!--- 10june16_15:40
       //TODO_d+??: make new asyn task  delete many employees from a friend
       ArrayList <String> employeeEmailsToDelete = new ArrayList<>();

       for ( int selectedItem : itemsSelected ){

          // Toast.makeText(this,  items [selectedItem], Toast.LENGTH_LONG).show();
                   // Toast.makeText(employeeRequestsActivity, mssg, Toast.LENGTH_LONG).show();

          employeeEmailsToDelete.add( items [selectedItem]);

       }


      new AsynTaskForManager(this, myIdInBase, employeeEmailsToDelete, AsynTaskForManagerEnum.DELETE_LIST_E_FROM_FRIEND  ).execute();
        //<--

   }



    @Override
    public void onResume() {
        super.onResume();

        if (flagNewRequest == true) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);

        }
    }


    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.myEmployeeRequest:

                // your action goes here
                String[] contentRequest = new String[employeeRequests .size()];
                int i =0;
                for (EmployeeRequest p : employeeRequests ){
                    contentRequest[i++] = p.getEmail();

                }

                Intent requests = new Intent (this, EmployeeRequestsActivity.class);
                requests.putStringArrayListExtra("requests", new ArrayList<>(Arrays.asList(contentRequest)));
                startActivity(requests);

                return true;

            case R.id.action_out:


                flagNewRequest = true;
                //users = null;
                // your action goes here
                sPref = getSharedPreferences("enter", MODE_PRIVATE);
                SharedPreferences.Editor editor = sPref.edit();
                editor.putBoolean("IsIn", false);

                editor.commit();

                Intent newI = new Intent( this, Main2Activity.class);
                newI.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(newI);


                return true;
            case R.id.action_delete_contact:
                // your action goes here
                createAlertDialog (itemAlertD);


                return true;
          /*  case R.id.action_rewrite_new_device_m:



                new AsynTaskForManager( this, myIdInBase, AsynTaskForManagerEnum.SET_NEW_REG_ID).execute();

                return true;*/

            case R.id.myRefresh:

                flagNewRequest = true;
                reloadActivity();
                return true;

            /*case R.id.action_stop_gps_service :

                stopService(new Intent(ManagerCabinetListActivity.this,GPSService.class));



                return true;
                */

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();

        //Вызывается, если открываем другое активити. При этом это активити остается в памяти в состоянии стоп



    }

    @Override
    public void onBackPressed()
    {
        finish();

    }





    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Store our shared preference
        SharedPreferences sp = getSharedPreferences(MessageConstant.MANAGER_INFO, MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putBoolean("active", false);
        ed.commit();


    }


    public void createUsersRecycleViewList (){

        //set data here MY_ENTER
        dataManager = new DataManager(users, new RecyclerClickListener(this, myIdInBase ),this, myIdInBase);


        rv = (RecyclerView) findViewById(R.id.rv); // layout reference
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        //rv.setHasFixedSize(true); // to improve performance
        rv.setAdapter(dataManager); // the data manager is assigner to the RV



      /*
        rv.addOnItemTouchListener( // and the click is handled
                new RecyclerClickListener(this, new RecyclerClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        CheckBox cbSelect = (CheckBox)view.findViewById(R.id.checkBox);

                        //<---TO_DO_LIST
                        //open map                                                                    {+}
                        //set data email of selected employee to static workaround class Sel Empl    {+}
                        // //#URGENTLY// //#URGENTLY

                        // In the onCreate, after the setContentView method


                        user = DataManager.users.get(position);
                        SelectedEmployeeWorkaround.SelectedEmployeeEmail = user.getUserEmail();


                        Intent mapIntent = new Intent( ManagerCabinetListActivity.this, UserMapsActivity.class);
                        mapIntent.putExtra("mamagerId",  myIdInBase);
                        //intent.putExtra("email", user.get("email").toString());
                        startActivity(mapIntent);



                        //<---



                        //#matrix reload //#matrix reload //#matrix reload
                        //Open user details activity

                        /*
                        Intent intent = new Intent( ManagerCabinetListActivity.this, UserDetailsActivity.class);
                        intent.putExtra(UserDetailsActivity.ID, position);
                        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                // the context of the activity
                                ManagerCabinetListActivity.this,
                                 //#matrix reload //#matrix reload //#matrix reload


                                // For each shared element, add to this method a new Pair item,
                                // which contains the reference of the view we are transitioning *from*,
                                // and the value of the transitionName attribute
                                /*new Pair<View, String>(view.findViewById(R.id.contact_circle),
                                        getString(R.string.transition_name_circle)),*/
                                /*new Pair<View, String>(view.findViewById(R.id.contact_user_name),
                                        getString(R.string.transition_name_name)),*/



                        //#matrix reload //#matrix reload //#matrix reload
                        //Open user details activity
                        /*
                                new Pair<View, String>(view.findViewById(R.id.contact_user_email),
                                        getString(R.string.transition_name_phone))
                        );
                        ActivityCompat.startActivity(ManagerCabinetListActivity.this, intent, options.toBundle());
                        */
                /*

                    }
                })

        );
        */


                        //#matrix reload //#matrix reload //#matrix reload

    }




    //for getting requests list from asynhrone task GET_Request
    public void setProducts(ArrayList<EmployeeRequest> employeeRequests) {
        this.employeeRequests = employeeRequests;
    }

    public ArrayList<EmployeeRequest> getProducts() {
        return employeeRequests;
    }


    public void showBadge (){

        View target = findViewById(R.id.myEmployeeRequest);
        BadgeView badge = new BadgeView(this, target);

        int number = this.getProducts().size();



        badge.setText( String.valueOf(number));
        badge.show();
    }


    public void reloadActivity (){

        ManagerCabinetListActivity.flagNewRequest = true;
        Intent intent = getIntent();
        finish();
        startActivity(intent);


    }

}