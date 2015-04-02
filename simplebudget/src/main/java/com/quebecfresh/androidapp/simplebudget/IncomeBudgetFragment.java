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
import com.quebecfresh.androidapp.simplebudget.model.IncomeBudget;
import com.quebecfresh.androidapp.simplebudget.model.Utils;

import java.util.List;
import java.util.zip.Inflater;


/**
 * A simple {@link Fragment} subclass.
 */
public class IncomeBudgetFragment extends Fragment {

    private List<IncomeBudget> mIncomeBudgetList;
    private Cycle mSelectedCycle = Cycle.Monthly;
    private TextView mTextViewTotal;

    public IncomeBudgetFragment() {
        // Required empty public constructor
    }

    public List<IncomeBudget> getIncomeBudgetList() {
        return mIncomeBudgetList;
    }

    public void setIncomeBudgetList(List<IncomeBudget> incomeBudgetList) {
        this.mIncomeBudgetList = incomeBudgetList;
    }

    public Cycle getSelectedCycle() {
        return mSelectedCycle;
    }

    public void setSelectedCycle(Cycle selectedCycle) {
        this.mSelectedCycle = selectedCycle;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_income_budget, container, false);

        ListView listViewIncomeBudget = (ListView)view.findViewById(R.id.listViewIncomeBudget);
        IncomeBudgetListViewAdapter incomeBudgetListViewAdapter = new IncomeBudgetListViewAdapter(mIncomeBudgetList, inflater.getContext());
        listViewIncomeBudget.setAdapter(incomeBudgetListViewAdapter);

        Spinner spinnerCycle = (Spinner)view.findViewById(R.id.spinnerCycle);
        CycleSpinnerAdapter cycleSpinnerAdapter = new CycleSpinnerAdapter(inflater.getContext(),Cycle.values());
        spinnerCycle.setAdapter(cycleSpinnerAdapter);
        spinnerCycle.setSelection(mSelectedCycle.ordinal());
        spinnerCycle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectedCycle = (Cycle)parent.getSelectedItem();
                mTextViewTotal.setText(Utils.calTotalIncomeBudgetAmount(mIncomeBudgetList, mSelectedCycle).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mTextViewTotal = (TextView)view.findViewById(R.id.textViewTotal);
        mTextViewTotal.setText(Utils.calTotalIncomeBudgetAmount(mIncomeBudgetList, mSelectedCycle).toString());
        return view;
    }


}
