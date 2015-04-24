package com.quebecfresh.androidapp.simplebudget;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.quebecfresh.androidapp.simplebudget.model.Transaction;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionFragment extends Fragment {

    private List<Transaction> mTransactionList;


    public TransactionFragment() {
        // Required empty public constructor
    }


    public List<Transaction> getTransationList() {
        return mTransactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.mTransactionList = transactionList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);

        ListView listViewTransaction = (ListView) view.findViewById(R.id.listViewTransations);
        TransactionListViewAdapter adapter = new TransactionListViewAdapter(mTransactionList, inflater.getContext());
        listViewTransaction.setAdapter(adapter);
        return view;
    }


}
