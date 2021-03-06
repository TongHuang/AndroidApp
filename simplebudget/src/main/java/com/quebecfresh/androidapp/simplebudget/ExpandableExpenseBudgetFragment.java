package com.quebecfresh.androidapp.simplebudget;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.quebecfresh.androidapp.simplebudget.model.Budget;
import com.quebecfresh.androidapp.simplebudget.model.Cycle;
import com.quebecfresh.androidapp.simplebudget.model.ExpenseBudget;
import com.quebecfresh.androidapp.simplebudget.model.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpandableExpenseBudgetFragment extends Fragment {
    public static final String EXTRA_EXPENSE_BUDGET_ID = "com.quebecfresh.androidapp.simplebudget.expense.budget.id";


    private ExpandableListView mExpandableListViewExpenseBudget;
    private List<ExpenseBudget> mExpenseBudgetList;
    private TextView mTextViewTotal;


    public ExpandableExpenseBudgetFragment() {
        // Required empty public constructor
    }


    public List<ExpenseBudget> getExpenseBudgetList() {
        return mExpenseBudgetList;
    }

    public void setExpenseBudgetList(List<ExpenseBudget> expenseBudgets) {
        this.mExpenseBudgetList = expenseBudgets;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_expandable_expense_budget, container, false);

        List<ExpenseBudget> foods = new ArrayList<ExpenseBudget>();
        List<ExpenseBudget> shelters = new ArrayList<ExpenseBudget>();
        List<ExpenseBudget> utilities = new ArrayList<ExpenseBudget>();
        List<ExpenseBudget> transportation = new ArrayList<ExpenseBudget>();
        List<ExpenseBudget> gifts = new ArrayList<ExpenseBudget>();
        List<ExpenseBudget> others = new ArrayList<ExpenseBudget>();
        ExpenseBudget category;
        for (int i = 0; i < mExpenseBudgetList.size(); i++) {
            category = mExpenseBudgetList.get(i);
            switch (category.getExpenseBudgetCategory()) {
                case FOODS:
                    foods.add(category);
                    break;
                case SHELTER:
                    shelters.add(category);
                    break;
                case UTILITIES:
                    utilities.add(category);
                    break;
                case TRANSPORTATION:
                    transportation.add(category);
                    break;
                case GIFTS:
                    gifts.add(category);
                    break;
                case OTHERS:
                    others.add(category);
                    break;
            }
        }

        List<String> group = new ArrayList<String>();
        group.add(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.FOODS.getLabel(inflater.getContext()));
        group.add(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.SHELTER.getLabel(inflater.getContext()));
        group.add(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.UTILITIES.getLabel(inflater.getContext()));
        group.add(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.TRANSPORTATION.getLabel(inflater.getContext()));
        group.add(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.GIFTS.getLabel(inflater.getContext()));
        group.add(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.OTHERS.getLabel(inflater.getContext()));

        HashMap<String, List<ExpenseBudget>> expenseBudgetMap = new HashMap<String, List<ExpenseBudget>>();
        expenseBudgetMap.put(group.get(0), foods);
        expenseBudgetMap.put(group.get(1), shelters);
        expenseBudgetMap.put(group.get(2), utilities);
        expenseBudgetMap.put(group.get(3), transportation);
        expenseBudgetMap.put(group.get(4),gifts);
        expenseBudgetMap.put(group.get(5), others);


        //Spinner have to initialize before mTextViewTotal, because mTextViewTotal invoke getSelectedItem
        Spinner spinnerCycle = (Spinner)view.findViewById(R.id.spinnerCycle);
        CycleSpinnerAdapter cycleSpinnerAdapter = new CycleSpinnerAdapter(Cycle.values(),inflater.getContext());
        spinnerCycle.setAdapter(cycleSpinnerAdapter);
        spinnerCycle.setSelection(3);
        spinnerCycle.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mTextViewTotal.setText(Utils.calTotalExpenseBudgetAmount(mExpenseBudgetList, (Cycle)parent.getSelectedItem()).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mTextViewTotal =  (TextView)view.findViewById(R.id.textViewTotal);
        mTextViewTotal.setText(Utils.calTotalExpenseBudgetAmount(mExpenseBudgetList, (Cycle)spinnerCycle.getSelectedItem()).toString());

        ExpenseBudgetExpandableListViewAdapter adapter = new ExpenseBudgetExpandableListViewAdapter(group, expenseBudgetMap, inflater.getContext());
        mExpandableListViewExpenseBudget = (ExpandableListView) view.findViewById(R.id.expandableListViewExpenseBudget);
        mExpandableListViewExpenseBudget.setAdapter(adapter);

        mExpandableListViewExpenseBudget.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(getActivity(), EditExpenseBudgetActivity.class);
                intent.putExtra(EXTRA_EXPENSE_BUDGET_ID, id);
                startActivity(intent);
                return true;
            }
        });

       return view;
    }


}
