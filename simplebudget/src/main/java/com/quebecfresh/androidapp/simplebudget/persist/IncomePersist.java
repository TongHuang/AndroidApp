package com.quebecfresh.androidapp.simplebudget.persist;

import android.content.ContentValues;
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
public class IncomePersist {
    private SQLiteDatabase db;
    private AccountPersist accountPersist;
    private IncomeBudgetPersist incomeBudgetPersist;

    public IncomePersist(SQLiteDatabase db) {
        this.db = db;
        this.accountPersist = new AccountPersist(db);
        this.incomeBudgetPersist = new IncomeBudgetPersist(db);
    }

    public Income insert(Income income) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(_NAME, income.getName());
        contentValues.put(_NOTE, income.getNote());
        contentValues.put(_BUDGET_ID, income.getIncomeBudget().getId());
        contentValues.put(_AMOUNT, income.getAmount().toString());
        contentValues.put(_RECEIVED_DATE, income.getReceivedDate());
        contentValues.put(_ACCOUNT_ID, income.getAccount().getId());
        contentValues.put(_CONFIRMED, income.getConfirmed() == Boolean.TRUE ? 1 : 0);
        Long rowID = this.db.insert(_TABLE, null, contentValues);
        income.setId(rowID);
        return income;
    }

    public Income read(Long rowID) {
        String sql = "select * from " + _TABLE + " where " + _ID + " = " + rowID;
        Cursor cursor = db.rawQuery(sql, null);

        cursor.moveToFirst();

        Long incomeBudgetID = cursor.getLong(cursor.getColumnIndexOrThrow(_BUDGET_ID));
        IncomeBudget incomeBudget = incomeBudgetPersist.read(incomeBudgetID);
        Long accountID = cursor.getLong(cursor.getColumnIndexOrThrow(_ACCOUNT_ID));
        Account account = accountPersist.read(accountID);
        Income income = new Income();
        income.setId(rowID);
        income.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
        income.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
        income.setIncomeBudget(incomeBudget);
        income.setAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_AMOUNT))));
        income.setAccount(account);
        income.setReceivedDate(cursor.getLong(cursor.getColumnIndexOrThrow(_RECEIVED_DATE)));
        income.setConfirmed(cursor.getLong(cursor.getColumnIndexOrThrow(_CONFIRMED)) == 1 ? true : false);
        return income;
    }

    public List<Income> readAll() {
        String sql = "select * from " + _TABLE + " order by " + _RECEIVED_DATE + " desc";
        List<Income> incomeList = new ArrayList<Income>();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Long incomeBudgetID = cursor.getLong(cursor.getColumnIndexOrThrow(_BUDGET_ID));
            IncomeBudget incomeBudget = incomeBudgetPersist.read(incomeBudgetID);
            Long accountID = cursor.getLong(cursor.getColumnIndexOrThrow(_ACCOUNT_ID));
            Account account = accountPersist.read(accountID);
            Income income = new Income();
            income.setId(cursor.getLong(cursor.getColumnIndexOrThrow(_ID)));
            income.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
            income.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
            income.setIncomeBudget(incomeBudget);
            income.setAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_AMOUNT))));
            income.setAccount(account);
            income.setReceivedDate(cursor.getLong(cursor.getColumnIndexOrThrow(_RECEIVED_DATE)));
            income.setConfirmed(cursor.getLong(cursor.getColumnIndexOrThrow(_CONFIRMED)) == 1 ? true : false);
            incomeList.add(income);
            cursor.moveToNext();
        }
        return incomeList;
    }

    public List<Income> readAll(long startDate, long endDate) {
        String sql = "select * from " + _TABLE + " where " + _RECEIVED_DATE + " >= " + startDate
                + " and " + _RECEIVED_DATE + " <= " + endDate + " order by " + _RECEIVED_DATE + " desc";
        List<Income> incomeList = new ArrayList<Income>();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Long incomeBudgetID = cursor.getLong(cursor.getColumnIndexOrThrow(_BUDGET_ID));
            IncomeBudget incomeBudget = incomeBudgetPersist.read(incomeBudgetID);
            Long accountID = cursor.getLong(cursor.getColumnIndexOrThrow(_ACCOUNT_ID));
            Account account = accountPersist.read(accountID);
            Income income = new Income();
            income.setId(cursor.getLong(cursor.getColumnIndexOrThrow(_ID)));
            income.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
            income.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
            income.setIncomeBudget(incomeBudget);
            income.setAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_AMOUNT))));
            income.setAccount(account);
            income.setReceivedDate(cursor.getLong(cursor.getColumnIndexOrThrow(_RECEIVED_DATE)));
            income.setConfirmed(cursor.getLong(cursor.getColumnIndexOrThrow(_CONFIRMED)) == 1 ? true : false);
            incomeList.add(income);
            cursor.moveToNext();
        }
        return incomeList;
    }

    public BigDecimal readTotal(long begin, long end) {
        String sql = "select sum(" + _AMOUNT + ") as total from " + _TABLE + " where " + _RECEIVED_DATE + " >= " + begin
                + " and " + _RECEIVED_DATE + " <= " + end;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        BigDecimal total;
        String totalStr = cursor.getString(cursor.getColumnIndexOrThrow("total"));

        if (totalStr != null) {
            total = new BigDecimal(totalStr);
        } else {
            total = new BigDecimal("0");
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }


    public List<Income> readAllUnconfirmedConfirmed() {
        String sql = "select * from " + _TABLE + " where " + _CONFIRMED + " = 0"
                + " order by " + _RECEIVED_DATE + " desc";
        List<Income> incomeList = new ArrayList<Income>();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Long incomeBudgetID = cursor.getLong(cursor.getColumnIndexOrThrow(_BUDGET_ID));
            IncomeBudget incomeBudget = incomeBudgetPersist.read(incomeBudgetID);
            Long accountID = cursor.getLong(cursor.getColumnIndexOrThrow(_ACCOUNT_ID));
            Account account = accountPersist.read(accountID);
            Income income = new Income();
            income.setId(cursor.getLong(cursor.getColumnIndexOrThrow(_ID)));
            income.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
            income.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
            income.setIncomeBudget(incomeBudget);
            income.setAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_AMOUNT))));
            income.setAccount(account);
            income.setReceivedDate(cursor.getLong(cursor.getColumnIndexOrThrow(_RECEIVED_DATE)));
            income.setConfirmed(cursor.getLong(cursor.getColumnIndexOrThrow(_CONFIRMED)) == 1 ? true : false);
            incomeList.add(income);
            cursor.moveToNext();
        }
        return incomeList;
    }

    public Income update(Income income) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(_NAME, income.getName());
        contentValues.put(_NOTE, income.getNote());
        contentValues.put(_BUDGET_ID, income.getIncomeBudget().getId());
        contentValues.put(_AMOUNT, income.getAmount().toString());
        contentValues.put(_ACCOUNT_ID, income.getAccount().getId());
        contentValues.put(_RECEIVED_DATE, income.getReceivedDate());
        contentValues.put(_CONFIRMED, income.getConfirmed() == true ? 1 : 0);
        db.update(_TABLE, contentValues, _ID + " = " + income.getId(), null);
        return income;
    }

    public Boolean delete(Long rowID) {
        db.delete(_TABLE, _ID + " = " + rowID, null);
        return true;
    }

    public Boolean create() {
        db.execSQL(CREATE);
        return true;
    }

    public Boolean drop() {
        db.execSQL(DROP);
        return true;
    }
}
