package com.quebecfresh.androidapp.simplebudget.persist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static  com.quebecfresh.androidapp.simplebudget.model.ExpenseBudgetHistory.Contract.*;

import com.quebecfresh.androidapp.simplebudget.model.ExpenseBudget;
import com.quebecfresh.androidapp.simplebudget.model.ExpenseBudgetHistory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tong Huang on 2015-03-31, 10:24 AM.
 */
public class ExpenseBudgetHistoryPersist extends Persist {

//    public ExpenseBudgetHistoryPersist(Context context) {
//        super(context);
//    }

    public ExpenseBudgetHistory insert(ExpenseBudgetHistory expenseBudgetHistory, SQLiteDatabase database) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(_BUDGET_ID, expenseBudgetHistory.getBudgetID());
        contentValues.put(_NAME, expenseBudgetHistory.getName());
        contentValues.put(_BEGIN_TIME, expenseBudgetHistory.getBeginTime());
        contentValues.put(_END_TIME, expenseBudgetHistory.getEndTime());
        contentValues.put(_NOTE, expenseBudgetHistory.getNote());
        Long rowID = database.insert(_TABLE, null, contentValues);
        expenseBudgetHistory.setId(rowID);
        return expenseBudgetHistory;
    }

    public ExpenseBudgetHistory read(Long rowID,SQLiteDatabase database){
        String sql = "select * from "+ _TABLE + " where " + _ID  + " = " + rowID;
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()){
            ExpenseBudgetHistory expenseBudgetHistory = new ExpenseBudgetHistory();
            expenseBudgetHistory.setId(rowID);
            expenseBudgetHistory.setBudgetID(cursor.getLong(cursor.getColumnIndexOrThrow(_BUDGET_ID)));
            expenseBudgetHistory.setBeginTime(cursor.getLong(cursor.getColumnIndexOrThrow(_BEGIN_TIME)));
            expenseBudgetHistory.setEndTime(cursor.getLong(cursor.getColumnIndexOrThrow(_END_TIME)));
            expenseBudgetHistory.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
            expenseBudgetHistory.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
            cursor.close();
            return expenseBudgetHistory;
        }else{
            cursor.close();;
            return null;
        }
    }

    public List<ExpenseBudgetHistory> readAllByBudgetID(Long budgetID, SQLiteDatabase database){
        String sql = " select * from " + _TABLE + " where " + _BUDGET_ID + " = " + budgetID;
        Cursor cursor = database.rawQuery(sql,null);
        List<ExpenseBudgetHistory> expenseBudgetHistoryList = new ArrayList<ExpenseBudgetHistory>();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            ExpenseBudgetHistory expenseBudgetHistory = new ExpenseBudgetHistory();
            expenseBudgetHistory.setId(cursor.getLong(cursor.getColumnIndexOrThrow(_ID)));
            expenseBudgetHistory.setBudgetID(cursor.getLong(cursor.getColumnIndexOrThrow(_BUDGET_ID)));
            expenseBudgetHistory.setBeginTime(cursor.getLong(cursor.getColumnIndexOrThrow(_BEGIN_TIME)));
            expenseBudgetHistory.setEndTime(cursor.getLong(cursor.getColumnIndexOrThrow(_END_TIME)));
            expenseBudgetHistory.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
            expenseBudgetHistory.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
            expenseBudgetHistoryList.add(expenseBudgetHistory);
        }
        cursor.close();
        return expenseBudgetHistoryList;
    }

    public ExpenseBudgetHistory update(ExpenseBudgetHistory expenseBudgetHistory, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put(_BUDGET_ID, expenseBudgetHistory.getId());
        contentValues.put(_BEGIN_TIME, expenseBudgetHistory.getBeginTime());
        contentValues.put(_END_TIME, expenseBudgetHistory.getEndTime());
        contentValues.put(_NAME, expenseBudgetHistory.getName());
        contentValues.put(_NOTE, expenseBudgetHistory.getNote());
        database.update(_TABLE, contentValues, _ID + " = " + expenseBudgetHistory.getId(), null);
        return  expenseBudgetHistory;
    }

    public Boolean delete(Long rowID, SQLiteDatabase database){
        database.delete(_TABLE, " where " + _ID + " = " + rowID, null );
        return true;
    }


    /**
     * This static method is only used by DatabaseHelper to create database;
     * @param db
     * @return
     */

    public static Boolean create(SQLiteDatabase db) {
        db.execSQL(CREATE);
        return true;
    }

    /**
     * This static method is only used by DatabaseHelper to drop table when Upgrade database;
     * @param db
     * @return
     */
    public static Boolean drop(SQLiteDatabase db) {
        db.execSQL(DROP);
        return true;
    }
}
