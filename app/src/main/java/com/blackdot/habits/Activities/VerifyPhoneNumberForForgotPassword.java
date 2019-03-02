package com.blackdot.habits.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blackdot.habits.Common.Constants;
import com.blackdot.habits.Database.DataBaseHelper;
import com.blackdot.habits.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class VerifyPhoneNumberForForgotPassword extends AppCompatActivity {
    private String verification_id = "";

    private EditText et_forgotpassword_VerificationCode;
    private Context context = VerifyPhoneNumberForForgotPassword.this;
    private FirebaseAuth mAuth;

    private Button btnForgotPasswordVerify, btnForgotPasswordCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_number_for_forgot_password);
        findId();
    }

    private void findId() {

       FirebaseApp.initializeApp(context);
        mAuth = FirebaseAuth.getInstance();

        btnForgotPasswordVerify = (Button) findViewById(R.id.btnForgotPasswordVerify);
        btnForgotPasswordCancel = (Button) findViewById(R.id.btnForgotPasswordCancel);

        et_forgotpassword_VerificationCode = (EditText) findViewById(R.id.et_forgotpassword_VerificationCode);

        Intent intent = getIntent();
        verification_id = intent.getStringExtra(Constants.VERIFICATION_ID);


    }


    public void onBtnForgotPasswordCancelClick(View view) {
        et_forgotpassword_VerificationCode.setText("");
        finish();
    }

    public void onBtnForgotPasswordVerifyClick(View view) {

        try {
            String verificationCode = et_forgotpassword_VerificationCode.getText().toString().trim();
            if (verificationCode.equalsIgnoreCase("") || verificationCode.equalsIgnoreCase(null) || verificationCode == "") {
                Toast.makeText(context, context.getResources().getString(R.string.err_enter_verification_code), Toast.LENGTH_SHORT).show();
                return;
            } else {

                if (verificationCode.length() < Constants.VERIFICATION_LENGTH) {
                    Toast.makeText(context, context.getResources().getString(R.string.err_enter_valid_verification_code), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            if (!verification_id.equalsIgnoreCase("") || !verification_id.equalsIgnoreCase(null) || verification_id != "") {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verification_id, verificationCode);
                signInWithPhoneAuthCredential(credential);
            } else {
                Toast.makeText(context, context.getResources().getString(R.string.err_please_try_again_technical_issue), Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (Exception ex) {
            Log.e(Constants.LOG_FORGOT_VERIFICATION_PHONE, "btn verification" + ex.getMessage());
            Toast.makeText(context, context.getResources().getString(R.string.err_verification_code_not_correct), Toast.LENGTH_SHORT).show();
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(context, context.getResources().getString(R.string.msg_verification_sucessfull), Toast.LENGTH_SHORT).show();


                            startActivity(new Intent(VerifyPhoneNumberForForgotPassword.this, ResetPassword.class));
                            finish(); // todo clear other back activity


                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(context, context.getResources().getString(R.string.err_verification_code_not_correct), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

    }

}
