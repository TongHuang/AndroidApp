package com.quebecfresh.androidapp.simplebudget;

import android.app.Dialog;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.quebecfresh.androidapp.simplebudget.model.Account;
import com.quebecfresh.androidapp.simplebudget.persist.AccountPersist;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;

import java.util.List;

/**
 * Created by Tong Huang on 2015-03-06, 8:27 AM.
 */
public class ChooseAccountDialogFragment extends DialogFragment {
    private List<Account> accountList;
    private AccountClickListener accountClickListener;

    public interface AccountClickListener{
        public void click(Account account);
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    public void setAccountClickListener(AccountClickListener accountClickListener){
        this.accountClickListener = accountClickListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_view_accounts, container, true);
        ListView listView = (ListView) view.findViewById(R.id.listViewAccounts);
        DatabaseHelper databaseHelper = new DatabaseHelper(this.getActivity());
        AccountPersist accountPersist = new AccountPersist(databaseHelper.getReadableDatabase());
        accountList = accountPersist.readAll();
        InitializeAccountListViewAdapter initializeAccountListViewAdapter = new InitializeAccountListViewAdapter(accountList, this.getActivity());
        listView.setAdapter(initializeAccountListViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                accountClickListener.click(accountList.get(position));
                ChooseAccountDialogFragment.this.dismiss();
            }
        });
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog =  super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
}