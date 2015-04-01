package com.quebecfresh.androidapp.simplebudget;

import android.content.Intent;
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


    public void startUseBudget(View view) {
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

        ExpenseBudgetPersist expenseBudgetPersist = new ExpenseBudgetPersist(this);
        IncomeBudgetPersist incomeBudgetPersist = new IncomeBudgetPersist(this);
        mExpenseBudgetList = expenseBudgetPersist.readAllBudgetAmountNotZero();
        mIncomeBudgetList = incomeBudgetPersist.readAllBudgetAmountNotZero();

        Spinner spinnerCycle = (Spinner) findViewById(R.id.spinnerCycle);
        CycleSpinnerAdapter cycleSpinnerAdapter = new CycleSpinnerAdapter(this, Cycle.values());
        spinnerCycle.setAdapter(cycleSpinnerAdapter);
        spinnerCycle.setSelection(3);
        spinnerCycle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setSurplusText((Cycle) parent.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mButtonIncomeBudget = (Button) findViewById(R.id.buttonIncomeBudget);
        mButtonExpenseBudget = (Button) findViewById(R.id.buttonExpenseBudget);
        mTextViewSurplus = (TextView) findViewById(R.id.textViewSurplus);

        setSurplusText((Cycle) spinnerCycle.getSelectedItem());
    }

    private void setSurplusText(Cycle selectedCycle) {
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
