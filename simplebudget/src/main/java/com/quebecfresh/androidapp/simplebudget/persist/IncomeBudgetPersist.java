package com.quebecfresh.androidapp.simplebudget.persist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.quebecfresh.androidapp.simplebudget.model.Account;
import com.quebecfresh.androidapp.simplebudget.model.Cycle;
import com.quebecfresh.androidapp.simplebudget.model.IncomeBudget;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.quebecfresh.androidapp.simplebudget.model.IncomeBudget.Contract.*;

/**
 * Created by Tong Huang on 2015-02-22, 7:05 PM.
 */
public class IncomeBudgetPersist extends Persist {



    public IncomeBudget insert(IncomeBudget incomeBudget, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put(_NAME, incomeBudget.getName());
        contentValues.put(_NOTE, incomeBudget.getNote());
        contentValues.put(_CYCLE, incomeBudget.getCycle().name());
        contentValues.put(_BUDGET_AMOUNT, incomeBudget.getBudgetAmount().toString());
        contentValues.put(_BUDGET_CATEGORY, incomeBudget.getIncomeBudgetCategory().name());
        contentValues.put(_UNREALIZED_BALANCE, incomeBudget.getUnrealizedBalance().toString());
        contentValues.put(_ROLL_OVER, incomeBudget.getRollOver() == Boolean.TRUE ? 1:0);
        contentValues.put(_ACCOUNT_ID, incomeBudget.getAccount().getId());
        Long rowID = database.insert(_TABLE, null, contentValues);
        incomeBudget.setId(rowID);
        return incomeBudget;
    }

