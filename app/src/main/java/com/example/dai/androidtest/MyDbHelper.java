package com.example.dai.androidtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

abstract class MyDBEntry implements BaseColumns {
    public static final String TABLE_NAME = "entry";

    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_NUMBER = "number";
    public static final String COLUMN_NAME_IMG = "img";

}

/**
 * Created by Dai on 10.26, 026.
 */
public final class MyDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String BLOB_TYPE = "BLOB";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MyDBEntry.TABLE_NAME + " (" +
                    MyDBEntry._ID + " INTEGER PRIMARY KEY," +
                    MyDBEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    MyDBEntry.COLUMN_NAME_NUMBER + TEXT_TYPE + COMMA_SEP +
                    MyDBEntry.COLUMN_NAME_IMG + BLOB_TYPE + COMMA_SEP +
                    ")";

    private SQLiteDatabase db;

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MyDBEntry.TABLE_NAME;

    public MyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = getWritableDatabase();
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public long insert(String name, String number, byte[] avatar) {
        // Gets the data repository in write mode


// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(MyDBEntry.COLUMN_NAME_NAME, name);
        values.put(MyDBEntry.COLUMN_NAME_NUMBER, number);
        values.put(MyDBEntry.COLUMN_NAME_IMG, avatar);

// Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                MyDBEntry.TABLE_NAME,
                null,
                values);

        return newRowId;
    }

    public Cursor retrieve() {
        String[] projection = {MyDBEntry.COLUMN_NAME_NAME,
                MyDBEntry.COLUMN_NAME_NUMBER, MyDBEntry.COLUMN_NAME_IMG};
        return db.query(MyDbHelper.DATABASE_NAME, projection, null, null, null, null, null);
    }

}
