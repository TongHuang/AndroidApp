package com.quebecfresh.androidapp.simplebudget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.quebecfresh.androidapp.simplebudget.model.Expense;

import java.util.List;

/**
 * Created by Tong Huang on 2015-03-17, 9:53 AM.
 */
public class ExpenseListViewAdapter extends BaseAdapter implements ListAdapter {

    private List<Expense> mExpenseList;
    private Context mContext;

    public ExpenseListViewAdapter(List<Expense> expenseList, Context context) {
        this.mExpenseList = expenseList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return this.mExpenseList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mExpenseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.mExpenseList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view ==null){
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_item_expense,null);
        }
        Expense expense = mExpenseList.get(position);
        TextView textViewExpenseDate = (TextView)view.findViewById(R.id.textViewExpenseDate);
        textViewExpenseDate.setText(expense.getDateShortLabel());
        TextView textViewExpenseBudgetName = (TextView)view.findViewById(R.id.textViewExpenseBudgetName);
        textViewExpenseBudgetName.setText(expense.getExpenseBudget().getName());
        TextView textViewExpenseAmount = (TextView)view.findViewById(R.id.textViewExpenseAmount);
        textViewExpenseAmount.setText(expense.getAmount().toString());
        return view;
    }
}
