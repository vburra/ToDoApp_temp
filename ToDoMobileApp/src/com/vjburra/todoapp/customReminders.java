package com.vjburra.todoapp;

import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.vjburra.todoapp.dbTransContract.dbEntry;

public class customReminders {
	
	public void setReminder(Context context, Date nxtDt, long taskId, String title, Integer dur){
    	Context ctx = context.getApplicationContext();
    	AlarmManager amgr = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
    	
    	Intent alarmIntent = new Intent(ctx, AlarmReceiver.class);
    	alarmIntent.putExtra(dbEntry.COLUMN_NAME_TASK_ID, taskId);
    	alarmIntent.putExtra(dbEntry.COLUMN_NAME_TITLE, title);
    	alarmIntent.putExtra(dbEntry.COLUMN_NAME_PLANNED_START_TIME, ""+nxtDt.getTime());
    	alarmIntent.putExtra(dbEntry.COLUMN_NAME_PLANNED_DURATION, dur);
    	
    	PendingIntent pAlarmInt = PendingIntent.getBroadcast(ctx, 0, alarmIntent, 0);
    	
    	//Calendar myCal = Calendar.getInstance();
        //myCal.setTimeInMillis(90000);
    	//OmyCal.add(Calendar.SECOND, 5);
    	//amgr.set(AlarmManager.RTC_WAKEUP, myCal.getTimeInMillis(), pAlarmInt);
    	amgr.set(AlarmManager.RTC_WAKEUP, nxtDt.getTime(), pAlarmInt);
    	//Log.v( TAG, "*****" + getString(R.string.user_name)+ "----" +  myCal.getTimeInMillis() + "####");
    	
    	Toast.makeText(context.getApplicationContext(),"Alarm set for testing " + nxtDt.getDate() + "/" + nxtDt.getHours() + ":" + nxtDt.getMinutes() + ":00" , Toast.LENGTH_LONG).show();
    	//createNotification();
	}

}
