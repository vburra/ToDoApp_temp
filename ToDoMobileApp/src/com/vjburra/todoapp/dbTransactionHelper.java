package com.vjburra.todoapp;

import com.vjburra.todoapp.dbTransContract.dbEntry;

import android.content.Context;
//import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class dbTransactionHelper extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ToDoDB_Test7.db";
    
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String INT_TYPE = " INTEGER";
    private static final String LONG_TYPE = " LONG";
    private static final String DATE_TIME_TYPE = " DATETIME";
    private static final String DATE_TYPE = " DATE";
    private static final String TIME_TYPE = " TIME";
    private static final String BOOLEAN_TYPE = " BOOLEAN";
    
    private static final String SQL_CREATE_TASK_ENTRIES =
            "CREATE TABLE " + dbEntry.TASK_TABLE_NAME + " (" +
            dbEntry.COLUMN_NAME_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            dbEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
            dbEntry.COLUMN_NAME_START_DATE + DATE_TIME_TYPE + COMMA_SEP +
            dbEntry.COLUMN_NAME_END_DATE + DATE_TYPE + COMMA_SEP +
            //dbEntry.COLUMN_NAME_START_TIME + TIME_TYPE + COMMA_SEP +
            dbEntry.COLUMN_NAME_DURATION + INT_TYPE + COMMA_SEP +
            dbEntry.COLUMN_NAME_FREQTABLE_ID + LONG_TYPE + COMMA_SEP +
            dbEntry.COLUMN_NAME_NEXT_TASK_AT + DATE_TIME_TYPE + COMMA_SEP +
            dbEntry.COLUMN_NAME_ENABLE_FLAG + TEXT_TYPE + 
            " )";
    
    private static final String SQL_CREATE_FREQUENCY_ENTRIES =
            "CREATE TABLE " + dbEntry.FREQUENCY_TABLE_NAME + " (" +
            dbEntry._ID + " INTEGER PRIMARY KEY," +
            dbEntry.COLUMN_NAME_REPETITION_FREQUENCY_SU + BOOLEAN_TYPE + COMMA_SEP +
            dbEntry.COLUMN_NAME_REPETITION_FREQUENCY_MO + BOOLEAN_TYPE + COMMA_SEP +
            dbEntry.COLUMN_NAME_REPETITION_FREQUENCY_TU + BOOLEAN_TYPE + COMMA_SEP +
            dbEntry.COLUMN_NAME_REPETITION_FREQUENCY_WE + BOOLEAN_TYPE + COMMA_SEP +
            dbEntry.COLUMN_NAME_REPETITION_FREQUENCY_TH + BOOLEAN_TYPE + COMMA_SEP +
            dbEntry.COLUMN_NAME_REPETITION_FREQUENCY_FR + BOOLEAN_TYPE + COMMA_SEP +
            dbEntry.COLUMN_NAME_REPETITION_FREQUENCY_SA + BOOLEAN_TYPE +
            " )";
    
    private static final String SQL_CREATE_TRACKING_ENTRIES =
            "CREATE TABLE " + dbEntry.TRACKING_TABLE_NAME + " (" +
            dbEntry._ID + " INTEGER PRIMARY KEY," +
            dbEntry.COLUMN_NAME_TASK_ID + INT_TYPE + COMMA_SEP +	
            dbEntry.COLUMN_NAME_PLANNED_START_TIME + TIME_TYPE + COMMA_SEP +
            dbEntry.COLUMN_NAME_ACTUAL_TIMETAKEN + TIME_TYPE + COMMA_SEP +
            dbEntry.COLUMN_NAME_PLANNED_DURATION + INT_TYPE + COMMA_SEP +
            dbEntry.COLUMN_NAME_DISCARDED + TEXT_TYPE + COMMA_SEP +
            dbEntry.COLUMN_NAME_START_DELAY + INT_TYPE +
            " )";
    
 /*   private static final String SQL_CREATE_DISCARDED_ENTRIES =
            "CREATE TABLE " + dbEntry.DISCARDED_TABLE_NAME + " (" +
            dbEntry._ID + " INTEGER PRIMARY KEY," +
            dbEntry.COLUMN_NAME_TASK_ID + INT_TYPE + COMMA_SEP +	
            dbEntry.COLUMN_NAME_DISCARDED + TEXT_TYPE + COMMA_SEP +
            dbEntry.COLUMN_NAME_NOTES + TEXT_TYPE + 
            " )";

    private static final String SQL_DELETE_TRACK_ENTRIES =
            "DROP TABLE IF EXISTS " + dbEntry.TRACKING_TABLE_NAME;
    private static final String SQL_DELETE_TASK_ENTRIES =
            "DROP TABLE IF EXISTS " + dbEntry.TASK_TABLE_NAME;
    */
    
    
	public dbTransactionHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_TASK_ENTRIES);
		db.execSQL(SQL_CREATE_TRACKING_ENTRIES);
		//db.execSQL(SQL_CREATE_DISCARDED_ENTRIES);
		db.execSQL(SQL_CREATE_FREQUENCY_ENTRIES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//db.execSQL(SQL_DELETE_TASK_ENTRIES);
		//db.execSQL(SQL_DELETE_TRACK_ENTRIES);
        //onCreate(db);
		
		//Logic to Update New schema.
	}

}
