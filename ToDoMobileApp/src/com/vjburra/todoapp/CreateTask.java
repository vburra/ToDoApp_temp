package com.vjburra.todoapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.vjburra.todoapp.dbTransContract.dbEntry;
//import android.support.v4.app.FragmentActivity;


public class CreateTask extends FragmentActivity 
						implements DatePickerDialog.OnDateSetListener , TimePickerDialog.OnTimeSetListener {

	String date_type = "";
	public static final String TASK_ID = "taskId";
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat dateOnlyFormat = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat timeOnlyFormat = new SimpleDateFormat("HH:mm");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_task);
		
		TextView sdtDef = (TextView) findViewById(R.id.startDate);
		TextView stvDef = (TextView) findViewById(R.id.startTime);
		
		Calendar cal = Calendar.getInstance();
		
		sdtDef.setText(dateOnlyFormat.format(cal.getTimeInMillis()));
		stvDef.setText(timeOnlyFormat.format(cal.getTimeInMillis()));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_task, menu);
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
	
    public static class TimePickerFragment extends DialogFragment  {
		
		private OnTimeSetListener tmListener;
		private Activity tmActivity;
		
		public int selectedHours;
		public int selectedMins;
		
		@Override
		public void onAttach(Activity activity){
			super.onAttach(activity);
			tmActivity = activity;
			
			try{
				tmListener = (OnTimeSetListener) activity;
			}catch(ClassCastException e){
				throw new ClassCastException(activity.toString() + " must implement onTimeSetListener");
			}
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);
		 
		
			// Create a new instance of TimePickerDialog and return it
			return new TimePickerDialog(tmActivity, tmListener, hour, minute,
					DateFormat.is24HourFormat(getActivity()));
		}
		
		
	}
	
	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		// Do something with the time chosen by the user
			//public static String localTime = "" + hourOfDay + ":" + minute;
			//this.selectedHours = hourOfDay;
			//this.selectedMins = minute;
		String hr = (hourOfDay<10)?("0" + hourOfDay):(""+hourOfDay);
		String min = (minute<10)?("0" + minute):(""+minute);
		TextView stTm = (TextView) findViewById(R.id.startTime);
	    stTm.setText( "" + hr + ":" + min + ":00" );//.getArguments("selectedHours");
	}
	public void showTimePickerDialog(View v) {
		TimePickerFragment newFragment = new TimePickerFragment();
	    newFragment.show(getFragmentManager() , "timePicker");
	}
	
    public static final class DatePickerFragment extends DialogFragment  {
		
		private OnDateSetListener tmListener;
		private Activity tmActivity;
		
		public int year;
		public int month;
		public int day;
		
		@Override
		public void onAttach(Activity activity){
			super.onAttach(activity);
			tmActivity = activity;
			
			try{
				tmListener = (OnDateSetListener) activity;
			}catch(ClassCastException e){
				throw new ClassCastException(activity.toString() + " must implement onDateSetListener");
			}
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DATE);
			// Create a new instance of TimePickerDialog and return it
			return new DatePickerDialog(tmActivity, tmListener, year, month,day);
		}
	}
	
	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		// Do something with the time chosen by the user
			//public static String localTime = "" + hourOfDay + ":" + minute;
			//this.selectedHours = hourOfDay;
			//this.selectedMins = minute;
		TextView Dt;
		//Spinner sp;
		if(date_type.equalsIgnoreCase("STARTDATE"))
		{
			Dt = (TextView) findViewById(R.id.startDate);
			//sp = (Spinner) findViewById(R.id.startDateSpinner);
		}
		else
		{
			Dt = (TextView) findViewById(R.id.endDate);
			//sp = (Spinner) findViewById(R.id.startDateSpinner);
			//Dt.VISIBLE = true;
		}
		Calendar selectedDate = Calendar.getInstance();
		selectedDate.set(year, month, day,0,0,0);
		
		Dt.setText(dateOnlyFormat.format(selectedDate.getTimeInMillis()));
		
		
