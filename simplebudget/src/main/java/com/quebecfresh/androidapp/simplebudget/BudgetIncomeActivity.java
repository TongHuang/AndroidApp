package com.quebecfresh.androidapp.simplebudget;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.quebecfresh.androidapp.simplebudget.model.Category;
import com.quebecfresh.androidapp.simplebudget.model.Cycle;
import com.quebecfresh.androidapp.simplebudget.model.IncomeCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class BudgetIncomeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_income);

        List<String> group = new ArrayList<String>();
        group.add("Employment");
        group.add("Government benefit");
        group.add("Investment");

        List<Category> employment = new ArrayList<Category>();
        employment.add(new IncomeCategory("Salary", Cycle.Weekly));
        employment.add(new IncomeCategory("Part-time job salary", Cycle.Every_2_Weekly));
        employment.add(new IncomeCategory("Bonus", Cycle.Yearly));

        List<Category> governmentBenefit = new ArrayList<Category>();
        governmentBenefit.add(new IncomeCategory("Social welfare", Cycle.Monthly));
        governmentBenefit.add(new IncomeCategory("Child care benefit", Cycle.Monthly));
        governmentBenefit.add(new IncomeCategory("Employment Insurance", Cycle.Every_2_Weekly));
        governmentBenefit.add(new IncomeCategory("Housing Allowance", Cycle.Monthly));


        List<Category> investment = new ArrayList<Category>();
        investment.add(new IncomeCategory("Saving Interest", Cycle.Yearly));
        investment.add(new IncomeCategory("Property renting", Cycle.Monthly));
        investment.add(new IncomeCategory("Stock market revenue", Cycle.Yearly));

        HashMap<String, List<Category>> incomeCategory = new HashMap<String, List<Category>>();
        incomeCategory.put(group.get(0), employment);
        incomeCategory.put(group.get(1), governmentBenefit);
        incomeCategory.put(group.get(2), investment);

        ArrayList<String> cycles = new ArrayList<String>();
        cycles.add("Daily");
        cycles.add("Weekly");
        cycles.add("Every_2_Weeks");
        cycles.add("Every_3_Weeks");
        cycles.add("Every_4_Weeks");
        cycles.add("Monthly");
        cycles.add("Every_2_Months");
        cycles.add("Every_3_Months");
        cycles.add("Every_4_Months");
        cycles.add("Every_5_Months");
        cycles.add("Every_6_Months");
        cycles.add("Yearly");

        CategoryExpandableListViewAdapter adapter = new CategoryExpandableListViewAdapter(group, incomeCategory,cycles, this);
        ExpandableListView expandableListView = (ExpandableListView)this.findViewById(R.id.expandableListView_incomeCategory);
        expandableListView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_budget_income, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_done) {
            Intent intent =new Intent(this, BudgetExpenseActivity.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
