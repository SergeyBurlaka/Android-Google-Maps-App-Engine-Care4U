package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.EmployeeListView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.GCMConstants.MessageConstant;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.ManagerCabinetListActivity;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.EmployeeDataWork.EmployeeOfM;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.ManagerMap.UserMapsActivity;
import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.R;

import java.util.ArrayList;

/**
 * Created by Operator on 17.05.2016.
 */
public class DataManager extends RecyclerView.Adapter<DataManager.RecyclerViewHolder> {

    public static ArrayList<EmployeeOfM> users;
    //private final OnItemClickListener listener;

    public ManagerCabinetListActivity getContext() {
        return context;
    }

    private ManagerCabinetListActivity context;

    private final OnItemClickListener listener;

    public long getMyIdInBase() {
        return myIdInBase;
    }

    private long myIdInBase;



    public interface OnItemClickListener {
        void onItemClick(EmployeeOfM item);
    }


    public DataManager(ArrayList<EmployeeOfM> users, OnItemClickListener listener, ManagerCabinetListActivity context , long myIdInBase ){
        this.users = users;
        this.listener = listener;
        this.context = context;
        this.myIdInBase = myIdInBase;
    }




    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView /*userName,*/ userEmail;
        View mCircle;
       // public CheckBox chkSelected;

        public ImageView imgViewRemoveIcon, imgViewMapIcon, imgEye;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            //userName = (TextView) itemView.findViewById(R.id.contact_user_name);
            userEmail = (TextView) itemView.findViewById(R.id.contact_user_email);
            mCircle = itemView.findViewById(R.id.contact_circle);
           // chkSelected = (CheckBox)itemView.findViewById(R.id.checkBox);
            imgViewRemoveIcon = (ImageView) itemView.findViewById(R.id.remove_icon);


            imgEye = (ImageView) itemView.findViewById(R.id.eye);



