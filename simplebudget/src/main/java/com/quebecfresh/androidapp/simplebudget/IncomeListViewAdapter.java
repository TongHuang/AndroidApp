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

    private List<Income> mIncomeList;
    private Context mContext;

    public IncomeListViewAdapter(List<Income> incomeList, Context context) {
        this.mIncomeList = incomeList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return this.mIncomeList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mIncomeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.mIncomeList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_item_income, null);
        }

        Income income = mIncomeList.get(position);
        TextView textViewIncomeReceivedDate = (TextView)view.findViewById(R.id.textViewIncomeReceivedDate);
        textViewIncomeReceivedDate.setText(income.getDateShortLabel());
        TextView textViewIncomeBudgetName = (TextView)view.findViewById(R.id.textViewIncomeBudgetName);
        textViewIncomeBudgetName.setText(income.getIncomeBudget().getName());
        TextView textViewIncomeAmount  = (TextView)view.findViewById(R.id.textViewIncomeAmount);
        textViewIncomeAmount.setText(income.getAmount().toString());
        return view;
    }
}
