package com.quebecfresh.androidapp.simplebudget;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.quebecfresh.androidapp.simplebudget.model.Cycle;
import com.quebecfresh.androidapp.simplebudget.persist.IncomeBudgetPersist;


public class IncomeBudgetActivity extends ActionBarActivity {
    private Cycle mSelectedCycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_budget);

        mSelectedCycle = Cycle.valueOf(getIntent().getStringExtra(BudgetOverviewActivity.EXTRA_SELECTED_CYCLE));

        IncomeBudgetPersist incomeBudgetPersist = new IncomeBudgetPersist(this);
        IncomeBudgetFragment incomeBudgetFragment = new IncomeBudgetFragment();
        incomeBudgetFragment.setIncomeBudgetList(incomeBudgetPersist.readAllBudgetAmountNotZero());
        incomeBudgetFragment.setSelectedCycle(mSelectedCycle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainerIncomeBudgetList, incomeBudgetFragment);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_income_budget, menu);
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
