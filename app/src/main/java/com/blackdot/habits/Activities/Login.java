package com.blackdot.habits.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.blackdot.habits.R;

public class Login extends AppCompatActivity {

    private Button btnLogin;
    private Context context = Login.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findId();

    }

    private void findId() {
        btnLogin = (Button) findViewById(R.id.btnLogin);
    }


    public void onBtnLoginClick(View view) {
        startActivity(new Intent(context, Registration.class));
    }


}
