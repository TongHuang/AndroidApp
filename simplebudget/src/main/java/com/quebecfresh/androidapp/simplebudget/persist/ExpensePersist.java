package com.quebecfresh.androidapp.simplebudget.persist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.quebecfresh.androidapp.simplebudget.model.Expense;
import com.quebecfresh.androidapp.simplebudget.model.ExpenseBudget;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.quebecfresh.androidapp.simplebudget.model.Expense.Contract.*;

/**
 * Created by Tong Huang on 2015-03-16, 7:01 AM.
 */
public class ExpensePersist {
    private SQLiteDatabase db;
    private AccountPersist accountPersist;
    private ExpenseBudgetPersist expenseBudgetPersist;

    public ExpensePersist(SQLiteDatabase db) {
        this.db = db;
        this.accountPersist = new AccountPersist(db);
        this.expenseBudgetPersist = new ExpenseBudgetPersist(db);
    }

    public Expense insert(Expense expense) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(_NAME, expense.getName());
        contentValues.put(_NOTE, expense.getNote());
        contentValues.put(_BUDGET_ID, expense.getExpenseBudget().getId());
        contentValues.put(_AMOUNT, expense.getAmount().toString());
        contentValues.put(_SPENT_DATE, expense.getSpentDate());

        Long rowID = this.db.insert(_TABLE, null, contentValues);
        expense.setId(rowID);
        return expense;
    }

    public Expense read(Long rowID) {
        String sql = "select * from " + _TABLE + " where " + _ID + " = " + rowID;
        Cursor cursor = this.db.rawQuery(sql, null);
        cursor.moveToFirst();
        Long expenseBudgetID = cursor.getLong(cursor.getColumnIndexOrThrow(_BUDGET_ID));
        Expense expense = new Expense();
        expense.setId(rowID);
        expense.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
        expense.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
        expense.setExpenseBudget(expenseBudgetPersist.read(expenseBudgetID));
        expense.setAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_AMOUNT))));
        expense.setSpentDate(cursor.getLong(cursor.getColumnIndexOrThrow(_SPENT_DATE)));
        return expense;
    }

    public List<Expense> readAll() {
        String sql = " select * from " + _TABLE + " order by " + _SPENT_DATE + " desc";
        List<Expense> expenseList = new ArrayList<Expense>();
        Cursor cursor = this.db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Long expenseBudgetID = cursor.getLong(cursor.getColumnIndexOrThrow(_BUDGET_ID));
            Expense expense = new Expense();
            expense.setId(cursor.getLong(cursor.getColumnIndexOrThrow(_ID)));
            expense.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
            expense.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
            expense.setExpenseBudget(expenseBudgetPersist.read(expenseBudgetID));
            expense.setAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_AMOUNT))));
            expense.setSpentDate(cursor.getLong(cursor.getColumnIndexOrThrow(_SPENT_DATE)));
            expenseList.add(expense);
            cursor.moveToNext();
        }
        return expenseList;
    }

    public List<Expense> readAll(long begin, long end) {
        String sql = " select * from " + _TABLE + " where " + _SPENT_DATE + " >= " + begin
        + " and " + _SPENT_DATE + " <= " + end + " order by " + _SPENT_DATE + " desc";
        List<Expense> expenseList = new ArrayList<Expense>();
        Cursor cursor = this.db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Long expenseBudgetID = cursor.getLong(cursor.getColumnIndexOrThrow(_BUDGET_ID));
            Expense expense = new Expense();
            expense.setId(cursor.getLong(cursor.getColumnIndexOrThrow(_ID)));
            expense.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
            expense.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
            expense.setExpenseBudget(expenseBudgetPersist.read(expenseBudgetID));
            expense.setAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_AMOUNT))));
            expense.setSpentDate(cursor.getLong(cursor.getColumnIndexOrThrow(_SPENT_DATE)));
            expenseList.add(expense);
            cursor.moveToNext();
        }
        return expenseList;
    }

    public BigDecimal readTotalAmount(long begin, long end){
        String sql = " select sum(" + _AMOUNT + ") as total from " + _TABLE + " where " + _SPENT_DATE + " >= " + begin
                + " and " + _SPENT_DATE + " <= " + end ;
        List<Expense> expenseList = new ArrayList<Expense>();
        Cursor cursor = this.db.rawQuery(sql, null);
        cursor.moveToFirst();
        String totalStr = cursor.getString(cursor.getColumnIndexOrThrow("total"));
        if(totalStr != null){
            return new BigDecimal(totalStr);
        }
        return new BigDecimal("0");
    }

    public Expense update(Expense expense) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(_NAME, expense.getName());
        contentValues.put(_NOTE, expense.getNote());
        contentValues.put(_BUDGET_ID, expense.getExpenseBudget().getId());
        contentValues.put(_AMOUNT, expense.getAmount().toString());
        contentValues.put(_SPENT_DATE, expense.getSpentDate());
        this.db.update(_TABLE, contentValues, _ID + " = " + expense.getId(), null);
        return expense;
    }

    public Boolean delete(Long rowID) {
        this.db.delete(_TABLE, _ID + " = " + rowID, null);
        return true;
    }

    public Boolean create() {
        this.db.execSQL(CREATE);
        return true;
    }

    public Boolean drop() {
        this.db.execSQL(DROP);
        return Boolean.TRUE;
    }

}
