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

    private Calendar mCalendar;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    public void  setCalendar(Calendar calendar) {
        this.mCalendar = calendar;
    }

    public DatePickerDialog.OnDateSetListener getDateSetListener() {
        return mDateSetListener;
    }

    public void setDateSetListener(DatePickerDialog.OnDateSetListener dateSetListener) {
        this.mDateSetListener = dateSetListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),this.mDateSetListener, year,month,day);

        return datePickerDialog;

    }


}
