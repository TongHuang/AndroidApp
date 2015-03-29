package com.quebecfresh.androidapp.simplebudget;

import android.app.TabActivity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.ToggleButton;

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
    private View mListViewFooterAccount;
    private ListView listViewBudget;
    private View mListViewFooterBudget;

    private  AccountFragment mAccountFragment;
    private ExpenseBudgetFragment mExpenseBudgetFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
//
//        if(findViewById(R.id.fragment_container) != null){
//            // However, if we're being restored from a previous state,
//            // then we don't need to do anything and should return or else
//            // we could end up with overlapping fragments.
//
//            if(savedInstanceState != null){
//                return;
//            }
//
//            AccountFragment accountFragment = new AccountFragment();
//            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, accountFragment).commit();
//        }

//        db = databaseHelper.getReadableDatabase();
//
//        AccountPersist accountPersist = new AccountPersist(db);
//        List<Account> accountList = accountPersist.readAll();
//        ExpenseBudgetPersist expenseBudgetPersist = new ExpenseBudgetPersist(db);
//        List<ExpenseBudget> mExpenseBudgetList = expenseBudgetPersist.readAllBudgetAmountNotZero();
//
//        LayoutInflater layoutInflater  = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        mListViewFooterAccount = layoutInflater.inflate(R.layout.list_footer_total,null);
//        mListViewFooterBudget = layoutInflater.inflate(R.layout.list_footer_total, null);
//
//        TextView textViewAccountTotal = (TextView) mListViewFooterAccount.findViewById(R.id.textViewTotal);
//        textViewAccountTotal.setText(accountPersist.readTotalBalance().toString());
//
//        TextView textViewBudgetTotal = (TextView) mListViewFooterBudget.findViewById(R.id.textViewTotal);
//        textViewBudgetTotal.setText(expenseBudgetPersist.readTotalUnusedBalance().toString());
//
//        AccountListViewAdapter accountListViewAdapter = new AccountListViewAdapter(accountList, this);
//        listViewAccount = (ListView)findViewById(R.id.listViewAccount);
//        listViewAccount.addFooterView(mListViewFooterAccount);
//        listViewAccount.setAdapter(accountListViewAdapter);
//        ExpenseBudgetListViewAdapter expenseBudgetListViewAdapter = new ExpenseBudgetListViewAdapter(mExpenseBudgetList, this);
//        listViewBudget = (ListView)findViewById(R.id.listViewBudget);
//        listViewBudget.addFooterView(mListViewFooterBudget);
//        listViewBudget.setAdapter(expenseBudgetListViewAdapter);
    }

    public void showAccount(View view){
        if(mAccountFragment == null) {
           mAccountFragment = new AccountFragment();
        }
        Boolean on = ((ToggleButton)view).isChecked();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(on) {
            fragmentTransaction.add(R.id.fragmentContainerAccount, mAccountFragment);
        }else {
            fragmentTransaction.remove(mAccountFragment);
        }
        fragmentTransaction.commit();
    }

    public void showExpenseBudget(View view){
        if(mExpenseBudgetFragment == null){
            mExpenseBudgetFragment  = new ExpenseBudgetFragment();
        }
        Boolean on = ((ToggleButton)view).isChecked();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(on) {
            fragmentTransaction.add(R.id.fragmentContainerExpenseBudget, mExpenseBudgetFragment);
        }else{
            fragmentTransaction.remove(mExpenseBudgetFragment);
        }
        fragmentTransaction.commit();
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
