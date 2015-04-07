package com.quebecfresh.androidapp.simplebudget;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.quebecfresh.androidapp.simplebudget.model.IncomeBudget;

import java.util.List;

/**
 * Created by Tong Huang on 2015-03-18, 9:42 AM.
 */
public class ChooseIncomeBudgetDialogFragment extends android.support.v4.app.DialogFragment {
    private List<IncomeBudget> mIncomeBudgetList;
    private IncomeBudgetChooseListener mIncomeBudgetChooseListener;

    public interface IncomeBudgetChooseListener{
        public void choose(IncomeBudget incomeBudget);
    }

    public List<IncomeBudget> getIncomeBudgetList() {
        return mIncomeBudgetList;
    }

    public void setIncomeBudgetList(List<IncomeBudget> incomeBudgetList) {
        this.mIncomeBudgetList = incomeBudgetList;
    }

    public IncomeBudgetChooseListener getIncomeBudgetChooseListener() {
        return mIncomeBudgetChooseListener;
    }

    public void setIncomeBudgetChooseListener(IncomeBudgetChooseListener incomeBudgetChooseListener) {
        this.mIncomeBudgetChooseListener = incomeBudgetChooseListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_view_budget,null);
        ListView listViewBudget = (ListView)view.findViewById(R.id.listViewBudget);
        IncomeBudgetListViewAdapter incomeBudgetListViewAdapter = new IncomeBudgetListViewAdapter(mIncomeBudgetList, this.getActivity());
        listViewBudget.setAdapter(incomeBudgetListViewAdapter);
        listViewBudget.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mIncomeBudgetChooseListener != null && position >= 0){
                    mIncomeBudgetChooseListener.choose(mIncomeBudgetList.get(position));
                }
                ChooseIncomeBudgetDialogFragment.this.dismiss();
            }
        });

        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
//        dialog.setTitle("Please choose a pocket");
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;

    }
}
