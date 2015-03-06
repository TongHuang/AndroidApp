package com.quebecfresh.androidapp.simplebudget.persist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.quebecfresh.androidapp.simplebudget.model.Cycle;
import com.quebecfresh.androidapp.simplebudget.model.ExpenseBudget;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.quebecfresh.androidapp.simplebudget.model.ExpenseBudget.Contract.*;

/**
 * Created by Tong Huang on 2015-02-24, 5:44 AM.
 */
public class ExpenseBudgetPersist {
    private SQLiteDatabase db;

    public ExpenseBudgetPersist(SQLiteDatabase db) {
        this.db = db;
    }

    public ExpenseBudget insert(ExpenseBudget expenseCategory) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(_NAME, expenseCategory.getName());
        contentValues.put(_CYCLE, expenseCategory.getCycle().name());
        contentValues.put(_BUDGET_AMOUNT, expenseCategory.getBudgetAmount().toString());
        contentValues.put(_NOTE, expenseCategory.getNote());
        contentValues.put(_CATEGORY_GROUP, expenseCategory.getCategoryGroup().name());
        contentValues.put(_UNUSED_BALANCE, expenseCategory.getUnusedBalance().toString());
        contentValues.put(_ROLL_OVER, expenseCategory.getRollOver()==Boolean.TRUE?1:0);