    public IncomeBudget read(Long rowID, SQLiteDatabase database){
        String sql = "Select * from " + _TABLE + " where " + _ID + "=" + rowID;
        Cursor cursor = database.rawQuery(sql, null);
        AccountPersist accountPersist = new AccountPersist();
        cursor.moveToFirst();
        IncomeBudget incomeBudget = new IncomeBudget();
        incomeBudget.setId(rowID);
        incomeBudget.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
        incomeBudget.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
        incomeBudget.setCycle(Cycle.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_CYCLE))));
        incomeBudget.setBudgetAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_BUDGET_AMOUNT))));
        incomeBudget.setIncomeBudgetCategory(IncomeBudget.INCOME_BUDGET_CATEGORY.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_BUDGET_CATEGORY))));
        incomeBudget.setUnrealizedBalance(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_UNREALIZED_BALANCE))));
        incomeBudget.setRollOver(cursor.getInt(cursor.getColumnIndexOrThrow(_ROLL_OVER)) == 1 ? Boolean.TRUE : Boolean.FALSE);
        long accountID = cursor.getLong(cursor.getColumnIndexOrThrow(_ACCOUNT_ID));
        incomeBudget.setAccount(accountPersist.read(accountID, database));
        cursor.close();
        return incomeBudget;
    }

    public List<IncomeBudget> readAll(SQLiteDatabase database){
        String sql = " Select * from " + _TABLE + " order by " + _BUDGET_AMOUNT + " desc";
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        AccountPersist accountPersist = new AccountPersist();
        List<IncomeBudget> incomeBudgetList = new ArrayList<IncomeBudget>();
        long accountID;
        while(!cursor.isAfterLast()){
            IncomeBudget incomeBudget = new IncomeBudget();
            incomeBudget.setId(cursor.getLong(cursor.getColumnIndexOrThrow(_ID)));
            incomeBudget.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
            incomeBudget.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
            incomeBudget.setCycle(Cycle.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_CYCLE))));
            incomeBudget.setBudgetAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_BUDGET_AMOUNT))));
            incomeBudget.setIncomeBudgetCategory(IncomeBudget.INCOME_BUDGET_CATEGORY.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_BUDGET_CATEGORY))));
            incomeBudget.setUnrealizedBalance(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_UNREALIZED_BALANCE))));
            incomeBudget.setRollOver(cursor.getInt(cursor.getColumnIndexOrThrow(_ROLL_OVER)) == 1 ? true : false);
            accountID = cursor.getLong(cursor.getColumnIndexOrThrow(_ACCOUNT_ID));
            incomeBudget.setAccount(accountPersist.read(accountID, database));
            incomeBudgetList.add(incomeBudget);
            cursor.moveToNext();
        }
        cursor.close();
        return incomeBudgetList;
    }

    public List<IncomeBudget> readAllBudgetAmountNotZero(SQLiteDatabase database){
        String sql = " Select * from " + _TABLE + " where " + _BUDGET_AMOUNT + " != 0 "
                + " order by " + _BUDGET_AMOUNT + " desc";
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        AccountPersist accountPersist = new AccountPersist();
        List<IncomeBudget> incomeBudgetList = new ArrayList<IncomeBudget>();
        long accountID;
        while(!cursor.isAfterLast()){
            IncomeBudget incomeBudget = new IncomeBudget();
            incomeBudget.setId(cursor.getLong(cursor.getColumnIndexOrThrow(_ID)));
            incomeBudget.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
            incomeBudget.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
            incomeBudget.setCycle(Cycle.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_CYCLE))));
            incomeBudget.setBudgetAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_BUDGET_AMOUNT))));
            incomeBudget.setIncomeBudgetCategory(IncomeBudget.INCOME_BUDGET_CATEGORY.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_BUDGET_CATEGORY))));
           incomeBudget.setUnrealizedBalance(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_UNREALIZED_BALANCE))));
            incomeBudget.setRollOver(cursor.getInt(cursor.getColumnIndexOrThrow(_ROLL_OVER)) == 1 ? true : false);
            incomeBudgetList.add(incomeBudget);
             accountID = cursor.getLong(cursor.getColumnIndexOrThrow(_ACCOUNT_ID));
            incomeBudget.setAccount(accountPersist.read(accountID, database));
            cursor.moveToNext();
        }
        cursor.close();
        return incomeBudgetList;
    }


    public IncomeBudget update(IncomeBudget incomeBudget, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put(_NAME, incomeBudget.getName());
        contentValues.put(_NOTE, incomeBudget.getNote());
        contentValues.put(_CYCLE, incomeBudget.getCycle().name());
        contentValues.put(_BUDGET_AMOUNT, incomeBudget.getBudgetAmount().toString());
        contentValues.put(_BUDGET_CATEGORY, incomeBudget.getIncomeBudgetCategory().name());
        contentValues.put(_UNREALIZED_BALANCE, incomeBudget.getUnrealizedBalance().toString());
        contentValues.put(_ROLL_OVER, incomeBudget.getRollOver() == true ? 1 : 0);
        contentValues.put(_ACCOUNT_ID, incomeBudget.getAccount().getId());
        database.update(_TABLE, contentValues, _ID + " = " + incomeBudget.getId(), null);
        return incomeBudget;
    }

    public Boolean delete(Long rowID, SQLiteDatabase database){
        database.delete(_TABLE, _ID + " = " + rowID, null);
        return true;
    }





    public boolean save(IncomeBudget incomeBudget, SQLiteDatabase database){
        if(incomeBudget.getId() > 0){
            this.update(incomeBudget, database);
        }else{
            this.insert(incomeBudget, database);
        }

        return true;
    }

    /**
     * This static method is only used by DatabaseHelper to create database;
     * @param db
     * @return
     */
    public static Boolean create(SQLiteDatabase db){
        db.execSQL(CREATE);
        return true;
    }
    /**
     * This static method is only used by DatabaseHelper to drop table when Upgrade database;
     * @param db
     * @return
     */
    public static Boolean drop(SQLiteDatabase db){
        db.execSQL(DROP);
        return true;
    }

    /**
     Insert initial income budget data into database;
     This static method is only used by DatabaseHelper;
     * @param db
     * @return
     */
    public static void initialize(SQLiteDatabase db){

        String[][] incomeBudgets = {{"Salary",IncomeBudget.INCOME_BUDGET_CATEGORY.EMPLOYMENT.toString(), Cycle.Weekly.toString(),"2"},
                {"Part-time job salary",IncomeBudget.INCOME_BUDGET_CATEGORY.EMPLOYMENT.toString(), Cycle.Every_2_Weeks.toString(),"1"},
                {"Bonus",IncomeBudget.INCOME_BUDGET_CATEGORY.EMPLOYMENT.toString(), Cycle.Yearly.toString(),"2"},
                {"Social welfare",IncomeBudget.INCOME_BUDGET_CATEGORY.GOVERNMENT_BENEFIT.toString(), Cycle.Monthly.toString(),"2"},
                {"Child care benefit",IncomeBudget.INCOME_BUDGET_CATEGORY.GOVERNMENT_BENEFIT.toString(), Cycle.Monthly.toString(),"2"},
                {"Employment Insurance",IncomeBudget.INCOME_BUDGET_CATEGORY.GOVERNMENT_BENEFIT.toString(), Cycle.Every_2_Weeks.toString(),"2"},
                {"Housing Allowance",IncomeBudget.INCOME_BUDGET_CATEGORY.GOVERNMENT_BENEFIT.toString(), Cycle.Monthly.toString(),"2"},
                {"Saving Interest",IncomeBudget.INCOME_BUDGET_CATEGORY.INVESTMENT.toString(), Cycle.Yearly.toString(),"2"},
                {"Property renting",IncomeBudget.INCOME_BUDGET_CATEGORY.INVESTMENT.toString(), Cycle.Monthly.toString(),"2"},
                {"Stock market revenue",IncomeBudget.INCOME_BUDGET_CATEGORY.INVESTMENT.toString(), Cycle.Yearly.toString(),"2"}};


        ContentValues contentValues = new ContentValues();
        for (int i = 0 ; i < incomeBudgets.length; i++){
            contentValues.put(_NAME, incomeBudgets[i][0]);
            contentValues.put(_BUDGET_CATEGORY, incomeBudgets[i][1]);
            contentValues.put(_CYCLE, incomeBudgets[i][2]);
            contentValues.put(_ACCOUNT_ID, incomeBudgets[i][3]);
            db.insert(_TABLE, null, contentValues);
            contentValues.clear();
        }



    }

}

