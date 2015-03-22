package com.quebecfresh.androidapp.simplebudget;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by Tong Huang on 2015-03-05, 2:48 PM.
 */
public class ChooseDateDialogFragment extends DialogFragment {

    private Calendar calendar;

    private DatePickerDialog.OnDateSetListener dateSetListener;

    public ChooseDateDialogFragment(Calendar calendar) {
        this.calendar = calendar;
    }

    public DatePickerDialog.OnDateSetListener getDateSetListener() {
        return dateSetListener;
    }

    public void setDateSetListener(DatePickerDialog.OnDateSetListener dateSetListener) {
        this.dateSetListener = dateSetListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(),this.dateSetListener, year,month,day);

    }


}
