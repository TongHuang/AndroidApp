package com.quebecfresh.androidapp.simplebudget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.quebecfresh.androidapp.simplebudget.model.IncomeBudget;

import java.util.List;

/**
 * Created by Tong Huang on 2015-03-18, 9:53 AM.
 */
public class IncomeBudgetListViewAdapter extends BaseAdapter implements ListAdapter {
    private List<IncomeBudget> mIncomeBudgetList;
    private Context mContext;

    public IncomeBudgetListViewAdapter(List<IncomeBudget> incomeBudgetList, Context context) {
        this.mIncomeBudgetList = incomeBudgetList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return this.mIncomeBudgetList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mIncomeBudgetList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.mIncomeBudgetList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        IncomeBudget incomeBudget = mIncomeBudgetList.get(position);
        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_item_income_budget, null);
        }
        TextView textViewBudgetName = (TextView)view.findViewById(R.id.textViewBudgetName);
        textViewBudgetName.setText(incomeBudget.getName());
        TextView textViewCycle = (TextView)view.findViewById(R.id.textViewCycle);
        textViewCycle.setText(incomeBudget.getCycle().getLabel(this.mContext));
        TextView textViewAmount =(TextView)view.findViewById(R.id.textViewAmount);
        textViewAmount.setText(incomeBudget.getBudgetAmount().toString());
        return view;
    }
}
