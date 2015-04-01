package com.quebecfresh.androidapp.simplebudget;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.quebecfresh.androidapp.simplebudget.model.Account;
import com.quebecfresh.androidapp.simplebudget.model.Cycle;
import com.quebecfresh.androidapp.simplebudget.model.IncomeBudget;
import com.quebecfresh.androidapp.simplebudget.persist.AccountPersist;
import com.quebecfresh.androidapp.simplebudget.persist.IncomeBudgetPersist;

import java.math.BigDecimal;
import java.util.List;


public class EditIncomeBudgetActivity extends ActionBarActivity {

    private Long mRowID;
    private EditText mEditTextName;
    private Button mButtonAccount;
    private Spinner mSpinnerIncomeGroup;
    private Spinner mSpinnerCycle;
    private EditText mEditTextAmount;
    private EditText mEditTextNote;
    private IncomeBudget mIncomeBudget;
    private List<Account> mAccountList;
    private IncomeBudgetPersist mIncomeBudgetPersist;

    public void chooseIncomeAccount(View view) {
        ChooseAccountDialogFragment chooseAccountDialogFragment = new ChooseAccountDialogFragment();
        chooseAccountDialogFragment.setAccountList(this.mAccountList);
        chooseAccountDialogFragment.setAccountChooseListener(new ChooseAccountDialogFragment.AccountChooseListener() {
            @Override
            public void choose(Account account) {
                mIncomeBudget.setAccount(account);
                mButtonAccount.setText(account.getName());
            }
        });

        chooseAccountDialogFragment.show(this.getSupportFragmentManager(), "Choose account");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_income_budget);

        Intent intent = getIntent();
        mRowID = intent.getLongExtra(ExpandableIncomeBudgetFragment.EXTRA_INCOME_BUDGET_ID, -1);
        AccountPersist accountPersist = new AccountPersist(this);
        mAccountList = accountPersist.readAll();
        mIncomeBudgetPersist = new IncomeBudgetPersist(this);
        if(mRowID > 0) {
            mIncomeBudget = mIncomeBudgetPersist.read(mRowID);
        }else{
            mIncomeBudget = new IncomeBudget();
        }

        mEditTextName = (EditText)this.findViewById(R.id.editTextName);
        mEditTextName.setText(mIncomeBudget.getName());
        mButtonAccount = (Button)this.findViewById(R.id.buttonAccount);
        if(mIncomeBudget.getAccount() != null){
            mButtonAccount.setText(mIncomeBudget.getAccount().getName());
        }
        mSpinnerIncomeGroup = (Spinner)this.findViewById(R.id.spinnerGroup);
        IncomeCategoryGroupSpinnerAdapter incomeCategoryGroupSpinnerAdapter = new IncomeCategoryGroupSpinnerAdapter(this, IncomeBudget.INCOME_BUDGET_CATEGORY.values());
        mSpinnerIncomeGroup.setAdapter(incomeCategoryGroupSpinnerAdapter);
        mSpinnerIncomeGroup.setSelection(mIncomeBudget.getIncomeBudgetCategory().ordinal());
        mSpinnerCycle = (Spinner)this.findViewById(R.id.spinnerCycle);
        CycleSpinnerAdapter adapter = new CycleSpinnerAdapter(this,  Cycle.values());
        mSpinnerCycle.setAdapter(adapter);
        mSpinnerCycle.setSelection(mIncomeBudget.getCycle().ordinal());
        mEditTextAmount = (EditText)this.findViewById(R.id.editTextAmount);
        mEditTextAmount.setText(mIncomeBudget.getBudgetAmount().toString());
        mEditTextNote = (EditText)this.findViewById(R.id.editTextNote);
        mEditTextNote.setText(mIncomeBudget.getNote());
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
                mIncomeBudget.setName(mEditTextName.getText().toString());
                mIncomeBudget.setIncomeBudgetCategory((IncomeBudget.INCOME_BUDGET_CATEGORY) mSpinnerIncomeGroup.getSelectedItem());
                mIncomeBudget.setCycle((Cycle) mSpinnerCycle.getSelectedItem());
                mIncomeBudget.setBudgetAmount(new BigDecimal(mEditTextAmount.getText().toString()));
                mIncomeBudget.setNote(mEditTextNote.getText().toString());
                mIncomeBudgetPersist.save(mIncomeBudget);
                break;
        }
        this.finish();
        return super.onOptionsItemSelected(item);
    }


}
