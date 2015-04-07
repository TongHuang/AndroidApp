package com.quebecfresh.androidapp.simplebudget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.quebecfresh.androidapp.simplebudget.model.ExpenseBudget;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Tong Huang on 2015-02-17, 8:24 AM.
 */
public class ExpenseBudgetExpandableListViewAdapter extends BaseExpandableListAdapter {

    private HashMap<String, List<ExpenseBudget>> mBudgetHashMap;
    private List<String> mCategoryList;

    private Context context;  public ExpenseBudgetExpandableListViewAdapter(List<String> categoryGroup, HashMap<String, List<ExpenseBudget>> categories, Context context) {
        this.mCategoryList = categoryGroup;
        this.mBudgetHashMap = categories;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return this.mCategoryList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String group = mCategoryList.get(groupPosition);
        return mBudgetHashMap.get(group).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mCategoryList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        String group = mCategoryList.get(groupPosition);
        return mBudgetHashMap.get(group).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        String group = this.mCategoryList.get(groupPosition);
        return mBudgetHashMap.get(group).get(childPosition).getId();

    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {


            String group = this.mCategoryList.get(groupPosition);
            View view = convertView;
            if (view == null) {
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = layoutInflater.inflate(R.layout.list_item_budget_category, null);
            }
            TextView textViewGroup = (TextView) view.findViewById(R.id.textView_Group);
            textViewGroup.setText(group);
            return view;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String category = this.mCategoryList.get(groupPosition);
        ExpenseBudget budget = this.mBudgetHashMap.get(category).get(childPosition);
        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_item_expense_budget, null);
        }
        TextView textViewCategory = (TextView) view.findViewById(R.id.textViewBudgetName);
        textViewCategory.setText(budget.getName());
        TextView textViewCycle = (TextView) view.findViewById(R.id.textViewCycle);
        textViewCycle.setText(budget.getCycle().getLabel(context));
        TextView textViewAmount = (TextView) view.findViewById(R.id.textViewAmount);
        textViewAmount.setText(budget.getBudgetAmount().toString());
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}
