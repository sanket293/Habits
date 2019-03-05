package com.blackdot.habits.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.blackdot.habits.Common.Constants;
import com.blackdot.habits.Database.DataBaseHelper;
import com.blackdot.habits.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ForgotPassword extends AppCompatActivity {
    private EditText et_forgotPassword_phone;
    private DataBaseHelper dataBaseHelper;
    private Context context = ForgotPassword.this;
    private String verification_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        findId();
    }

    private void findId() {
        dataBaseHelper = DataBaseHelper.getInstance(getApplicationContext());
        et_forgotPassword_phone = (EditText) findViewById(R.id.et_forgotPassword_phone);
    }

    public void onTvForgotAlreadyRegisterClick(View view) {

        startActivity(new Intent(context, Login.class));
        finish();

    }

    public void onTvForgotNeedAccountClick(View view) {

        startActivity(new Intent(context, Registration.class));
        finish();

    }

    public void onBtnGetVerificationCodeClick(View view) {

        if (isAllValid()) {


        }
    }

    private boolean isAllValid() {
        final String phoneNumber = et_forgotPassword_phone.getText().toString().trim();
        if (phoneNumber.equalsIgnoreCase("") || phoneNumber == "") {
            Toast.makeText(context, context.getResources().getString(R.string.err_enter_phone), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (phoneNumber.length() < Constants.PHONE_LENGTH) {
                Toast.makeText(context, context.getResources().getString(R.string.err_enter_valid_phone), Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        // check if phonenumber is already registerd or not or not
        if (dataBaseHelper.isPhoneNumberAvailable(phoneNumber)) {
            Toast.makeText(context, context.getResources().getString(R.string.err_no_account_for_this_phone), Toast.LENGTH_SHORT).show();
            return false;
        }


        if (Constants.isInternetConnection(context)) {

            generateVerificationCode(phoneNumber);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (!verification_id.equalsIgnoreCase("") || !verification_id.equalsIgnoreCase(null) || verification_id != "" || verification_id != null) {

                        Intent intent = new Intent(context, VerifyPhoneNumberForForgotPassword.class);
                        intent.putExtra(Constants.INTENT_VERIFICATION_ID_STR, verification_id);
                        intent.putExtra(Constants.INTENT_PHONE_NUMBER_STR, phoneNumber);
                        startActivity(intent);
                    } else {
                        verification_id = "";
                        Toast.makeText(context, context.getResources().getString(R.string.err_please_try_again), Toast.LENGTH_SHORT).show();
                    }
                }
            }, Constants.VERIFICATION_TIMING);
        }


        return true;


    }


    // generate verification code for auth.
    private void generateVerificationCode(String phoneNumber) {

        try {
            String phone = "+1" + phoneNumber;

            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phone,
                    60,
                    TimeUnit.SECONDS,
                    this,
                    mCallbacks);

        } catch (Exception ex) {
            Log.e(Constants.LOG_FORGOT_PASSWORD, "err generate veri. code" + ex.getMessage());
        }
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {


        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            verification_id = "";
            Toast.makeText(context, context.getString(R.string.err_please_try_again), Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCodeSent(String _verificationId,
                               PhoneAuthProvider.ForceResendingToken token) {
            try {
                if (!_verificationId.equalsIgnoreCase("") || !_verificationId.equalsIgnoreCase(null)) {
                    verification_id = _verificationId;
                    Log.d(Constants.LOG_FORGOT_PASSWORD, "token:" + token);
                    Toast.makeText(context, context.getString(R.string.msg_verification_sent_success), Toast.LENGTH_SHORT).show();

                } else {
                    verification_id = "";
                    Toast.makeText(context, context.getString(R.string.err_please_try_again), Toast.LENGTH_SHORT).show();
                    Log.e(Constants.LOG_FORGOT_PASSWORD, "verification id null ");
                }
            } catch (Exception ex) {
                Log.e(Constants.LOG_FORGOT_PASSWORD, "oncodesent fn " + ex);
            }
        }

        @Override
        public void onCodeAutoRetrievalTimeOut(String s) {
            Log.e(Constants.LOG_FORGOT_PASSWORD, "time out");
            super.onCodeAutoRetrievalTimeOut(s);
        }
    };


}
