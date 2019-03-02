package com.blackdot.habits.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.blackdot.habits.R;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class Constants {
    public static int SPLASH_TIMING = 1000;
    public static int VERIFICATION_TIMING = 2500;
    public static int PHONE_LENGTH = 10;
    public static int PASSWORD_LENGTH = 6;
    public static int VERIFICATION_LENGTH = 6;

    public static String REGISTRATION_USER = "registration_user";
    public static String VERIFICATION_ID = "";

    // SharedPreferences variables
    public static final String PREFERENCE_LOGIN = "loginPreferences";
    public static final String PREFERENCE_LOGIN_NAME = "loginPreferencesName";
    public static final String PREFERENCE_LOGIN_PHONE_NUMBER = "loginPreferencesPhoneNumber";
    public static final String PREFERENCE_LOGIN_PASSWORD = "loginPreferencesPassword";
    public static final String PREFERENCE_LOGIN_EMAIL = "loginPreferencesEmail";


    // log variables
    public static String LOG_LOGIN = "LOGIN ACTIVITY";
    public static String LOG_REGISTRATION = "REGISTRATION ACTIVITY";
    public static String LOG_FORGOT_PASSWORD= "FORGOT PASSWORD ACTIVITY";
    public static String LOG_VERIFICATION_PHONE = "VERIFICATION ACTIVITY";
    public static String LOG_FORGOT_VERIFICATION_PHONE = "FORGOT PASSWORD VERIFICATION ACTIVITY";
    public static String LOG_DATABASE = "DATABASE ";

    // database variables
    public static String DATABASE_NAME = "Habits.db";

    public static String DB_TABLE_USERLOGIN = "UserLogin";
    public static String DB_USERLOGIN_NAME = "Name";
    public static String DB_USERLOGIN_PASSWORD = "Password";
    public static String DB_USERLOGIN_EMAIL = "Email";
    public static String DB_USERLOGIN_PHONE_NUMBER = "PhoneNumber";


    public static boolean isInternetConnection(Context activity) {

        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(connectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        }
        Toast.makeText(activity, activity.getResources().getString(R.string.err_no_internet_connection), Toast.LENGTH_SHORT).show();
        return false;
    }


}
