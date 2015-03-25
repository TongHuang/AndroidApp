package com.quebecfresh.androidapp.simplebudget;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

/**
 * Created by Tong Huang on 2015-03-25, 10:17 AM.
 */
public class ConfirmDeletionDialogFragment extends DialogFragment {

    private ConfirmDeletionDialogListener mConfirmDeletionDialogListener;
    private String mTitle ="Delete can not undo";
    private String mMessage ="Are you sure?" ;
    private String mButtonYesText = "Yes" ;
    private String mButtonNoText ="No";

    public void setInfo(String mTitle, String mMessage) {
        this.mTitle = mTitle;
        this.mMessage = mMessage;
    }

    public void setInfo(String mTitle, String mMessage, String mButtonYesText, String mButtonNoText) {
        this.mTitle = mTitle;
        this.mMessage = mMessage;
        this.mButtonYesText = mButtonYesText;
        this.mButtonNoText = mButtonNoText;
    }

    public interface  ConfirmDeletionDialogListener{
        public void onYesClick();
        public void onNoClick();
    }

    public ConfirmDeletionDialogListener getConfirmDeletionListener() {
        return mConfirmDeletionDialogListener;
    }

    public void setConfirmDeletionDialogListener(ConfirmDeletionDialogListener confirmDeletionListener) {
        this.mConfirmDeletionDialogListener = confirmDeletionListener;
    }

    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if(mTitle != null) {
            builder.setTitle(mTitle);
        }
        if(mMessage != null) {
            builder.setMessage(mMessage);
        }

        builder.setPositiveButton(mButtonYesText,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mConfirmDeletionDialogListener.onYesClick();
            }
        });

        builder.setNegativeButton(mButtonNoText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mConfirmDeletionDialogListener.onNoClick();
            }
        });
        return builder.create();
    }
}