           // imgViewMapIcon = (ImageView) itemView.findViewById(R.id.actionMapLogo);

        }

        public void bind(final EmployeeOfM item, final OnItemClickListener listener,final DataManager dataManager) {

            /*imgViewMapIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                   // listener.onItemClick(item);

                   // Toast.makeText(v.getContext(), "Map item was clicked!"+item.getUserEmail(), Toast.LENGTH_LONG).show();


                    //<---TO_DO_LIST
                    //open map                                                                    {+}
                    //set data email of selected employee to static workaround class Sel Empl    {+}
                    // //#URGENTLY// //#URGENTLY

                    // In the onCreate, after the setContentView method


                   // user = DataManager.users.get(position);
                   // SelectedEmployeeWorkaround.SelectedEmployeeEmail = item.getUserEmail();


                    Intent mapIntent = new Intent( dataManager.getContext(), UserMapsActivity.class);
                    mapIntent.putExtra("mamagerId",  dataManager.getMyIdInBase());
                    //intent.putExtra("email", user.get("email").toString());

                    mapIntent.putExtra(MessageConstant.OPEN_USER_MAP_FROM,MessageConstant.FROM_CABINET);

                    mapIntent.putExtra(MessageConstant.EMPLOYEE_LOC_EMAIL, item.getUserEmail());


                    dataManager.getContext().startActivity(mapIntent);


                }
            });*/

            mCircle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mapIntent = new Intent( dataManager.getContext(), UserMapsActivity.class);
                    mapIntent.putExtra("mamagerId",  dataManager.getMyIdInBase());
                    //intent.putExtra("email", user.get("email").toString());
                    mapIntent.putExtra(MessageConstant.OPEN_USER_MAP_FROM,MessageConstant.FROM_CABINET);
                    mapIntent.putExtra(MessageConstant.EMPLOYEE_LOC_EMAIL, item.getUserEmail());
                    dataManager.getContext().startActivity(mapIntent);
                }
            });

            imgEye.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mapIntent = new Intent( dataManager.getContext(), UserMapsActivity.class);
                    mapIntent.putExtra("mamagerId",  dataManager.getMyIdInBase());
                    //intent.putExtra("email", user.get("email").toString());
                    mapIntent.putExtra(MessageConstant.OPEN_USER_MAP_FROM,MessageConstant.FROM_CABINET);
                    mapIntent.putExtra(MessageConstant.EMPLOYEE_LOC_EMAIL, item.getUserEmail());
                    dataManager.getContext().startActivity(mapIntent);
                }
            });


            userEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // listener.onItemClick(item);
                    // Toast.makeText(v.getContext(), "Map item was clicked!"+item.getUserEmail(), Toast.LENGTH_LONG).show();

                    //<---TO_DO_LIST
                    //open map                                                                    {+}
                    //set data email of selected employee to static workaround class Sel Empl    {+}
                    // //#URGENTLY// //#URGENTLY
                    // In the onCreate, after the setContentView method
                    // user = DataManager.users.get(position);
                    // SelectedEmployeeWorkaround.SelectedEmployeeEmail = item.getUserEmail();
                    Intent mapIntent = new Intent( dataManager.getContext(), UserMapsActivity.class);
                    mapIntent.putExtra("mamagerId",  dataManager.getMyIdInBase());
                    //intent.putExtra("email", user.get("email").toString());
                    mapIntent.putExtra(MessageConstant.OPEN_USER_MAP_FROM,MessageConstant.FROM_CABINET);
                    mapIntent.putExtra(MessageConstant.EMPLOYEE_LOC_EMAIL, item.getUserEmail());
                    dataManager.getContext().startActivity(mapIntent);
                }
            });








            // name.setText(item.name);
            //Picasso.with(itemView.getContext()).load(item.imageUrl).into(image);
            imgViewRemoveIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   listener.onItemClick(item);

                  //  Toast.makeText(v.getContext(), "Remove item was clicked!"+item.getUserEmail(), Toast.LENGTH_LONG).show();




                }
            });


        }

    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_item, viewGroup, false);

        return new RecyclerViewHolder(v);
    }




    @Override
    public void onBindViewHolder(RecyclerViewHolder viewHolder, int position) {


        GradientDrawable bgShape = (GradientDrawable) viewHolder.mCircle.getBackground();

        // get the single element from the main array
        final EmployeeOfM user = users.get(position);



        //<!---13:40 17june16
        //TODO_d+: 1-----------
        //TODO_d+from30min: check spying status - and set status color in background of eye image view item

       switch (user.getStatusSpy()){
           case MessageConstant.EXIT_CIRCLE:


               // Set the color of the shape

               bgShape.setColor(Color.RED);

               break;

           case MessageConstant.ENTER_CIRCLE:

               bgShape.setColor(Color.BLUE);

               break;

           case MessageConstant.NO_SPY:

              // bgShape.setColor(Color.BLUE);
               bgShape.setColor(Color.parseColor("#99000000"));

               break;

       }

        //-->

        // Set the values
        //viewHolder.userName.setText(user.getUserName());
        viewHolder.userEmail.setText(user.getUserEmail());
        // Set the color of the shape

        viewHolder.bind(users.get(position), listener, this);


       // final int pos = position;



      /*
        // get the single element from the main array
        final EmployeeOfM user = users.get(position);
        // Set the values
        //viewHolder.userName.setText(user.getUserName());
        viewHolder.userEmail.setText(user.getUserEmail());


        viewHolder.imgViewRemoveIcon.setOnClickListener(new View.OnClickListener() {
                                                            public void onClick(View v) {

                                                                    Toast.makeText(this, "The Item Clicked is: "+v.getPosition(), Toast.LENGTH_SHORT).show();

                                                        }


        );
        // Set the color of the shape

        //GradientDrawable bgShape = (GradientDrawable) viewHolder.mCircle.getBackground();
       // bgShape.setColor( Color.parseColor("#D1C4E9") );
        //bgShape.setB


        //in some cases, it will prevent unwanted situations


       /*
        viewHolder.chkSelected.setChecked(users.get(position).isSelected());

        viewHolder.chkSelected.setTag(users.get(position));


        viewHolder.chkSelected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                EmployeeOfM contact = (EmployeeOfM) cb.getTag();

                contact.setSelected(cb.isChecked());
                users.get(pos).setSelected(cb.isChecked());

                Toast.makeText(
                        v.getContext(),
                        "Clicked on Checkbox: " + cb.getText() + " is "
                                + cb.isChecked(), Toast.LENGTH_LONG).show();
            }
        }


        );*/









    }
    @Override
    public int getItemCount() {
        return users.size();
    }
}
