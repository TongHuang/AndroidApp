package com.quebecfresh.androidapp.simplebudget;

import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.quebecfresh.androidapp.simplebudget.model.Account;
import com.quebecfresh.androidapp.simplebudget.persist.AccountPersist;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;

import java.math.BigDecimal;
import static com.quebecfresh.androidapp.simplebudget.model.Utils.*;


public class EditAccountActivity extends ActionBarActivity {

    private  DatabaseHelper mDBHelper = new DatabaseHelper(this);
    private SQLiteDatabase mWritableDatabase;
    private AccountPersist mAccountPersist;

    private Long mAccountID;
    private EditText mEditTextName;
    private EditText mEditTextNumber;
    private EditText mEditTextBalance;
    private EditText mEditTextNote;
    private Account mAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);


        LinearLayout linearLayout =(LinearLayout)findViewById(R.id.linearLayoutInsideScrollView);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        linearLayout.setMinimumHeight(metrics.heightPixels*8/10);

        Intent intent = getIntent();
        mAccountID = intent.getLongExtra(InitializeAccountActivity.EXTRA_ACCOUNT_ID, 0);

        mWritableDatabase = mDBHelper.getWritableDatabase();
        mAccountPersist = new AccountPersist();


        mEditTextName = (EditText) this.findViewById(R.id.editTextName);
        mEditTextNumber = (EditText) this.findViewById(R.id.editTextNumber);
        mEditTextBalance = (EditText) this.findViewById(R.id.editTextBalance);

        mEditTextNote = (EditText) this.findViewById(R.id.editTextNote);
        if (mAccountID > 0) {
            mAccount = mAccountPersist.read(mAccountID, mWritableDatabase);
            mEditTextName.setText(mAccount.getName());
            mEditTextNumber.setText(mAccount.getAccountNumber());
            mEditTextBalance.setText(mAccount.getBalance().toString());
            mEditTextNote.setText(mAccount.getNote());
        } else {
            mAccount = new Account();
        }
        mEditTextName.requestFocus();
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
                    mAccountPersist.save(mAccount, mWritableDatabase);
                    this.finish();
                }else{
                    return false;
                }
                break;
            case R.id.action_delete:
                ConfirmDeletionDialogFragment confirmDeletionDialogFragment = new ConfirmDeletionDialogFragment();
                confirmDeletionDialogFragment.setConfirmDeletionDialogListener(new ConfirmDeletionDialogFragment.ConfirmDeletionDialogListener() {
                    @Override
                    public void onYesClick() {
                        if(mAccountID > 0) {
                            mAccountPersist.delete(mAccountID, mWritableDatabase);
                        }
                        finish();
                    }

                    @Override
                    public void onNoClick() {

                    }
                });
                confirmDeletionDialogFragment.show(getSupportFragmentManager(), "Confirm delete account");
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    private Boolean validateInput(){
        if(isEditTextEmpty(mEditTextName)){
            mEditTextName.requestFocus();
            Toast.makeText(this, getResources().getString(R.string.toast_name_is_required), Toast.LENGTH_LONG).show();
            return false;
        }
        if(isEditTextEmpty(mEditTextNumber)){
            mEditTextNumber.requestFocus();
            Toast.makeText(this, getResources().getString(R.string.toast_number_is_required), Toast.LENGTH_LONG).show();
            return false;
        }
        if(!isEditTextNumeric(mEditTextBalance)){
            mEditTextBalance.requestFocus();
            Toast.makeText(this, getResources().getString(R.string.toast_balance_have_to_be_numeric), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
