package com.blackdot.habits.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.blackdot.habits.R;

public class Instruction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
    }

    public void onBtnContinueClick(View view) {

        startActivity(new Intent(Instruction.this, MainActivity.class));
        finish();

    }


}
