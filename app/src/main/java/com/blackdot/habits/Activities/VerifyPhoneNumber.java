
package com.blackdot.habits.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blackdot.habits.R;

public class VerifyPhoneNumber extends AppCompatActivity {
    private Button btnVerify, btnCancel;
    private TextView tvVerificationCode;
    private Context context = VerifyPhoneNumber.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_number);
        findId();
    }

    private void findId() {
        btnVerify = (Button) findViewById(R.id.btnVerify);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        tvVerificationCode = (TextView) findViewById(R.id.tvVerificationCode);
    }

    public void onBtnVerifyClick(View view) {

        // todo
        //1. verify code details
        //2. enter the data into databas
        //3. send to home page

        startActivity(new Intent(context, VerifyPhoneNumber.class));

    }

    public void onBtnCancelClick(View view) {
        //todo clear every text box entries and ssend back to registration page

    }
}
