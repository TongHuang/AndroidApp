package com.quebecfresh.androidapp.simplebudget.persist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.quebecfresh.androidapp.simplebudget.model.Category;
import com.quebecfresh.androidapp.simplebudget.model.Cycle;
import com.quebecfresh.androidapp.simplebudget.model.Expense;
import com.quebecfresh.androidapp.simplebudget.model.ExpenseCategory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.quebecfresh.androidapp.simplebudget.model.ExpenseCategory.Contract.*;

/**
 * Created by Tong Huang on 2015-02-24, 5:44 AM.
 */
public class ExpenseCategoryPersist {
    private SQLiteDatabase db;

    public ExpenseCategoryPersist(SQLiteDatabase db) {
        this.db = db;
    }

    public ExpenseCategory insert(ExpenseCategory expenseCategory) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(_NAME, expenseCategory.getName());
        contentValues.put(_CYCLE, expenseCategory.getCycle().name());
        contentValues.put(_BUDGET_AMOUNT, expenseCategory.getBudgetAmount().toString());
        contentValues.put(_NOTE, expenseCategory.getNote());
        contentValues.put(_CATEGORY_GROUP, expenseCategory.getCategoryGroup().name());
        Long rowID = this.db.insert(_TABLE, null, contentValues);
        expenseCategory.setId(rowID);
        return expenseCategory;
    }

    public ExpenseCategory read(Long rowID) {
        String sql = "select * from " + _TABLE + " where " + _ID + "=" + rowID;
        Cursor cursor = this.db.rawQuery(sql, null);
        cursor.moveToFirst();
        ExpenseCategory expenseCategory = new ExpenseCategory();
        expenseCategory.setId(rowID);
        expenseCategory.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
        expenseCategory.setCycle(Cycle.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_CYCLE))));
        expenseCategory.setBudgetAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_BUDGET_AMOUNT))));
        expenseCategory.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
        expenseCategory.setCategoryGroup(ExpenseCategory.EXPENSE_CATEGORY_GROUP.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_CATEGORY_GROUP))));
        return expenseCategory;
    }

    public List<ExpenseCategory> readAll(){
        String sql = "select * from " + _TABLE;
        Cursor cursor = this.db.rawQuery(sql, null);
        cursor.moveToFirst();
        List<ExpenseCategory> categories = new ArrayList<ExpenseCategory>();
        while(!cursor.isAfterLast()){
            ExpenseCategory expenseCategory = new ExpenseCategory();
            expenseCategory.setId(cursor.getLong(cursor.getColumnIndexOrThrow(_ID)));
            expenseCategory.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
            expenseCategory.setCycle(Cycle.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_CYCLE))));
            expenseCategory.setBudgetAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_BUDGET_AMOUNT))));
            expenseCategory.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
            expenseCategory.setCategoryGroup(ExpenseCategory.EXPENSE_CATEGORY_GROUP.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_CATEGORY_GROUP))));
            categories.add(expenseCategory);
            cursor.moveToNext();
        }
        return categories;
    }

    public ExpenseCategory update(ExpenseCategory expenseCategory){
        ContentValues contentValues = new ContentValues();
        contentValues.put(_NAME, expenseCategory.getName());
        contentValues.put(_CYCLE, expenseCategory.getCycle().name());
        contentValues.put(_BUDGET_AMOUNT, expenseCategory.getBudgetAmount().toString());
        contentValues.put(_NOTE, expenseCategory.getNote());
        contentValues.put(_CATEGORY_GROUP, expenseCategory.getCategoryGroup().name());
        this.db.update(_TABLE, contentValues,_ID + " = " +expenseCategory.getId(), null);
        return expenseCategory;
    }

    public Boolean delete(Long rowID){
        this.db.delete(_TABLE, _ID + " = " + rowID, null);
        return true;
    }

    public void initialize(){

        ExpenseCategory expenseCategory;
        expenseCategory = new ExpenseCategory("Groceries", Cycle.Weekly);
        expenseCategory.setCategoryGroup(ExpenseCategory.EXPENSE_CATEGORY_GROUP.FOODS);
        this.insert(expenseCategory);
        expenseCategory = new ExpenseCategory("Restaurant", Cycle.Weekly);
        expenseCategory.setCategoryGroup(ExpenseCategory.EXPENSE_CATEGORY_GROUP.FOODS);
        this.insert(expenseCategory);
        expenseCategory = new ExpenseCategory("Pet foods", Cycle.Weekly);
        expenseCategory.setCategoryGroup(ExpenseCategory.EXPENSE_CATEGORY_GROUP.FOODS);
        this.insert(expenseCategory);




        expenseCategory = new ExpenseCategory("Mortgage", Cycle.Every_2_Weeks);
        expenseCategory.setCategoryGroup(ExpenseCategory.EXPENSE_CATEGORY_GROUP.SHELTER);
        this.insert(expenseCategory);
        expenseCategory = new ExpenseCategory("Rent", Cycle.Monthly);
        expenseCategory.setCategoryGroup(ExpenseCategory.EXPENSE_CATEGORY_GROUP.SHELTER);
        this.insert(expenseCategory);
        expenseCategory = new ExpenseCategory("Property Taxes", Cycle.Yearly);
        expenseCategory.setCategoryGroup(ExpenseCategory.EXPENSE_CATEGORY_GROUP.SHELTER);
        this.insert(expenseCategory);
        expenseCategory = new ExpenseCategory("House repair", Cycle.Yearly);
        expenseCategory.setCategoryGroup(ExpenseCategory.EXPENSE_CATEGORY_GROUP.SHELTER);
        this.insert(expenseCategory);
        expenseCategory = new ExpenseCategory("Insurance", Cycle.Yearly);
        expenseCategory.setCategoryGroup(ExpenseCategory.EXPENSE_CATEGORY_GROUP.SHELTER);
        this.insert(expenseCategory);

        expenseCategory = new ExpenseCategory("Electricity", Cycle.Monthly);
        expenseCategory.setCategoryGroup(ExpenseCategory.EXPENSE_CATEGORY_GROUP.UTILITIES);
        this.insert(expenseCategory);
        expenseCategory = new ExpenseCategory("Phone", Cycle.Monthly);
        expenseCategory.setCategoryGroup(ExpenseCategory.EXPENSE_CATEGORY_GROUP.UTILITIES);
        this.insert(expenseCategory);
        expenseCategory = new ExpenseCategory("Cable TV", Cycle.Monthly);
        expenseCategory.setCategoryGroup(ExpenseCategory.EXPENSE_CATEGORY_GROUP.UTILITIES);
        this.insert(expenseCategory);
        expenseCategory = new ExpenseCategory("Internet service", Cycle.Monthly);
        expenseCategory.setCategoryGroup(ExpenseCategory.EXPENSE_CATEGORY_GROUP.UTILITIES);
        this.insert(expenseCategory);
        expenseCategory = new ExpenseCategory("Water", Cycle.Yearly);
        expenseCategory.setCategoryGroup(ExpenseCategory.EXPENSE_CATEGORY_GROUP.UTILITIES);
        this.insert(expenseCategory);
        expenseCategory = new ExpenseCategory("Garbage", Cycle.Yearly);
        expenseCategory.setCategoryGroup(ExpenseCategory.EXPENSE_CATEGORY_GROUP.UTILITIES);
        this.insert(expenseCategory);
        expenseCategory = new ExpenseCategory("Heating", Cycle.Yearly);
        expenseCategory.setCategoryGroup(ExpenseCategory.EXPENSE_CATEGORY_GROUP.UTILITIES);
        this.insert(expenseCategory);


        expenseCategory = new ExpenseCategory("Fuel", Cycle.Weekly);
        expenseCategory.setCategoryGroup(ExpenseCategory.EXPENSE_CATEGORY_GROUP.TRANSPORTATION);
        this.insert(expenseCategory);
        expenseCategory = new ExpenseCategory("Tire", Cycle.Every_6_Months);
        expenseCategory.setCategoryGroup(ExpenseCategory.EXPENSE_CATEGORY_GROUP.TRANSPORTATION);
        this.insert(expenseCategory);
        expenseCategory = new ExpenseCategory("Oil change", Cycle.Every_6_Months);
        expenseCategory.setCategoryGroup(ExpenseCategory.EXPENSE_CATEGORY_GROUP.TRANSPORTATION);
        this.insert(expenseCategory);
        expenseCategory = new ExpenseCategory("Insurance", Cycle.Monthly);
        expenseCategory.setCategoryGroup(ExpenseCategory.EXPENSE_CATEGORY_GROUP.TRANSPORTATION);
        this.insert(expenseCategory);
        expenseCategory = new ExpenseCategory("Auto plate", Cycle.Yearly);
        expenseCategory.setCategoryGroup(ExpenseCategory.EXPENSE_CATEGORY_GROUP.TRANSPORTATION);
        this.insert(expenseCategory);
        expenseCategory = new ExpenseCategory("Driver licence", Cycle.Yearly);
        expenseCategory.setCategoryGroup(ExpenseCategory.EXPENSE_CATEGORY_GROUP.TRANSPORTATION);
        this.insert(expenseCategory);
        expenseCategory = new ExpenseCategory("Bus ticket", Cycle.Monthly);
        expenseCategory.setCategoryGroup(ExpenseCategory.EXPENSE_CATEGORY_GROUP.TRANSPORTATION);
        this.insert(expenseCategory);

    }

    public Boolean save(ExpenseCategory expenseCategory){
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
