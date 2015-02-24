package com.quebecfresh.androidapp.simplebudget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.quebecfresh.androidapp.simplebudget.model.Account;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tong Huang on 2015-02-18, 10:07 AM.
 */
public class InitializeAccountListViewAdapter extends BaseAdapter implements ListAdapter {



    private List<Account> accounts = new ArrayList<Account>();
    private Context context;

    public InitializeAccountListViewAdapter(List<Account> accounts, Context context) {
        this.accounts = accounts;
        this.context = context;
    }

    public void addNewAccount(){
        accounts.add(0,new Account());
        this.notifyDataSetChanged();
    }

    public void removeAccount(int position){
        accounts.remove(position);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return accounts.size();
    }

    @Override
    public Object getItem(int position) {
        return accounts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return accounts.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Account account = accounts.get(position);
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_item_initialize_account, null);
        }

        TextView textViewName = (TextView) view.findViewById(R.id.textViewName);
        textViewName.setText(account.getName());

        TextView textViewBalance = (TextView) view.findViewById((R.id.textViewBalance));
        textViewBalance.setText(account.getBalance().toString());

        return view;
    }
}
