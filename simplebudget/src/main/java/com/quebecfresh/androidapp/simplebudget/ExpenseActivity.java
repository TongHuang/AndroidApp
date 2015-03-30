package com.quebecfresh.androidapp.simplebudget;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.quebecfresh.androidapp.simplebudget.model.Expense;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;
import com.quebecfresh.androidapp.simplebudget.persist.ExpensePersist;

import java.util.List;


public class ExpenseActivity extends ActionBarActivity {

    private DatabaseHelper databaseHelper = new DatabaseHelper(this);
    private ExpensePersist expensePersist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        expensePersist = new ExpensePersist(this);
        List<Expense> expenseList = expensePersist.readAll();

        ExpenseListViewAdapter expenseListViewAdapter = new ExpenseListViewAdapter(expenseList, this);
        ListView listViewExpense = (ListView) this.findViewById(R.id.listViewExpense);
        listViewExpense.setAdapter(expenseListViewAdapter);
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
