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
import com.quebecfresh.androidapp.simplebudget.persist.AccountPersist;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;

import java.math.BigDecimal;
import java.util.List;


public class AccountActivity extends ActionBarActivity {

    private DatabaseHelper mDBHelper = new DatabaseHelper(this);
    private SQLiteDatabase mDB;
    private AccountPersist mAccountPersist;
    private List<Account> mAccountList;

    private ListView mListViewAccounts;
    private View mListViewFooterAccounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        mAccountPersist = new AccountPersist(this);
        mAccountList = mAccountPersist.readAll();
        mListViewAccounts = (ListView)findViewById(R.id.listViewAccounts);
        AccountListViewAdapter accountListViewAdapter = new AccountListViewAdapter(mAccountList,this);
        mListViewAccounts.setAdapter(accountListViewAdapter);

        BigDecimal totalBalance = mAccountPersist.readTotalBalance();
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mListViewFooterAccounts = layoutInflater.inflate(R.layout.list_footer_total, null);
        TextView textViewTotal = (TextView)mListViewFooterAccounts.findViewById(R.id.textViewTotal);
        textViewTotal.setText(totalBalance.toString());
        mListViewAccounts.addFooterView(mListViewFooterAccounts);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_account, menu);
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
