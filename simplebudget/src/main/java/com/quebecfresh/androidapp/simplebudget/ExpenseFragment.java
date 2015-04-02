package com.quebecfresh.androidapp.simplebudget;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.quebecfresh.androidapp.simplebudget.model.Expense;
import com.quebecfresh.androidapp.simplebudget.model.Utils;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseFragment extends Fragment {

    private List<Expense> mExpenseList;
    public ExpenseFragment() {
        // Required empty public constructor
    }

    public List<Expense> getExpenseList() {
        return mExpenseList;
    }

    public void setExpenseList(List<Expense> expenseList) {
        this.mExpenseList = expenseList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_expense, container, false);

        ListView listViewExpense = (ListView)view.findViewById(R.id.listViewExpense);
        ExpenseListViewAdapter expenseListViewAdapter = new ExpenseListViewAdapter(mExpenseList, inflater.getContext());
        listViewExpense.setAdapter(expenseListViewAdapter);
        View listFooterExpense = (View)inflater.inflate(R.layout.list_footer_total,null);
        TextView textViewTotal = (TextView)listFooterExpense.findViewById(R.id.textViewTotal);
        textViewTotal.setText(Utils.calTotalExpense(mExpenseList).toString());
        listViewExpense.addFooterView(listFooterExpense);
        return view;

    }


}
