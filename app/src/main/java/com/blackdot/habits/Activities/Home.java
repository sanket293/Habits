package com.blackdot.habits.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blackdot.habits.Common.Constants;
import com.blackdot.habits.Database.DataBaseHelper;
import com.blackdot.habits.Models.Habits;
import com.blackdot.habits.R;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    private RelativeLayout rel_home_noHabits;
    private LinearLayout lin_home_habitList;
    private ListView lv_home_habitList;
    private CustomAdapter listAdapter;
    private FloatingActionButton fab_add_habits;
    private Context context = Home.this;

    private List<Habits> userHabitsList = new ArrayList<>();

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

        rel_home_noHabits.setVisibility(View.VISIBLE);
        lv_home_habitList.setVisibility(View.INVISIBLE);

        Constants.dismissDialog();
        fillListView();
    }

    private void fillListView() {

        try {
//            userHabitsList.clear();
            userHabitsList = DataBaseHelper.getUserHabitList(Constants.getPhoneNumber(), context);

            if (userHabitsList.isEmpty()) {
                rel_home_noHabits.setVisibility(View.VISIBLE);
                lv_home_habitList.setVisibility(View.INVISIBLE);
                Log.e(Constants.LOG_HOME
                        , "fill list view null");
                return;
            }
            if (userHabitsList.size() <= 0) {
                rel_home_noHabits.setVisibility(View.VISIBLE);
                lv_home_habitList.setVisibility(View.INVISIBLE);
                Log.e(Constants.LOG_ADD_HABITS, "fill listview habit size 0");
                return;
            }

            rel_home_noHabits.setVisibility(View.GONE);
            lv_home_habitList.setVisibility(View.VISIBLE);

            listAdapter = new CustomAdapter(context);
            lv_home_habitList.setAdapter(listAdapter);

        } catch (Exception ex) {
            rel_home_noHabits.setVisibility(View.VISIBLE);
            lv_home_habitList.setVisibility(View.INVISIBLE);
            Log.e(Constants.LOG_ADD_HABITS, "fill listview habit" + ex.getMessage());
        }
    }

    public void onFabAddHabits(View view) {

        startActivity(new Intent(getApplicationContext(), AddHabits.class));
        finish();
//        startActivity(new Intent(getApplicationContext(), Temp.class));

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


    public class CustomAdapter extends BaseSwipeAdapter {

        private Context mContext;

        public CustomAdapter(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public int getSwipeLayoutResourceId(int position) {
            return R.id.swipe;
        }

        @Override
        public View generateView(int position, ViewGroup parent) {
            View v = null;
            v = rowview(v, position);

            return v;
        }

        @Override
        public void fillValues(int position, View convertView) {
            rowview(convertView, position);
        }

        @Override
        public int getCount() {
            Log.e("size of list", ".." + userHabitsList.size());
            return userHabitsList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private View rowview(View convertView, final int position) {

            viewHolder holder;
            View rowview = convertView;
            LayoutInflater flater;


            if (rowview == null) {

                holder = new viewHolder();
                flater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowview = flater.inflate(R.layout.adapter_listview_home_habit_list, null, false);

                holder.swipeLayout = (SwipeLayout) rowview.findViewById(R.id.swipe);
                holder.position = (TextView) rowview.findViewById(R.id.position);
                holder.text_data = (TextView) rowview.findViewById(R.id.text_data);
                holder.delete = (Button) rowview.findViewById(R.id.delete);
                rowview.setTag(holder);
            } else {
                holder = (viewHolder) rowview.getTag();
            }


            Log.e("userHabitsList", ".." + userHabitsList.get(position).getHabitName());
            holder.text_data.setText(userHabitsList.get(position).getHabitName());
            holder.position.setText(userHabitsList.get(position).getNumberOfDays() + "");

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //               Toast.makeText(mContext, position + "Delete button", Toast.LENGTH_SHORT).show();
                }
            });

            holder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
                @Override
                public void onStartOpen(SwipeLayout layout) {

                }

                @Override
                public void onOpen(SwipeLayout layout) {
                    //   Toast.makeText(mContext, "click delete" + position, Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onStartClose(SwipeLayout layout) {

                }

                @Override
                public void onClose(SwipeLayout layout) {

                }

                @Override
                public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

                }

                @Override
                public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

                }

            });

            return rowview;
        }

        private class viewHolder {
            TextView position, text_data;
            Button delete;
            SwipeLayout swipeLayout;
        }

    }


}
