package com.quebecfresh.androidapp.simplebudget;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.quebecfresh.androidapp.simplebudget.model.Income;
import com.quebecfresh.androidapp.simplebudget.model.Utils;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class IncomeFragment extends Fragment {

    private List<Income> mIncomeList;
    private ListView mListViewIncomes;
    private View mListViewFooterIncome;
    public IncomeFragment() {
        // Required empty public constructor
    }

    public List<Income> getIncomeList() {
        return mIncomeList;
    }

    public void setIncomeList(List<Income> incomeList) {
        this.mIncomeList = incomeList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_income, container, false);
        mListViewIncomes = (ListView)view.findViewById(R.id.listViewIncomes);
        mListViewFooterIncome = inflater.inflate(R.layout.list_footer_total, null);
        TextView textViewTotal = (TextView)mListViewFooterIncome.findViewById(R.id.textViewTotal);
        textViewTotal.setText(Utils.calTotalIncome(mIncomeList).toString());
        mListViewIncomes.addFooterView(mListViewFooterIncome);

        IncomeListViewAdapter incomeListViewAdapter = new IncomeListViewAdapter(mIncomeList, inflater.getContext());
        mListViewIncomes.setAdapter(incomeListViewAdapter);


        return view;
    }


}
