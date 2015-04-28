package com.quebecfresh.androidapp.simplebudget;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;

import com.quebecfresh.androidapp.simplebudget.model.BudgetInfo;
import com.quebecfresh.androidapp.simplebudget.model.Cycle;
import com.quebecfresh.androidapp.simplebudget.model.Utils;

import java.util.Calendar;


public class EditBudgetInfoActivity extends ActionBarActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_budget_info);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearLayoutInsideScrollView);
        linearLayout.setMinimumHeight(displayMetrics.heightPixels*8/10);

        BudgetInfo  budgetInfo = new BudgetInfo();
        budgetInfo.setBeginDate(System.currentTimeMillis());
        budgetInfo.setEndDate(Utils.getEndOfNextCycle(Cycle.Yearly, Calendar.getInstance()));

        Button buttonBeginDate = (Button)findViewById(R.id.buttonBeginDate);
        buttonBeginDate.setText(budgetInfo.getBeginDateLabel() + ("  (Now)"));
        Button buttonEndDate = (Button)findViewById(R.id.buttonEndDate);
        buttonEndDate.setText(budgetInfo.getEndDateLabel());


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_budget_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_next) {
            Intent intent = new Intent(this,InitializeAccountActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
