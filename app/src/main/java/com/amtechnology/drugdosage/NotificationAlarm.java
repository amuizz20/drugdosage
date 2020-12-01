package com.amtechnology.drugdosage;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Calendar;


public class NotificationAlarm {
    private Context context;
    private PendingIntent pendingIntent;

    public NotificationAlarm(Context context) {
        this.context = context;
    }

    public void setAlarm(int year, int month, int day, int hour,String AM_PM, String BODY,boolean repeating){
        Calendar calendar = Calendar.getInstance();
        Intent intent = new Intent(context,AlarmReceiver.class);
        intent.putExtra("BODY",BODY);
        pendingIntent = PendingIntent.getBroadcast(context,0,intent, PendingIntent.FLAG_UPDATE_CURRENT);

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, --month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if(AM_PM.equals("AM")) {
            calendar.set(Calendar.AM_PM, Calendar.AM);
        }
        else{
            calendar.set(Calendar.AM_PM, Calendar.PM);
        }

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if(repeating) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
        else{
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),24*60*60*1000, pendingIntent);

        }
        Toast.makeText(context,"Reminder is set for "+day+"/"+month+"/"+year+"  "+hour+":00:00",Toast.LENGTH_LONG).show();

    }

}
