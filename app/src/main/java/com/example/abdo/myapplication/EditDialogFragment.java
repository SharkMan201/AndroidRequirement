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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

public class EditDialogFragment extends DialogFragment {
    // Use this instance of the interface to deliver action events
    private static final String ARG_OLD_REMINDER = "reminder-old";
    NoticeDialogListener mListener;

    ReminderModel mReminderOld;

    public static EditDialogFragment newInstance(ReminderModel oldReminder) {
        EditDialogFragment f = new EditDialogFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putParcelable(ARG_OLD_REMINDER, oldReminder);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReminderOld = getArguments().getParcelable(ARG_OLD_REMINDER);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.edit_reminder_title);
        builder.setCancelable(true);

        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint(R.string.edit_reminder_input_placeholder);
        input.setText(mReminderOld.getReminderData());
        input.requestFocus();

        final CheckBox importantCheckBox = new CheckBox(getActivity());
        importantCheckBox.setText(R.string.edit_important_checkbox);
        importantCheckBox.setChecked(mReminderOld.isImportant());

        final LinearLayout layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(input);
        layout.addView(importantCheckBox);

        builder.setView(layout);

        // Set up the buttons
        builder.setPositiveButton(R.string.edit_reminder_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onDialogEditClick(mReminderOld.getID(), input.getText().toString(), importantCheckBox.isChecked());
            }
        });
        builder.setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        return  builder.create();
    }

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        public void onDialogEditClick(String dbID, String input, boolean isChecked);
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
