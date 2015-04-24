package com.quebecfresh.androidapp.simplebudget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.quebecfresh.androidapp.simplebudget.model.Expense;
import com.quebecfresh.androidapp.simplebudget.model.Income;
import com.quebecfresh.androidapp.simplebudget.model.Transaction;

import java.util.List;

/**
 * Created by Tong on 24/04/2015.
 */
public class TransactionListViewAdapter extends BaseAdapter implements ListAdapter {

    private List<Transaction> mTransactioList;
    private Context mContext;

    public TransactionListViewAdapter(List<Transaction> transactionList, Context context) {
        mTransactioList = transactionList;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mTransactioList.size();
    }

    @Override
    public Object getItem(int position) {
        return mTransactioList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mTransactioList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_item_income, null);
        }
        TextView textViewDate = (TextView) view.findViewById(R.id.textViewIncomeReceivedDate);
        TextView textViewName = (TextView) view.findViewById(R.id.textViewIncomeBudgetName);
        TextView textViewAmount = (TextView) view.findViewById(R.id.textViewIncomeAmount);


        if (mTransactioList.get(position) instanceof Income) {
            Income income = (Income) mTransactioList.get(position);
            textViewDate.setText(income.getDateShortLabel());
            textViewDate.setBackgroundColor(mContext.getResources().getColor(R.color.background_positive));
            textViewName.setText(income.getIncomeBudget().getName());
            textViewName.setBackgroundColor(mContext.getResources().getColor(R.color.background_positive));
            textViewAmount.setText(income.getAmount().toString());
            textViewAmount.setBackgroundColor(mContext.getResources().getColor(R.color.background_positive));
        } else {
            Expense expense = (Expense) mTransactioList.get(position);
            textViewDate.setText(expense.getDateShortLabel());
            textViewDate.setBackgroundColor(mContext.getResources().getColor(R.color.background_negative));
            textViewName.setText(expense.getExpenseBudget().getName());
            textViewName.setBackgroundColor(mContext.getResources().getColor(R.color.background_negative));
            textViewAmount.setText(expense.getAmount().toString());
            textViewAmount.setBackgroundColor(mContext.getResources().getColor(R.color.background_negative));
        }
        return view;
    }
}
