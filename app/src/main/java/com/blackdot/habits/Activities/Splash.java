package com.blackdot.habits.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.blackdot.habits.Common.Constants;
import com.blackdot.habits.R;

public class Splash extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        dataBaseHelper = DataBaseHelper.getInstance(getApplicationContext());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                sharedPreferences = getSharedPreferences(Constants.PREFERENCE_LOGIN, MODE_PRIVATE);
                if (sharedPreferences != null) {
                    String phoneNumber = sharedPreferences.getString(Constants.PREFERENCE_LOGIN_PHONE_NUMBER, "");
                    String password = sharedPreferences.getString(Constants.PREFERENCE_LOGIN_PASSWORD, "");

                    if (!phoneNumber.equalsIgnoreCase("") || !password.equalsIgnoreCase("")) {

                        if (dataBaseHelper.checkCredentials(phoneNumber, password)) {
                            startActivity(new Intent(Splash.this, MainActivity.class));
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

    private void goToLoginActivity() {

        sharedPreferences = getSharedPreferences(Constants.PREFERENCE_LOGIN, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.PREFERENCE_LOGIN_PHONE_NUMBER, "");
        editor.putString(Constants.PREFERENCE_LOGIN_PASSWORD, "");
        editor.commit();

        startActivity(new Intent(Splash.this, Login.class));
        finish();
    }


}
