package com.vjburra.todoapp;

import android.provider.BaseColumns;

public final class dbTransContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public dbTransContract() {}

    /* Inner class that defines the table contents */
    public static abstract class dbEntry implements BaseColumns {
    	public static final String TASK_TABLE_NAME = "taskDetails";
    	public static final String TRACKING_TABLE_NAME = "trackingDetails";
    	public static final String FREQUENCY_TABLE_NAME = "frequencyDetails";
    	//public static final String DISCARDED_TABLE_NAME = "discardedDetails";
        
        public static final String COLUMN_NAME_TASK_ID = "TaskId";
        //Task Table
        public static final String COLUMN_NAME_TITLE = "Title";
        public static final String COLUMN_NAME_START_DATE = "StartDate";
        public static final String COLUMN_NAME_END_DATE = "EndDate";
        //public static final String COLUMN_NAME_START_TIME = "StartTime";
        public static final String COLUMN_NAME_DURATION = "Duration";
        public static final String COLUMN_NAME_ENABLE_FLAG = "Enabled";
        public static final String COLUMN_NAME_FREQTABLE_ID = "Frequency";
        public static final String COLUMN_NAME_NEXT_TASK_AT = "NextTaskAt";
        
        //Frequency Table
        public static final String COLUMN_NAME_REPETITION_FREQUENCY_SU = "SUNDAY";
        public static final String COLUMN_NAME_REPETITION_FREQUENCY_MO = "MONDAY";
        public static final String COLUMN_NAME_REPETITION_FREQUENCY_TU = "TUESDAY";
        public static final String COLUMN_NAME_REPETITION_FREQUENCY_WE = "WEDNESDAY";
        public static final String COLUMN_NAME_REPETITION_FREQUENCY_TH = "THURSDAY";
        public static final String COLUMN_NAME_REPETITION_FREQUENCY_FR = "FRIDAY";
        public static final String COLUMN_NAME_REPETITION_FREQUENCY_SA = "SATURDAY";
        
        
        //Tracking Table
        public static final String COLUMN_NAME_PLANNED_START_TIME = "PlannedStartTime";
        public static final String COLUMN_NAME_PLANNED_DURATION = "PlannedDuration";
        public static final String COLUMN_NAME_ACTUAL_TIMETAKEN = "ActualTimeTaken";
        public static final String COLUMN_NAME_START_DELAY = "StartDelay";
        public static final String COLUMN_NAME_DISCARDED = "Discarded";
       // public static final String COLUMN_NAME_NOTES = "Notes";
        
        
       
    }
        
}