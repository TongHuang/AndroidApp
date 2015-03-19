package com.quebecfresh.androidapp.simplebudget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.quebecfresh.androidapp.simplebudget.model.Income;

import java.util.List;

/**
 * Created by Tong Huang on 2015-03-17, 2:49 PM.
 */
public class IncomeListViewAdapter extends BaseAdapter implements ListAdapter {
    private List<Income> incomeList;
    private Context context;

    public IncomeListViewAdapter(List<Income> incomeList, Context context) {
        this.incomeList = incomeList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.incomeList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.incomeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.incomeList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_item_income, null);
        }

        Income income = incomeList.get(position);
        TextView textViewIncomeReceivedDate = (TextView)view.findViewById(R.id.textViewIncomeReceivedDate);
        textViewIncomeReceivedDate.setText(income.getReceivedDateShortLabel());
        TextView textViewIncomeBudgetName = (TextView)view.findViewById(R.id.textViewIncomeBudgetName);
        textViewIncomeBudgetName.setText(income.getIncomeBudget().getName());
        TextView textViewIncomeAmount  = (TextView)view.findViewById(R.id.textViewIncomeAmount);
        textViewIncomeAmount.setText(income.getAmount().toString());
        return view;
    }
}
