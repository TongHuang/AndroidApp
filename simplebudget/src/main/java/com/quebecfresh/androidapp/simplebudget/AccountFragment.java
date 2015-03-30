package com.quebecfresh.androidapp.simplebudget;


import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.quebecfresh.androidapp.simplebudget.model.Account;
import com.quebecfresh.androidapp.simplebudget.persist.AccountPersist;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;

import java.math.BigDecimal;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    public static final String EXTRA_ACCOUNT_ID = "com.quebecfresh.androidapp.simplebudget.account";

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDB;
    private AccountPersist mAccountPersist;
    private List<Account> mAccountList;

    private ListView mListViewAccounts;
    private View mListViewFooterAccounts;
    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_account, container, false);
        mDBHelper = new DatabaseHelper(inflater.getContext());
        mAccountPersist = new AccountPersist(inflater.getContext());
        mAccountList = mAccountPersist.readAll();
        mListViewAccounts = (ListView)view.findViewById(R.id.listViewAccounts);
        AccountListViewAdapter accountListViewAdapter = new AccountListViewAdapter(mAccountList,inflater.getContext());
        mListViewAccounts.setAdapter(accountListViewAdapter);

        BigDecimal totalBalance = mAccountPersist.readTotalBalance();
        mListViewFooterAccounts = inflater.inflate(R.layout.list_footer_total, null);
        TextView textViewTotal = (TextView)mListViewFooterAccounts.findViewById(R.id.textViewTotal);
        textViewTotal.setText(totalBalance.toString());
        mListViewAccounts.addFooterView(mListViewFooterAccounts);
        mListViewAccounts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //If user click on ListView Footer, do nothing
                if(position < parent.getAdapter().getCount()-1) {
                    Intent intent = new Intent(getActivity(), EditAccountActivity.class);
                    intent.putExtra(EXTRA_ACCOUNT_ID, id);
                    startActivity(intent);
                }
            }
        });
        return  view;
    }


}
