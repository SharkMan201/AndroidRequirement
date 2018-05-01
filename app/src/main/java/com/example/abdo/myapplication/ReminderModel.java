package com.example.abdo.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class ReminderModel implements Parcelable {
    private static int IMPORTANT = 1;
    private static int NOT_IMPORTANT = 0;

    private String ID, reminderData;
    int mImportant;

    ReminderModel() {
        mImportant = NOT_IMPORTANT;
    }

    ReminderModel(Parcel in) {
        ID = in.readString();
        reminderData = in.readString();
        mImportant = in.readInt();
    }

    public static final Creator<ReminderModel> CREATOR = new Creator<ReminderModel>() {
        @Override
        public ReminderModel createFromParcel(Parcel in) {
            return new ReminderModel(in);
        }

        @Override
        public ReminderModel[] newArray(int size) {
            return new ReminderModel[size];
        }
    };

    public String getReminderData() {
        return reminderData;
    }

    public void setReminderData(String reminderData) {
        this.reminderData = reminderData;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getImportant() {
        return String.valueOf(mImportant == IMPORTANT ? IMPORTANT : NOT_IMPORTANT);
    }

    public void setImportant(String val) {
        mImportant = Integer.parseInt(val);
    }
    public boolean  isImportant() {
        return mImportant == IMPORTANT;
    }

    public void toggleImportance() {
        mImportant = mImportant == IMPORTANT ? NOT_IMPORTANT : IMPORTANT;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ID);
        dest.writeString(reminderData);
        dest.writeInt(mImportant);
    }

    @Override
    public String toString() {
        return this.getReminderData();
    }
}
