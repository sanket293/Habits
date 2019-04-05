package com.blackdot.habits.Common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.blackdot.habits.Models.PredefineHabits;
import com.blackdot.habits.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dmax.dialog.SpotsDialog;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.Context.MODE_PRIVATE;

public class Constants {

    // constants
    public static int SPLASH_TIMING = 1000;
    public static int VERIFICATION_TIMING = 2500;
    public static int DAILY_TASK_UPDATION_TIMING = 2500;
    public static int PHONE_LENGTH = 10;
    public static int PASSWORD_LENGTH = 6;
    public static int VERIFICATION_LENGTH = 6;
    public static int MINIMUM_DAYS_FOR_NEW_HABIT = 1;
    public static int MAXIMUM_DAYS_FOR_NEW_HABIT = 90;

    public static int NOTIFICATION_REQUEST_CODE = 2903;
    public static int NOTIFICATION_TIME_HOURS = 17;
    public static int NOTIFICATION_TIME_MINUTES = 36;
    public static int NOTIFICATION_TIME_SECONDS = 29;

    public static int TASK_PERFORMANCE_REQUEST_CODE = 293;
    public static int TASK_PERFORMANCE_TIME_HOURS = 5;
    public static int TASK_PERFORMANCE_TIME_MINUTES = 33
            ;
    public static int TASK_PERFORMANCE_TIME_SECONDS = 00;

    public static int HABIT_FINISHED = 1;
    public static int HABIT_STATUS_YES = 1;
    public static int HABIT_STATUS_NO = 0;
    public static int HABIT_PERFORMED_YES = 1;
    public static int HABIT_PERFORMED_NO = 0;

    public static int ZERO_HABIT_DAY = 0;
    public static int HABIT_CONTINUE = 0;
    public static int HABIT_ID_COUNTER = 1000;


    public static int PROGRESS_SPEED =5, PROGRESS_END_VALUE=1000, PROGRESS_START_VALUE=0,PROGRESS_THRED_SPEED=50,PROGRESS_INTERVAL=120,PROGRESS_TOTAL_ANGLE=360,PROGRESS_END_ANGLE=270;

    public static String PHONE_NUMBER;
    public static String DATE_FORMATE = "dd-MM-yyyy";



    //sharedpreference
    public static SharedPreferences sharedPreferencesHabitIdCounter;

    // alert dialog
    public static AlertDialog alertDialog;

    // used for intent passing followed by data type or type. i.e. int, string , object
    public static String INTENT_USER_OBJ = "userObject";
    public static String INTENT_PHONE_NUMBER_STR = "phoneStr";
    public static String INTENT_VERIFICATION_ID_STR = "verificationIdStr";
    public static String INTENT_HBAIT_ID_STR = "habitIdStr";
    public static String INTENT_HBAIT_OBJ = "habitObj";
    public static String INTENT_IS_HBAIT_PERFORMED_BOOL = "habitPerformedBool";
    public static String INTENT_HBAIT_NAME_STR = "habitNameStr";


    // SharedPreferences variables
    public static final String PREFERENCE_LOGIN = "loginPreferences";
    public static final String PREFERENCE_LOGIN_NAME = "loginPreferencesName";
    public static final String PREFERENCE_LOGIN_PHONE_NUMBER = "loginPreferencesPhoneNumber";
    public static final String PREFERENCE_LOGIN_PASSWORD = "loginPreferencesPassword";
    public static final String PREFERENCE_LOGIN_EMAIL = "loginPreferencesEmail";
    public static final String PREFERENCE_HABIT = "habitPreferences";
    public static final String PREFERENCE_HABIT_ID_COUNTER = "habitPreferenceHabitId";
    public static final String PREFERENCE_SPLASH = "splashPreferences";
    public static final String PREFERENCE_SPLASH_FIRST_TIME_APP_USE = "firstTimeAppUse";

    // log variables
    public static String LOG_LOGIN = "LOGIN ACTIVITY";
    public static String LOG_REGISTRATION = "REGISTRATION ACTIVITY";
    public static String LOG_FORGOT_PASSWORD = "FORGOT PASSWORD ACTIVITY";
    public static String LOG_VERIFICATION_PHONE = "VERIFICATION ACTIVITY";
    public static String LOG_FORGOT_VERIFICATION_PHONE = "FORGOT PASSWORD VERIFICATION ACTIVITY";
    public static String LOG_DATABASE = "DATABASE ";
    public static String LOG_CONSTANTS = "CONSTANTS";
    public static String LOG_ADD_HABITS = "ADD HABITS";
    public static String LOG_HOME = "HOME ACTIVITY";
    public static String LOG_PERFORM_HABIT = "PERFORM HABIT ACTIVITY";
    public static String LOG_FINISHED_HABIT = "FINISHED HABIT ACTIVITY";
    public static String LOG_RESETED_HABIT = "RESETED HABIT ACTIVITY";
    public static String LOG_DEVICE_BOOT_RECEIVER = "DEVICE BOOT RECEIVER";
    public static String LOG_DAILY_TASK_PERFORMANCE = "DAILY TASK PERFORMANCE";
    public static String LOG_HABIT_STATISTICS = "HABIT STATISTICS";

    // database variables
    public static String DATABASE_NAME = "Habits.db";
    public static String DB_TABLE_USERLOGIN = "UserLogin";
    public static String DB_USERLOGIN_NAME = "Name";
    public static String DB_USERLOGIN_PASSWORD = "Password";
    public static String DB_USERLOGIN_EMAIL = "Email";
    public static String DB_USERLOGIN_PHONE_NUMBER = "PhoneNumber";

