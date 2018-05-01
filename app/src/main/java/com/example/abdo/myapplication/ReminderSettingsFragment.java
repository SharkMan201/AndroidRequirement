package com.example.abdo.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class ReminderSettingsFragment extends DialogFragment {
    private static final int EDIT_LIST_ITEM_IDX = 0;
    private static final int DELETE_LIST_ITEM_IDX = 1;
    private static final int TOGGLE_IMPORTANCE_LIST_ITEM_IDX = 2;

    private static final String ARG_LIST_ITEM_IDX = "idx";
    private static final String ARG_LIST_ITEM_ID = "id";

    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;

    private int mItemIdx;
    private String mDBID;
    public static ReminderSettingsFragment newInstance(int idx, String ID) {
        ReminderSettingsFragment f = new ReminderSettingsFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt(ARG_LIST_ITEM_IDX, idx);
        args.putString(ARG_LIST_ITEM_ID, ID);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItemIdx = getArguments().getInt(ARG_LIST_ITEM_IDX);
        mDBID = getArguments().getString(ARG_LIST_ITEM_ID);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.reminder_options_title);
        builder.setCancelable(true);

        builder.setItems(R.array.reminders_options_array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case EDIT_LIST_ITEM_IDX:
                        mListener.editReminder(mDBID);
                        break;
                    case DELETE_LIST_ITEM_IDX:
                        mListener.deleteReminder(mDBID);
                        break;
                    case TOGGLE_IMPORTANCE_LIST_ITEM_IDX:
                        mListener.toggleReminderImportance(mDBID);
                        break;
                }
            }
        });

        return  builder.create();
    }

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        // TODO initialize methods names
        void editReminder(String dbID);
        void deleteReminder(String dbID);
        void toggleReminderImportance(String dbID);
    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

}
