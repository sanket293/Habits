package com.blackdot.habits.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blackdot.habits.Common.Constants;
import com.blackdot.habits.Models.UserLogin;
import com.blackdot.habits.R;

public class Login extends AppCompatActivity {

    private Button btnLogin;
    private Context context = Login.this;
    private EditText et_login_phone, et_login_password;
    private TextView tv_login_needAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        

        findId();

    }

    private void findId() {
        btnLogin = (Button) findViewById(R.id.btnLogin);
        et_login_phone = (EditText) findViewById(R.id.et_login_phone);
        et_login_password = (EditText) findViewById(R.id.et_login_password);
        tv_login_needAccount = (TextView) findViewById(R.id.tv_login_needAccount);
    }


    public void onBtnLoginClick(View view) {

        if (isAllValid()) {
            startActivity(new Intent(context, Registration.class));
            finish();
        }
    }

    public void onTvNeedAccountClick(View view) {

        startActivity(new Intent(context, Registration.class));
        finish();

    }


    private boolean isAllValid() {

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
            return isValidUser(userLogin);
        } else {
            Toast.makeText(context, context.getResources().getString(R.string.err_please_try_again), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean isValidUser(UserLogin userLogin) {

//todo check credential with DB
        return true;
    }

}
