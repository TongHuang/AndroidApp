package com.quebecfresh.androidapp.simplebudget;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.quebecfresh.androidapp.simplebudget.model.Account;
import com.quebecfresh.androidapp.simplebudget.persist.AccountPersist;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;

import java.math.BigDecimal;


public class EditAccountActivity extends ActionBarActivity {

    private Long accountID;
    private EditText editTextName;
    private EditText editTextNumber;
    private EditText editTextBalance;
    private EditText editTextNote;
    private AccountPersist accountPersist;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        Intent intent = getIntent();
        accountID = intent.getLongExtra(InitializeAccountActivity.EXTRA_ACCOUNT_ID, 0);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        accountPersist = new AccountPersist(db);
        if (accountID > 0) {
            account = accountPersist.read(accountID);
        } else {
            account = new Account();
        }


        editTextName = (EditText) this.findViewById(R.id.editTextName);
        editTextName.setText(account.getName());
        editTextNumber = (EditText) this.findViewById(R.id.editTextNumber);
        editTextNumber.setText(account.getAccountNumber());
        editTextBalance = (EditText) this.findViewById(R.id.editTextBalance);
        editTextBalance.setText(account.getBalance().toString());
        editTextNote = (EditText) this.findViewById(R.id.editTextNote);
        editTextNote.setText(account.getNote());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_save:
                EditText editTextName = (EditText) this.findViewById(R.id.editTextName);
                account.setName(editTextName.getText().toString());
                EditText editTextNumber = (EditText) this.findViewById(R.id.editTextNumber);
                account.setAccountNumber(editTextNumber.getText().toString());
                EditText editTextBalance = (EditText) this.findViewById(R.id.editTextBalance);
                account.setBalance(new BigDecimal(editTextBalance.getText().toString()));
                EditText editTextNote = (EditText) this.findViewById(R.id.editTextNote);
                account.setNote(editTextNote.getText().toString());
                accountPersist.save(account);
                break;
        }

        this.finish();
        return super.onOptionsItemSelected(item);
    }
}
