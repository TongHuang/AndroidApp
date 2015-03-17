package com.quebecfresh.androidapp.simplebudget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.quebecfresh.androidapp.simplebudget.model.Account;
import com.quebecfresh.androidapp.simplebudget.model.ExpenseBudget;
import com.quebecfresh.androidapp.simplebudget.model.IncomeBudget;
import com.quebecfresh.androidapp.simplebudget.persist.AccountPersist;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;
import com.quebecfresh.androidapp.simplebudget.persist.ExpenseBudgetPersist;
import com.quebecfresh.androidapp.simplebudget.persist.IncomeBudgetPersist;

import java.math.BigDecimal;
import java.util.List;


public class MainActivity extends ActionBarActivity {




    public void showExpenseList(View view){
        Intent intent = new Intent(this, ExpenseActivity.class);
        startActivity(intent);
    }

    public void editAccounts(View view){
        Intent intent = new Intent(this, InitializeAccountActivity.class);
        startActivity(intent);
    }

    public void editIncomeBudget(View view){
        Intent intent = new Intent(this, InitializeIncomeBudgetActivity.class);
        startActivity(intent);
    }

    public void editExpenseBudget(View view){
        Intent intent =  new Intent(this,InitializeExpenseBudgetActivity.class);
        startActivity(intent);
    }

    public void newExpense(View view){
        Intent intent = new Intent(this, NewExpenseActivity.class);
        startActivity(intent);
    }

    public void newIncome(View view){}


    public void demo(View view){
        Intent intent = new Intent(this, DemoActivity.class);
        startActivity(intent);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = this.getSharedPreferences(getString(R.string.preference_file), Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putBoolean(getString(R.string.initialize_done), false);
//        editor.commit();




    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences preferences = this.getSharedPreferences(getString(R.string.preference_file), Context.MODE_PRIVATE);
        Boolean initializeDone = preferences.getBoolean(getString(R.string.initialize_done), false);
        if(initializeDone == false) {
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
            this.finish();
        }else{
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
            AccountPersist  accountPersist = new AccountPersist(sqLiteDatabase);
            List<Account> accounts = accountPersist.readAll();
            BigDecimal accountTotal = new BigDecimal("0");
            for(int i = 0; i < accounts.size(); i++){
                accountTotal = accountTotal.add(accounts.get(i).getBalance());
            }

            ExpenseBudgetPersist expenseCategoryPersist = new ExpenseBudgetPersist(sqLiteDatabase);
            List<ExpenseBudget> expenseCategoryList = expenseCategoryPersist.readAll();
            BigDecimal expenseBudgetTotal = new BigDecimal("0");
            for(int i = 0; i < expenseCategoryList.size(); i++){
                expenseBudgetTotal = expenseBudgetTotal.add(expenseCategoryList.get(i).getBudgetAmount());
            }

            IncomeBudgetPersist incomeCategoryPersist = new IncomeBudgetPersist(sqLiteDatabase);
            List<IncomeBudget> incomeCategoryList = incomeCategoryPersist.readAll();
            BigDecimal incomeBudgetTotal = new BigDecimal("0");
            for(IncomeBudget incomeCategory : incomeCategoryList){
                incomeBudgetTotal = incomeBudgetTotal.add(incomeCategory.getBudgetAmount());
            }


            BigDecimal max = accountTotal.max(expenseBudgetTotal).max(incomeBudgetTotal);

            ProgressBar progressBarBalance = (ProgressBar)this.findViewById(R.id.progressBarBalance);
            TextView textViewProgressBarBalanceCenter= (TextView)this.findViewById(R.id.textViewProgressBarBalanceCenter);
            progressBarBalance.setMax(max.intValue());
            progressBarBalance.setProgress(accountTotal.intValue());
            textViewProgressBarBalanceCenter.setText(accountTotal.toString());

            ProgressBar progressBarExpenseBudget = (ProgressBar)this.findViewById(R.id.progressBarExpenseBudget);
            TextView textViewProgressBarExpenseBudgetCenter = (TextView)this.findViewById(R.id.textViewProgressBarExpenseBudgetCenter);
            progressBarExpenseBudget.setMax(max.intValue());
            progressBarExpenseBudget.setProgress(expenseBudgetTotal.intValue());
            textViewProgressBarExpenseBudgetCenter.setText(expenseBudgetTotal.toString());

            ProgressBar progressBarIncomeBudget=(ProgressBar)this.findViewById(R.id.progressBarIncomeBudget);
            TextView textViewProgressBarIncomeBudgetCenter = (TextView)this.findViewById(R.id.textViewProgressBarIncomeBudgetCenter);
            progressBarIncomeBudget.setMax(max.intValue());
            progressBarIncomeBudget.setProgress(incomeBudgetTotal.intValue());
            textViewProgressBarIncomeBudgetCenter.setText(incomeBudgetTotal.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id) {
            case R.id.action_reInitialize:
                SharedPreferences preferences = this.getSharedPreferences(getString(R.string.initialize_done), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean(getString(R.string.initialize_done), false);
                editor.putLong(getString(R.string.initialize_date), 0);
                editor.commit();
                Intent intent = new Intent(this, WelcomeActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
