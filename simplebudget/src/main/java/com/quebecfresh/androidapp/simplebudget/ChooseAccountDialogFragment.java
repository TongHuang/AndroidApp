package com.quebecfresh.androidapp.simplebudget;

import android.app.Dialog;
import android.os.Bundle;
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

import java.util.List;

/**
 * Created by Tong Huang on 2015-03-06, 8:27 AM.
 */
public class ChooseAccountDialogFragment extends DialogFragment {


    private List<Account> mAccountList;
    private AccountChooseListener mAccountChooseListener;

    public interface AccountChooseListener {
        public void choose(Account account);
    }

    public List<Account> getAccountList() {
        return mAccountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.mAccountList = accountList;
    }

    public void setAccountChooseListener(AccountChooseListener accountChooseListener){
        this.mAccountChooseListener = accountChooseListener;
    }

    public AccountChooseListener getAccountChooseListener() {
        return mAccountChooseListener;
    }

    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_view_accounts, container, true);
        ListView listView = (ListView) view.findViewById(R.id.listViewAccounts);
        AccountListViewAdapter accountListViewAdapter = new AccountListViewAdapter(mAccountList, this.getActivity());
        listView.setAdapter(accountListViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAccountChooseListener.choose(mAccountList.get(position));
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