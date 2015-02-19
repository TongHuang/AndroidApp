package com.quebecfresh.androidapp.simplebudget;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.quebecfresh.androidapp.simplebudget.InitializeAccountListViewAdapter;
import com.quebecfresh.androidapp.simplebudget.InitializeExpenseActivity;
import com.quebecfresh.androidapp.simplebudget.R;
import com.quebecfresh.androidapp.simplebudget.model.Account;

import java.util.ArrayList;


public class InitializeAccountActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialize_account);

        ArrayList<Account> accounts = new ArrayList<Account>();
        Account account1 = new Account();
        account1.setName("Cash on Hand");
        account1.setAccountNumber("000001");
        account1.setNote("Cash cash cash !!!!.");

        Account account2 = new Account();
        account2.setName("Bank account");
        account2.setAccountNumber("000002");

        Account account3 = new Account();
        account3.setName("Credit card");
        account3.setAccountNumber("000003");
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);

        InitializeAccountListViewAdapter adapter = new InitializeAccountListViewAdapter(accounts, this);
        ListView listView = (ListView) this.findViewById(R.id.listView_Account);
        listView.setAdapter(adapter);
    }

    public void startInitializeExpense(View view){
        Intent intent = new Intent(this, InitializeExpenseActivity.class);
        this.startActivity(intent);
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

        switch (id) {

            case R.id.action_done:
                Intent intent = new Intent(this, InitializeExpenseActivity.class);
                this.startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
