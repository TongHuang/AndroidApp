package com.quebecfresh.androidapp.simplebudget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.quebecfresh.androidapp.simplebudget.model.Account;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tong Huang on 2015-03-12, 9:56 AM.
 */
public class AccountsSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {


    private Context context;
    private List<Account> accountList = new ArrayList<Account>();

    public AccountsSpinnerAdapter(Context context,List<Account> accountList){
        this.context = context;
        this.accountList =accountList;
    }


    @Override
    public int getCount() {
        return this.accountList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.accountList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.accountList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view =  convertView;

        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.spinner_item_account, null);
        }

        Account account = this.accountList.get(position);
        TextView textViewAccountName = (TextView)view.findViewById(R.id.textViewAccountName);
        textViewAccountName.setText(account.getName());
        TextView textViewAccountBalance = (TextView)view.findViewById(R.id.textViewAccountBalance);
        textViewAccountBalance.setText(account.getBalance().toString());
        return view;
    }
}
