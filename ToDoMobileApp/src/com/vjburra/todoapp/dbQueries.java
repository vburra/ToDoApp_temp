package com.vjburra.todoapp;

import com.vjburra.todoapp.dbTransContract.dbEntry;

import android.content.*;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class dbQueries {
	
	//dbTransactionHelper dbHlpr = new dbTransactionHelper(context);
	private void createTaskTableEntry(Context context, Intent intent){
		dbTransactionHelper dbHlpr = new dbTransactionHelper(context);
		SQLiteDatabase dbw = dbHlpr.getWritableDatabase();
		
		//Intent intent = getIntent();
		String title = intent.getStringExtra(MainActivity.TEXT_MESSAGE);
		String StDate = intent.getStringExtra(MainActivity.TEXT_MESSAGE);
		String EnDate = intent.getStringExtra(MainActivity.TEXT_MESSAGE);
		String StTime = intent.getStringExtra(MainActivity.TEXT_MESSAGE);
		String Duration = intent.getStringExtra(MainActivity.TEXT_MESSAGE);
		String Enable = intent.getStringExtra(MainActivity.TEXT_MESSAGE);
		//String mnth = intent.getStringExtra(MainActivity.SELECTED_MONTH);
		
		ContentValues val = new ContentValues();
		val.put(dbEntry.COLUMN_NAME_TITLE, title);
		val.put(dbEntry.COLUMN_NAME_FREQTABLE_ID, StDate);
		val.put(dbEntry.COLUMN_NAME_ENABLE_FLAG, Enable);
		val.put(dbEntry.COLUMN_NAME_START_DATE, StDate);
		val.put(dbEntry.COLUMN_NAME_END_DATE, EnDate);
		//val.put(dbEntry.COLUMN_NAME_START_TIME, StTime);
		val.put(dbEntry.COLUMN_NAME_DURATION, Duration);
		val.put(dbEntry.COLUMN_NAME_ENABLE_FLAG, Enable);
		
		long newRow = dbw.insert(dbEntry.TASK_TABLE_NAME, null, val);
		
		//createNotif();
	}
	
	private void createFrequencyTable(Context context, Intent intent){
		dbTransactionHelper dbHlpr = new dbTransactionHelper(context);
		SQLiteDatabase dbw = dbHlpr.getWritableDatabase();
		
		//Intent intent = getIntent();
		//Boolean su = intent.getBooleanExtra(su, true);
		//Boolean mo = intent.getBooleanExtra(true);
		//Boolean tu = intent.getBooleanExtra(true);
		//Boolean we = intent.getBooleanExtra(true);
		//Boolean th = intent.getBooleanExtra(true);
		//Boolean fr = intent.getBooleanExtra(true);
		//Boolean sa = intent.getBooleanExtra(true);
		
		String taskId = intent.getStringExtra(MainActivity.TEXT_MESSAGE);
		
		ContentValues val = new ContentValues();
		val.put(dbEntry.COLUMN_NAME_TASK_ID, taskId);
		val.put(dbEntry.COLUMN_NAME_REPETITION_FREQUENCY_SU, true);
		val.put(dbEntry.COLUMN_NAME_REPETITION_FREQUENCY_MO, true);
		val.put(dbEntry.COLUMN_NAME_REPETITION_FREQUENCY_TU, true);
		val.put(dbEntry.COLUMN_NAME_REPETITION_FREQUENCY_WE, true);
		val.put(dbEntry.COLUMN_NAME_REPETITION_FREQUENCY_TH, true);
		val.put(dbEntry.COLUMN_NAME_REPETITION_FREQUENCY_FR, true);
		val.put(dbEntry.COLUMN_NAME_REPETITION_FREQUENCY_SA, true);
		
		long newRow = dbw.insert(dbEntry.FREQUENCY_TABLE_NAME, null, val);
		
	}
	
	private void createTrackingTableEntry(Context context, Intent intent){
		dbTransactionHelper dbHlpr = new dbTransactionHelper(context);
		SQLiteDatabase dbw = dbHlpr.getWritableDatabase();
		
		//Intent intent = getIntent();
		String taskId = intent.getStringExtra(MainActivity.TEXT_MESSAGE);
		String StDate = intent.getStringExtra(MainActivity.TEXT_MESSAGE);
		String EnDate = intent.getStringExtra(MainActivity.TEXT_MESSAGE);
		String StTime = intent.getStringExtra(MainActivity.TEXT_MESSAGE);
		String Duration = intent.getStringExtra(MainActivity.TEXT_MESSAGE);
		String Enable = intent.getStringExtra(MainActivity.TEXT_MESSAGE);
		String Discarded = intent.getStringExtra(MainActivity.TEXT_MESSAGE);
		//String mnth = intent.getStringExtra(MainActivity.SELECTED_MONTH);
		
		ContentValues val = new ContentValues();
		val.put(dbEntry.COLUMN_NAME_TASK_ID, taskId);
		val.put(dbEntry.COLUMN_NAME_PLANNED_START_TIME, StTime);
		val.put(dbEntry.COLUMN_NAME_PLANNED_DURATION, Duration);
		val.put(dbEntry.COLUMN_NAME_ACTUAL_TIMETAKEN, StTime);
		val.put(dbEntry.COLUMN_NAME_START_DELAY, StTime);
		
		long newRow = dbw.insert(dbEntry.TRACKING_TABLE_NAME, null, val);
		
		//createNotif();
	}
	
	private void createDiscardedTableEntry(Context context, Intent intent){
		dbTransactionHelper dbHlpr = new dbTransactionHelper(context);
		SQLiteDatabase dbw = dbHlpr.getWritableDatabase();
		
		//Intent intent = getIntent();
		String taskId = intent.getStringExtra(MainActivity.TEXT_MESSAGE);
		String Discarded = intent.getStringExtra(MainActivity.TEXT_MESSAGE);
		String Notes = intent.getStringExtra(MainActivity.TEXT_MESSAGE);
		//String mnth = intent.getStringExtra(MainActivity.SELECTED_MONTH);
		
		ContentValues val = new ContentValues();
		val.put(dbEntry.COLUMN_NAME_TASK_ID, taskId);
//		val.put(dbEntry.COLUMN_NAME_NOTES, Notes);
		val.put(dbEntry.COLUMN_NAME_DISCARDED, Discarded);
		
	//	long newRow = dbw.insert(dbEntry.DISCARDED_TABLE_NAME, null, val);
		
		//createNotif();
	}
}
