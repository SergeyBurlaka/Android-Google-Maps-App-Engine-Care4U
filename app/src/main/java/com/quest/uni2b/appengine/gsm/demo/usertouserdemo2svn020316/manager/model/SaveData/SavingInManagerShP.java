package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.model.SaveData;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Operator on 01.08.2016.
 */
public abstract class SavingInManagerShP {

    Context context;

    public SavingInManagerShP(Context context) {
        this.context = context;
    }
    //

    abstract SharedPreferences.Editor getShPEditor ();
    abstract SharedPreferences getShP ();
    abstract void commitData();

    abstract void readData();

    abstract void commitStatus (int status);
    abstract void readStatus ();


}
