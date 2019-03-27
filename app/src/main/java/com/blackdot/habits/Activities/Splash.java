package com.blackdot.habits.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.blackdot.habits.Common.Constants;
import com.blackdot.habits.Common.DailyTaskPerfomance;
import com.blackdot.habits.Common.NotificationReciever;
import com.blackdot.habits.Database.DataBaseHelper;
import com.blackdot.habits.R;

import java.util.Calendar;

public class Splash extends AppCompatActivity {

    private SharedPreferences sharedPreferences, sharedPreferencesFirstTime;
    private DataBaseHelper dataBaseHelper;
    private Context context = Splash.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        dataBaseHelper = DataBaseHelper.getInstance(getApplicationContext());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (checkForFirstTime()) {
                    setNotificationHours(context);
                    checkDailyTaskPerformance(context);
                    Constants.setHabitIdCounter( context);
                }

                sharedPreferences = getSharedPreferences(Constants.PREFERENCE_LOGIN, MODE_PRIVATE);
                if (sharedPreferences != null) {
                    String phoneNumber = sharedPreferences.getString(Constants.PREFERENCE_LOGIN_PHONE_NUMBER, "");
                    String password = sharedPreferences.getString(Constants.PREFERENCE_LOGIN_PASSWORD, "");

                    if (!phoneNumber.equalsIgnoreCase("") || !password.equalsIgnoreCase("")) {

                        if (dataBaseHelper.checkCredentials(phoneNumber, password)) {
                            startActivity(new Intent(Splash.this, Home.class));
                            Constants.setPhoneNumber(phoneNumber);
                            finish();
                        } else {
                            goToLoginActivity();
                        }

                    } else {
                        goToLoginActivity();
                    }

                } else {
                    goToLoginActivity();
                }


            }
        }, Constants.SPLASH_TIMING);


    }

    private void checkDailyTaskPerformance(Context context) {

        Intent intent
                = new Intent(context, DailyTaskPerfomance.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, Constants.TASK_PERFORMANCE_REQUEST_CODE, intent
                , PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, Constants.TASK_PERFORMANCE_TIME_HOURS);
        calendar.set(Calendar.MINUTE, Constants.TASK_PERFORMANCE_TIME_MINUTES);

//      alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1, pendingIntent);

        //   alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1, pendingIntent);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                alarmManager.INTERVAL_DAY, pendingIntent);


    }

    public static void setNotificationHours(Context context) {

        Intent intent
                = new Intent(context, NotificationReciever.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, Constants.NOTIFICATION_REQUEST_CODE, intent
                , PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, Constants.NOTIFICATION_TIME_HOURS);
        calendar.set(Calendar.MINUTE, Constants.NOTIFICATION_TIME_MINUTES);

//        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1, pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                alarmManager.INTERVAL_DAY, pendingIntent);


    }

    private void setFirstTimeAppUse(SharedPreferences sharedPreferencesFirstTime) {

        SharedPreferences.Editor editor = sharedPreferencesFirstTime.edit();
        editor.putBoolean(Constants.PREFERENCE_SPLASH_FIRST_TIME_APP_USE, false);
        editor.commit();

    }

    private boolean checkForFirstTime() {

        boolean isFirstTime = true;

        sharedPreferencesFirstTime = getSharedPreferences(Constants.PREFERENCE_LOGIN, MODE_PRIVATE);

        if (sharedPreferencesFirstTime != null) {
            isFirstTime = sharedPreferencesFirstTime.getBoolean(Constants.PREFERENCE_SPLASH_FIRST_TIME_APP_USE, true);
            if (isFirstTime) {

                setFirstTimeAppUse(sharedPreferencesFirstTime);
            }
        } else {
            setFirstTimeAppUse(sharedPreferencesFirstTime);
        }

        return isFirstTime;
    }


    private void goToLoginActivity() {

        sharedPreferences = getSharedPreferences(Constants.PREFERENCE_LOGIN, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.PREFERENCE_LOGIN_PHONE_NUMBER, "");
        editor.putString(Constants.PREFERENCE_LOGIN_PASSWORD, "");
        editor.commit();
        Constants.setPhoneNumber("");
        startActivity(new Intent(Splash.this, Login.class));
        finish();
    }
}
