package com.quebecfresh.androidapp.simplebudget.persist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Tong Huang on 2015-03-28, 11:37 PM.
 */
public abstract class Persist {
    private DatabaseHelper mDBH;

    public Persist() {

    }

    public Persist(Context context) {
        mDBH = new DatabaseHelper(context);
    }

    public SQLiteDatabase getWritableDatabase() {
        return mDBH.getWritableDatabase();
    }

    public SQLiteDatabase getReadableDatabase() {
        return mDBH.getReadableDatabase();
    }

}
