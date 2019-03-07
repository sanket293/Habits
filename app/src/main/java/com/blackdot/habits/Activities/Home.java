package com.blackdot.habits.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.blackdot.habits.Common.Constants;
import com.blackdot.habits.R;

public class Home extends AppCompatActivity {

    private RelativeLayout rel_home_noHabits;
    private LinearLayout lin_home_habitList;
    private ListView lv_home_habitList;
    private FloatingActionButton fab_add_habits;

    private Context context = Home.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findId();
    }

    private void findId() {

        Toolbar toolbar = findViewById(R.id.primary_toolbar);
        setSupportActionBar(toolbar);

        fab_add_habits = findViewById(R.id.fab_add_habits);
        rel_home_noHabits = (RelativeLayout) findViewById(R.id.rel_home_noHabits);
        lin_home_habitList = (LinearLayout) findViewById(R.id.lin_home_habitList);
        lv_home_habitList = (ListView) findViewById(R.id.lv_home_habitList);

        Constants.dismissDialog();
    }




    public void onFabAddHabits(View view) {

        startActivity(new Intent(getApplicationContext(), AddHabits.class));
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

        if (id == R.id.action_favorite) {
            Toast.makeText(Home.this, "Action clicked", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    // endregion


}
