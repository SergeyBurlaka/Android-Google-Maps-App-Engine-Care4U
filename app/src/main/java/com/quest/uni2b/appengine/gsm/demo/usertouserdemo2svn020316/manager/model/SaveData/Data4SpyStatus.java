package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.model.SaveData;

import android.content.Context;
import android.content.SharedPreferences;

import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.gcm.GCMConstants.MessageConstant;

/**
 * Created by Operator on 01.08.2016.
 */
public class Data4SpyStatus extends SavingInManagerShP {


    private final String employeeEmail;
    private SharedPreferences.Editor editor;

    public Data4SpyStatus(Context context, String employeeEmail) {
        super(context);
        this.employeeEmail = employeeEmail;

    }

    @Override
    SharedPreferences.Editor getShPEditor() {
        return context.getSharedPreferences(MessageConstant.STATUS_SPY_VIEW , context.MODE_PRIVATE).edit();
    }

    @Override
    SharedPreferences getShP() {
        return null;
    }



    @Override
    void commitData() {


    }

    @Override
    void readData() {

    }


    @Override
    void commitStatus(int status ) {
        editor = getShPEditor();
        editor.putInt(employeeEmail,  status  );
        //end  commit
        editor.commit();
    }

    @Override
    void readStatus() {

    }
}
