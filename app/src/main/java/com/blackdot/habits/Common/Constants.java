package com.blackdot.habits.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.blackdot.habits.R;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class Constants {
    public static int SPLASH_TIMING = 1000;
    public static int VERIFICATION_TIMING = 1500;
    public static int PHONE_LENGTH = 10;
    public static int PASSWORD_LENGTH = 16;
    public static int VERIFICATION_LENGTH = 6;

    public static String REGISTRATION_USER = "registration_user";
    public static String VERIFICATION_ID = "";


    public static String LOG_REGISTRATION = "REGISTRATION ACTIVITY";
    public static String LOG_VERIFICATION_PHONE = "VERIFICATION ACTIVITY";


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
