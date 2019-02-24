
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
    private String verification_id = "";
    private UserLogin user = null;

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
        user = (UserLogin) intent.getSerializableExtra(Constants.REGISTRATION_USER);
        verification_id = intent.getStringExtra(Constants.VERIFICATION_ID);
    }

    public void onBtnVerifyClick(View view) {
        try {
            String verificationCode = etVerificationCode.getText().toString().trim();
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
            Log.e(Constants.LOG_VERIFICATION_PHONE, "btn verification" + ex.getMessage());
            Toast.makeText(context, context.getResources().getString(R.string.err_verification_code_not_correct), Toast.LENGTH_SHORT).show();
        }
    }

    public void onBtnCancelClick(View view) {
        etVerificationCode.setText("");
        finish();
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(context, context.getResources().getString(R.string.msg_verification_sucessfull), Toast.LENGTH_SHORT).show();

                            // todo enter data into db
                            if (user != null) {
                                Intent intent = new Intent(context, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            } else {
                                Toast.makeText(context, context.getResources().getString(R.string.err_please_try_again_technical_issue), Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(context, context.getResources().getString(R.string.err_verification_code_not_correct), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

    }


}
