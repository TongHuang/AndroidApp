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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.quebecfresh.androidapp.simplebudget.model.Cycle;
import com.quebecfresh.androidapp.simplebudget.model.ExpenseBudget;
import com.quebecfresh.androidapp.simplebudget.model.IncomeBudget;
import com.quebecfresh.androidapp.simplebudget.model.Utils;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;
import com.quebecfresh.androidapp.simplebudget.persist.ExpenseBudgetPersist;
import com.quebecfresh.androidapp.simplebudget.persist.IncomeBudgetPersist;

import java.math.BigDecimal;
import java.util.List;


public class BudgetReviewActivity extends ActionBarActivity {

    private List<ExpenseBudget> mExpenseBudgetList;
    private List<IncomeBudget> mIncomeBudgetList;
    private Button mButtonIncomeBudget;
    private Button mButtonExpenseBudget;
    private TextView mTextViewSurplus;


    public void showIncomeBudget(View view){
        ChooseIncomeBudgetDialogFragment chooseIncomeBudgetDialogFragment = new ChooseIncomeBudgetDialogFragment();
        chooseIncomeBudgetDialogFragment.setIncomeBudgetList(mIncomeBudgetList);
        chooseIncomeBudgetDialogFragment.show(getSupportFragmentManager(), "Show incomeBudget");
    }

    public void showExpenseBudget(View view){
        ChooseExpenseBudgetDialogFragment chooseExpenseBudgetDialogFragment = new ChooseExpenseBudgetDialogFragment();
        chooseExpenseBudgetDialogFragment.setExpenseBudgetList(mExpenseBudgetList);
        chooseExpenseBudgetDialogFragment.show(getSupportFragmentManager(), "Show expenseBudget");
    }

    public void startUseBudget(View view) {
        SharedPreferences preferences = this.getSharedPreferences(getString(R.string.preference_file), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(getString(R.string.initialize_done), true);
        editor.commit();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void fineTuneBudget(View view) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_review);


        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase readableDatabase = databaseHelper.getReadableDatabase();

        ExpenseBudgetPersist expenseBudgetPersist = new ExpenseBudgetPersist();
        IncomeBudgetPersist incomeBudgetPersist = new IncomeBudgetPersist();
        mExpenseBudgetList = expenseBudgetPersist.readAllBudgetAmountNotZero(readableDatabase);
        mIncomeBudgetList = incomeBudgetPersist.readAllBudgetAmountNotZero(readableDatabase);

        Spinner spinnerCycle = (Spinner) findViewById(R.id.spinnerCycle);
        CycleSpinnerAdapter cycleSpinnerAdapter = new CycleSpinnerAdapter(Cycle.values(),this);
        spinnerCycle.setAdapter(cycleSpinnerAdapter);
        spinnerCycle.setSelection(3);
        spinnerCycle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateView((Cycle) parent.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mButtonIncomeBudget = (Button) findViewById(R.id.buttonIncomeBudget);
        mButtonExpenseBudget = (Button) findViewById(R.id.buttonExpenseBudget);
        mTextViewSurplus = (TextView) findViewById(R.id.textViewSurplus);

        updateView((Cycle) spinnerCycle.getSelectedItem());
    }

    private void updateView(Cycle selectedCycle) {
        BigDecimal totalIncome = Utils.calTotalIncomeBudgetAmount(mIncomeBudgetList, selectedCycle);
        BigDecimal totalExpense = Utils.calTotalExpenseBudgetAmount(mExpenseBudgetList, selectedCycle);
        BigDecimal surplus = totalIncome.subtract(totalExpense);
        mButtonIncomeBudget.setText(totalIncome.toString());
        mButtonExpenseBudget.setText(totalExpense.toString());
        mTextViewSurplus.setText(surplus.toString());
        if (surplus.doubleValue() > 0) {
            mTextViewSurplus.setTextColor(getResources().getColor(R.color.green));
        } else {
            mTextViewSurplus.setTextColor(getResources().getColor(R.color.red));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_budget_review, menu);


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
