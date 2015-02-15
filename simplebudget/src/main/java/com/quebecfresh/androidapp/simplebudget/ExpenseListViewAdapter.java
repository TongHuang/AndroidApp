package com.quebecfresh.androidapp.simplebudget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.quebecfresh.androidapp.simplebudget.model.Expense;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Tong Huang on 2015-02-14, 8:16 PM.
 */
public class ExpenseListViewAdapter extends BaseAdapter implements ListAdapter {
    ArrayList<Expense>  expenses = new ArrayList<Expense>();
    Context context;

    public ExpenseListViewAdapter(ArrayList<Expense> expenses, Context context) {
        this.expenses = expenses;
        this.context = context;
    }

    @Override
    public int getCount() {
        return expenses.size();
    }

    @Override
    public Object getItem(int position) {
        return expenses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return expenses.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Expense expense = expenses.get(position);
        View view = (View)convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_expense, null);
        }
        TextView nameTextView = (TextView)view.findViewById(R.id.textView_Name);
        nameTextView.setText(expense.getName());
        TextView categoryTextView = (TextView)view.findViewById(R.id.textView_Category);
        categoryTextView.setText(expense.getCategory().getName());
        TextView payeeTextView = (TextView)view.findViewById(R.id.textView_Payee);
        payeeTextView.setText(expense.getPayee().getName());
        CheckBox paidStatusCheckBox = (CheckBox)view.findViewById(R.id.checkBox_PaidStatus);
        paidStatusCheckBox.setChecked(expense.getIsPaid());
        TextView amountTextView = (TextView)view.findViewById(R.id.textView_Amount);
        amountTextView.setText(expense.getAmount().toString());
        return view;
    }
}
