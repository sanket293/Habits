package com.blackdot.habits.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blackdot.habits.Common.Constants;
import com.blackdot.habits.Database.DataBaseHelper;
import com.blackdot.habits.R;

public class ResetPassword extends AppCompatActivity {
    private String phoneNumber;
    private Button btnReset, btnCancel;
    private DataBaseHelper dataBaseHelper;
    private Context context = ResetPassword.this;
    private EditText et_reset_password, et_reset_confirm_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        findId();
    }

    private void findId() {

        dataBaseHelper = DataBaseHelper.getInstance(getApplicationContext());

        et_reset_password = (EditText) findViewById(R.id.et_reset_password);
        et_reset_confirm_password = (EditText) findViewById(R.id.et_reset_confirm_password);
        btnReset = (Button) findViewById(R.id.btnReset);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra(Constants.INTENT_PHONE_NUMBER_STR);
        Constants.dismissDialog();
    }

    public void onBtnResetClick(View view) {
        if (isAllValid()) {

            Toast.makeText(context, context.getResources().getString(R.string.msg_password_reset_success), Toast.LENGTH_SHORT).show();
            Constants.dismissDialog();
            startActivity(new Intent(context, Login.class));
            finish();

        } else {
            Constants.dismissDialog();
        }

    }

    // check for validation
    private boolean isAllValid() {

        Constants.showDialog(context, context.getResources().getString(R.string.dialog_please_wait), false);


        final String password = et_reset_password.getText().toString().trim();
        if (password.equalsIgnoreCase("") || password == "") {
            Toast.makeText(context, context.getResources().getString(R.string.err_enter_password), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (password.length() < Constants.PASSWORD_LENGTH) {
                Toast.makeText(context, context.getResources().getString(R.string.err_enter_valid_password), Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        String confirmPassword = et_reset_confirm_password.getText().toString().trim();
        if (confirmPassword.equalsIgnoreCase("") || confirmPassword == "") {
            Toast.makeText(context, context.getResources().getString(R.string.err_enter_confirm_password), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (confirmPassword.length() < Constants.PASSWORD_LENGTH) {
                Toast.makeText(context, context.getResources().getString(R.string.err_enter_valid_password), Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if (!password.equalsIgnoreCase(confirmPassword)) {
            Toast.makeText(context, context.getResources().getString(R.string.err_confirm_password_not_match), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (phoneNumber.length() != Constants.PHONE_LENGTH) {
            Toast.makeText(context, context.getResources().getString(R.string.err_please_try_again_technical_issue), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!dataBaseHelper.resetPassword(phoneNumber, password)) {
            Toast.makeText(context, context.getResources().getString(R.string.err_please_try_again), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void onBtnCancelClick(View view) {
        Constants.dismissDialog();
        et_reset_confirm_password.setText("");
        et_reset_password.setText("");
    }

    public void onTvForgotAlreadyRegisterClick(View view) {
        Constants.dismissDialog();
        startActivity(new Intent(context, Login.class));
        finish();

    }

    public void onTvForgotNeedAccountClick(View view) {

        Constants.dismissDialog();
        startActivity(new Intent(context, Registration.class));
        finish();

    }

}
