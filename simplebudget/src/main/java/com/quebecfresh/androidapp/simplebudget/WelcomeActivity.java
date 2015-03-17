package com.quebecfresh.androidapp.simplebudget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.quebecfresh.androidapp.simplebudget.model.Account;
import com.quebecfresh.androidapp.simplebudget.model.Cycle;
import com.quebecfresh.androidapp.simplebudget.model.ExpenseBudget;
import com.quebecfresh.androidapp.simplebudget.model.IncomeBudget;
import com.quebecfresh.androidapp.simplebudget.persist.AccountPersist;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;
import com.quebecfresh.androidapp.simplebudget.persist.ExpenseBudgetPersist;
import com.quebecfresh.androidapp.simplebudget.persist.IncomeBudgetPersist;

import java.math.BigDecimal;
import java.util.List;


public class WelcomeActivity extends ActionBarActivity {

    public void budgetIncome(View view) {
        Intent intent = new Intent(this, InitializeIncomeBudgetActivity.class);
        this.startActivity(intent);
    }

    public void budgetExpense(View view) {
        Intent intent = new Intent(this, InitializeExpenseBudgetActivity.class);
        this.startActivity(intent);
    }

    public void initializeAccount(View view) {
        Intent intent = new Intent(this, InitializeAccountActivity.class);
        this.startActivity(intent);
    }

    public void go(View view) {

        SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.preference_file), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(getString(R.string.initialize_done), true);
        editor.putLong(getString(R.string.initialize_date), System.currentTimeMillis());
        editor.commit();
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        ExpenseBudgetPersist expenseBudgetPersist = new ExpenseBudgetPersist(dbHelper.getWritableDatabase());
        expenseBudgetPersist.fillAllEnvelopes();
        this.finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences preferences = this.getSharedPreferences(getString(R.string.preference_file), Context.MODE_PRIVATE);

        Boolean initializeAccountDone = preferences.getBoolean(getString(R.string.initialize_account_done), false);
        Boolean initializeIncomeBudgetDone = preferences.getBoolean(getString(R.string.initialize_income_budget_done), false);
        Boolean initializeExpenseBudgetDone = preferences.getBoolean(getString(R.string.initialize_expense_budget_done), false);

        BigDecimal accountTotal = new BigDecimal("0");
        BigDecimal incomeTotal = new BigDecimal("0");
        BigDecimal expenseTotal = new BigDecimal("0");

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        if (initializeAccountDone) {
            AccountPersist accountPersist = new AccountPersist(dbHelper.getReadableDatabase());
            List<Account> accountList = accountPersist.readAll();

            for (int i = 0; i < accountList.size(); i++) {
                accountTotal = accountTotal.add(accountList.get(i).getBalance());
            }
        }


        if (initializeIncomeBudgetDone) {
            IncomeBudgetPersist incomeCategoryPersist = new IncomeBudgetPersist(dbHelper.getReadableDatabase());
            List<IncomeBudget> incomeCategoryList = incomeCategoryPersist.readAll();

            for (int i = 0; i < incomeCategoryList.size(); i++) {
                incomeTotal = incomeTotal.add(incomeCategoryList.get(i).convertBudgetAmountTo(Cycle.Yearly));
            }
        }

        Button buttonInitializeExpense = (Button) this.findViewById(R.id.buttonInitializeExpense);
        if (initializeExpenseBudgetDone) {
            ExpenseBudgetPersist expenseCategoryPersist = new ExpenseBudgetPersist(dbHelper.getReadableDatabase());
            List<ExpenseBudget> expenseCategoryList = expenseCategoryPersist.readAll();

            for (int i = 0; i < expenseCategoryList.size(); i++) {
                expenseTotal = expenseTotal.add(expenseCategoryList.get(i).convertBudgetAmountTo(Cycle.Yearly));
            }
        }

        if (accountTotal.compareTo(new BigDecimal("0")) != 0 || incomeTotal.compareTo(new BigDecimal("0")) != 0 || expenseTotal.compareTo(new BigDecimal("0")) != 0) {
            BigDecimal yearlySurplus = incomeTotal.subtract(expenseTotal);
            String welcomeBudgetOverviewMessage = getString(R.string.welcome_budget_overview_message);
            TextView textViewWelcome = (TextView) this.findViewById(R.id.textViewWelcome);
            welcomeBudgetOverviewMessage = String.format(welcomeBudgetOverviewMessage, accountTotal.toString(), incomeTotal.toString(), expenseTotal.toString(), yearlySurplus.toString());
            textViewWelcome.setText(welcomeBudgetOverviewMessage);
            if (yearlySurplus.compareTo(new BigDecimal("0")) >= 0) {
                textViewWelcome.setTextColor(getResources().getColor(R.color.light_blue));
            } else {
                textViewWelcome.setTextColor(getResources().getColor(R.color.light_red));
            }
        }

        Button buttonReadyToGo = (Button) this.findViewById(R.id.buttonReadyToGo);
        if (initializeAccountDone && initializeIncomeBudgetDone && initializeExpenseBudgetDone) {
            buttonReadyToGo.setEnabled(true);
        } else {
            buttonReadyToGo.setEnabled(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
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
