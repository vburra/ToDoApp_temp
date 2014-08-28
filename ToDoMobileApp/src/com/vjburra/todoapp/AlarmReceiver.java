package com.vjburra.todoapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.vjburra.todoapp.R;
import com.vjburra.todoapp.dbTransContract.dbEntry;

public final class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm worked.", Toast.LENGTH_LONG).show();
        Long taskId = intent.getLongExtra(dbEntry.COLUMN_NAME_TASK_ID,-1);
        String title = intent.getStringExtra(dbEntry.COLUMN_NAME_TITLE);
        String sdt = intent.getStringExtra(dbEntry.COLUMN_NAME_PLANNED_START_TIME);
        Integer duration = intent.getIntExtra(dbEntry.COLUMN_NAME_PLANNED_DURATION,0);
        
        String notifTitle = context.getResources().getString(R.string.notification_title);
        String notifButtonSt = context.getResources().getString(R.string.start_activity_button_label);
        String notifButtonDiscard = context.getResources().getString(R.string.discard_activity_button_label);
        // mId allows you to update the notification later on.
 		Integer mId = 3126767;
        
        
     // Creates an explicit intent for an Activity in your app
     		Intent resultIntent = new Intent(context.getApplicationContext(), DisplayTaskInfo.class);
     		resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
     		resultIntent.putExtra(dbEntry.COLUMN_NAME_TASK_ID, taskId);
     		//resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP); 

     		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context.getApplicationContext());
     		stackBuilder.addParentStack(DisplayTaskInfo.class);
     		stackBuilder.addNextIntent(resultIntent);

     		int requestID = (int) System.currentTimeMillis();

     		PendingIntent resultPendingIntent =
     		        stackBuilder.getPendingIntent(
     		        		requestID,
     		            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT 
     		        );
     		
     		Intent dismissIntent = new Intent(context.getApplicationContext(), NotifHandler.class);
     		dismissIntent.putExtra("notificationId",mId);
     		dismissIntent.putExtra("buttonClicked","DISMISS");
     		dismissIntent.putExtra(dbEntry.COLUMN_NAME_PLANNED_START_TIME,sdt);
     		dismissIntent.putExtra(dbEntry.COLUMN_NAME_PLANNED_DURATION,duration);
     		dismissIntent.putExtra(dbEntry.COLUMN_NAME_TASK_ID,taskId);
     		PendingIntent piDismiss = PendingIntent.getBroadcast(context.getApplicationContext(), 0, dismissIntent, 0);

     		Intent startIntent = new Intent(context.getApplicationContext(), NotifHandlerTemp.class);
     		startIntent.putExtra("notificationId",mId);
     		startIntent.putExtra("buttonClicked","START");
     		startIntent.putExtra(dbEntry.COLUMN_NAME_PLANNED_START_TIME,sdt);
     		startIntent.putExtra(dbEntry.COLUMN_NAME_PLANNED_DURATION,duration);
     		startIntent.putExtra(dbEntry.COLUMN_NAME_TASK_ID,taskId);
     		PendingIntent piStart = PendingIntent.getBroadcast(context.getApplicationContext(), 0, startIntent, 0);
     		
     		NotificationCompat.Builder mBuilder =
     		        new NotificationCompat.Builder(context.getApplicationContext())
         			.setSmallIcon(R.drawable.ic_action_search)
     		        .setContentTitle(notifTitle)
     		        .setDefaults(Notification.DEFAULT_SOUND)
     		        .setContentText(title);
     		mBuilder.setContentIntent(resultPendingIntent);
     		mBuilder.addAction(R.drawable.ic_launcher, notifButtonSt, piStart);
     		mBuilder.addAction(R.drawable.ic_action_search, notifButtonDiscard, piDismiss);
     		mBuilder.setAutoCancel(true);
     		NotificationManager mNotificationManager =
     		    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
     		
     		mNotificationManager.notify(mId, mBuilder.build());
    }
    
 }