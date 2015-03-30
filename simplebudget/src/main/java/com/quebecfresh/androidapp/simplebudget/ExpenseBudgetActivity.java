package com.quebecfresh.androidapp.simplebudget;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.quebecfresh.androidapp.simplebudget.model.Account;
import com.quebecfresh.androidapp.simplebudget.model.ExpenseBudget;
import com.quebecfresh.androidapp.simplebudget.persist.AccountPersist;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;
import com.quebecfresh.androidapp.simplebudget.persist.ExpenseBudgetPersist;

import java.math.BigDecimal;
import java.util.List;


public class ExpenseBudgetActivity extends ActionBarActivity {

    private DatabaseHelper mDBHelper = new DatabaseHelper(this);
    private SQLiteDatabase mDB;
    private ExpenseBudgetPersist mExpenseBudgetPersist;
    private List<ExpenseBudget> mExpenseBudgetList;

    private ListView mListViewExpenseBudgets;
    private View mListViewFooterExpenseBudgets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_budget);

        mExpenseBudgetPersist = new ExpenseBudgetPersist(this);
        mExpenseBudgetList = mExpenseBudgetPersist.readAllBudgetAmountNotZero();
        mListViewExpenseBudgets = (ListView)findViewById(R.id.listViewExpenseBudgets);
        ExpenseBudgetListViewAdapter expenseBudgetListViewAdapter = new ExpenseBudgetListViewAdapter(mExpenseBudgetList, this);
        mListViewExpenseBudgets.setAdapter(expenseBudgetListViewAdapter);

        BigDecimal total = mExpenseBudgetPersist.readTotalUnusedBalance();
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mListViewFooterExpenseBudgets  = layoutInflater.inflate(R.layout.list_footer_total, null);
        TextView textViewTotal =(TextView)mListViewFooterExpenseBudgets.findViewById(R.id.textViewTotal);
        textViewTotal.setText(total.toString());
        mListViewExpenseBudgets.addFooterView(mListViewFooterExpenseBudgets);

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
