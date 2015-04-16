package com.quebecfresh.androidapp.simplebudget.persist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.quebecfresh.androidapp.simplebudget.model.Expense;
import com.quebecfresh.androidapp.simplebudget.model.ExpenseBudget;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static com.quebecfresh.androidapp.simplebudget.model.Expense.Contract.*;

/**
 * Created by Tong Huang on 2015-03-16, 7:01 AM.
 */
public class ExpensePersist extends  Persist{


    public Expense insert(Expense expense, SQLiteDatabase database) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(_NAME, expense.getName());
        contentValues.put(_NOTE, expense.getNote());
        contentValues.put(_BUDGET_ID, expense.getExpenseBudget().getId());
        contentValues.put(_AMOUNT, expense.getAmount().toString());
        contentValues.put(_SPENT_DATE, expense.getSpentDate());
        Long rowID = database.insert(_TABLE, null, contentValues);
        expense.setId(rowID);
        return expense;
    }

    public Expense read(Long rowID, SQLiteDatabase database) {
        String sql = "select * from " + _TABLE + " where " + _ID + " = " + rowID;
        Cursor cursor =database.rawQuery(sql, null);
        cursor.moveToFirst();
        ExpenseBudgetPersist expenseBudgetPersist = new ExpenseBudgetPersist();
        Long expenseBudgetID = cursor.getLong(cursor.getColumnIndexOrThrow(_BUDGET_ID));
        Expense expense = new Expense();
        expense.setId(rowID);
        expense.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
        expense.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
        expense.setExpenseBudget(expenseBudgetPersist.read(expenseBudgetID, database));
        expense.setAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_AMOUNT))));
        expense.setSpentDate(cursor.getLong(cursor.getColumnIndexOrThrow(_SPENT_DATE)));
        cursor.close();
        return expense;
    }

    public List<Expense> readAll(SQLiteDatabase database) {
        String sql = " select * from " + _TABLE + " order by " + _SPENT_DATE + " desc";
        List<Expense> expenseList = new ArrayList<Expense>();
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        ExpenseBudgetPersist expenseBudgetPersist = new ExpenseBudgetPersist();
        while (!cursor.isAfterLast()) {
            Long expenseBudgetID = cursor.getLong(cursor.getColumnIndexOrThrow(_BUDGET_ID));
            Expense expense = new Expense();
            expense.setId(cursor.getLong(cursor.getColumnIndexOrThrow(_ID)));
            expense.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
            expense.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
            expense.setExpenseBudget(expenseBudgetPersist.read(expenseBudgetID, database));
            expense.setAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_AMOUNT))));
            expense.setSpentDate(cursor.getLong(cursor.getColumnIndexOrThrow(_SPENT_DATE)));
            expenseList.add(expense);
            cursor.moveToNext();
        }
        cursor.close();
        return expenseList;
    }

    public List<Expense> readAll(long begin, long end, SQLiteDatabase database) {
        String sql = " select * from " + _TABLE + " where " + _SPENT_DATE + " >= " + begin
                + " and " + _SPENT_DATE + " <= " + end + " order by " + _SPENT_DATE + " desc";
        List<Expense> expenseList = new ArrayList<Expense>();

        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Long expenseBudgetID = cursor.getLong(cursor.getColumnIndexOrThrow(_BUDGET_ID));
            Expense expense = new Expense();
            expense.setId(cursor.getLong(cursor.getColumnIndexOrThrow(_ID)));
            expense.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
            expense.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
            ExpenseBudgetPersist expenseBudgetPersist = new ExpenseBudgetPersist();
            expense.setExpenseBudget(expenseBudgetPersist.read(expenseBudgetID, database));
            expense.setAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_AMOUNT))));
            expense.setSpentDate(cursor.getLong(cursor.getColumnIndexOrThrow(_SPENT_DATE)));
            expenseList.add(expense);
            cursor.moveToNext();
        }
        cursor.close();
        return expenseList;
    }

    public List<Expense> readAll(long begin, long end, ExpenseBudget expenseBudget, SQLiteDatabase database) {
        String sql = " select * from " + _TABLE + " where " + _SPENT_DATE + " >= " + begin
                + " and " + _SPENT_DATE + " <= " + end + " and " + _BUDGET_ID +  " = " + expenseBudget.getId()
        + " order by " + _SPENT_DATE + " desc";
        List<Expense> expenseList = new ArrayList<Expense>();
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Expense expense = new Expense();
            expense.setId(cursor.getLong(cursor.getColumnIndexOrThrow(_ID)));
            expense.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
            expense.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
            ExpenseBudgetPersist expenseBudgetPersist = new ExpenseBudgetPersist();
            expense.setExpenseBudget(expenseBudget);
            expense.setAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_AMOUNT))));
            expense.setSpentDate(cursor.getLong(cursor.getColumnIndexOrThrow(_SPENT_DATE)));
            expenseList.add(expense);
            cursor.moveToNext();
        }
        cursor.close();
        return expenseList;
    }

    public BigDecimal readTotalAmount(long begin, long end, SQLiteDatabase database) {
        String sql = " select sum(" + _AMOUNT + ") as total from " + _TABLE + " where " + _SPENT_DATE + " >= " + begin
                + " and " + _SPENT_DATE + " <= " + end;
        List<Expense> expenseList = new ArrayList<Expense>();
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        BigDecimal total;
        Double totalDouble = cursor.getDouble(cursor.getColumnIndexOrThrow("total"));
        total = new BigDecimal(totalDouble);
        cursor.close();
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    public Expense update(Expense expense, SQLiteDatabase database) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(_NAME, expense.getName());
        contentValues.put(_NOTE, expense.getNote());
        contentValues.put(_BUDGET_ID, expense.getExpenseBudget().getId());
        contentValues.put(_AMOUNT, expense.getAmount().toString());
        contentValues.put(_SPENT_DATE, expense.getSpentDate());
        database.update(_TABLE, contentValues, _ID + " = " + expense.getId(), null);
        return expense;
    }

    public Boolean delete(Long rowID, SQLiteDatabase database) {
        database.delete(_TABLE, _ID + " = " + rowID, null);
        return true;
    }

    /**
     * This static method is only used by DatabaseHelper to create database;
     * @param db
     * @return
     */
    public  static Boolean create(SQLiteDatabase db) {
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
        return Boolean.TRUE;
    }

}
