package com.quebecfresh.androidapp.simplebudget;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.math.BigDecimal;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseBudgetCheckerFragment extends Fragment {


    private BigDecimal mBudgetAmount = new BigDecimal("0");
    private BigDecimal mUsedAmount = new BigDecimal("0");
    private BigDecimal mUnusedBalance = new BigDecimal("0");
    private String mDateLabel="21.03.2015";

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

    public String getDateLabel() {
        return mDateLabel;
    }

    public void setDateLabel(String dateLabel) {
        this.mDateLabel = dateLabel;
    }

    public void setUnusedBalance(BigDecimal unusedBalance) {
        this.mUnusedBalance = unusedBalance;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_expense_budget_checker, container, false);

        TextView textViewStartDate = (TextView)view.findViewById(R.id.textViewStartDate);
        textViewStartDate.setText(getResources().getString(R.string.since) + " " + mDateLabel);

        BigDecimal max = mBudgetAmount.max(mUsedAmount);
        ProgressBar progressBarBudgetAmount=(ProgressBar)view.findViewById(R.id.progressBarBudgetAmount);
        progressBarBudgetAmount.setMax(max.intValue());
        progressBarBudgetAmount.setProgress(mBudgetAmount.intValue());

        ProgressBar progressBarUsedAmount = (ProgressBar)view.findViewById(R.id.progressBarUsedAmount);
        progressBarUsedAmount.setMax(max.intValue());
        progressBarUsedAmount.setProgress(mUsedAmount.intValue());
        return view;
    }


}
