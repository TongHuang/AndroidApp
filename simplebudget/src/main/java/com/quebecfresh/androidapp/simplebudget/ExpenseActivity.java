package com.quebecfresh.androidapp.simplebudget;

import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.quebecfresh.androidapp.simplebudget.model.Cycle;
import com.quebecfresh.androidapp.simplebudget.model.Expense;
import com.quebecfresh.androidapp.simplebudget.model.Utils;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;
import com.quebecfresh.androidapp.simplebudget.persist.ExpensePersist;

import java.util.Calendar;
import java.util.List;


public class ExpenseActivity extends ActionBarActivity {

    private Cycle mSelectedCycle = Cycle.Monthly;
    private Calendar mSelectedDate = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase readableDatabase = databaseHelper.getReadableDatabase();

        this.mSelectedCycle = Cycle.valueOf(getIntent().getStringExtra(BudgetOverviewActivity.EXTRA_SELECTED_CYCLE));
        this.mSelectedDate.setTimeInMillis(getIntent().getLongExtra(BudgetOverviewActivity.EXTRA_SELECTED_DATE, System.currentTimeMillis()));
        long begin = Utils.getBeginOfCycle(mSelectedCycle, mSelectedDate);
        long end = Utils.getEndOfCycle(mSelectedCycle, mSelectedDate);
        ExpensePersist expensePersist = new ExpensePersist();
        ExpenseFragment expenseFragment = new ExpenseFragment();
        expenseFragment.setExpenseList(expensePersist.readAll(begin, end, readableDatabase));
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainerExpenseList, expenseFragment);
        fragmentTransaction.commit();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_expense, menu);
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
