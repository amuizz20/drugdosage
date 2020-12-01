package com.amtechnology.drugdosage;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat;

import androidx.core.app.NotificationCompat;

import java.util.Random;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager =(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATIONCHANNEID = "com.comsatsuniversal.comsatsuniversal";
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(
                    NOTIFICATIONCHANNEID,"Notification", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Notification");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationManager.createNotificationChannel(notificationChannel);

        }


        Intent onclickintent2 = new Intent(context, MainActivity.class);
        onclickintent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        onclickintent2.setAction("1");
        onclickintent2.putExtra("MainActivity","1");

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, onclickintent2,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Intent onclickintent = new Intent(context, MainActivity.class);
        onclickintent.setAction("2");
        onclickintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        onclickintent.putExtra("MainActivity","2");
        PendingIntent pendingIntent2 = PendingIntent.getActivity(context, 2, onclickintent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Bundle bundle = intent.getExtras();
        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            notification = new NotificationCompat.Builder(context, NOTIFICATIONCHANNEID)
                       .setSmallIcon(R.mipmap.pill1)
                       .setContentTitle("Medicine Reminder")
                       .setContentText(bundle.getString("BODY"))
                       .setAutoCancel(true)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.pill1))

                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                       .setStyle(new NotificationCompat.BigTextStyle()
                               .bigText(bundle.getString("BODY")))
                       .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .addAction(new NotificationCompat.Action(R.mipmap.pill1,"Taken", pendingIntent))
                    .addAction(new NotificationCompat.Action(R.mipmap.pill1,"Not Taken", pendingIntent2))
                    .build();
            notification.flags = Notification.FLAG_AUTO_CANCEL;
        }
        notificationManager.notify(new Random().nextInt(),notification);



    }

}
