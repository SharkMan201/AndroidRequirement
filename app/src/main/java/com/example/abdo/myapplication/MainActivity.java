package com.example.abdo.myapplication;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ReminderList.OnFragmentInteractionListener,
        AddDialogFragment.NoticeDialogListener,
        ReminderSettingsFragment.NoticeDialogListener,
        EditDialogFragment.NoticeDialogListener {
    DBHandler mDBHandler;
    private ReminderList mReminderListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDBHandler = new DBHandler(getBaseContext());
        mDBHandler.getWritableDatabase();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                DialogFragment addFragment = new AddDialogFragment();
                addFragment.show(getFragmentManager(), "add_tag");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        getSupportFragmentManager().beginTransaction().remove(mReminderListFragment).commit();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mReminderListFragment = ReminderList.newInstance(mDBHandler.getAllRecords());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_reminder_id, mReminderListFragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_close) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onListItemHold(int idx, String dbID) {
        ReminderSettingsFragment reminderOptionsFragmnet = ReminderSettingsFragment.newInstance(idx, dbID);
        reminderOptionsFragmnet.show(getFragmentManager().beginTransaction(), "tag2");
    }

    public void showSnackBar(String text) {
        Snackbar.make(findViewById(R.id.fragment_reminder_id), text, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public void editReminder(String dbID) {
        EditDialogFragment reminderEditFragment = EditDialogFragment.newInstance(mDBHandler.getRecord(dbID));
        reminderEditFragment.show(getFragmentManager().beginTransaction(), "tag3");
    }

    public void deleteReminder(String dbID) {
        mDBHandler.deleteRecord(mDBHandler.getRecord(dbID));
        mReminderListFragment.updateView(mDBHandler.getAllRecords());
        showSnackBar(getResources().getString(R.string.successful_delete_snackbar));
    }

    public void toggleReminderImportance(String dbID) {
        ReminderModel row = mDBHandler.getRecord(dbID);
        row.toggleImportance();
        mDBHandler.updateRecord(row);
        mReminderListFragment.updateView(mDBHandler.getAllRecords());

        showSnackBar(getResources().getString(R.string.successful_edit_snackbar));
    }

    @Override
    public void onDialogAddClick(String input, boolean isChecked) {
        if (input.length() == 0) return;

        ReminderModel row = new ReminderModel();
        row.setReminderData(input);
        row.setImportant(isChecked ? "1" : "0");
        mDBHandler.insertRecord(row);
        mReminderListFragment.updateView(mDBHandler.getAllRecords());

        showSnackBar(getResources().getString(R.string.successful_add_snackbar));
    }

    @Override
    public void onDialogEditClick(String dbID, String input, boolean isChecked) {
        if (input.length() == 0) return;

        ReminderModel row = mDBHandler.getRecord(dbID);
        row.setReminderData(input);
        row.setImportant(isChecked ? "1" : "0");
        mDBHandler.updateRecord(row);
        mReminderListFragment.updateView(mDBHandler.getAllRecords());

        showSnackBar(getResources().getString(R.string.successful_edit_snackbar));
    }
}
