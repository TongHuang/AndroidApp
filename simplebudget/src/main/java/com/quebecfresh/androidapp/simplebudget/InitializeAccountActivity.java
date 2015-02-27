package com.quebecfresh.androidapp.simplebudget;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.quebecfresh.androidapp.simplebudget.model.Account;
import com.quebecfresh.androidapp.simplebudget.persist.AccountPersist;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class InitializeAccountActivity extends ActionBarActivity {
    public static final String EXTRA_ACCOUNT = "com.quebecfresh.androidapp.simplebudget.account";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialize_account);

    }

    @Override
    protected void onResume() {

        DatabaseHelper dHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dHelper.getWritableDatabase();

        AccountPersist accountPersist = new AccountPersist(db);

        List<Account> accounts = accountPersist.readAll();

        BigDecimal total = new BigDecimal("0");
        for(int i = 0; i < accounts.size(); i++){
            total = total.add(accounts.get(i).getBalance());
        }

        TextView textViewTotal = (TextView)this.findViewById(R.id.textViewTotal);
        textViewTotal.setText("Total:" + total.toString());
        InitializeAccountListViewAdapter adapter = new InitializeAccountListViewAdapter(accounts, this);
        ListView listView = (ListView) this.findViewById(R.id.listViewAccount);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(InitializeAccountActivity.this, EditAccountActivity.class);
                intent.putExtra(EXTRA_ACCOUNT, id);
                startActivity(intent);
            }
        });

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

        switch (id) {
            case R.id.action_add:
                ListView listView = (ListView)this.findViewById(R.id.listViewAccount);
                InitializeAccountListViewAdapter adapter = (InitializeAccountListViewAdapter)listView.getAdapter();
                adapter.addNewAccount();

                return true;

            case R.id.action_done:
                this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
