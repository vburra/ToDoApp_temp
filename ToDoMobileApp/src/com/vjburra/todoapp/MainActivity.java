package com.vjburra.todoapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.support.v7.app.ActionBarActivity;

import com.vjburra.todoapp.dbTransContract.dbEntry;
//import android.support.v4.app.Fragment;

public class MainActivity extends ActionBarActivity{
	
	public static final String TEXT_MESSAGE = "com.vjburra.todoapp.MESSAGE";
	public static final String SELECTED_MONTH = "com.vjburra.todoapp.MONTH";
	public static final String TAG = "LogActivity";

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
        	//android.support.v4.app.Fragment f =  new PlaceholderFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        
        SharedPreferences pref = getPreferences(Context.MODE_PRIVATE);
    	//String str = getResources().getString(getString(R.string.user_name));
    	String name = pref.getString(getString(R.string.user_name), "");
    	Log.v( TAG, "*****" + getString(R.string.user_name)+ "----" +  name + "####");
    	/*
    	if(name == "")
    	{
    		//Display Field to enter name
    		
    	}
    	else
    	{
    		//Hide Field to enter name, display welcome message instead
    		TextView txt = (TextView) findViewById(R.id.welcomeMsg);
    		txt.setText("name");
    		
    		//findViewById(R.id.editMsg).setVisibility(0);
    	}*/
    }
	
	@Override
    protected void onStart() {
        super.onStart();
                
        SharedPreferences pref = getPreferences(Context.MODE_PRIVATE);
    	String name = pref.getString(getString(R.string.user_name), "");    	
    	if(name != "")
    	{
    		//Hide Field to enter name, display welcome message instead
    		TextView txt = (TextView) findViewById(R.id.welcomeMsg);
    		txt.setText(name);    		
    		//startIntent(name);
    		//findViewById(R.id.editMsg).setVisibility(View.INVISIBLE);
    		//findViewById(R.id.save).setVisibility(View.INVISIBLE);
    		readFromDB();
    	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_action, menu);
        return super.onCreateOptionsMenu(menu);
        //return true;
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
        else if(id == R.id.main_action)
        {
        	actionClicked();
        	return true;
        }
        else if(id == R.id.create_NewTask)
        {
        	displayNewTaskView();
        	return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
    
    /*
     * Start Main Programming
     */
    public void saveInfo(View view){
    	
    	EditText edt = (EditText) findViewById(R.id.editMsg);
    	String msg = edt.getText().toString();
    	
    	SharedPreferences pref = getPreferences(Context.MODE_PRIVATE);
    	SharedPreferences.Editor editor = pref.edit();
    	editor.putString(getString(R.string.user_name), msg);
    	editor.commit();
    	startIntent(msg);
    }
    
    public void displayNewTaskView(){
    	Intent createTaskIntent = new Intent(this, CreateTask.class);
    	startActivity(createTaskIntent);
    }
    
    public void actionClicked(){
    	Log.v(TAG, "***********Action Click Called");
    	setReminder();

    }
    
    private void startIntent(String msg){
    	Intent intent = new Intent(this, DisplayTaskInfo.class);
    	//Spinner mnthSpinner = (Spinner) findViewById(R.id.month_spinner);
    	//String mnth = String.valueOf(mnthSpinner.getSelectedItem());
    	
    	intent.putExtra(TEXT_MESSAGE, msg);
    	//intent.putExtra(SELECTED_MONTH, mnth);
    	startActivity(intent);
    }
    
    private void setReminder(){
    	Context ctx = getApplicationContext();
    	AlarmManager amgr = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
    	
    	Intent alarmIntent = new Intent(ctx, AlarmReceiver.class);
    	//alarmIntent.putExtra(name, value);
    	PendingIntent pAlarmInt = PendingIntent.getBroadcast(ctx, 0, alarmIntent, 0);
    	
    	Calendar myCal = Calendar.getInstance();
        //myCal.setTimeInMillis(90000);
    	myCal.add(Calendar.SECOND, 5);
    	amgr.set(AlarmManager.RTC_WAKEUP, myCal.getTimeInMillis(), pAlarmInt);
    	
    	Log.v( TAG, "*****" + getString(R.string.user_name)+ "----" +  myCal.getTimeInMillis() + "####");
    	
    	Toast.makeText(getApplicationContext(),"Alarm set for testing " + myCal.getTime().toString(), Toast.LENGTH_LONG).show();
    }
    
    public void readFromDB(){
    	dbTransactionHelper dbHlpr = new dbTransactionHelper(getApplicationContext());
		SQLiteDatabase dbw = dbHlpr.getWritableDatabase();
		
    	String query = "SELECT * FROM " + dbEntry.TASK_TABLE_NAME + " WHERE " + dbEntry.COLUMN_NAME_ENABLE_FLAG + " = 1 ORDER BY " + dbEntry.COLUMN_NAME_NEXT_TASK_AT + " ASC LIMIT 5";
    	//String[] selectionArgs = [];
    	Cursor c = dbw.rawQuery(query, null);
    	if(c.moveToFirst())
    	{
    		LinearLayout taskDisplay = (LinearLayout) findViewById(R.id.dbTasks);
    		taskDisplay.removeAllViews();//need to be removed and the loop should be modified to include just the latest entry
    		Context ctx = getApplicationContext();
    		do{
        		String taskID = c.getString(0);
        		String title = c.getString(1);
        		//String stDate = c.getString(2);
        		//String enDate = c.getString(3);
        		String duration = c.getString(4);
        		//String freqTableId = c.getString(5);
        		String nextTaskAt = c.getString(6);
        		//String enabled = c.getString(6);
        		
        		LinearLayout row = new LinearLayout(ctx);
        		row.setOrientation(LinearLayout.HORIZONTAL);
        		
        		TextView tvTask = new TextView(ctx);
        		tvTask.setText(taskID);
        		
        		TextView tvTitle = new TextView(ctx);
        		tvTitle.setText(title);
        		
        		TextView tvNextTask = new TextView(ctx);
        		tvNextTask.setText(nextTaskAt);
        		
        		
        		ToggleButton btn = new ToggleButton(ctx);
        		//btn.setTag(taskID);
        		//btn.setTag(2, duration);
        		//btn.setTag(3, nextTaskAt);
        		//btn.setTag(4, "Record");
        		Calendar cal = Calendar.getInstance();
        		String tmpDt = "" + cal.YEAR + "-" + cal.MONTH + "-" + cal.DATE + " " + cal.HOUR + ":" + cal.MINUTE + ":00"; 
        		String tempStr[] = {taskID,duration,nextTaskAt,tmpDt,null};
//        		btn.setText("Record");
        		btn.setTextOn("Pause");// need to replace with custom style green/red/grey for tracking time
        		btn.setTextOff("Record");
        		
        		//btn.getTag();
        		btn.setTag(tempStr);
        		btn.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						String tempArr[] = (String []) v.getTag();
						long id = saveTrackingDetails(tempArr);
						tempArr[4] = Long.toString(id);
						
						v.setTag(tempArr);
					}
				});
        		row.addView(tvTask);
        		row.addView(tvTitle);
        		row.addView(tvNextTask);
        		row.addView(btn);
        		taskDisplay.addView(row);
        	}while(c.moveToNext());
    	}
    }
    
    public long saveTrackingDetails(String obj[]){
    	//String tempStr[] = {taskID,duration,nextTaskAt};
    	//String taskID = obj.[0];
    	dbTransactionHelper dbHlpr = new dbTransactionHelper(getApplicationContext());
		SQLiteDatabase dbw = dbHlpr.getWritableDatabase();
		
		java.util.Date executeDt = new java.util.Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date startDate = new java.util.Date();
		try {
			startDate = dateFormat.parse(obj[3]);
			executeDt = dateFormat.parse(obj[2]);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
    	
    	ContentValues val = new ContentValues();
    	if(obj[4] == null)
    	{
    		long stDelay = (startDate.getTime() - executeDt.getTime())/(1000*60*60);
        	//Ignore delay by resetting it to ZERO if the delay is less than 2 mins
        	if(stDelay < 2)
        		stDelay = 0;
        	
    		val.put(dbEntry.COLUMN_NAME_TASK_ID, obj[0]);
        	val.put(dbEntry.COLUMN_NAME_PLANNED_DURATION, obj[1]);
        	val.put(dbEntry.COLUMN_NAME_PLANNED_START_TIME, obj[2]);
        	val.put(dbEntry.COLUMN_NAME_START_DELAY, stDelay);
        	
        	long dbTaskId = dbw.insert(dbEntry.TRACKING_TABLE_NAME, null, val);
    		return dbTaskId;
    	}
    	else{
    		Calendar cal = Calendar.getInstance();
    		long actualTimeTakenForTask = ((cal.getTimeInMillis() - startDate.getTime())/(1000*60*60));
        	
    		val.put(dbEntry.COLUMN_NAME_ACTUAL_TIMETAKEN, actualTimeTakenForTask);
    		
    		//String selection = dbEntry._ID + " = ";
    		//String[] selectionArgs = { obj[4]};

    		long id = dbw.update(
    		    dbEntry.TRACKING_TABLE_NAME,
    		    val,
    		    "_id = " + Integer.parseInt(obj[4]), null);
    		
    		return id;
    	}
    }
}