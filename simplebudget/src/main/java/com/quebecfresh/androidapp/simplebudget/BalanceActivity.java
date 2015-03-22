package com.quebecfresh.androidapp.simplebudget;

import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.quebecfresh.androidapp.simplebudget.model.Account;
import com.quebecfresh.androidapp.simplebudget.model.ExpenseBudget;
import com.quebecfresh.androidapp.simplebudget.persist.AccountPersist;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;
import com.quebecfresh.androidapp.simplebudget.persist.ExpenseBudgetPersist;

import java.util.List;


public class BalanceActivity extends ActionBarActivity {

    private DatabaseHelper databaseHelper = new DatabaseHelper(this);
    private SQLiteDatabase db;

    private ListView listViewAccount;
    private ListView listViewBudget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        db = databaseHelper.getReadableDatabase();

        AccountPersist accountPersist = new AccountPersist(db);
        List<Account> accountList = accountPersist.readAll();
        ExpenseBudgetPersist expenseBudgetPersist = new ExpenseBudgetPersist(db);
        List<ExpenseBudget> expenseBudgetList = expenseBudgetPersist.readAllBudgetAmountNotZero();

        AccountListViewAdapter accountListViewAdapter = new AccountListViewAdapter(accountList, this);
        listViewAccount = (ListView)findViewById(R.id.listViewAccount);
        listViewAccount.setAdapter(accountListViewAdapter);

        ExpenseBudgetListViewAdapter expenseBudgetListViewAdapter = new ExpenseBudgetListViewAdapter(expenseBudgetList, this);
        listViewBudget = (ListView)findViewById(R.id.listViewBudget);
        listViewBudget.setAdapter(expenseBudgetListViewAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_balance, menu);
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
