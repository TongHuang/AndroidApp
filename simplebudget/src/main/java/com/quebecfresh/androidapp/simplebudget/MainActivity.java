package com.quebecfresh.androidapp.simplebudget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.quebecfresh.androidapp.simplebudget.model.Expense;
import com.quebecfresh.androidapp.simplebudget.model.Income;
import com.quebecfresh.androidapp.simplebudget.model.Transaction;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;
import com.quebecfresh.androidapp.simplebudget.persist.ExpensePersist;
import com.quebecfresh.androidapp.simplebudget.persist.IncomePersist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private SQLiteDatabase mReadableDatabase;

    public void showBudgetOverView(View view) {
        Intent intent = new Intent(this, BudgetOverviewActivity.class);
        startActivity(intent);
    }


    public void newExpense(View view) {
        Intent intent = new Intent(this, NewExpenseActivity.class);
        startActivity(intent);
    }

    public void newIncome(View view) {
        Intent intent = new Intent(this, NewIncomeActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = this.getSharedPreferences(getString(R.string.preference_file), Context.MODE_PRIVATE);
        if (!preferences.getBoolean(getString(R.string.initialize_done), true)) {
            Intent intent = new Intent(this, EditBudgetInfoActivity.class);
            startActivity(intent);
        }

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        mReadableDatabase = databaseHelper.getReadableDatabase();
    }

    @Override
    protected void onResume() {
        ExpensePersist expensePersist = new ExpensePersist();
        List<Expense> expenseList = expensePersist.readAll(mReadableDatabase);
        IncomePersist incomePersist = new IncomePersist();
        List<Income> incomeList = incomePersist.readAll(mReadableDatabase);
        List<Transaction> transactionList = new ArrayList<Transaction>();
        transactionList.addAll(expenseList);
        transactionList.addAll(incomeList);
        Collections.sort(transactionList);

        TransactionFragment transactionFragment = new TransactionFragment();
        transactionFragment.setTransactionList(transactionList);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerRecentTransaction, transactionFragment).commit();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Intent intent;
        switch (id) {
            case R.id.action_reInitialize:
                SharedPreferences preferences = this.getSharedPreferences(getString(R.string.preference_file), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean(getString(R.string.initialize_done), false);
                editor.putLong(getString(R.string.initialize_date), 0);
                editor.putBoolean(getString(R.string.initialize_account_done),false);
                editor.putBoolean(getString(R.string.initialize_income_budget_done),false);
                editor.putBoolean(getString(R.string.initialize_expense_budget_done),false);
                editor.commit();
                intent = new Intent(this, WelcomeActivity.class);
                startActivity(intent);
                break;
            case R.id.action_demo:
                intent = new Intent(this, DemoActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
