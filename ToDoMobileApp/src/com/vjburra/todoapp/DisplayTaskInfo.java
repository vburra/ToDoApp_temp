package com.vjburra.todoapp;

import java.util.Calendar;

import com.vjburra.todoapp.R;
import com.vjburra.todoapp.dbTransContract.dbEntry;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class DisplayTaskInfo extends ActionBarActivity {

	Long taskId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_task_info);
		
		Intent intent = getIntent();
		taskId = intent.getLongExtra(dbEntry.COLUMN_NAME_TASK_ID,-1);
		//String mnth = intent.getStringExtra(MainActivity.SELECTED_MONTH);
		
		//TextView txt = new TextView(this);
		//txt.setText(msg);
		//setContentView(txt);
	}
	
	@Override
    protected void onStart() {
        super.onStart();
       // writeToDB();
        readFromDB();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_confirmation, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void createNotif(){
		
		
	}
	
	private void readFromDB(){
		dbTransactionHelper dbHlpr = new dbTransactionHelper(getApplicationContext());
		SQLiteDatabase dbw = dbHlpr.getWritableDatabase();
		
    	String query = "SELECT * FROM " + dbEntry.TASK_TABLE_NAME + " WHERE " + dbEntry.COLUMN_NAME_TASK_ID + " = " + taskId ;
    	//String[] selectionArgs = [];
    	Cursor c = dbw.rawQuery(query, null);
    	if(c.moveToFirst())
    	{
    		LinearLayout taskDisplay = (LinearLayout) findViewById(R.id.dbTaskDisplay);
    		taskDisplay.removeAllViews();//need to be removed and the loop should be modified to include just the latest entry
    		Context ctx = getApplicationContext();
    		do{
        		String taskID = c.getString(0);
        		String title = c.getString(1);
        		String duration = c.getString(4);
        		String nextTaskAt = c.getString(6);
        		
        		LinearLayout row = new LinearLayout(ctx);
        		row.setOrientation(LinearLayout.HORIZONTAL);
        		
        		TextView tvTask = new TextView(ctx);
        		tvTask.setText(taskID);
        		
        		TextView tvTitle = new TextView(ctx);
        		tvTitle.setText(title);
        		
        		TextView tvNextTask = new TextView(ctx);
        		tvNextTask.setText(nextTaskAt);
        		
        		Calendar cal = Calendar.getInstance();
        		String tmpDt = "" + cal.YEAR + "-" + cal.MONTH + "-" + cal.DATE + " " + cal.HOUR + ":" + cal.MINUTE + ":00"; 
        		String tempStr[] = {taskID,duration,nextTaskAt,tmpDt,null};
        		row.addView(tvTask);
        		row.addView(tvTitle);
        		row.addView(tvNextTask);
        		taskDisplay.addView(row);
        	}while(c.moveToNext());
    	}

	}
}