    public static String DB_TABLE_HABITS = "Habits";
    public static String DB_HABITS_HABIT_ID = "HabitId";
    public static String DB_HABITS_PHONE_NUMBER = "PhoneNumber";
    public static String DB_HABITS_HABIT_NAME = "HabitName";
    public static String DB_HABITS_NUMBER_OF_DAYS = "NumberOfDays";
    public static String DB_HABITS_NUMBER_OF_DAYS_LEFT = "NumberOfDaysLeft";
    public static String DB_HABITS_HABIT_START_DATE = "HabitStartDate";
    public static String DB_HABITS_HABIT_END_DATE = "HabitEndDate";
    public static String DB_HABITS_HABIT_STATUS = "HabitStatus";

    public static String DB_TABLE_HABITSLOG = "HabitsLog";
    public static String DB_HABITSLOG_HABIT_ID = "habitId";
    public static String DB_HABITSLOG_PHONE_NUMBER = "PhoneNumber";
    public static String DB_HABITSLOG_HABIT_PERFORM_DATE = "HabitPerformDate";
    public static String DB_HABITSLOG_HABIT_ACTION = "HabitAction";
    public static String DB_HABITSLOG_NUMBER_OF_DAYS_LEFT = "numberOfDaysLeft";

    public static String FIREBASE_CHILD_LISTNAME = "MotivationaQuotes";


    public static String getPhoneNumber() {
        return PHONE_NUMBER;
    }

    public static void setPhoneNumber(String phoneNumber) {
        PHONE_NUMBER = phoneNumber;
    }



    public static int getHabitIdCounter(Context context) {

        sharedPreferencesHabitIdCounter = context.getSharedPreferences(Constants.PREFERENCE_HABIT, MODE_PRIVATE);
        if (sharedPreferencesHabitIdCounter != null) {

            HABIT_ID_COUNTER = sharedPreferencesHabitIdCounter.getInt(Constants.PREFERENCE_HABIT_ID_COUNTER, HABIT_ID_COUNTER);
        }
        Log.w(LOG_CONSTANTS, "get Habitcounter" + HABIT_ID_COUNTER);
        return HABIT_ID_COUNTER;
    }

    public static void setHabitIdCounter(Context context) {

        sharedPreferencesHabitIdCounter = context.getSharedPreferences(Constants.PREFERENCE_HABIT, MODE_PRIVATE);
        HABIT_ID_COUNTER = sharedPreferencesHabitIdCounter.getInt(Constants.PREFERENCE_HABIT_ID_COUNTER, HABIT_ID_COUNTER);
        HABIT_ID_COUNTER++;
        SharedPreferences.Editor editor = sharedPreferencesHabitIdCounter.edit();
        editor.putInt(Constants.PREFERENCE_HABIT_ID_COUNTER, HABIT_ID_COUNTER);
        editor.commit();
        Log.w(LOG_CONSTANTS, "set Habitcounter" + HABIT_ID_COUNTER);
    }

    public static String getNewHabitId(Context context) {

        int habitIdCounter = getHabitIdCounter(context);
        String habitId = "Habit_" + habitIdCounter + "_" + getPhoneNumber();  //example: habit_1001_6479010329
        habitIdCounter++;
        setHabitIdCounter(context);
        return habitId;
    }

    // to check internet connection
    public static boolean isInternetConnection(Context activity) {

        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(connectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        }
        Toast.makeText(activity, activity.getResources().getString(R.string.err_no_internet_connection), Toast.LENGTH_SHORT).show();
        return false;
    }

    // to display alert dialog
    public static void showDialog(Context context, String message, boolean cancelable) {
        alertDialog = new SpotsDialog
                .Builder()
                .setContext(context)
                .setMessage(message)
                .setCancelable(cancelable)
                .build();
        alertDialog.show();
    }

    // to hide alert dialog
    public static void dismissDialog() {
        try {
            alertDialog.dismiss();
        } catch (Exception e) {
            Log.e(LOG_CONSTANTS, "dismiss dialog" + e.getMessage());
        }
    }

    // get current date
    public static String getCurrentDate() {

        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMATE);
        Date date = new Date();
        return dateFormat.format(date);

    }

    // get custome date
    public static String getCustomDate(String initialDate, int incrementalDays) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMATE);
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(initialDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, incrementalDays - 1);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
        SimpleDateFormat sdf1 = new SimpleDateFormat(DATE_FORMATE);
        String output = sdf1.format(c.getTime());
        return output;
    }


    public static List<PredefineHabits> getPredefinedHabitList(Context context) {

        List<PredefineHabits> getPredefinedHabitList = new ArrayList<>();


        String[] habitNames = context.getResources().getStringArray(R.array.str_predefine_habits);
        int[] numberOfDays = context.getResources().getIntArray(R.array.int_predefine_habits);
        for (int i = 0; i < habitNames.length; i++) {

            getPredefinedHabitList.add(new PredefineHabits(habitNames[i], numberOfDays[i]));
        }

        return getPredefinedHabitList;
    }


    public static void logOut(Context context) {

//        SharedPreferences sharedPreferences_habit = context.getSharedPreferences(Constants.PREFERENCE_HABIT, MODE_PRIVATE);
//        SharedPreferences.Editor editor_habit = sharedPreferences_habit.edit();
//        editor_habit.clear();
//        editor_habit.commit();

        SharedPreferences sharedPreferences_login = context.getSharedPreferences(Constants.PREFERENCE_LOGIN, MODE_PRIVATE);
        SharedPreferences.Editor editor_loggin = sharedPreferences_login.edit();
        editor_loggin.clear();
        editor_loggin.commit();

        setPhoneNumber("");

        Toast.makeText(context, context.getResources().getString(R.string.msg_logout_successfully), Toast.LENGTH_SHORT).show();

    }
}