        Long rowID = this.db.insert(_TABLE, null, contentValues);
        expenseCategory.setId(rowID);
        return expenseCategory;
    }

    public ExpenseBudget read(Long rowID) {
        String sql = "select * from " + _TABLE + " where " + _ID + "=" + rowID;
        Cursor cursor = this.db.rawQuery(sql, null);
        cursor.moveToFirst();
        ExpenseBudget expenseCategory = new ExpenseBudget();
        expenseCategory.setId(rowID);
        expenseCategory.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
        expenseCategory.setCycle(Cycle.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_CYCLE))));
        expenseCategory.setBudgetAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_BUDGET_AMOUNT))));
        expenseCategory.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
        expenseCategory.setCategoryGroup(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_CATEGORY_GROUP))));
        expenseCategory.setUnusedBalance(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_UNUSED_BALANCE))));
        expenseCategory.setRollOver(cursor.getInt(cursor.getColumnIndexOrThrow(_ROLL_OVER))== 1?true:false);
        return expenseCategory;
    }

    public List<ExpenseBudget> readAll(){
        String sql = "select * from " + _TABLE;
        Cursor cursor = this.db.rawQuery(sql, null);
        cursor.moveToFirst();
        List<ExpenseBudget> categories = new ArrayList<ExpenseBudget>();
        while(!cursor.isAfterLast()){
            ExpenseBudget expenseCategory = new ExpenseBudget();
            expenseCategory.setId(cursor.getLong(cursor.getColumnIndexOrThrow(_ID)));
            expenseCategory.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
            expenseCategory.setCycle(Cycle.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_CYCLE))));
            expenseCategory.setBudgetAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_BUDGET_AMOUNT))));
            expenseCategory.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
            expenseCategory.setCategoryGroup(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_CATEGORY_GROUP))));
            expenseCategory.setUnusedBalance(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_UNUSED_BALANCE))));
            expenseCategory.setRollOver(cursor.getInt(cursor.getColumnIndexOrThrow(_ROLL_OVER)) == 1 ? true:false);
            categories.add(expenseCategory);
            cursor.moveToNext();
        }
        return categories;
    }

    public ExpenseBudget update(ExpenseBudget expenseCategory){
        ContentValues contentValues = new ContentValues();
        contentValues.put(_NAME, expenseCategory.getName());
        contentValues.put(_CYCLE, expenseCategory.getCycle().name());
        contentValues.put(_BUDGET_AMOUNT, expenseCategory.getBudgetAmount().toString());
        contentValues.put(_NOTE, expenseCategory.getNote());
        contentValues.put(_CATEGORY_GROUP, expenseCategory.getCategoryGroup().name());
        contentValues.put(_UNUSED_BALANCE, expenseCategory.getUnusedBalance().toString());
        contentValues.put(_ROLL_OVER, expenseCategory.getRollOver() == true?1:0);
        this.db.update(_TABLE, contentValues,_ID + " = " +expenseCategory.getId(), null);
        return expenseCategory;
    }

    public Boolean delete(Long rowID){
        this.db.delete(_TABLE, _ID + " = " + rowID, null);
        return true;
    }

    public void initialize(){

        ExpenseBudget expenseCategory;
        expenseCategory = new ExpenseBudget("Groceries", Cycle.Weekly);
        expenseCategory.setCategoryGroup(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.FOODS);
        this.insert(expenseCategory);
        expenseCategory = new ExpenseBudget("Restaurant", Cycle.Weekly);
        expenseCategory.setCategoryGroup(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.FOODS);
        this.insert(expenseCategory);
        expenseCategory = new ExpenseBudget("Pet foods", Cycle.Weekly);
        expenseCategory.setCategoryGroup(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.FOODS);
        this.insert(expenseCategory);




        expenseCategory = new ExpenseBudget("Mortgage", Cycle.Every_2_Weeks);
        expenseCategory.setCategoryGroup(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.SHELTER);
        this.insert(expenseCategory);
        expenseCategory = new ExpenseBudget("Rent", Cycle.Monthly);
        expenseCategory.setCategoryGroup(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.SHELTER);
        this.insert(expenseCategory);
        expenseCategory = new ExpenseBudget("Property Taxes", Cycle.Yearly);
        expenseCategory.setCategoryGroup(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.SHELTER);
        this.insert(expenseCategory);
        expenseCategory = new ExpenseBudget("House repair", Cycle.Yearly);
        expenseCategory.setCategoryGroup(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.SHELTER);
        this.insert(expenseCategory);
        expenseCategory = new ExpenseBudget("Insurance", Cycle.Yearly);
        expenseCategory.setCategoryGroup(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.SHELTER);
        this.insert(expenseCategory);

        expenseCategory = new ExpenseBudget("Electricity", Cycle.Monthly);
        expenseCategory.setCategoryGroup(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.UTILITIES);
        this.insert(expenseCategory);
        expenseCategory = new ExpenseBudget("Phone", Cycle.Monthly);
        expenseCategory.setCategoryGroup(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.UTILITIES);
        this.insert(expenseCategory);
        expenseCategory = new ExpenseBudget("Cable TV", Cycle.Monthly);
        expenseCategory.setCategoryGroup(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.UTILITIES);
        this.insert(expenseCategory);
        expenseCategory = new ExpenseBudget("Internet service", Cycle.Monthly);
        expenseCategory.setCategoryGroup(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.UTILITIES);
        this.insert(expenseCategory);
        expenseCategory = new ExpenseBudget("Water", Cycle.Yearly);
        expenseCategory.setCategoryGroup(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.UTILITIES);
        this.insert(expenseCategory);
        expenseCategory = new ExpenseBudget("Garbage", Cycle.Yearly);
        expenseCategory.setCategoryGroup(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.UTILITIES);
        this.insert(expenseCategory);
        expenseCategory = new ExpenseBudget("Heating", Cycle.Yearly);
        expenseCategory.setCategoryGroup(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.UTILITIES);
        this.insert(expenseCategory);


        expenseCategory = new ExpenseBudget("Fuel", Cycle.Weekly);
        expenseCategory.setCategoryGroup(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.TRANSPORTATION);
        this.insert(expenseCategory);
        expenseCategory = new ExpenseBudget("Tire", Cycle.Every_6_Months);
        expenseCategory.setCategoryGroup(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.TRANSPORTATION);
        this.insert(expenseCategory);
        expenseCategory = new ExpenseBudget("Oil change", Cycle.Every_6_Months);
        expenseCategory.setCategoryGroup(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.TRANSPORTATION);
        this.insert(expenseCategory);
        expenseCategory = new ExpenseBudget("Insurance", Cycle.Monthly);
        expenseCategory.setCategoryGroup(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.TRANSPORTATION);
        this.insert(expenseCategory);
        expenseCategory = new ExpenseBudget("Auto plate", Cycle.Yearly);
        expenseCategory.setCategoryGroup(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.TRANSPORTATION);
        this.insert(expenseCategory);
        expenseCategory = new ExpenseBudget("Driver licence", Cycle.Yearly);
        expenseCategory.setCategoryGroup(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.TRANSPORTATION);
        this.insert(expenseCategory);
        expenseCategory = new ExpenseBudget("Bus ticket", Cycle.Monthly);
        expenseCategory.setCategoryGroup(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.TRANSPORTATION);
        this.insert(expenseCategory);

    }

    public Boolean save(ExpenseBudget expenseCategory){
        if(expenseCategory.getId() > 0){
            this.update(expenseCategory);
        }else{
            this.insert(expenseCategory);
        }
        return true;
    }

    public Boolean create(){
         this.db.execSQL(CREATE);
        return true;
    }

    public Boolean drop(){
        this.db.execSQL(DROP);
        return true;
    }
}
