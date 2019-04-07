package com.blackdot.habits.Common;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.blackdot.habits.Database.DataBaseHelper;
import com.blackdot.habits.Models.Habits;
import com.blackdot.habits.Models.HabitsLog;
import com.blackdot.habits.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DailyTaskPerfomance extends BroadcastReceiver {
    private List<Habits> userHabitsList = new ArrayList<>();
    private ArrayList<HabitsLog> areadyPerformedHabitList = new ArrayList<>();

    private DataBaseHelper dataBaseHelper;
    private Intent mIntent;
    private Context mContext;
    private ArrayList<String> phoneNumberList = new ArrayList<>();

    @Override
    public void onReceive(Context context, Intent intent) {
        mIntent = intent;
        mContext = context;
        Log.e(Constants.LOG_DAILY_TASK_PERFORMANCE, "habit reset" + mContext.getPackageName());

        dataBaseHelper = DataBaseHelper.getInstance(context);

        phoneNumberList = dataBaseHelper.getAllPhoneNumbers(); // get all user(here, phonenumber as user id) so that we can check each user's not performed habits

        for (int i = 0; i < phoneNumberList.size(); i++) {
            Log.w(Constants.LOG_DAILY_TASK_PERFORMANCE, "inside for loop, threads arry" + i);
            getUserHabitList(phoneNumberList.get(i));
        }

//
//        final Thread[] threads = new Thread[phoneNumberList.size()];
//        final int counter = 0;
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//   }
//        }, Constants.DAILY_TASK_UPDATION_TIMING);


    }

    private void getUserHabitList(String phoneNumber) {

        try {
            userHabitsList = dataBaseHelper.getUserHabitList(phoneNumber, Constants.HABIT_STATUS_NO); // user's not completed list of activity

            if (userHabitsList.isEmpty()) {
                Log.e(Constants.LOG_DAILY_TASK_PERFORMANCE
                        , "fill list view null");
                return;
            }
            if (userHabitsList.size() <= 0) {
                Log.e(Constants.LOG_DAILY_TASK_PERFORMANCE, "fill listview habit size 0");
                return;
            }
            String yesterdayDate = Constants.getCustomDate(Constants.getCurrentDate(), -1); // yesterday's date
            areadyPerformedHabitList = dataBaseHelper.findAlreadyPerformedHabitList(phoneNumber, yesterdayDate);   // check that user performed habit yesterday or not
            checkHabitPerformance();

        } catch (Exception ex) {
            Log.e(Constants.LOG_DAILY_TASK_PERFORMANCE, "fill listview habit" + ex.getMessage());
        }
    }

    private void checkHabitPerformance() {
        for (int i = 0; i < userHabitsList.size(); i++) {
            boolean match = false;
            String habitId = userHabitsList.get(i).getHabitId();
            for (int j = 0; j < areadyPerformedHabitList.size(); j++) {
                if (habitId.equalsIgnoreCase(areadyPerformedHabitList.get(j).getHabitId())) {
                    match = true;
                    break;
                }
            }
            if (!match) {
                //todo reset habit to no and nootify user
                resetHabitIfNotPerfomed(habitId);
            }
        }
    }

    private void resetHabitIfNotPerfomed(String habitId) {

        Habits habitsDetails = dataBaseHelper.getHabitDetails(habitId);
        if (habitsDetails.equals(null)) {
            Log.e(Constants.LOG_DAILY_TASK_PERFORMANCE, "Find  Id, resetHabitIfNotPerfomed fn is null");
            return;
        }
        String currentDate = Constants.getCurrentDate();
        String endDate = Constants.getCustomDate(currentDate, habitsDetails.getNumberOfDays());
        habitsDetails.setHabitStartDate(currentDate);
        habitsDetails.setHabitEndDate(endDate);
        boolean isUpdated = dataBaseHelper.updateDailyTaskPerformance(habitsDetails, false);
        if (isUpdated) {
            notifyUser(habitsDetails);
        }
    }

    private void notifyUser(Habits habitsDetails) {
        Log.e(Constants.LOG_DAILY_TASK_PERFORMANCE, "habit reset" + habitsDetails.getHabitName());


        Random random = new Random();
        int notificationId = random.nextInt(999);

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, notificationId, mIntent
                , PendingIntent.FLAG_UPDATE_CURRENT);

        String notificationContent = "Some habits are reseated for " + habitsDetails.getPhoneNumber();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
                .setContentIntent(pendingIntent)
                .setSmallIcon(android.R.drawable.arrow_up_float)
                .setContentTitle(mContext.getResources().getString(R.string.notification_daily_task_not_completed_title))
                .setContentText(notificationContent)
                .setAutoCancel(false);

        notificationManager.notify(notificationId, builder.build());


    }

}








