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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.quebecfresh.androidapp.simplebudget.model.Account;
import com.quebecfresh.androidapp.simplebudget.persist.AccountPersist;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;

import java.math.BigDecimal;
import java.util.List;


public class InitializeAccountActivity extends ActionBarActivity {
    public static final String EXTRA_ACCOUNT_ID = "com.quebecfresh.androidapp.simplebudget.account";

    private SQLiteDatabase mReadableDatabase;
    private AccountPersist mAccountPersist;

    private AccountFragment mAccountFragment;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialize_account);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        mReadableDatabase = databaseHelper.getReadableDatabase();
        mAccountPersist = new AccountPersist();


    }


    @Override
    protected void onResume() {

        //When initialization is not done, disabled actionBar up button
        SharedPreferences preferences = this.getSharedPreferences(getString(R.string.preference_file), Context.MODE_PRIVATE);
        Boolean initializeDone = preferences.getBoolean(getString(R.string.initialize_done), false);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        if (initializeDone) {
//            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        } else {
//            this.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        }


        mAccountFragment = new AccountFragment();
        mAccountFragment.setAccountList(mAccountPersist.readAll(mReadableDatabase));
        mAccountFragment.setTotalBalance(mAccountPersist.readTotalBalance(mReadableDatabase));
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerAccountList, mAccountFragment);
        fragmentTransaction.commit();

        super.onResume();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_initialize_account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        Intent intent;
        switch (id) {
            case R.id.action_add:
                intent = new Intent(this, EditAccountActivity.class);
                intent.putExtra(EXTRA_ACCOUNT_ID, -1L);
                startActivity(intent);
                break;
            case R.id.action_next:
                intent = new Intent(this, InitializeIncomeBudgetActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
