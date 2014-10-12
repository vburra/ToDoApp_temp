package com.vjburra.todoapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vjburra.todoapp.dbTransContract.dbEntry;

public class updateNextAlarmTime extends Activity {
	
	public void fetchRepeatInfo(long taskId, Context context){
	
	    	dbTransactionHelper dbHlpr = new dbTransactionHelper(context.getApplicationContext());
			SQLiteDatabase dbw = dbHlpr.getWritableDatabase();
			
			Boolean updatedFlag = false;
			
	    	String query = "SELECT * FROM " + dbEntry.TASK_TABLE_NAME + " WHERE " + 
	    					dbEntry.COLUMN_NAME_TASK_ID + " = " + taskId + " AND " +
	    					dbEntry.COLUMN_NAME_ENABLE_FLAG + " = 1";
	    	//String[] selectionArgs = [];
	    	ContentValues cv = new ContentValues();
	    	
	    	Cursor c = dbw.rawQuery(query, null);
	    	
	    	if(c.moveToFirst())
	    	{
	    		//Context ctx = getApplicationContext();
	    		do{
	        		//String taskID = c.getString(0);
	        		String title = c.getString(1);
	        		//String stDate = c.getString(2);
	        		String enDate = c.getString(3);// + " 00:00:00";//TODO Fix this to be correct from DB
	        		Integer duration = c.getInt(4);
	        		String freqTableId = c.getString(5);
	        		String nextTaskAt = c.getString(6);
	        		String enabled = c.getString(7);
	        		
	        		Boolean selectedDays[] = new Boolean[8];
	        		String nextDateCalculated = "";
	        		
	        		if(enabled.equalsIgnoreCase("1") && freqTableId != null)
	        		{
	        			String freqTableQuery = "SELECT * FROM " + dbEntry.FREQUENCY_TABLE_NAME + " WHERE " + 
		    					dbEntry._ID + " = " + freqTableId;
	        			Cursor c2 = dbw.rawQuery(freqTableQuery, null);
	        			c2.moveToFirst();
	        			selectedDays[1] = (c2.getString(1).equalsIgnoreCase("1"));
	        			selectedDays[2] = (c2.getString(2).equalsIgnoreCase("1"));
	        			selectedDays[3] = (c2.getString(3).equalsIgnoreCase("1"));
	        			selectedDays[4] = (c2.getString(4).equalsIgnoreCase("1"));
	        			selectedDays[5] = (c2.getString(5).equalsIgnoreCase("1"));
	        			selectedDays[6] = (c2.getString(6).equalsIgnoreCase("1"));
	        			selectedDays[7] = (c2.getString(7).equalsIgnoreCase("1"));
	        			computeNextAlarmDate newDtComp = new computeNextAlarmDate();
	        			nextDateCalculated = newDtComp.generateNextAlarmDate(nextTaskAt, selectedDays, enDate, true);
		        		cv.put(dbEntry.COLUMN_NAME_NEXT_TASK_AT, nextDateCalculated);
		        		boolean enableFlag = true;
		        		if(nextDateCalculated == null){
		        			enableFlag = false;
		        			cv.put(dbEntry.COLUMN_NAME_ENABLE_FLAG,enableFlag );
		        		}
		        		c2.close();
		        		updatedFlag = true;
		        		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        				Date tempDt = new Date();
								try {
									tempDt = dateFormat.parse(nextDateCalculated);
								} catch (ParseException e) {
									e.printStackTrace();
								}
						customReminders rem = new customReminders();
						rem.setReminder(context, tempDt, taskId, title, duration);
	        		}
	        		
	        		//Calendar cal = Calendar.getInstance();
	        		//String tmpDt = "" + cal.YEAR + "-" + cal.MONTH + "-" + cal.DATE + " " + cal.HOUR + ":" + cal.MINUTE + ":00"; 

	        		//String tempStr[] = {taskID,duration,nextTaskAt,tmpDt,null};
	        	}while(c.moveToNext());
	    	}
	    	c.close();
	    	
	    	if(updatedFlag){
	    		dbw.update(dbEntry.TASK_TABLE_NAME, cv,  dbEntry.COLUMN_NAME_TASK_ID  +  " = " + taskId, null);
	    	}
	    	dbw.close();
	    }


}
