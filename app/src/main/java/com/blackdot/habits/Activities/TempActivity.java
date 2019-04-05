package com.blackdot.habits.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.blackdot.habits.R;

import pl.pawelkleczkowski.customgauge.CustomGauge;

public class TempActivity extends AppCompatActivity {

    private int i;
    private CustomGauge gauge2;
    int speed = 5, endValue = 800, startValue = 0, threadSpeed = 50, interval = 120, totalAngle = 360, endAngle = 270;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_activity);

        Button button = findViewById(R.id.button);
        gauge2 = findViewById(R.id.gauge2);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new Thread() {
                    public void run() {
                        for (i = 0; i < 121; i++) {
                            try {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                      //  gauge1.setValue(i * 10);
                                        gauge2.setValue( 200 + i * 5 );
                                   //     gauge3.setValue(i);
                                        Log.e("progress",""+i+"        "+" actual"+ (200 + i * 5));

//                                        Toast.makeText(TempActivity.this, "111"+gauge1.getEndValue(), Toast.LENGTH_SHORT).show();
//                                        Toast.makeText(TempActivity.this, "222"+gauge2.getEndValue(), Toast.LENGTH_SHORT).show();
//                                        text1.setText(Integer.toString(gauge1.getEndValue()));
//                                        text2.setText(Integer.toString(gauge2.getEndValue()));
                                    }
                                });
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();

            }

        });


    }
}
