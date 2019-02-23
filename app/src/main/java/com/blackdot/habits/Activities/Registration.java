package com.blackdot.habits.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blackdot.habits.R;

public class Registration extends AppCompatActivity {
    private Button btnRegisterMe, btnCancel;
    private TextView tvAlreadyRegister;
    private Context context = Registration.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        findId();
    }

    private void findId() {
        btnRegisterMe = (Button) findViewById(R.id.btnRegisterMe);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        tvAlreadyRegister = (TextView) findViewById(R.id.tvAlreadyRegister);
    }


    public void onBtnRegisterMeClick(View view) {

        // todo
        //1. verify other details
        //2. send sms to the registerd number and verify it

        startActivity(new Intent(context, VerifyPhoneNumber.class));

    }

    public void onBtnCancelClick(View view) {
        //todo clear every text box entries

    }
    public void onTvAlreadyRegisterClick(View view) {
        startActivity(new Intent(context, Login.class));
    }
}
