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
import com.blackdot.habits.Database.DataBaseHelper;
import com.blackdot.habits.Models.UserLogin;
import com.blackdot.habits.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {

    private Button btnRegisterMe, btnCancel;
    private TextView tvAlreadyRegister;
    private EditText et_register_name, et_register_phone_number, et_register_password, et_register_confirm_password, et_register_email;
    private Context context = Registration.this;
    private FirebaseAuth mAuth;
    private String verification_id = "";
private DataBaseHelper dataBaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        findId();
    }

    private void findId() {
        dataBaseHelper =DataBaseHelper.getInstance(getApplicationContext());
        FirebaseApp.initializeApp(Registration.this);
        mAuth = FirebaseAuth.getInstance();  // instance of firebase


        btnRegisterMe = (Button) findViewById(R.id.btnRegisterMe);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        tvAlreadyRegister = (TextView) findViewById(R.id.tvAlreadyRegister);
        et_register_name = (EditText) findViewById(R.id.et_register_name);
        et_register_phone_number = (EditText) findViewById(R.id.et_register_phone_number);
        et_register_password = (EditText) findViewById(R.id.et_register_password);
        et_register_confirm_password = (EditText) findViewById(R.id.et_register_confirm_password);
        et_register_email = (EditText) findViewById(R.id.et_register_email);
    }

    public void onBtnCancelClick(View view) {
        et_register_name.setText("");
        et_register_phone_number.setText("");
        et_register_password.setText("");
        et_register_confirm_password.setText("");
        et_register_email.setText("");
        verification_id = "";


        et_register_name.setText("opopopo");
        et_register_phone_number.setText("6479010329");
        et_register_password.setText("123456");
        et_register_confirm_password.setText("123456");
        et_register_email.setText("ababa@gmail.com");




    }

    public void onTvAlreadyRegisterClick(View view) {
        startActivity(new Intent(context, Login.class));
        finish();
    }

    public void onBtnRegisterMeClick(View view) {
//todo check user click button only once
        try {
            isAllValid();
        } catch (Exception ex) {
            Toast.makeText(context, context.getResources().getString(R.string.err_please_try_again), Toast.LENGTH_SHORT).show();
            Log.e(Constants.LOG_REGISTRATION, "onBtnRegister" + ex.getMessage());
        }

    }


    // check for validation
    private boolean isAllValid() {

        final String name = et_register_name.getText().toString().trim();
        if (name.equalsIgnoreCase("") || name == "") {
            Toast.makeText(context, context.getResources().getString(R.string.err_enter_name), Toast.LENGTH_SHORT).show();
            return false;
        }
        final String phoneNumber = et_register_phone_number.getText().toString().trim();
        if (phoneNumber.equalsIgnoreCase("") || phoneNumber == "") {
            Toast.makeText(context, context.getResources().getString(R.string.err_enter_phone), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (phoneNumber.length() < Constants.PHONE_LENGTH) {
                Toast.makeText(context, context.getResources().getString(R.string.err_enter_valid_phone), Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        // check if phonenumber is available or not
        if(!dataBaseHelper.isPhoneNumberAvailable(phoneNumber)){
            Toast.makeText(context, context.getResources().getString(R.string.err_phone_already_in_use), Toast.LENGTH_SHORT).show();
            return false;
        }


        final String password = et_register_password.getText().toString().trim();
        if (password.equalsIgnoreCase("") || password == "") {
            Toast.makeText(context, context.getResources().getString(R.string.err_enter_password), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (password.length() < Constants.PASSWORD_LENGTH) {
                Toast.makeText(context, context.getResources().getString(R.string.err_enter_valid_password), Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        String confirmPassword = et_register_confirm_password.getText().toString().trim();
        if (confirmPassword.equalsIgnoreCase("") || confirmPassword == "") {
            Toast.makeText(context, context.getResources().getString(R.string.err_enter_confirm_password), Toast.LENGTH_SHORT).show();
            return false;
        }else {
            if (confirmPassword.length() < Constants.PASSWORD_LENGTH) {
                Toast.makeText(context, context.getResources().getString(R.string.err_enter_valid_confirm_password), Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if (!password.equalsIgnoreCase(confirmPassword)) {
            Toast.makeText(context, context.getResources().getString(R.string.err_confirm_password_not_match), Toast.LENGTH_SHORT).show();
            return false;
        }


        final String email = et_register_email.getText().toString().trim();
        if (!email.equalsIgnoreCase("") || email != "") {
            if (!isValidEmailId(email)) {
                Toast.makeText(context, context.getResources().getString(R.string.err_enter_valid_email), Toast.LENGTH_SHORT).show();
                return false;
            }
        }




        if (Constants.isInternetConnection(context)) {

            generateVerificationCode(phoneNumber);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (!verification_id.equalsIgnoreCase("") || !verification_id.equalsIgnoreCase(null) || verification_id != "" || verification_id != null) {
                        UserLogin user = new UserLogin(name, password, email, phoneNumber);

                        Intent intent = new Intent(context, VerifyPhoneNumber.class);
                        intent.putExtra(Constants.INTENT_USER_OBJ, (Serializable) user);
                        intent.putExtra(Constants.INTENT_VERIFICATION_ID_STR, verification_id);
                        startActivity(intent);
                    } else {
                        verification_id="";
                        Toast.makeText(context, context.getResources().getString(R.string.err_please_try_again), Toast.LENGTH_SHORT).show();
                    }
                }
            }, Constants.VERIFICATION_TIMING);
        }
        return true;
    }

    private boolean isValidEmailId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
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
            verification_id="";
            Toast.makeText(context, context.getString(R.string.err_please_try_again), Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCodeSent(String _verificationId,
                               PhoneAuthProvider.ForceResendingToken token) {
            try {
                if (!_verificationId.equalsIgnoreCase("") || !_verificationId.equalsIgnoreCase(null)) {
                    verification_id = _verificationId;
                    Log.d(Constants.LOG_REGISTRATION, "token:" + token);
                    Toast.makeText(context, context.getString(R.string.msg_verification_sent_success), Toast.LENGTH_SHORT).show();

                } else {
                    verification_id="";
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
