package com.blackdot.habits.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.blackdot.habits.Common.Constants;
import com.blackdot.habits.Models.Habits;
import com.blackdot.habits.R;

import pl.pawelkleczkowski.customgauge.CustomGauge;

public class HabitStatistic extends AppCompatActivity {
    private Context context = HabitStatistic.this;
    private TextView tv_statistics_progress_day, tv_statistics_habitName, tv_statistics_habitStartDate, tv_statistics_habitEndDate, tv_statistics_habitPerformed,tv_statistics_habitDaysLeft;
    private CustomGauge progress_habit_statistic;
    private Habits userHabitsObj = new Habits();
    private boolean isPerfromedToday = false;
    private int i, j;

    int daysProgress,interval,speed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_statistic);
        findId();
        fillProgressStatistic();
    }

    private void findId() {

        Toolbar toolbar = findViewById(R.id.primary_toolbar);
        setSupportActionBar(toolbar);

        progress_habit_statistic = (CustomGauge) findViewById(R.id.progress_habit_statistic);
        tv_statistics_progress_day = (TextView) findViewById(R.id.tv_statistics_progress_day);
        tv_statistics_habitName = (TextView) findViewById(R.id.tv_statistics_habitName);
        tv_statistics_habitStartDate = (TextView) findViewById(R.id.tv_statistics_habitStartDate);
        tv_statistics_habitEndDate = (TextView) findViewById(R.id.tv_statistics_habitEndDate);
        tv_statistics_habitPerformed = (TextView) findViewById(R.id.tv_statistics_habitPerformed);
        tv_statistics_habitDaysLeft = (TextView) findViewById(R.id.tv_statistics_habitDaysLeft);

        Intent intent = getIntent();
        userHabitsObj = (Habits) intent.getSerializableExtra(Constants.INTENT_HBAIT_OBJ);
        if (userHabitsObj == null) {
            finish();
        }
        isPerfromedToday = intent.getBooleanExtra(Constants.INTENT_IS_HBAIT_PERFORMED_BOOL, false);

        interval = userHabitsObj.getNumberOfDays();
        // calculate speed
        speed = (Constants.PROGRESS_END_VALUE / interval);

        //calculate progress
        daysProgress = userHabitsObj.getNumberOfDays() - userHabitsObj.getNumberOfDaysLeft();

    }

    @Override
    protected void onResume() {
        super.onResume();

        fillStatisticDetails();

    }

    private void fillStatisticDetails() {

        tv_statistics_habitName.setText(userHabitsObj.getHabitName());
        tv_statistics_habitStartDate.setText(userHabitsObj.getHabitStartDate());
        tv_statistics_habitEndDate.setText(userHabitsObj.getHabitEndDate());
        tv_statistics_habitDaysLeft.setText(""+userHabitsObj.getNumberOfDaysLeft());
        if (isPerfromedToday) {
            tv_statistics_habitPerformed.setText(context.getResources().getString(R.string.yes));
        } else {
            tv_statistics_habitPerformed.setText(context.getResources().getString(R.string.no));
        }

        tv_statistics_progress_day.setText(daysProgress+" Out of "+userHabitsObj.getNumberOfDays());
//        fillProgressStatistic();
    }

    private void fillProgressStatistic() {
        clockWiseFill();
    }

    private void clockWiseFill() {
        progress_habit_statistic.setStartValue(Constants.PROGRESS_START_VALUE);


        new Thread() {
            public void run() {
                for (i = Constants.PROGRESS_START_VALUE; i <= interval; i++) {
                    try {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progress_habit_statistic.setValue(Constants.PROGRESS_START_VALUE + i * speed);
                                Log.e(Constants.LOG_HABIT_STATISTICS, "" + i + "        " + " actual" + (Constants.PROGRESS_START_VALUE + i * speed));
                                if (i == interval) {
                                    antiClockWiseFill();
                                }
                            }
                        });
                        Thread.sleep(Constants.PROGRESS_THRED_SPEED);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private void antiClockWiseFill() {
        progress_habit_statistic.setStartValue(Constants.PROGRESS_END_VALUE);
        progress_habit_statistic.setEndValue(Constants.PROGRESS_START_VALUE);

        new Thread() {
            public void run() {
                for (i = interval; i >= Constants.PROGRESS_START_VALUE; i--) {
                    try {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progress_habit_statistic.setValue(Constants.PROGRESS_END_VALUE + i * speed);
                                Log.e(Constants.LOG_HABIT_STATISTICS, "" + i + "        " + " actual" + (Constants.PROGRESS_END_VALUE + i * speed));
                            }
                        });
                        Thread.sleep(Constants.PROGRESS_THRED_SPEED);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (i == daysProgress) {
                        Log.e(Constants.LOG_HABIT_STATISTICS, ".." + daysProgress);
                        break;
                    }

                }
            }
        }.start();
    }

    //    region Menu implementation
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.action_home: {
                startActivity(new Intent(getApplicationContext(), Home.class));
                finish();
                return true;
            }
            case R.id.action_logout: {
                Constants.logOut(context);
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
                return true;
            }
            case R.id.action_add_new_habit: {
                startActivity(new Intent(getApplicationContext(), AddHabits.class));
                finish();
                return true;
            }
            case R.id.action_see_completed_habits: {
                startActivity(new Intent(getApplicationContext(), FinishedHabits.class));
                finish();
                return true;
            }
            case R.id.action_see_reseted_habits: {
                startActivity(new Intent(getApplicationContext(), ResetedHabits.class));
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    // endregion


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, Home.class));
        finish();
    }

}
