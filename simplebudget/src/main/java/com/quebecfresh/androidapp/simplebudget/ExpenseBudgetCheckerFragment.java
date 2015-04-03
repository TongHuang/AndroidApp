package com.quebecfresh.androidapp.simplebudget;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.math.BigDecimal;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseBudgetCheckerFragment extends Fragment {


    private BigDecimal mBudgetAmount = new BigDecimal("0");
    private BigDecimal mUsedAmount = new BigDecimal("0");
    private BigDecimal mUnusedBalance = new BigDecimal("0");

    public ExpenseBudgetCheckerFragment() {
        // Required empty public constructor
    }

    public BigDecimal getBudgetAmount() {
        return mBudgetAmount;
    }

    public void setBudgetAmount(BigDecimal budgetAmount) {
        this.mBudgetAmount = budgetAmount;
    }

    public BigDecimal getUsedAmount() {
        return mUsedAmount;
    }

    public void setUsedAmount(BigDecimal usedAmount) {
        this.mUsedAmount = usedAmount;
    }

    public BigDecimal getUnusedBalance() {
        return mUnusedBalance;
    }

    public void setUnusedBalance(BigDecimal unusedBalance) {
        this.mUnusedBalance = unusedBalance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_expense_budget_checker, container, false);
        TextView textViewBudgetAmount=(TextView)view.findViewById(R.id.textViewBudgetAmount);
        textViewBudgetAmount.setText("Your budget amount " + mBudgetAmount.toString());
        TextView textViewUsedAmount = (TextView)view.findViewById(R.id.textViewUsedAmount);
        textViewUsedAmount.setText("You have used " + mUsedAmount.toString());
        TextView textViewUnusedBalance = (TextView)view.findViewById(R.id.textViewUnusedBalance);
        textViewUnusedBalance.setText("Your unused balance " + mUnusedBalance.toString());
        return view;
    }


}
