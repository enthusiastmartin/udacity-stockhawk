package com.sam_chordas.android.stockhawk.historyData;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by martin on 6/9/16.
 */
public class HistoryDbHelper extends SQLiteOpenHelper{

    private static final String TEXT_TYPE = " TEXT";
    private static final String REAL_TYPE = " FLOAT";
    private static final String DATETIME_TYPE = " DATETIME";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
        "CREATE TABLE " + HistoryContract.QuoteEntry.TABLE_NAME + " (" +
            HistoryContract.QuoteEntry._ID+ " INTEGER PRIMARY KEY," +
            HistoryContract.QuoteEntry.COLUMN_QUoTE_SYMBOL+ TEXT_TYPE + COMMA_SEP +
            HistoryContract.QuoteEntry.COLUMN_QUoTE_VALUE+ REAL_TYPE + COMMA_SEP +
            HistoryContract.QuoteEntry.COLUMN_QUoTE_TIMESTAMP + DATETIME_TYPE + " DEFAULT CURRENT_TIMESTAMP"+
        " )";

    private static final String SQL_DELETE_ENTRIES =
        "DROP TABLE IF EXISTS " + HistoryContract.QuoteEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "quotes.db";

    public HistoryDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String TAG = "BLA";

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: creating db " + SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
