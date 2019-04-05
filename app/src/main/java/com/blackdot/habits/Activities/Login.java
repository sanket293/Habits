package com.blackdot.habits.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blackdot.habits.Common.Constants;
import com.blackdot.habits.Database.DataBaseHelper;
import com.blackdot.habits.Models.UserLogin;
import com.blackdot.habits.R;

public class
Login extends AppCompatActivity {

    private Button btnLogin;
    private Context context = Login.this;
    private EditText et_login_phone, et_login_password;
    private TextView tv_login_needAccount, tv_login_forgotPassword;
    private DataBaseHelper dataBaseHelper;
    private CheckBox cb_login_rememberMe;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findId();

    }

    private void findId() {
        dataBaseHelper = DataBaseHelper.getInstance(getApplicationContext());

        btnLogin = (Button) findViewById(R.id.btnLogin);
        et_login_phone = (EditText) findViewById(R.id.et_login_phone);
        et_login_password = (EditText) findViewById(R.id.et_login_password);
        tv_login_needAccount = (TextView) findViewById(R.id.tv_login_needAccount);
        tv_login_forgotPassword = (TextView) findViewById(R.id.tv_login_forgotPassword);
        cb_login_rememberMe = (CheckBox) findViewById(R.id.cb_login_rememberMe);
        Constants.dismissDialog();
    }


    public void onBtnLoginClick(View view) {

        if (isAllValid()) {
            startActivity(new Intent(context, Home.class));
            finish();
        } else {
            Constants.dismissDialog();
        }


    }

    public void onTvNeedAccountClick(View view) {
        startActivity(new Intent(context, Registration.class));
        finish();
    }

    public void onTvForgotPasswordClick(View view) {

        startActivity(new Intent(context, ForgotPassword.class));
        finish();

    }


    private boolean isAllValid() {
        Constants.showDialog(context, context.getResources().getString(R.string.dialog_please_wait), false);

        String phoneNumber = et_login_phone.getText().toString().trim();
        if (phoneNumber.equalsIgnoreCase("") || phoneNumber == "") {
            Toast.makeText(context, context.getResources().getString(R.string.err_enter_phone), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (phoneNumber.length() < Constants.PHONE_LENGTH) {
                Toast.makeText(context, context.getResources().getString(R.string.err_enter_valid_phone), Toast.LENGTH_SHORT).show();
                return false;
            }
        }


        String password = et_login_password.getText().toString().trim();
        if (password.equalsIgnoreCase("") || password == "") {
            Toast.makeText(context, context.getResources().getString(R.string.err_enter_password), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (password.length() < Constants.PASSWORD_LENGTH) {
                Toast.makeText(context, context.getResources().getString(R.string.err_enter_valid_password), Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        UserLogin userLogin = new UserLogin(phoneNumber, password);

        if (userLogin != null) {

            //  return isValidUser(userLogin)? true:false;  // if valid user then return true else false

            if (isValidUser(userLogin)) {

                if (cb_login_rememberMe.isChecked()) {

                    try {

                        sharedPreferences = getSharedPreferences(Constants.PREFERENCE_LOGIN, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Constants.PREFERENCE_LOGIN_PHONE_NUMBER, phoneNumber);
                        editor.putString(Constants.PREFERENCE_LOGIN_PASSWORD, password);


                        editor.commit();


                        Constants.setPhoneNumber(phoneNumber);

                    } catch (Exception ex) {
                        Log.e(Constants.LOG_LOGIN, "shared pref: " + ex.getMessage());
                        return false;
                    }
                }

                return true;
            } else {
                Toast.makeText(context, context.getResources().getString(R.string.err_credential_not_match), Toast.LENGTH_SHORT).show();
                return false;
            }

        } else {


            Toast.makeText(context, context.getResources().getString(R.string.err_please_try_again), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean isValidUser(UserLogin userLogin) {

        return dataBaseHelper.checkCredentials(userLogin.getPhoneNumber(), userLogin.getPassword());
    }

}
