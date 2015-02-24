package com.quebecfresh.androidapp.simplebudget;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.quebecfresh.androidapp.simplebudget.model.Category;
import com.quebecfresh.androidapp.simplebudget.model.Cycle;
import com.quebecfresh.androidapp.simplebudget.model.ExpenseCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class BudgetExpenseActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget__expense);

        ArrayList<String> categoryGroup = new ArrayList<String>();
        categoryGroup.add("Foods");
        categoryGroup.add("shelter");
        categoryGroup.add("utilities");
        categoryGroup.add("transportation");

        HashMap<String, List<Category>> categoryHashMap = new HashMap<String, List<Category>>();

        List<Category> foods = new ArrayList<Category>();
        foods.add(new ExpenseCategory("Groceries", Cycle.Weekly));
        foods.add(new ExpenseCategory("Restaurant", Cycle.Weekly));
        foods.add(new ExpenseCategory("Pet foods", Cycle.Weekly));

        List<Category> shelter = new ArrayList<Category>();
        shelter.add(new ExpenseCategory("Mortgage", Cycle.Every_2_Weeks));
        shelter.add(new ExpenseCategory("Rent", Cycle.Monthly));
        shelter.add(new ExpenseCategory("Property Taxes", Cycle.Yearly));
        shelter.add(new ExpenseCategory("House repair", Cycle.Yearly));
        shelter.add(new ExpenseCategory("Insurance", Cycle.Yearly));

        List<Category> utilities = new ArrayList<Category>();
        utilities.add(new ExpenseCategory("Electricity", Cycle.Monthly));
        utilities.add(new ExpenseCategory("Phone", Cycle.Monthly));
        utilities.add(new ExpenseCategory("Cable TV", Cycle.Monthly));
        utilities.add(new ExpenseCategory("Internet service", Cycle.Monthly));
        utilities.add(new ExpenseCategory("Water", Cycle.Yearly));
        utilities.add(new ExpenseCategory("Garbage", Cycle.Yearly));
        utilities.add(new ExpenseCategory("Heating", Cycle.Yearly));



        List<Category> transportation = new ArrayList<Category>();
        transportation.add(new ExpenseCategory("Fuel", Cycle.Weekly));
        transportation.add(new ExpenseCategory("Tire", Cycle.Every_6_Months));
        transportation.add(new ExpenseCategory("Oil change", Cycle.Every_6_Months));
        transportation.add(new ExpenseCategory("Insurance", Cycle.Monthly));
        transportation.add(new ExpenseCategory("Auto plate", Cycle.Yearly));
        transportation.add(new ExpenseCategory("Driver licence", Cycle.Yearly));
        transportation.add(new ExpenseCategory("Bus ticket", Cycle.Monthly));

        categoryHashMap.put(categoryGroup.get(0), foods);
        categoryHashMap.put(categoryGroup.get(1), shelter);
        categoryHashMap.put(categoryGroup.get(2), utilities);
        categoryHashMap.put(categoryGroup.get(3), transportation);

//        ArrayList<String> cycles = new ArrayList<String>();
//        cycles.add("Daily");
//        cycles.add("Weekly");
//        cycles.add("Every_2_Weeks");
//        cycles.add("Every_3_Weeks");
//        cycles.add("Every_4_Weeks");
//        cycles.add("Monthly");
//        cycles.add("Every_2_Months");
//        cycles.add("Every_3_Months");
//        cycles.add("Every_4_Months");
//        cycles.add("Every_5_Months");
//        cycles.add("Every_6_Months");
//        cycles.add("Yearly");

        CategoryExpandableListViewAdapter adapter =new CategoryExpandableListViewAdapter(categoryGroup, categoryHashMap,  this);
        ExpandableListView listView = (ExpandableListView)findViewById(R.id.expandableListView_Category);
        listView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_budget__expense, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
