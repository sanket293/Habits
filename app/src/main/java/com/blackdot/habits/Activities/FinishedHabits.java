package com.blackdot.habits.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.blackdot.habits.Common.Constants;
import com.blackdot.habits.Database.DataBaseHelper;
import com.blackdot.habits.Models.Habits;
import com.blackdot.habits.R;

import java.util.ArrayList;
import java.util.List;

public class FinishedHabits extends AppCompatActivity {


    private Context context = FinishedHabits.this;
    private DataBaseHelper dataBaseHelper;
    private ListView lv_finished_habit_list;
    private CustomAdapter listAdapter;
    private List<Habits> finishedHabitList = new ArrayList<>();


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, Home.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished_habits);
        findId();
    }

    private void findId() {
        dataBaseHelper = DataBaseHelper.getInstance(getApplicationContext());

        Toolbar toolbar = findViewById(R.id.primary_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(context
                .getResources().getString(R.string.habit_finished_date));


        lv_finished_habit_list = (ListView) findViewById(R.id.lv_finished_habit_list);

        Constants.dismissDialog();
        fillListView();
    }
// todo if user not selected anything yes or no it should be zero

    private void fillListView() {

        try {
            finishedHabitList = dataBaseHelper.getUserHabitList(Constants.getPhoneNumber(), Constants.HABIT_FINISHED);

            if (finishedHabitList.isEmpty()) {
                Log.e(Constants.LOG_FINISHED_HABIT
                        , "fill list view null");
                return;
            }
            if (finishedHabitList.size() <= 0) {
                Log.e(Constants.LOG_FINISHED_HABIT, "fill listview habit size 0");
                return;
            }

            listAdapter = new CustomAdapter(context);
            lv_finished_habit_list.setAdapter(listAdapter);

        } catch (Exception ex) {
            Log.e(Constants.LOG_FINISHED_HABIT, "fill listview habit" + ex.getMessage());
        }
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

        return super.onOptionsItemSelected(item);
    }
    // endregion


    public class CustomAdapter extends BaseAdapter {

        private Context mContext;

        public CustomAdapter(Context mContext) {
            this.mContext = mContext;
        }


        @Override
        public int getCount() {
            Log.e(Constants.LOG_FINISHED_HABIT, "get count fn: habit list " + finishedHabitList.size());
            return finishedHabitList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return rowview(convertView, position);
        }

        private View rowview(View convertView, final int position) {

            CustomAdapter.viewHolder holder;
            View rowview = convertView;
            LayoutInflater flater;

            if (rowview == null) {

                holder = new CustomAdapter.viewHolder();
                flater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowview = flater.inflate(R.layout.adapter_listview_finished_habit_list, null, false);

                holder.txt_adapter_finished_habitlist_task = (TextView) rowview.findViewById(R.id.txt_adapter_finished_habitlist_task);
                holder.txt_adapter_finished_habitlist_numberOfDaysForCompletion = (TextView) rowview.findViewById(R.id.txt_adapter_finished_habitlist_numberOfDaysForCompletion);
                holder.txt_adapter_finished_Habitlist_finishedOn = (TextView) rowview.findViewById(R.id.txt_adapter_finished_Habitlist_finishedOn);

                rowview.setTag(holder);
            } else {
                holder = (CustomAdapter.viewHolder) rowview.getTag();
            }

            final String habitName = finishedHabitList.get(position).getHabitName();
            holder.txt_adapter_finished_habitlist_task.setText(habitName);

            final int totalNumbersOfDay = finishedHabitList.get(position).getNumberOfDays();
            holder.txt_adapter_finished_habitlist_numberOfDaysForCompletion.setText("" + totalNumbersOfDay);

            final String habitFinishedOn = finishedHabitList.get(position).getHabitEndDate();
            holder.txt_adapter_finished_Habitlist_finishedOn.setText(habitFinishedOn);


            return rowview;
        }


        private class viewHolder {
            TextView txt_adapter_finished_habitlist_numberOfDaysForCompletion, txt_adapter_finished_Habitlist_finishedOn, txt_adapter_finished_habitlist_task;
        }

    }


}