//		String mnth,da;// = "" + month;
//		//String da = "" + day;;
//		if (month < 9) 
//			mnth = "0" + (month+1);
//		else
//			mnth = "" + (month+1);
//		if (day <10) 
//			da = "0" + day;
//		else
//			da = "" + day;
//		
//		Dt.setText( "" + year + "-" + mnth + "-" + da );//.getArguments("selectedHours");
//		
//		List <String> lst = new ArrayList <String> ();
//		lst.add("" + year + "-" + mnth + "-" + da );
//		ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lst);
//		adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		//sp.setOnItemClickListener(showDatePickerDialog);
//	    
//	    sp.setAdapter(adp);
	}
	
	public void showDatePickerDialog(View v) {
		date_type = "STARTDATE";
		DatePickerFragment dtFragment = new DatePickerFragment();
	    dtFragment.show(getFragmentManager() , "datePicker");
	}
	
	public void showEndDateDialog(View v) {
		date_type = "ENDDATE";
		DatePickerFragment dtFragment = new DatePickerFragment();
	    dtFragment.show(getFragmentManager() , "datePicker");
	}
	    
	public void saveTaskDetails(View view){
		EditText titleComp = (EditText) findViewById(R.id.title);
    	String title = titleComp.getText().toString();
    	
    	RadioButton repeatBtn = (RadioButton) findViewById(R.id.freq_repeat);
    	boolean repeat = repeatBtn.isChecked();
    	
    	TextView sdtComp = (TextView) findViewById(R.id.startDate);    	
    	TextView stTimeComp = (TextView) findViewById(R.id.startTime);
    	//String sdt = "" + sdtComp.getYear() + "-" + sdtComp.getMonth() + "-" + sdtComp.getDayOfMonth()  + " " + stTimeComp.getCurrentHour() 
    			//+ ":" + stTimeComp.getCurrentMinute() + ":" + "00";
    	String sdt = "" + sdtComp.getText() + " " + stTimeComp.getText();
    	//Date sdt = new Datenew Date(sdtComp.getYear(), sdtComp.getMonth(), sdtComp.getDayOfMonth());
    	// stTimeComp = new (sdtComp.getYear(), sdtComp.getMonth(), sdtComp.getDayOfMonth());
    	
    	TimePicker durationComp = (TimePicker) findViewById(R.id.duration);
    	Integer duration = Integer.valueOf(durationComp.getCurrentHour()*60 + durationComp.getCurrentMinute());
    	
    	//Spinner durationSpinner = (Spinner) findViewById(R.id.duration);
    	//Integer duration = Integer.valueOf(durationSpinner.getSelectedItem().toString());
    	TextView edtComp = (TextView) findViewById(R.id.endDate);    	
    	//String edt = "" + edtComp.getYear() + "-" + edtComp.getMonth() + "-" + edtComp.getDayOfMonth()  + " 00:00:00";
    	String edt = "" + edtComp.getText();
    	
    	dbTransactionHelper dbHlpr = new dbTransactionHelper(getApplicationContext());
		SQLiteDatabase dbw = dbHlpr.getWritableDatabase();
		
		
    	
    	ContentValues val = new ContentValues();
		val.put(dbEntry.COLUMN_NAME_TITLE, title);
		//val.put(dbEntry.COLUMN_NAME_FREQUENCY, frequency);
		//val.put(dbEntry.COLUMN_NAME_ENABLE_FLAG, Enable);
		//Date startDateForDB = dateFormat.parse(sdt);
		val.put(dbEntry.COLUMN_NAME_START_DATE, sdt);
		val.put(dbEntry.COLUMN_NAME_END_DATE, edt);
		//val.put(dbEntry.COLUMN_NAME_START_TIME, StTime);
		val.put(dbEntry.COLUMN_NAME_DURATION, duration);
		//val.put(dbEntry.COLUMN_NAME_NEXT_TASK_AT, sdt);
		//val.put(dbEntry.COLUMN_NAME_NEXT_TASK_AT, sdt);
		String nxtDt = sdt;
		//Update next task date time by figuring out the date/time between start and end dates in case of repeteition
		//if the freq is once and date is today and time > now , what should be the logic ???
		//if()	
		//	val.put(dbEntry.COLUMN_NAME_DURATION, duration);
		
		if(repeat)
		{
			ContentValues freqVal = new ContentValues();
			Boolean sun = ((CheckBox) findViewById(R.id.sun)).isChecked();
			Boolean mon = ((CheckBox) findViewById(R.id.mon)).isChecked();
			Boolean tue = ((CheckBox) findViewById(R.id.tue)).isChecked();
			Boolean wed = ((CheckBox) findViewById(R.id.wed)).isChecked();
			Boolean thu = ((CheckBox) findViewById(R.id.thu)).isChecked();
			Boolean fri = ((CheckBox) findViewById(R.id.fri)).isChecked();
			Boolean sat = ((CheckBox) findViewById(R.id.sat)).isChecked();
			
			Boolean selectedDays[] = {false, mon, tue, wed, thu , fri, sat, sun};

			freqVal.put(dbEntry.COLUMN_NAME_REPETITION_FREQUENCY_SU, sun);
			freqVal.put(dbEntry.COLUMN_NAME_REPETITION_FREQUENCY_MO, mon);
			freqVal.put(dbEntry.COLUMN_NAME_REPETITION_FREQUENCY_TU, tue);
			freqVal.put(dbEntry.COLUMN_NAME_REPETITION_FREQUENCY_WE, wed);
			freqVal.put(dbEntry.COLUMN_NAME_REPETITION_FREQUENCY_TH, thu);
			freqVal.put(dbEntry.COLUMN_NAME_REPETITION_FREQUENCY_FR, fri);
			freqVal.put(dbEntry.COLUMN_NAME_REPETITION_FREQUENCY_SA, sat);
			
			long freqTableID = dbw.insert(dbEntry.FREQUENCY_TABLE_NAME, null, freqVal);
			val.put(dbEntry.COLUMN_NAME_FREQTABLE_ID, freqTableID);
			
			computeNextAlarmDate newDtComp = new computeNextAlarmDate();
			nxtDt = newDtComp.generateNextAlarmDate(sdt, selectedDays, edt,false);
			
		}
		val.put(dbEntry.COLUMN_NAME_NEXT_TASK_AT, nxtDt);
		boolean enabled = true;
		if(nxtDt == null){
			enabled = false;
		}
		val.put(dbEntry.COLUMN_NAME_ENABLE_FLAG, enabled);
		
		long dbTaskId = dbw.insert(dbEntry.TASK_TABLE_NAME, null, val);
		try {
			customReminders rem = new customReminders();
			rem.setReminder(getApplicationContext(), dateFormat.parse(nxtDt), dbTaskId, title, duration);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Close the view after saving to DB by calling this.finish();
		closeTask();
	}
	
	public void cancelTask(View view)
	{
		closeTask();
	}
	
	public void closeTask()
	{
		this.finish();
	}
	
}


