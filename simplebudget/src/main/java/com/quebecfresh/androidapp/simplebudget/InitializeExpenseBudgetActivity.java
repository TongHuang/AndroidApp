package com.quebecfresh.androidapp.simplebudget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.quebecfresh.androidapp.simplebudget.model.Cycle;
import com.quebecfresh.androidapp.simplebudget.model.ExpenseBudget;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;
import com.quebecfresh.androidapp.simplebudget.persist.ExpenseBudgetPersist;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class InitializeExpenseBudgetActivity extends ActionBarActivity {

    private  SQLiteDatabase mReadableDatabase;
    private  ExpenseBudgetPersist mExpenseBudgetPersist;
    private ExpandableExpenseBudgetFragment mExpandableExpenseBudgetFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialize_expense_budget);


        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        mReadableDatabase = databaseHelper.getReadableDatabase();
        mExpenseBudgetPersist = new ExpenseBudgetPersist();


        mExpandableExpenseBudgetFragment = new ExpandableExpenseBudgetFragment();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_initialize_expense_budget, menu);
        return true;
    }

    @Override
    protected void onResume() {



        mExpandableExpenseBudgetFragment.setExpenseBudgetList(mExpenseBudgetPersist.readAll(mReadableDatabase));

        FragmentTransaction fragmentTransaction =  getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerExpenseBudget, mExpandableExpenseBudgetFragment);
        fragmentTransaction.commit();

        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.id.action_add:
                 intent = new Intent(this, EditExpenseBudgetActivity.class);
                startActivity(intent);
                break;
            case R.id.action_next:
                intent = new Intent(this, BudgetReviewActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
