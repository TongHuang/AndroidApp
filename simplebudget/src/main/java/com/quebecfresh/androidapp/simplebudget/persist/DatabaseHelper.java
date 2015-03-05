package com.quebecfresh.androidapp.simplebudget.persist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.quebecfresh.androidapp.simplebudget.model.IncomeCategory;


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
        IncomeCategoryPersist incomeCategoryPersist = new IncomeCategoryPersist(db);
        incomeCategoryPersist.create();
        incomeCategoryPersist.initialize();
        ExpenseCategoryPersist expenseCategoryPersist = new ExpenseCategoryPersist(db);
        expenseCategoryPersist.create();
        expenseCategoryPersist.initialize();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        AccountPersist accountPersist = new AccountPersist(db);
        accountPersist.drop();
        IncomeCategoryPersist incomeCategoryPersist = new IncomeCategoryPersist(db);
        incomeCategoryPersist.drop();
        ExpenseCategoryPersist expenseCategoryPersist = new ExpenseCategoryPersist(db);
        expenseCategoryPersist.drop();
        onCreate(db);
    }
}
