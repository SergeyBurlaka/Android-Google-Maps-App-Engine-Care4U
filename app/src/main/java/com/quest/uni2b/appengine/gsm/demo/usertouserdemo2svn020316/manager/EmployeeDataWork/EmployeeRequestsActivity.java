package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.EmployeeDataWork;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.ManagerRequests.AsynTaskForManager;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.ManagerRequests.AsynTaskForManagerEnum;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.R;
import com.wdullaer.swipeactionadapter.SwipeActionAdapter;
import com.wdullaer.swipeactionadapter.SwipeDirection;

import java.util.ArrayList;

public class EmployeeRequestsActivity extends ListActivity implements
        SwipeActionAdapter.SwipeActionListener, View.OnClickListener
{
    protected SwipeActionAdapter mAdapter;
    ArrayList<EmployeeRequest> objects;
    private  ArrayAdapter<String> stringAdapter;
    private  ArrayList<String> listRequests, confirmRequest, deleteRequest;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_employee_requests);

        //<!--23.05.16
        //TODO_done 1>>
        //TODO_done 21:53 22.06.16: Add arrow return to the toolbar #later
        //make finish () for intent
        //#Urgently
       // setActionBar(toolbar);
      //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarEmployeeReq);
       // setSupportActionBar(toolbar);

       // setActionBar(toolbar);

       //ActionBar actionBar = getActionBar();
       // if(actionBar == null)
         //   actionBar = getActionBarSupport();
        //getActionBar().setHomeButtonEnabled(true);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        //-->
        AppCompatCallback callback = new AppCompatCallback() {
            @Override
            public void onSupportActionModeStarted(ActionMode actionMode) {
            }

            @Override
            public void onSupportActionModeFinished(ActionMode actionMode) {
            }

            @Nullable
            @Override
            public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback callback) {
                return null;
            }
        };

        AppCompatDelegate delegate = AppCompatDelegate.create(this, callback);

        delegate.onCreate(savedInstanceState);
        delegate.setContentView(R.layout.activity_employee_requests);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbarEmployeeRequestReq);

        delegate.setSupportActionBar(toolbar);


        delegate.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        delegate.getSupportActionBar().setDisplayShowHomeEnabled(true);

       // delegate.getSupportActionBar().setDisplayShowTitleEnabled(false);


        // getSupportActionBar().setLogo(R.drawable.babycare);
        // Display icon in the toolbar

        delegate.getSupportActionBar().setLogo(R.mipmap.swipe_toolbar_i);
        delegate.getSupportActionBar().setDisplayUseLogoEnabled(true);

        delegate.getSupportActionBar().setTitle("  Swipe to confirm");







       /*
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(SavedReportActivity.this);
            }
        });
        */


      /*
        FloatingActionButton fabButton = new FloatingActionButton.Builder(this)
                .withDrawable(getResources().getDrawable(R.drawable.ic_action_add))
                .withButtonColor(Color.WHITE)
                .withGravity(Gravity.BOTTOM | Gravity.RIGHT)
                .withMargins(0, 0, 16, 16)
                .create();
        fabButton.setOnClickListener(this);
            //- See more at: http://www.ahotbrew.com/android-floating-action-button/#sthash.DCU4NkBG.dpuf
        */



        //String[] content = new String[20];
        //for (int i=0;i<20;i++) content[i] = "Row "+(i+1);
        Intent intent = getIntent();

        /*
        String[] contentRequest = new String[objects.size()];
        int i =0;
        for (EmployeeRequest p : objects){
            contentRequest[i++] = p.getEmail();

        }*/

        listRequests = intent.getStringArrayListExtra("requests");
        confirmRequest = new ArrayList<>();
        deleteRequest = new ArrayList<>();

         stringAdapter = new ArrayAdapter<>(
                this,

                R.layout.row_bg,
                R.id.text,
                //new ArrayList<>(Arrays.asList(contentRequest))
                 listRequests
        );




        mAdapter = new SwipeActionAdapter(stringAdapter);
        mAdapter.setSwipeActionListener(this)
                .setDimBackgrounds(false)
                .setListView(getListView());
        setListAdapter(mAdapter);

        mAdapter.addBackground(SwipeDirection.DIRECTION_FAR_LEFT, R.layout.row_bg_left_far)
               .addBackground(SwipeDirection.DIRECTION_NORMAL_LEFT, R.layout.row_bg_left)
                .addBackground(SwipeDirection.DIRECTION_FAR_RIGHT, R.layout.row_bg_right_far)
             .addBackground(SwipeDirection.DIRECTION_NORMAL_RIGHT, R.layout.row_bg_right);








    }





    // 23.05.16
    //TODO_done: add onBackPress () method, add finish() to oBP m, #later

    @Override
    public void onBackPressed()
    {

      //  moveTaskToBack(true);
        finish();


    }
    //add finish() to oBP m
    //#Urgently



    //<----


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_main, menu);
        return (super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

        case android.R.id.home:

      //  NavUtils.navigateUpFromSameTask(this);

            finish();

        return true;
       // int id = item.getItemId();
      //  case R.id.action_settings:
         //   return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id){
     /*   Toast.makeText(
                this,
                "Clicked "+mAdapter.getItem(position),
                Toast.LENGTH_SHORT
        ).show();*/

     //  View view2 = mAdapter.getView(position,view,listView);
      //view2.findViewById(R.id.delete).setVisibility(View.GONE);

       // mAdapter.hasActions(position, );
    }

    @Override
    public boolean hasActions(int position, SwipeDirection direction){
        if(direction.isLeft()) return true;
        if(direction.isRight()) return true;
        return false;
    }

    @Override
    public boolean shouldDismiss(int position, SwipeDirection direction){
        return direction == SwipeDirection.DIRECTION_NORMAL_LEFT;
    }

    @Override
    public void onSwipe(int[] positionList, SwipeDirection[] directionList){
        for(int i=0;i<positionList.length;i++) {
            SwipeDirection direction = directionList[i];
            int position = positionList[i];
            String dir = "";

            switch (direction) {
                case DIRECTION_NORMAL_LEFT:
                  //  return;
                case DIRECTION_FAR_LEFT:


                    dir = "Far left";

                    confirmRequest.add(listRequests.get(position));
                    //mAdapter.getListView().getSelectedView().findViewById(R.id.delete).setVisibility(View.VISIBLE);
                   //View view2 = mAdapter.getView(position );
                   //view2.findViewById(R.id.delete).setVisibility(View.GONE);
                    StringBuilder sb = new StringBuilder();
                    for (String str : confirmRequest){
                        sb.append(str+"/"+"\n");

                    }
                  /*  Toast.makeText(
                            this, "You confirm:"+"\n"+sb,
                            Toast.LENGTH_SHORT
                    ).show();*/

                    // TODO_DONE 23.05.16
                    //make request to add employee to friend
                    //#Urgently
                    makeConfirm(listRequests.get(position));//add confirmed email for newAsynTask request to server

                    //<----




                    break;
               /* case DIRECTION_NORMAL_LEFT:
                    dir = "Left";
                    break;
                    */

                case DIRECTION_NORMAL_RIGHT:
                  //  return;
                case DIRECTION_FAR_RIGHT:




                    dir = "Far right";

                    deleteRequest.add(listRequests.get(position));

                    sb = new StringBuilder();
                    for (String str : deleteRequest){
                        sb.append(str+"/"+"\n");

                    }

                   /* Toast.makeText(
                            this, "You delete:"+"\n"+sb,
                            Toast.LENGTH_SHORT
                    ).show();*/


                    //<!---23.06.16
                    //TODO_DONE_01.07.16 make request to delete from list
                    //#Urgently

                        new AsynTaskForManager(this, getIdInBase (),listRequests.get(position), AsynTaskForManagerEnum.DELETE_REQUEST ).execute();
                    //<----
                    break;
             //   case DIRECTION_NORMAL_RIGHT:
               //     AlertDialog.Builder builder = new AlertDialog.Builder(this);
                 //   builder.setTitle("Test Dialog").setMessage("You swiped right").create().show();
                  //  dir = "right";
                   // break;
            }


           /*
            Toast.makeText(
                  this,
                   dir + " swipe Action triggered on " + mAdapter.getItem(position),
                   Toast.LENGTH_SHORT
           ).show();
            */


          //  mAdapter.notifyDataSetChanged();






            listRequests.remove(position);

            //23.05.16
            //TODO_aborted_by_me: make check if listReq empty? #later

            //if yes return to manager cabinet intent
            //make finish () for intent
            //#Urgently



            //<----


            stringAdapter = new ArrayAdapter<>(
                    this,
                    R.layout.row_bg,
                    R.id.text,
                    //new ArrayList<>(Arrays.asList(contentRequest))
                    listRequests
            );

            mAdapter = new SwipeActionAdapter(stringAdapter);
            mAdapter.setSwipeActionListener(this)
                    .setDimBackgrounds(false)
                    .setListView(getListView());
            setListAdapter(mAdapter);

            mAdapter.addBackground(SwipeDirection.DIRECTION_FAR_LEFT, R.layout.row_bg_left_far)
                    .addBackground(SwipeDirection.DIRECTION_NORMAL_LEFT, R.layout.row_bg_left)
                    .addBackground(SwipeDirection.DIRECTION_FAR_RIGHT, R.layout.row_bg_right_far)
                    .addBackground(SwipeDirection.DIRECTION_NORMAL_RIGHT, R.layout.row_bg_right);

            mAdapter.notifyDataSetChanged();


        }
    }

    @Override
    public void onClick(View v) {

        StringBuilder sb = new StringBuilder();
        for (String str : confirmRequest){
            sb.append(str+"/"+"\n");

        }
       /* Toast.makeText(
                this, "You confirm:"+"\n"+sb,
                Toast.LENGTH_SHORT
        ).show();*/




    }







    public void makeConfirm( String confirmRequest ) {

        //some workaround
        //i don't  want remake AsynTaskForManager confirm
        ArrayList<String> listConfirmEmployee = new ArrayList<>();
            listConfirmEmployee.add(confirmRequest);


            new AsynTaskForManager(this,  getIdInBase (), listConfirmEmployee, AsynTaskForManagerEnum.CONFIRM).execute();


        //Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }


   private long getIdInBase (){

       SharedPreferences shared = getSharedPreferences("info",MODE_PRIVATE);
       //Using getXXX- with XX is type date you wrote to file "name_file"
      String string_temp = shared.getString("manager id","");

       if ( string_temp =="") {
         //  Toast.makeText(this, "Unknown error", Toast.LENGTH_LONG).show();
           return Long.parseLong(null);
       }


       return  Long.parseLong(string_temp);
   }


}

