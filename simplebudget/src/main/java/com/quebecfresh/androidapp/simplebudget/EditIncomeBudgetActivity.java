package com.quebecfresh.androidapp.simplebudget;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.quebecfresh.androidapp.simplebudget.model.Account;
import com.quebecfresh.androidapp.simplebudget.model.Cycle;
import com.quebecfresh.androidapp.simplebudget.model.IncomeBudget;
import com.quebecfresh.androidapp.simplebudget.persist.AccountPersist;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;
import com.quebecfresh.androidapp.simplebudget.persist.IncomeBudgetPersist;

import java.math.BigDecimal;
import java.util.List;


public class EditIncomeBudgetActivity extends ActionBarActivity {

    private Long rowID;
    private EditText editTextName;
    private Button buttonAccount;
    private Spinner spinnerIncomeGroup;
    private Spinner spinnerCycle;
    private EditText editTextAmount;
    private EditText editTextNote;
    private CheckBox checkBoxRollover;
    private IncomeBudget incomeBudget;
    private List<Account> accountList;
    private IncomeBudgetPersist incomeBudgetPersist;
    private AccountPersist accountPersist;

    public void chooseIncomeAccount(View view) {
        ChooseAccountDialogFragment chooseAccountDialogFragment = new ChooseAccountDialogFragment();
        chooseAccountDialogFragment.setAccountList(this.accountList);
        chooseAccountDialogFragment.setAccountChooseListener(new ChooseAccountDialogFragment.AccountChooseListener() {
            @Override
            public void choose(Account account) {
                incomeBudget.setAccount(account);
                buttonAccount.setText(account.getName() + ":" + account.getBalance().toString());
            }
        });

        chooseAccountDialogFragment.show(this.getSupportFragmentManager(), "Choose account");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_income_budget);

        Intent intent = getIntent();
        rowID = intent.getLongExtra(InitializeIncomeBudgetActivity.EXTRA_INCOME_CATEGORY_ID, 0);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        accountPersist = new AccountPersist(this);
        accountList = accountPersist.readAll();
        incomeBudgetPersist = new IncomeBudgetPersist(this);
        if(rowID > 0) {
            incomeBudget = incomeBudgetPersist.read(rowID);
        }else{
            incomeBudget = new IncomeBudget();
        }

        editTextName = (EditText)this.findViewById(R.id.editTextName);
        editTextName.setText(incomeBudget.getName());
        buttonAccount = (Button)this.findViewById(R.id.buttonAccount);
        if(incomeBudget.getAccount() != null){
            buttonAccount.setText(incomeBudget.getAccount().getName() + " : " + incomeBudget.getAccount().getBalance().toString());
        }
        spinnerIncomeGroup  = (Spinner)this.findViewById(R.id.spinnerGroup);
        IncomeCategoryGroupSpinnerAdapter incomeCategoryGroupSpinnerAdapter = new IncomeCategoryGroupSpinnerAdapter(this, IncomeBudget.INCOME_BUDGET_CATEGORY.values());
        spinnerIncomeGroup.setAdapter(incomeCategoryGroupSpinnerAdapter);
        spinnerIncomeGroup.setSelection(incomeBudget.getIncomeBudgetCategory().ordinal());
        spinnerCycle = (Spinner)this.findViewById(R.id.spinnerCycle);
        CycleSpinnerAdapter adapter = new CycleSpinnerAdapter(this,  Cycle.values());
        spinnerCycle.setAdapter(adapter);
        spinnerCycle.setSelection(incomeBudget.getCycle().ordinal());
        editTextAmount = (EditText)this.findViewById(R.id.editTextAmount);
        editTextAmount.setText(incomeBudget.getBudgetAmount().toString());
        editTextNote = (EditText)this.findViewById(R.id.editTextNote);
        editTextNote.setText(incomeBudget.getNote());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_income_budget, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id){

            case R.id.action_save:
                incomeBudget.setName(editTextName.getText().toString());
                incomeBudget.setIncomeBudgetCategory((IncomeBudget.INCOME_BUDGET_CATEGORY) spinnerIncomeGroup.getSelectedItem());
                incomeBudget.setCycle((Cycle) spinnerCycle.getSelectedItem());
                incomeBudget.setBudgetAmount(new BigDecimal(editTextAmount.getText().toString()));
                incomeBudget.setNote(editTextNote.getText().toString());
                incomeBudgetPersist.save(incomeBudget);
                break;
        }
        this.finish();
        return super.onOptionsItemSelected(item);
    }

    public void showWhatIsRollover(View view){
        WhatIsThisDialogFragment dialogFragment = new WhatIsThisDialogFragment();
        dialogFragment.setTitle(getString(R.string.what_is_roll_over_title));
        dialogFragment.setMessage(getString(R.string.what_is_roll_over));
        dialogFragment.show(getSupportFragmentManager(), "tag");
    }
}
