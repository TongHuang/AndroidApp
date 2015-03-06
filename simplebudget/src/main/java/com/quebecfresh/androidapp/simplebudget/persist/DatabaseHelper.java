package com.quebecfresh.androidapp.simplebudget.persist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Tong Huang on 2015-02-20, 3:29 PM.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "simplebudget";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        AccountPersist accountPersist = new AccountPersist(db);
        accountPersist.create();
        accountPersist.initialize();
        IncomeBudgetPersist incomeCategoryPersist = new IncomeBudgetPersist(db);
        incomeCategoryPersist.create();
        incomeCategoryPersist.initialize();
        ExpenseBudgetPersist expenseCategoryPersist = new ExpenseBudgetPersist(db);
        expenseCategoryPersist.create();
        expenseCategoryPersist.initialize();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        AccountPersist accountPersist = new AccountPersist(db);
        accountPersist.drop();
        IncomeBudgetPersist incomeCategoryPersist = new IncomeBudgetPersist(db);
        incomeCategoryPersist.drop();
        ExpenseBudgetPersist expenseCategoryPersist = new ExpenseBudgetPersist(db);
        expenseCategoryPersist.drop();
        onCreate(db);
    }
}
