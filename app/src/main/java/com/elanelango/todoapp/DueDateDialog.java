package com.elanelango.todoapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by eelango on 1/11/16.
 */
public class DueDateDialog extends DialogFragment {

    DatePickerDialog.OnDateSetListener mActivity;

    public DueDateDialog() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // if the Activity does not implement this interface, it will crash
        mActivity = (DatePickerDialog.OnDateSetListener) activity;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        Calendar originalDue = ((MainActivity) mActivity).dueDate;
        if(originalDue == null) {
            originalDue = Calendar.getInstance();
        }

        int year = originalDue.get(Calendar.YEAR);
        int month = originalDue.get(Calendar.MONTH);
        int day = originalDue.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of TimePickerDialog and return it
        // mActivity is the callback interface instance
        return new DatePickerDialog(getActivity(), mActivity, year, month, day);
    }
}
