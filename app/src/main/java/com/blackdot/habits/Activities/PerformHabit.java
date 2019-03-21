package com.blackdot.habits.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.blackdot.habits.Common.Constants;
import com.blackdot.habits.Database.DataBaseHelper;
import com.blackdot.habits.Models.Habits;
import com.blackdot.habits.R;

public class PerformHabit extends AppCompatActivity {
    private TextView tv_performHabit_habitName;
    private boolean isHabitPerformed = false;
    private DataBaseHelper dataBaseHelper;
    private String habitId = "";
    private Habits habitsDetails;
    private Context context = PerformHabit.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perform_habit);
        findId();

    }

    private void findId() {
        dataBaseHelper = DataBaseHelper.getInstance(getApplicationContext());

        tv_performHabit_habitName = (TextView) findViewById(R.id.tv_performHabit_habitName);

        Intent intent = getIntent();
        habitId = intent.getStringExtra(Constants.INTENT_HBAIT_ID_STR);
        String habitName = intent.getStringExtra(Constants.INTENT_HBAIT_NAME_STR);
        if (habitId == null) {
            finish();
        }

        tv_performHabit_habitName.setText(habitName);

        habitsDetails = dataBaseHelper.getHabitDetails(habitId);
        if (habitsDetails.equals(null)) {
            Log.e(Constants.LOG_PERFORM_HABIT, "Find  Id, Habitdetails is null");
            Toast.makeText(this, context.getResources().getString(R.string.err_please_try_again_later), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    // todo confirm from user one more time before updating db
    // avoid dublicate update
    //check today is last date or not?  is yes then change status and add into new page where use can see all finished list
    // daily notification
    // daily motivational quotes


    public void onBtnPerformHabitYesClick(View view) {
        isHabitPerformed = true;
        int numberOfDaysLeft = habitsDetails.getNumberOfDaysLeft() - 1;
        habitsDetails.setNumberOfDaysLeft(numberOfDaysLeft);
        habitsDetails.setHabitId(habitId);
        boolean isUpdated = dataBaseHelper.updateDailyTaskPerformance(habitsDetails, isHabitPerformed);
        if (isUpdated) {
            Toast.makeText(this, context.getResources().getString(R.string.msg_task_performed_successfully), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, context.getResources().getString(R.string.err_please_try_again), Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    public void onBtnPerformHabitNoClick(View view) {
        isHabitPerformed = false;
        String currentDate = Constants.getCurrentDate();
        String endDate = Constants.getCustomDate(currentDate, habitsDetails.getNumberOfDays());
        habitsDetails.setHabitStartDate(currentDate);
        habitsDetails.setHabitEndDate(endDate);
        habitsDetails.setHabitId(habitId);
        boolean isUpdated = dataBaseHelper.updateDailyTaskPerformance(habitsDetails, isHabitPerformed);
        if (isUpdated) {
            Toast.makeText(this, context.getResources().getString(R.string.msg_task_reset_successfully), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, context.getResources().getString(R.string.err_please_try_again), Toast.LENGTH_SHORT).show();
        }
        finish();
    }


    public void onBtnPerformHabitSeeStatisticClick(View view) {
    }
}
