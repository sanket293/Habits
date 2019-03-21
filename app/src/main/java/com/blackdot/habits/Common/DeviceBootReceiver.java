package com.blackdot.habits.Common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.blackdot.habits.Activities.Splash;

public class DeviceBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            try {
                Splash.setNotificationHours(context);
            } catch (Exception ex) {
                Log.e(Constants.LOG_DATABASE, "onReceive err" + ex.getMessage());
            }
        }
    }
}

