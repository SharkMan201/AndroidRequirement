package com.example.abdo.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "remindersDb.db";
    private static final int DATABASE_VERSION = 7;

    private static final String TABLE_NAME = "Reminders";
    private static final String COLUMN_DATA = "reminder_data";
    private static final String COLUMN_IMPORTANT = "importance";
    private static final String COLUMN_ID = "id";

    private static final int COLUMN_ID_IDX = 0;
    private static final int COLUMN_DATA_IDX = 1;
    private static final int COLUMN_IMPORTANT_IDX = 2;


    private SQLiteDatabase mDatabase;
    DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME
                + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_DATA + " Varchar, " + COLUMN_IMPORTANT  + " Varchar);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertRecord(ReminderModel row) {
        mDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DATA, row.getReminderData());
        contentValues.put(COLUMN_IMPORTANT, row.getImportant());
        mDatabase.insert(TABLE_NAME, null, contentValues);
        mDatabase.close();
    }

    public void updateRecord(ReminderModel row) {
        mDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DATA, row.getReminderData());
        contentValues.put(COLUMN_IMPORTANT, row.getImportant());
        mDatabase.update(TABLE_NAME, contentValues, COLUMN_ID + " = ?", new String[]{row.getID()});
        mDatabase.close();
    }

    public void deleteRecord(ReminderModel row) {
        mDatabase = this.getWritableDatabase();
        mDatabase.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{row.getID()});
        mDatabase.close();
    }

    ReminderModel getRow(Cursor cursor) {
        ReminderModel row = new ReminderModel();
        row.setID(cursor.getString(COLUMN_ID_IDX));
        row.setReminderData(cursor.getString(COLUMN_DATA_IDX));
        row.setImportant(cursor.getString(COLUMN_IMPORTANT_IDX));

        return row;
    }

    public ReminderModel getRecord(String dbID) {
        mDatabase = this.getReadableDatabase();
        Cursor cursor = mDatabase.query(TABLE_NAME, null, COLUMN_ID + " = " + dbID, null, null, null, null);
        ReminderModel row = new ReminderModel();
        if (cursor.getCount() > 0) {
            cursor.moveToNext();
            row = getRow(cursor);
        }
        cursor.close();
        mDatabase.close();
        return row;
    }

    public ArrayList<ReminderModel> getAllRecords() {
        mDatabase = this.getReadableDatabase();
        Cursor cursor = mDatabase.query(TABLE_NAME, null, null, null, null, null, null);
        ArrayList<ReminderModel> rows = new ArrayList<>();
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();

                rows.add(getRow(cursor));
            }
        }
        cursor.close();
        mDatabase.close();
        return rows;
    }
}
