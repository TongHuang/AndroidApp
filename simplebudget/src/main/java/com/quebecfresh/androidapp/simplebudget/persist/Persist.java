package com.quebecfresh.androidapp.simplebudget.persist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Tong Huang on 2015-03-28, 11:37 PM.
 */
public class Persist {
    protected DatabaseHelper mDBH;

    protected SQLiteDatabase mDB;

    public Persist(Context context){
        mDBH = new DatabaseHelper(context);

    }

    void setDB(SQLiteDatabase DB){
        this.mDB = DB;
    }
}
