package com.quebecfresh.androidapp.simplebudget;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.quebecfresh.androidapp.simplebudget.model.Cycle;
import com.quebecfresh.androidapp.simplebudget.model.IncomeBudget;
import com.quebecfresh.androidapp.simplebudget.model.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpandableIncomeBudgetFragment extends Fragment {

    public static final String EXTRA_INCOME_BUDGET_ID = "com.quebecfresh.androidapp.simplebudget.id";
    private  ExpandableListView mExpandableListViewIncomeBudget;
    private List<IncomeBudget> mIncomeBudgetList;
    private  TextView mTextViewTotal;
    private Spinner mCycleSpinner;

    public ExpandableIncomeBudgetFragment() {
        // Required empty public constructor
    }

    public List<IncomeBudget> getIncomeBudgetList() {
        return mIncomeBudgetList;
    }

    public void setIncomeBudgetList(List<IncomeBudget> incomeBudgetList) {
        this.mIncomeBudgetList = incomeBudgetList;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_expandable_income_budget, container, false);



        List<IncomeBudget> employments = new ArrayList<IncomeBudget>();
        List<IncomeBudget> governmentBenefits = new ArrayList<IncomeBudget>();
        List<IncomeBudget> investments = new ArrayList<IncomeBudget>();
        List<IncomeBudget> others = new ArrayList<IncomeBudget>();

        for (IncomeBudget incomeBudget : mIncomeBudgetList) {
            switch (incomeBudget.getIncomeBudgetCategory()) {
                case EMPLOYMENT:
                    employments.add(incomeBudget);
                    break;
                case GOVERNMENT_BENEFIT:
                    governmentBenefits.add(incomeBudget);
                    break;
                case INVESTMENT:
                    investments.add(incomeBudget);
                    break;
                case OTHERS:
                    others.add(incomeBudget);
                    break;
            }
        }


        List<String> group = new ArrayList<String>();
        group.add(IncomeBudget.INCOME_BUDGET_CATEGORY.EMPLOYMENT.getLabel(inflater.getContext()));
        group.add(IncomeBudget.INCOME_BUDGET_CATEGORY.GOVERNMENT_BENEFIT.getLabel(inflater.getContext()));
        group.add(IncomeBudget.INCOME_BUDGET_CATEGORY.INVESTMENT.getLabel(inflater.getContext()));
        group.add(IncomeBudget.INCOME_BUDGET_CATEGORY.OTHERS.getLabel(inflater.getContext()));

        HashMap<String, List<IncomeBudget>> incomeBudgetMap = new HashMap<String, List<IncomeBudget>>();
        incomeBudgetMap.put(group.get(0), employments);
        incomeBudgetMap.put(group.get(1), governmentBenefits);
        incomeBudgetMap.put(group.get(2), investments);
        incomeBudgetMap.put(group.get(3), others);



        //Spinner have to initialize before mTextViewTotal, because mTextViewTotal invoke getSelectedItem
        CycleSpinnerAdapter spinnerAdapter = new CycleSpinnerAdapter(Cycle.values(),inflater.getContext());
        mCycleSpinner = (Spinner) view.findViewById(R.id.spinnerCycle);
        mCycleSpinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mTextViewTotal.setText(Utils.calTotalIncomeBudgetAmount(mIncomeBudgetList, (Cycle) mCycleSpinner.getSelectedItem()).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mCycleSpinner.setAdapter(spinnerAdapter);
        mTextViewTotal = (TextView) view.findViewById(R.id.textViewTotal);
        mCycleSpinner.setSelection(3);
        mTextViewTotal.setText(Utils.calTotalIncomeBudgetAmount(mIncomeBudgetList, (Cycle) mCycleSpinner.getSelectedItem()).toString());
        mExpandableListViewIncomeBudget = (ExpandableListView) view.findViewById(R.id.expandableListViewIncomeBudget);
        IncomeBudgetExpandableListViewAdapter adapter = new IncomeBudgetExpandableListViewAdapter(group, incomeBudgetMap, inflater.getContext());
        mExpandableListViewIncomeBudget.setAdapter(adapter);
        mExpandableListViewIncomeBudget.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(getActivity(), EditIncomeBudgetActivity.class);
                intent.putExtra(EXTRA_INCOME_BUDGET_ID, id);
                startActivity(intent);
                return true;
            }
        });


        return  view;
    }


}
