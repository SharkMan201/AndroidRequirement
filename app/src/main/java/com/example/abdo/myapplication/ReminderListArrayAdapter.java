package com.example.abdo.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ReminderListArrayAdapter extends ArrayAdapter<ReminderModel> {

    Context mContext;
    ArrayList<ReminderModel> mReminderList;

    public ReminderListArrayAdapter(@NonNull Context context, @NonNull ArrayList<ReminderModel> objects) {
        super(context, 0, objects);
        mContext = context;
        mReminderList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.fragment_reminder_list_item,parent,false);

        ReminderModel currentReminder = mReminderList.get(position);

        ImageView imageView = listItem.findViewById(R.id.image_important);
        TextView view = listItem.findViewById(R.id.fragment_reminder_text_view);
        view.setText(currentReminder.getReminderData());

        if (currentReminder.isImportant()) {
            imageView.setVisibility(View.VISIBLE);
            view.setTextColor(Color.RED);
        } else {
            imageView.setVisibility(View.INVISIBLE);
            view.setTextColor(Color.BLACK);
        }

        return listItem;
    }
}
