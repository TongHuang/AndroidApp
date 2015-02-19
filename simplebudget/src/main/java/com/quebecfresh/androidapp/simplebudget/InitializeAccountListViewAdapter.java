package com.quebecfresh.androidapp.simplebudget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;

import com.quebecfresh.androidapp.simplebudget.model.Account;

import java.util.ArrayList;

/**
 * Created by Tong Huang on 2015-02-18, 10:07 AM.
 */
public class InitializeAccountListViewAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<Account> accounts = new ArrayList<Account>();
    private Context context;

    public InitializeAccountListViewAdapter(ArrayList<Account> accounts, Context context) {
        this.accounts = accounts;
        this.context = context;
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Account account = accounts.get(position);
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_item_initialize_account, null);
        }

        EditText editTextName = (EditText) view.findViewById(R.id.editText_Name);
        editTextName.setText(account.getName());
        EditText editTextNumber = (EditText) view.findViewById(R.id.editText_Number);
        editTextNumber.setText(account.getAccountNumber());
        EditText editTextBalance = (EditText) view.findViewById((R.id.editText_Balance));
        editTextBalance.setText(account.getBalance().toString());
        EditText editTextNote = (EditText) view.findViewById(R.id.editText_Note);
        editTextNote.setText(account.getNote());
        return view;
    }
}
