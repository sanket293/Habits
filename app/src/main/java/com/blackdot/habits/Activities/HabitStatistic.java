package com.blackdot.habits.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.blackdot.habits.Common.Constants;
import com.blackdot.habits.R;

public class HabitStatistic extends AppCompatActivity {
    private Context context = HabitStatistic.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_statistic);
        findId();
    }

    private void findId() {

        Toolbar toolbar = findViewById(R.id.primary_toolbar);
        setSupportActionBar(toolbar);

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

        if (id == R.id.action_logout) {

            Constants.logOut(context);
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
            return true;
        }
        if (id == R.id.action_see_completed_habits) {
            startActivity(new Intent(getApplicationContext(), FinishedHabits.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    // endregion

}
