package com.quebecfresh.androidapp.simplebudget;

import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.quebecfresh.androidapp.simplebudget.model.Cycle;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;
import com.quebecfresh.androidapp.simplebudget.persist.ExpenseBudgetPersist;


public class ExpenseBudgetActivity extends ActionBarActivity {

    private Cycle mSelectedCycle = Cycle.Monthly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_budget);

        mSelectedCycle =Cycle.valueOf(getIntent().getStringExtra(BudgetOverviewActivity.EXTRA_SELECTED_CYCLE));

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase readableDatabase = databaseHelper.getReadableDatabase();

        ExpenseBudgetPersist expenseBudgetPersist = new ExpenseBudgetPersist();
        ExpenseBudgetFragment expenseBudgetFragment = new ExpenseBudgetFragment();
        expenseBudgetFragment.setExpenseBudgetList(expenseBudgetPersist.readAllBudgetAmountNotZero(readableDatabase));
        expenseBudgetFragment.setSelectedCycle(mSelectedCycle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainerExpenseBudgetList, expenseBudgetFragment);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_expense_budget, menu);
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
