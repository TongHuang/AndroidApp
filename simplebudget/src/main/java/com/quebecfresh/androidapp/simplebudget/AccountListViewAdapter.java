package com.quebecfresh.androidapp.simplebudget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.quebecfresh.androidapp.simplebudget.model.Account;

import java.util.List;

/**
 * Created by Tong Huang on 2015-02-18, 10:07 AM.
 */
public class AccountListViewAdapter extends BaseAdapter implements ListAdapter {



    private List<Account> mAccounts;
    private Context mContext;

    public AccountListViewAdapter(List<Account> accounts, Context context) {
        this.mAccounts = accounts;
        this.mContext = context;
    }



    @Override
    public int getCount() {
        return mAccounts.size();
    }

    @Override
    public Object getItem(int position) {
        return mAccounts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mAccounts.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Account account = mAccounts.get(position);
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_item_account, null);
        }

        TextView textViewName = (TextView) view.findViewById(R.id.textViewName);
        textViewName.setText(account.getName());

        TextView textViewBalance = (TextView) view.findViewById((R.id.textViewBalance));
        textViewBalance.setText(account.getBalance().toString());

        return view;
    }
}
