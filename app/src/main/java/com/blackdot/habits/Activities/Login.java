package com.blackdot.habits.Activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.balysv.materialripple.MaterialRippleLayout;
import com.blackdot.habits.R;

public class Login extends AppCompatActivity {

    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

findId();

    }

    private void findId() {
        btnLogin=(Button) findViewById(R.id.btnLogin);



    }
}
