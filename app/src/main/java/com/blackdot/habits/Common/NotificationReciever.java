package com.blackdot.habits.Common;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.blackdot.habits.R;

import java.util.Calendar;
import java.util.Random;

public class NotificationReciever extends BroadcastReceiver {
    int i = 0;

    @Override
    public void onReceive(Context context, Intent intent) {


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

//        Intent repeting_intent = new Intent(context, TempActivity.class);
//        repeting_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, Constants.NOTIFICATION_REQUEST_CODE, intent
                , PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(android.R.drawable.arrow_up_float)
                .setContentTitle(context.getResources().getString(R.string.notification_daily_title))
                .setContentText(context.getResources().getString(R.string.notification_daily_content))
                .setAutoCancel(false);

        notificationManager.notify(Constants.NOTIFICATION_REQUEST_CODE, builder.build());

    }
}
