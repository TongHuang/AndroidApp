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

    private Long mAccountID;
    private EditText mEditTextName;
    private EditText mEditTextNumber;
    private EditText mEditTextBalance;
    private EditText mEditTextNote;
    private Account mAccount;
    private  DatabaseHelper mDBHelper;
    private AccountPersist mAccountPersist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        Intent intent = getIntent();
        mAccountID = intent.getLongExtra(InitializeAccountActivity.EXTRA_ACCOUNT_ID, 0);

        mDBHelper = new DatabaseHelper(this);
        mAccountPersist = new AccountPersist(mDBHelper.getWritableDatabase());
        if (mAccountID > 0) {
            mAccount = mAccountPersist.read(mAccountID);
            mEditTextName = (EditText) this.findViewById(R.id.editTextName);
            mEditTextName.setText(mAccount.getName());
            mEditTextNumber = (EditText) this.findViewById(R.id.editTextNumber);
            mEditTextNumber.setText(mAccount.getAccountNumber());
            mEditTextBalance = (EditText) this.findViewById(R.id.editTextBalance);
            mEditTextBalance.setText(mAccount.getBalance().toString());
            mEditTextNote = (EditText) this.findViewById(R.id.editTextNote);
            mEditTextNote.setText(mAccount.getNote());
        } else {
            mAccount = new Account();
        }

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
                if(this.validateInput()) {
                    mAccount.setName(mEditTextName.getText().toString());
                    mAccount.setAccountNumber(mEditTextNumber.getText().toString());
                    mAccount.setBalance(new BigDecimal(mEditTextBalance.getText().toString()));
                    mAccount.setNote(mEditTextNote.getText().toString());
                    mAccountPersist.save(mAccount);
                }else{
                    return false;
                }
                break;
        }

        this.finish();
        return super.onOptionsItemSelected(item);
    }

    private Boolean validateInput(){
        if(mEditTextName.getText().length() <= 0){
            mEditTextName.requestFocus();
            return false;
        }
        if(mEditTextNumber.getText().length() <= 0){
            mEditTextNumber.requestFocus();
            return false;
        }
        if(mEditTextBalance.getText().length() <= 0){
            mEditTextBalance.requestFocus();
            return false;
        }
        return true;
    }
}
