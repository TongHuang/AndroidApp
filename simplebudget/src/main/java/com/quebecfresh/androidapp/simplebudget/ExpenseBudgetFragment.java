package com.quebecfresh.androidapp.simplebudget;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.quebecfresh.androidapp.simplebudget.model.ExpenseBudget;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;
import com.quebecfresh.androidapp.simplebudget.persist.ExpenseBudgetPersist;

import java.math.BigDecimal;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseBudgetFragment extends Fragment {
    private DatabaseHelper mDBHelper ;
    private SQLiteDatabase mDB;
    private ExpenseBudgetPersist mExpenseBudgetPersist;
    private List<ExpenseBudget> mExpenseBudgetList;

    private ListView mListViewExpenseBudgets;
    private View mListViewFooterExpenseBudgets;

    public ExpenseBudgetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_expense_budget, container, false);

        mDBHelper = new DatabaseHelper(inflater.getContext());
        mExpenseBudgetPersist = new ExpenseBudgetPersist(inflater.getContext());
        mExpenseBudgetList = mExpenseBudgetPersist.readAllBudgetAmountNotZero();
        mListViewExpenseBudgets = (ListView)view.findViewById(R.id.listViewExpenseBudgets);
        ExpenseBudgetListViewAdapter expenseBudgetListViewAdapter = new ExpenseBudgetListViewAdapter(mExpenseBudgetList, inflater.getContext());
        mListViewExpenseBudgets.setAdapter(expenseBudgetListViewAdapter);

        BigDecimal total = mExpenseBudgetPersist.readTotalUnusedBalance();
        mListViewFooterExpenseBudgets  = inflater.inflate(R.layout.list_footer_total, null);
        TextView textViewTotal =(TextView)mListViewFooterExpenseBudgets.findViewById(R.id.textViewTotal);
        textViewTotal.setText(total.toString());
        mListViewExpenseBudgets.addFooterView(mListViewFooterExpenseBudgets);
        return view;
    }


}
