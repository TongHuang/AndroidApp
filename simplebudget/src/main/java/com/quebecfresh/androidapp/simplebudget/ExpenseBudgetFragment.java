package com.quebecfresh.androidapp.simplebudget;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.quebecfresh.androidapp.simplebudget.model.Cycle;
import com.quebecfresh.androidapp.simplebudget.model.ExpenseBudget;
import com.quebecfresh.androidapp.simplebudget.model.Utils;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseBudgetFragment extends Fragment {
    private List<ExpenseBudget> mExpenseBudgetList;
    private Cycle mSelectedCycle = Cycle.Monthly;
    private TextView mTextViewTotal;

    public ExpenseBudgetFragment() {
        // Required empty public constructor
    }


    public List<ExpenseBudget> getExpenseBudgetList() {
        return mExpenseBudgetList;
    }

    public void setExpenseBudgetList(List<ExpenseBudget> expenseBudgetList) {
        this.mExpenseBudgetList = expenseBudgetList;
    }

    public Cycle getSelectedCycleCycle() {
        return mSelectedCycle;
    }

    public void setSelectedCycle(Cycle cycle) {
        this.mSelectedCycle = cycle;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expense_budget, container, false);

        ListView listViewExpenseBudgets = (ListView) view.findViewById(R.id.listViewExpenseBudgets);
        ExpenseBudgetListViewAdapter expenseBudgetListViewAdapter = new ExpenseBudgetListViewAdapter(mExpenseBudgetList, inflater.getContext());
        listViewExpenseBudgets.setAdapter(expenseBudgetListViewAdapter);
        Spinner spinnerCycle = (Spinner) view.findViewById(R.id.spinnerCycle);
        CycleSpinnerAdapter cycleSpinnerAdapter = new CycleSpinnerAdapter(Cycle.values(),inflater.getContext());
        spinnerCycle.setAdapter(cycleSpinnerAdapter);
        spinnerCycle.setSelection(mSelectedCycle.ordinal());
        spinnerCycle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectedCycle = (Cycle)parent.getSelectedItem();
                mTextViewTotal.setText(Utils.calTotalExpenseBudgetAmount(mExpenseBudgetList, mSelectedCycle).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mTextViewTotal = (TextView) view.findViewById(R.id.textViewTotal);
        mTextViewTotal.setText(Utils.calTotalExpenseBudgetAmount(mExpenseBudgetList, mSelectedCycle).toString());
        return view;

    }


}
