package com.quebecfresh.androidapp.simplebudget.persist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.quebecfresh.androidapp.simplebudget.model.Expense;


/**
 * Created by Tong Huang on 2015-02-20, 3:29 PM.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 11;
    public static final String DATABASE_NAME = "simplebudget";

    private  Context mContext;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        AccountPersist.create(db);
        AccountPersist.initialize(db);
        IncomeBudgetPersist.create(db);
        IncomeBudgetPersist.initialize(db);
        ExpenseBudgetPersist.create(db);
        ExpenseBudgetPersist.initialize(db);
        IncomePersist.create(db);
        ExpensePersist.create(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        AccountPersist.drop(db);
        IncomeBudgetPersist.drop(db);
        ExpenseBudgetPersist.drop(db);
        IncomePersist.drop(db);
        ExpensePersist.drop(db);
        onCreate(db);
    }
}
