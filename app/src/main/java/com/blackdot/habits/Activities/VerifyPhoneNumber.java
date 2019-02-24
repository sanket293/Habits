
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
import com.blackdot.habits.Models.UserLogin;
import com.blackdot.habits.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class VerifyPhoneNumber extends AppCompatActivity {

    private Button btnVerify, btnCancel;
    private EditText etVerificationCode;
    private Context context = VerifyPhoneNumber.this;
    private FirebaseAuth mAuth;
    private String verification_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_number);
        findId();
    }

    private void findId() {
        FirebaseApp.initializeApp(VerifyPhoneNumber.this);
        mAuth = FirebaseAuth.getInstance();

        btnVerify = (Button) findViewById(R.id.btnVerify);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        etVerificationCode = (EditText) findViewById(R.id.etVerificationCode);


        Intent intent = getIntent();
        UserLogin user = (UserLogin) intent.getSerializableExtra(Constants.REGISTRATION_USER);
         verification_id = intent.getStringExtra(Constants.VERIFICATION_ID);
    }

    public void onBtnVerifyClick(View view) {

        // todo
        //1. verify code details
        //2. enter the data into databas
        //3. send to home page
        try {
            if (!verification_id.equalsIgnoreCase("") || !verification_id.equalsIgnoreCase(null)) {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verification_id, etVerificationCode.getText().toString());
                signInWithPhoneAuthCredential(credential);
            }else {
                Toast.makeText(context, context.getResources().getString(R.string.err_please_try_again_technical_issue), Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (Exception ex) {
            Log.e(Constants.LOG_VERIFICATION_PHONE, "btn verification" + ex.getMessage());
            Toast.makeText(context, context.getResources().getString(R.string.err_verification_code_not_correct), Toast.LENGTH_SHORT).show();
        }
    }

    public void onBtnCancelClick(View view) {
        //todo clear every text box entries and ssend back to registration page

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(context, context.getResources().getString(R.string.msg_verification_sucessfull), Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(context, MainActivity.class));
                            // todo finish
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(context, context.getResources().getString(R.string.err_verification_code_not_correct), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

    }


}
