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
    private  AccountPersist accountPersist;

    public IncomeBudgetPersist(Context context){
        super(context);
        this.accountPersist = new AccountPersist(context);
    }



    public IncomeBudget insert(IncomeBudget incomeBudget){
        ContentValues contentValues = new ContentValues();
        contentValues.put(_NAME, incomeBudget.getName());
        contentValues.put(_NOTE, incomeBudget.getNote());
        contentValues.put(_CYCLE, incomeBudget.getCycle().name());
        contentValues.put(_BUDGET_AMOUNT, incomeBudget.getBudgetAmount().toString());
        contentValues.put(_BUDGET_CATEGORY, incomeBudget.getIncomeBudgetCategory().name());
        contentValues.put(_UNREALIZED_BALANCE, incomeBudget.getUnrealizedBalance().toString());
        contentValues.put(_ROLL_OVER, incomeBudget.getRollOver() == Boolean.TRUE ? 1:0);
        contentValues.put(_ACCOUNT_ID, incomeBudget.getAccount().getId());
        Long rowID = this.mDBH.getWritableDatabase().insert(_TABLE, null, contentValues);
        incomeBudget.setId(rowID);
        return incomeBudget;
    }

    public IncomeBudget read(Long rowID){
        String sql = "Select * from " + _TABLE + " where " + _ID + "=" + rowID;

        Cursor cursor = this.mDBH.getReadableDatabase().rawQuery(sql, null);
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
        incomeBudget.setAccount(accountPersist.read(accountID));
        return incomeBudget;
    }

    public List<IncomeBudget> readAll(){
        String sql = " Select * from " + _TABLE + " order by " + _BUDGET_AMOUNT + " desc";
        Cursor cursor = this.mDBH.getReadableDatabase().rawQuery(sql, null);
        cursor.moveToFirst();
        List<IncomeBudget> incomeBudgetList = new ArrayList<IncomeBudget>();
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
            long accountID = cursor.getLong(cursor.getColumnIndexOrThrow(_ACCOUNT_ID));
            incomeBudget.setAccount(accountPersist.read(accountID));
            incomeBudgetList.add(incomeBudget);
            cursor.moveToNext();
        }
        return incomeBudgetList;
    }

    public List<IncomeBudget> readAllBudgetAmountNotZero(){
        String sql = " Select * from " + _TABLE + " where " + _BUDGET_AMOUNT + " != 0 "
                + " order by " + _BUDGET_AMOUNT + " desc";
        Cursor cursor = this.mDBH.getReadableDatabase().rawQuery(sql, null);
        cursor.moveToFirst();
        List<IncomeBudget> incomeBudgetList = new ArrayList<IncomeBudget>();
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
            long accountID = cursor.getLong(cursor.getColumnIndexOrThrow(_ACCOUNT_ID));
            incomeBudget.setAccount(accountPersist.read(accountID));
            cursor.moveToNext();
        }
        return incomeBudgetList;
    }


    /*
    public List<IncomeBudget> readAllUnusedBalanceNotZero(){
        String sql = " Select * from " + _TABLE + " where " + _UNUSED_BALANCE + " != 0 ";
        Cursor cursor = this.db.rawQuery(sql, null);
        cursor.moveToFirst();
        List<IncomeBudget> incomeBudgetList = new ArrayList<IncomeBudget>();
        while(!cursor.isAfterLast()){
            IncomeBudget incomeBudget = new IncomeBudget();
            incomeBudget.setId(cursor.getLong(cursor.getColumnIndexOrThrow(_ID)));
            incomeBudget.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
            incomeBudget.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
            incomeBudget.setCycle(Cycle.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_CYCLE))));
            incomeBudget.setBudgetAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_BUDGET_AMOUNT))));
            incomeBudget.setIncomeBudgetCategory(IncomeBudget.INCOME_BUDGET_CATEGORY.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_BUDGET_CATEGORY))));
            incomeBudget.setUnusedBalance(new BigDecimal((cursor.getString(cursor.getColumnIndexOrThrow(_UNUSED_BALANCE)))));
            incomeBudget.setRollOver(cursor.getInt(cursor.getColumnIndexOrThrow(_ROLL_OVER)) == 1 ? true : false);
            incomeBudgetList.add(incomeBudget);
            cursor.moveToNext();
        }
        return incomeBudgetList;
    }
*/

    public IncomeBudget update(IncomeBudget incomeBudget){
        ContentValues contentValues = new ContentValues();
        contentValues.put(_NAME, incomeBudget.getName());
        contentValues.put(_NOTE, incomeBudget.getNote());
        contentValues.put(_CYCLE, incomeBudget.getCycle().name());
        contentValues.put(_BUDGET_AMOUNT, incomeBudget.getBudgetAmount().toString());
        contentValues.put(_BUDGET_CATEGORY, incomeBudget.getIncomeBudgetCategory().name());
        contentValues.put(_UNREALIZED_BALANCE, incomeBudget.getUnrealizedBalance().toString());
        contentValues.put(_ROLL_OVER, incomeBudget.getRollOver() == true ? 1 : 0);
        contentValues.put(_ACCOUNT_ID, incomeBudget.getAccount().getId());
        this.mDBH.getWritableDatabase().update(_TABLE, contentValues, _ID + " = " + incomeBudget.getId(), null);
        return incomeBudget;
    }

    public Boolean delete(Long rowID){
        this.mDBH.getWritableDatabase().delete(_TABLE, _ID + " = " + rowID, null);
        return true;
    }





    public boolean save(IncomeBudget incomeBudget){
        if(incomeBudget.getId() > 0){
            this.update(incomeBudget);
        }else{
            this.insert(incomeBudget);
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



        //Read the first account as the default account
//        Account account = this.accountPersist.read(1L);
//
//        IncomeBudget incomeBudget =  new IncomeBudget("Salary", Cycle.Weekly);
//        incomeBudget.setIncomeBudgetCategory(IncomeBudget.INCOME_BUDGET_CATEGORY.EMPLOYMENT);
//        incomeBudget.setAccount(account);
//        this.insert(incomeBudget);
//        incomeBudget = new IncomeBudget("Part-time job salary", Cycle.Every_2_Weeks);
//        incomeBudget.setIncomeBudgetCategory(IncomeBudget.INCOME_BUDGET_CATEGORY.EMPLOYMENT);
//        incomeBudget.setAccount(account);
//        this.insert(incomeBudget);
//        incomeBudget = new IncomeBudget("Bonus", Cycle.Yearly);
//        incomeBudget.setIncomeBudgetCategory(IncomeBudget.INCOME_BUDGET_CATEGORY.EMPLOYMENT);
//        incomeBudget.setAccount(account);
//        this.insert(incomeBudget);
//
//
//        incomeBudget =new IncomeBudget("Social welfare", Cycle.Monthly);
//        incomeBudget.setIncomeBudgetCategory(IncomeBudget.INCOME_BUDGET_CATEGORY.GOVERNMENT_BENEFIT);
//        incomeBudget.setAccount(account);
//        this.insert(incomeBudget);
//        incomeBudget = new IncomeBudget("Child care benefit", Cycle.Monthly);
//        incomeBudget.setIncomeBudgetCategory(IncomeBudget.INCOME_BUDGET_CATEGORY.GOVERNMENT_BENEFIT);
//        incomeBudget.setAccount(account);
//        this.insert(incomeBudget);
//        incomeBudget = new IncomeBudget("Employment Insurance", Cycle.Every_2_Weeks);
//        incomeBudget.setIncomeBudgetCategory(IncomeBudget.INCOME_BUDGET_CATEGORY.GOVERNMENT_BENEFIT);
//        incomeBudget.setAccount(account);
//        this.insert(incomeBudget);
//        incomeBudget =new IncomeBudget("Housing Allowance", Cycle.Monthly);
//        incomeBudget.setIncomeBudgetCategory(IncomeBudget.INCOME_BUDGET_CATEGORY.GOVERNMENT_BENEFIT);
//        incomeBudget.setAccount(account);
//        this.insert(incomeBudget);
//
//        incomeBudget = new IncomeBudget("Saving Interest", Cycle.Yearly);
//        incomeBudget.setIncomeBudgetCategory(IncomeBudget.INCOME_BUDGET_CATEGORY.INVESTMENT);
//        incomeBudget.setAccount(account);
//        this.insert(incomeBudget);
//        incomeBudget = new IncomeBudget("Property renting", Cycle.Monthly);
//        incomeBudget.setIncomeBudgetCategory(IncomeBudget.INCOME_BUDGET_CATEGORY.INVESTMENT);
//        incomeBudget.setAccount(account);
//        this.insert(incomeBudget);
//        incomeBudget =new IncomeBudget("Stock market revenue", Cycle.Yearly);
//        incomeBudget.setIncomeBudgetCategory(IncomeBudget.INCOME_BUDGET_CATEGORY.INVESTMENT);
//        incomeBudget.setAccount(account);
//        this.insert(incomeBudget);
    }

}

