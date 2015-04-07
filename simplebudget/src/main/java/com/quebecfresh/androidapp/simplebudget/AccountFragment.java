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
    //When user click on specific account
    public static final String EXTRA_ACCOUNT_ID = "com.quebecfresh.androidapp.simplebudget.account";

    //Provide interfaces to set data into Fragment;
    private List<Account> mAccountList;
    private BigDecimal mTotalBalance = new BigDecimal("0");



    public AccountFragment() {
        // Required empty public constructor
    }


    public BigDecimal getTotalBalance() {
        return mTotalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.mTotalBalance = totalBalance;
    }

    public List<Account> getAccountList() {
        return mAccountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.mAccountList = accountList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_account, container, false);

        ListView listViewAccounts = (ListView)view.findViewById(R.id.listViewAccounts);


        //Inflate listViewFooter
        View listViewFooterAccounts = inflater.inflate(R.layout.list_footer_total, null);
        TextView textViewTotal = (TextView)listViewFooterAccounts.findViewById(R.id.textViewTotal);
        textViewTotal.setText(mTotalBalance.toString());

        //ListViewFooter have to be add before setAdapter()
        listViewAccounts.addFooterView(listViewFooterAccounts);

        AccountListViewAdapter accountListViewAdapter = new AccountListViewAdapter(mAccountList,inflater.getContext());
        listViewAccounts.setAdapter(accountListViewAdapter);
        listViewAccounts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //If user click on ListView Footer, do nothing
                if (position < parent.getAdapter().getCount() - 1) {
                    Intent intent = new Intent(getActivity(), EditAccountActivity.class);
                    intent.putExtra(EXTRA_ACCOUNT_ID, id);
                    startActivity(intent);
                }
            }
        });
        return  view;
    }


}
