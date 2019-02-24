package com.blackdot.habits.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blackdot.habits.Common.Constants;
import com.blackdot.habits.Models.UserLogin;
import com.blackdot.habits.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class Registration extends AppCompatActivity {

    private Button btnRegisterMe, btnCancel;
    private TextView tvAlreadyRegister;
    private EditText et_register_name, et_register_phone_number, et_register_password, et_register_confirm_password, et_register_email;
    private Context context = Registration.this;
    private FirebaseAuth mAuth;
    private String verification_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        findId();
    }

    private void findId() {
        FirebaseApp.initializeApp(Registration.this);
        mAuth = FirebaseAuth.getInstance();

        btnRegisterMe = (Button) findViewById(R.id.btnRegisterMe);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        tvAlreadyRegister = (TextView) findViewById(R.id.tvAlreadyRegister);
        et_register_name = (EditText) findViewById(R.id.et_register_name);
        et_register_phone_number = (EditText) findViewById(R.id.et_register_phone_number);
        et_register_password = (EditText) findViewById(R.id.et_register_password);
        et_register_confirm_password = (EditText) findViewById(R.id.et_register_confirm_password);
        et_register_email = (EditText) findViewById(R.id.et_register_email);
    }

    public void onBtnRegisterMeClick(View view) {

        // todo
        // check the user hase internet or not

        //1. verify other details
        //2. send sms to the registerd number and verify it


        if (Constants.isInternetConnection(context)) {

            final String phoneNumber = et_register_phone_number.getText().toString();
            final String name = et_register_name.getText().toString(), password = et_register_password.getText().toString(),
                    email = et_register_email.getText().toString();


            generateVerificationCode(phoneNumber);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (!verification_id.equalsIgnoreCase("") || !verification_id.equalsIgnoreCase(null) || verification_id == "" || verification_id == null) {
                        UserLogin user = new UserLogin(name, password, email, phoneNumber);
                        Intent intent = new Intent(context, VerifyPhoneNumber.class);
                        intent.putExtra(Constants.REGISTRATION_USER, (Serializable) user);
                        intent.putExtra(Constants.VERIFICATION_ID, verification_id);
                        startActivity(intent);
                    } else {
                        Toast.makeText(context, context.getResources().getString(R.string.err_please_try_again), Toast.LENGTH_SHORT).show();
                    }

                }
            }, Constants.VERIFICATION_TIMING);


        }
    }


    public void onBtnCancelClick(View view) {
        //todo clear every text box entries

    }

    public void onTvAlreadyRegisterClick(View view) {
        startActivity(new Intent(context, Login.class));
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
            Log.e(Constants.LOG_REGISTRATION, "err generate veri. code" + ex.getMessage());
        }
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {


        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }

        @Override
        public void onCodeSent(String _verificationId,
                               PhoneAuthProvider.ForceResendingToken token) {
            try {
                if (!_verificationId.equalsIgnoreCase("") || !_verificationId.equalsIgnoreCase(null)) {
                    verification_id = _verificationId;
                    Log.d(Constants.LOG_REGISTRATION, "token:" + token);
                    Toast.makeText(context, context.getString(R.string.msg_verification_sent_sucessfull), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, context.getString(R.string.err_please_try_again), Toast.LENGTH_SHORT).show();
                    Log.e(Constants.LOG_REGISTRATION, "verification id null ");
                }
            } catch (Exception ex) {
                Log.e(Constants.LOG_REGISTRATION, "oncodesent fn " + ex);
            }
        }

        @Override
        public void onCodeAutoRetrievalTimeOut(String s) {
            Log.e(Constants.LOG_REGISTRATION, "time out");
            super.onCodeAutoRetrievalTimeOut(s);
        }
    };

}
