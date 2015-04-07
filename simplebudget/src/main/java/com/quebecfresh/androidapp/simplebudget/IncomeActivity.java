package com.quebecfresh.androidapp.simplebudget;

import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.quebecfresh.androidapp.simplebudget.model.Cycle;
import com.quebecfresh.androidapp.simplebudget.model.Utils;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;
import com.quebecfresh.androidapp.simplebudget.persist.IncomePersist;

import java.util.Calendar;


public class IncomeActivity extends ActionBarActivity {


    private Calendar mSelectedDate = Calendar.getInstance();
    private Cycle mSelectedCycle = Cycle.Monthly;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase readableDatabase = databaseHelper.getWritableDatabase();

        this.mSelectedCycle = Cycle.valueOf(getIntent().getStringExtra(BudgetOverviewActivity.EXTRA_SELECTED_CYCLE));
        this.mSelectedDate.setTimeInMillis(getIntent().getLongExtra(BudgetOverviewActivity.EXTRA_SELECTED_DATE, System.currentTimeMillis()));
        IncomePersist incomePersist = new IncomePersist();
        long begin = Utils.getBeginOfCycle(this.mSelectedCycle, this.mSelectedDate);
        long end = Utils.getEndOfCycle(this.mSelectedCycle, this.mSelectedDate);
        IncomeFragment incomeFragment = new IncomeFragment();
        incomeFragment.setIncomeList(incomePersist.readAll(begin, end, readableDatabase));
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainIncomeList, incomeFragment).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_income, menu);
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
