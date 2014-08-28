package com.vjburra.todoapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import com.vjburra.todoapp.dbTransContract.dbEntry;

public class NotifHandlerTemp extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		int notificationId = intent.getIntExtra("notificationId", 0);
		String buttonClicked = intent.getStringExtra("buttonClicked");
		String sdt = intent.getStringExtra(dbEntry.COLUMN_NAME_PLANNED_START_TIME);
		Integer duration = intent.getIntExtra(dbEntry.COLUMN_NAME_PLANNED_DURATION,0);
		Long taskId = intent.getLongExtra(dbEntry.COLUMN_NAME_TASK_ID,-1);
		String discardFlag = "";
		if(buttonClicked == "START")
		{
	    	
			discardFlag = "N";
		}
		else
		{
			//if Discard update the Discard Flag
			discardFlag = "Y";
		}
		
		dbTransactionHelper dbHlpr = new dbTransactionHelper(context.getApplicationContext());
		SQLiteDatabase dbw = dbHlpr.getWritableDatabase();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calNow = Calendar.getInstance();
		Calendar calStart = Calendar.getInstance();
    	ContentValues val = new ContentValues();
    	Date tempDt = calNow.getTime();
		try {
			tempDt = dateFormat.parse(sdt);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		calStart.set(tempDt.getYear(), tempDt.getMonth(), tempDt.getDay(), tempDt.getHours(), tempDt.getMinutes());
		long delay = (calNow.getTimeInMillis() - calStart.getTimeInMillis())/(60*1000);
    	
    	val.put(dbEntry.COLUMN_NAME_TASK_ID, taskId);
    	val.put(dbEntry.COLUMN_NAME_START_DELAY, delay);
		val.put(dbEntry.COLUMN_NAME_PLANNED_DURATION, duration);
		val.put(dbEntry.COLUMN_NAME_PLANNED_START_TIME, sdt);
		val.put(dbEntry.COLUMN_NAME_DISCARDED, discardFlag);
		
		long dbRwoId = dbw.insert(dbEntry.TRACKING_TABLE_NAME, null, val);
		
		NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(notificationId);
		
	}

}
