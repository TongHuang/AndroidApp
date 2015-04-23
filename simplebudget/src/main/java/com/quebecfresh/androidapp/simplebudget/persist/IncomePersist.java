package com.quebecfresh.androidapp.simplebudget.persist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.quebecfresh.androidapp.simplebudget.model.Account;
import com.quebecfresh.androidapp.simplebudget.model.Income;
import com.quebecfresh.androidapp.simplebudget.model.IncomeBudget;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static com.quebecfresh.androidapp.simplebudget.model.Income.Contract.*;

/**
 * Created by Tong Huang on 2015-03-15, 11:22 PM.
 */
public class IncomePersist extends Persist {

//    public IncomePersist(Context context) {
//        super(context);
//        this.accountPersist = new AccountPersist(context);
//        this.incomeBudgetPersist = new IncomeBudgetPersist(context);
//    }

    public Income insert(Income income, SQLiteDatabase database) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(_NAME, income.getName());
        contentValues.put(_NOTE, income.getNote());
        contentValues.put(_BUDGET_ID, income.getIncomeBudget().getId());
        contentValues.put(_AMOUNT, income.getAmount().toString());
        contentValues.put(_RECEIVED_DATE, income.getDate());
        contentValues.put(_ACCOUNT_ID, income.getAccount().getId());
        contentValues.put(_CONFIRMED, income.getConfirmed() == Boolean.TRUE ? 1 : 0);
        Long rowID = database.insert(_TABLE, null, contentValues);
        income.setId(rowID);
        return income;
    }

    public Income read(Long rowID, SQLiteDatabase database) {
        String sql = "select * from " + _TABLE + " where " + _ID + " = " + rowID;
        Cursor cursor = database.rawQuery(sql, null);
        IncomeBudgetPersist incomeBudgetPersist = new IncomeBudgetPersist();
        AccountPersist accountPersist = new AccountPersist();
        cursor.moveToFirst();
        Long incomeBudgetID = cursor.getLong(cursor.getColumnIndexOrThrow(_BUDGET_ID));
        IncomeBudget incomeBudget = incomeBudgetPersist.read(incomeBudgetID, database);
        Long accountID = cursor.getLong(cursor.getColumnIndexOrThrow(_ACCOUNT_ID));
        Account account = accountPersist.read(accountID, database);
        Income income = new Income();
        income.setId(rowID);
        income.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
        income.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
        income.setIncomeBudget(incomeBudget);
        income.setAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_AMOUNT))));
        income.setAccount(account);
        income.setDate(cursor.getLong(cursor.getColumnIndexOrThrow(_RECEIVED_DATE)));
        income.setConfirmed(cursor.getLong(cursor.getColumnIndexOrThrow(_CONFIRMED)) == 1 ? true : false);
       cursor.close();
        return income;
    }

    public List<Income> readAll(SQLiteDatabase database) {
        String sql = "select * from " + _TABLE + " order by " + _RECEIVED_DATE + " desc";
        List<Income> incomeList = new ArrayList<Income>();
        Cursor cursor =  database.rawQuery(sql, null);
        cursor.moveToFirst();
        IncomeBudgetPersist incomeBudgetPersist = new IncomeBudgetPersist();
        AccountPersist accountPersist = new AccountPersist();
        while (!cursor.isAfterLast()) {
            Long incomeBudgetID = cursor.getLong(cursor.getColumnIndexOrThrow(_BUDGET_ID));
            IncomeBudget incomeBudget = incomeBudgetPersist.read(incomeBudgetID,database);
            Long accountID = cursor.getLong(cursor.getColumnIndexOrThrow(_ACCOUNT_ID));
            Account account = accountPersist.read(accountID, database);
            Income income = new Income();
            income.setId(cursor.getLong(cursor.getColumnIndexOrThrow(_ID)));
            income.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
            income.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
            income.setIncomeBudget(incomeBudget);
            income.setAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_AMOUNT))));
            income.setAccount(account);
            income.setDate(cursor.getLong(cursor.getColumnIndexOrThrow(_RECEIVED_DATE)));
            income.setConfirmed(cursor.getLong(cursor.getColumnIndexOrThrow(_CONFIRMED)) == 1 ? true : false);
            incomeList.add(income);
            cursor.moveToNext();
        }
        cursor.close();
        return incomeList;
    }

    public List<Income> readAll(long startDate, long endDate, SQLiteDatabase database) {
        String sql = "select * from " + _TABLE + " where " + _RECEIVED_DATE + " >= " + startDate
                + " and " + _RECEIVED_DATE + " <= " + endDate + " order by " + _RECEIVED_DATE + " desc";
        List<Income> incomeList = new ArrayList<Income>();
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        IncomeBudgetPersist incomeBudgetPersist = new IncomeBudgetPersist();
        AccountPersist accountPersist = new AccountPersist();
        while (!cursor.isAfterLast()) {
            Long incomeBudgetID = cursor.getLong(cursor.getColumnIndexOrThrow(_BUDGET_ID));
            IncomeBudget incomeBudget = incomeBudgetPersist.read(incomeBudgetID, database);
            Long accountID = cursor.getLong(cursor.getColumnIndexOrThrow(_ACCOUNT_ID));
            Account account = accountPersist.read(accountID, database);
            Income income = new Income();
            income.setId(cursor.getLong(cursor.getColumnIndexOrThrow(_ID)));
            income.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
            income.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
            income.setIncomeBudget(incomeBudget);
            income.setAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_AMOUNT))));
            income.setAccount(account);
            income.setDate(cursor.getLong(cursor.getColumnIndexOrThrow(_RECEIVED_DATE)));
            income.setConfirmed(cursor.getLong(cursor.getColumnIndexOrThrow(_CONFIRMED)) == 1 ? true : false);
            incomeList.add(income);
            cursor.moveToNext();
        }
        cursor.close();
        return incomeList;
    }

    public BigDecimal readTotal(long begin, long end, SQLiteDatabase database) {
        String sql = "select sum(" + _AMOUNT + ") as total from " + _TABLE + " where " + _RECEIVED_DATE + " >= " + begin
                + " and " + _RECEIVED_DATE + " <= " + end;
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        BigDecimal total;
        Double totalDouble = cursor.getDouble(cursor.getColumnIndexOrThrow("total"));
        total = new BigDecimal(totalDouble);
        cursor.close();
        return total.setScale(2, RoundingMode.HALF_UP);
    }


    public List<Income> readAllUnconfirmedConfirmed(SQLiteDatabase database) {
        String sql = "select * from " + _TABLE + " where " + _CONFIRMED + " = 0"
                + " order by " + _RECEIVED_DATE + " desc";
        List<Income> incomeList = new ArrayList<Income>();
        IncomeBudgetPersist incomeBudgetPersist = new IncomeBudgetPersist();
        AccountPersist accountPersist = new AccountPersist();
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Long incomeBudgetID = cursor.getLong(cursor.getColumnIndexOrThrow(_BUDGET_ID));
            IncomeBudget incomeBudget = incomeBudgetPersist.read(incomeBudgetID, database);
            Long accountID = cursor.getLong(cursor.getColumnIndexOrThrow(_ACCOUNT_ID));
            Account account = accountPersist.read(accountID, database);
            Income income = new Income();
            income.setId(cursor.getLong(cursor.getColumnIndexOrThrow(_ID)));
            income.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
            income.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
            income.setIncomeBudget(incomeBudget);
            income.setAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_AMOUNT))));
            income.setAccount(account);
            income.setDate(cursor.getLong(cursor.getColumnIndexOrThrow(_RECEIVED_DATE)));
            income.setConfirmed(cursor.getLong(cursor.getColumnIndexOrThrow(_CONFIRMED)) == 1 ? true : false);
            incomeList.add(income);
            cursor.moveToNext();
        }
        cursor.close();
        return incomeList;
    }

    public List<Income> readAllByAccount(Long accountID, SQLiteDatabase database){
        IncomeBudgetPersist incomeBudgetPersist = new IncomeBudgetPersist();
        AccountPersist accountPersist = new AccountPersist();
        Account account = accountPersist.read(accountID,database );
        String sql = "select * from " + _TABLE + " where " + _ACCOUNT_ID + " = " + accountID + "  order by "
                + _RECEIVED_DATE + " desc";
        List<Income> incomeList = new ArrayList<Income>();
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Long incomeBudgetID = cursor.getLong(cursor.getColumnIndexOrThrow(_BUDGET_ID));
            IncomeBudget incomeBudget = incomeBudgetPersist.read(incomeBudgetID, database);
            Income income = new Income();
            income.setId(cursor.getLong(cursor.getColumnIndexOrThrow(_ID)));
            income.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
            income.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
            income.setIncomeBudget(incomeBudget);
            income.setAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_AMOUNT))));
            income.setAccount(account);
            income.setDate(cursor.getLong(cursor.getColumnIndexOrThrow(_RECEIVED_DATE)));
            income.setConfirmed(cursor.getLong(cursor.getColumnIndexOrThrow(_CONFIRMED)) == 1 ? true : false);
            incomeList.add(income);
            cursor.moveToNext();
        }
        cursor.close();
        return incomeList;
    }

    public Income update(Income income, SQLiteDatabase database) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(_NAME, income.getName());
        contentValues.put(_NOTE, income.getNote());
        contentValues.put(_BUDGET_ID, income.getIncomeBudget().getId());
        contentValues.put(_AMOUNT, income.getAmount().toString());
        contentValues.put(_ACCOUNT_ID, income.getAccount().getId());
        contentValues.put(_RECEIVED_DATE, income.getDate());
        contentValues.put(_CONFIRMED, income.getConfirmed() == true ? 1 : 0);
        database.update(_TABLE, contentValues, _ID + " = " + income.getId(), null);
        return income;
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
