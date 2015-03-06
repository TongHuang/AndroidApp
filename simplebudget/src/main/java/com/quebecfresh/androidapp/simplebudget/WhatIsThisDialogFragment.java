package com.quebecfresh.androidapp.simplebudget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * Created by Tong Huang on 2015-03-05, 8:52 AM.
 */
public class WhatIsThisDialogFragment extends android.support.v4.app.DialogFragment {

    private String message;
    private String title;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTitle(String title){
        this.title = title;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(this.title)
                .setMessage(this.message)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }
}
