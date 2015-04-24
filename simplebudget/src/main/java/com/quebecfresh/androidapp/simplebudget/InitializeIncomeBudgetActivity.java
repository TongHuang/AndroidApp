package com.quebecfresh.androidapp.simplebudget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.quebecfresh.androidapp.simplebudget.model.Cycle;
import com.quebecfresh.androidapp.simplebudget.model.IncomeBudget;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;
import com.quebecfresh.androidapp.simplebudget.persist.IncomeBudgetPersist;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class InitializeIncomeBudgetActivity extends ActionBarActivity {

    private SQLiteDatabase mReadableDatabase;
    private IncomeBudgetPersist mIncomeBudgetPersist;

    ExpandableIncomeBudgetFragment mExpandableIncomeBudgetFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialize_income_budget);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        mReadableDatabase = databaseHelper.getReadableDatabase();
        mIncomeBudgetPersist = new IncomeBudgetPersist();

        mExpandableIncomeBudgetFragment = new ExpandableIncomeBudgetFragment();
    }



    @Override
    protected void onResume() {
        mExpandableIncomeBudgetFragment.setIncomeBudgetList(mIncomeBudgetPersist.readAll(mReadableDatabase));
        FragmentTransaction fragmentTransaction =  getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerIncomeBudget, mExpandableIncomeBudgetFragment);
        fragmentTransaction.commit();

        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_initialize_income_budget, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_add:
                Intent intent = new Intent(this, EditIncomeBudgetActivity.class);
                startActivity(intent);
                break;
            case R.id.action_done:
                SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(getString(R.string.initialize_income_budget_done), true);
                editor.commit();
                this.finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
