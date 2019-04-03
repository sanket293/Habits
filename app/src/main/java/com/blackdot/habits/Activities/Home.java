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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blackdot.habits.Common.Constants;
import com.blackdot.habits.Database.DataBaseHelper;
import com.blackdot.habits.Models.Habits;
import com.blackdot.habits.Models.HabitsLog;
import com.blackdot.habits.R;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Home extends AppCompatActivity {

    private RelativeLayout rel_home_noHabits;
    private LinearLayout lin_home_habitList;
    private TextView tv_home_motivationalQuotes;
    private ListView lv_home_habitList;
    private CustomAdapter listAdapter;
    private FloatingActionButton fab_add_habits;
    private Context context = Home.this;
    private DataBaseHelper dataBaseHelper;
    private List<Habits> userHabitsList = new ArrayList<>();
    private ArrayList<HabitsLog> areadyPerformedHabitList = new ArrayList<>();
    private ArrayList<String> motivalionalQuoteList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findId();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void findId() {
        dataBaseHelper = DataBaseHelper.getInstance(getApplicationContext());


        Toolbar toolbar = findViewById(R.id.primary_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle(context.getResources().getString(R.string.title_activity_home));

        fab_add_habits = findViewById(R.id.fab_add_habits);
        rel_home_noHabits = (RelativeLayout) findViewById(R.id.rel_home_noHabits);
        lin_home_habitList = (LinearLayout) findViewById(R.id.lin_home_habitList);
        lv_home_habitList = (ListView) findViewById(R.id.lv_home_habitList);
        tv_home_motivationalQuotes = (TextView) findViewById(R.id.tv_home_motivationalQuotes);

        rel_home_noHabits.setVisibility(View.GONE);
//        lv_home_habitList.setVisibility(View.INVISIBLE);

        Constants.dismissDialog();
        //     fillListView();

    }

    @Override
    protected void onResume() {
        super.onResume();

        areadyPerformedHabitList = dataBaseHelper.findAlreadyPerformedHabitList(Constants.getPhoneNumber(), Constants.getCurrentDate());
        setMotivationalQuotes();
        fillListView();
    }

    private void setMotivationalQuotes() {
        try {

            DatabaseReference rootRefrence = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_CHILD_LISTNAME);

            rootRefrence.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    motivalionalQuoteList.clear();
                    for (int i = 0; i < dataSnapshot.getChildrenCount(); i++) {
                        motivalionalQuoteList.add(dataSnapshot.child(""+i).getValue().toString());
                    }

                    Random random = new Random();
                    int randomNumber = random.nextInt(motivalionalQuoteList.size());
                    tv_home_motivationalQuotes.setText("\" "+motivalionalQuoteList.get(randomNumber)+" \"");


                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        } catch (Exception localException) {
            Log.e(Constants.LOG_HOME, "err in setmotivational quote fn" + localException);
        }

    }

    private void fillListView() {

        try {
            userHabitsList = dataBaseHelper.getUserHabitList(Constants.getPhoneNumber(), Constants.HABIT_STATUS_NO);

            if (userHabitsList.isEmpty()) {
                Log.e(Constants.LOG_HOME
                        , "fill list view null");
                return;
            }
            if (userHabitsList.size() <= 0) {
                Log.e(Constants.LOG_HOME, "fill listview habit size 0");
                return;
            }

            listAdapter = new CustomAdapter(context);
            lv_home_habitList.setAdapter(listAdapter);

        } catch (Exception ex) {
            Log.e(Constants.LOG_HOME, "fill listview habit" + ex.getMessage());
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
        switch (id) {
            case R.id.action_home: {
            }
            case R.id.action_logout: {
                Constants.logOut(context);
                startActivity(new Intent(getApplicationContext(), Login.class));
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


    public class CustomAdapter extends BaseSwipeAdapter {

        private Context mContext;

        public CustomAdapter(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public int getSwipeLayoutResourceId(int position) {
            return R.id.swipe_habit_list_adapter;
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
            Log.e(Constants.LOG_HOME, "get count fn: habit list " + userHabitsList.size());
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

                holder.swipeLayout = (SwipeLayout) rowview.findViewById(R.id.swipe_habit_list_adapter);
                holder.txt_adapter_habitlist_numberOfDaysLeft = (TextView) rowview.findViewById(R.id.txt_adapter_habitlist_numberOfDaysLeft);
                holder.txt_adapter_habitlist_task = (TextView) rowview.findViewById(R.id.txt_adapter_habitlist_task);
                holder.iv_adapter_habitlist_performTask = (ImageView) rowview.findViewById(R.id.iv_adapter_habitlist_performTask);
                holder.iv_adapter_habitlist_statistic = (ImageView) rowview.findViewById(R.id.iv_adapter_habitlist_statistic);

                rowview.setTag(holder);
            } else {
                holder = (viewHolder) rowview.getTag();
            }

            final String habitId = userHabitsList.get(position).getHabitId();
            final String habitName = userHabitsList.get(position).getHabitName();

            holder.txt_adapter_habitlist_task.setText(habitName);
            holder.txt_adapter_habitlist_numberOfDaysLeft.setText(userHabitsList.get(position).getNumberOfDaysLeft() + "");

            holder.iv_adapter_habitlist_performTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //               Toast.makeText(mContext, position + "Delete button", Toast.LENGTH_SHORT).show();

                    if (!isAlreadyPerformedToday(habitId)) {
                        Intent intent = new Intent(Home.this, PerformHabit.class);
                        intent.putExtra(Constants.INTENT_HBAIT_ID_STR, "" + habitId);
                        intent.putExtra(Constants.INTENT_HBAIT_NAME_STR, "" + habitName);
                        Log.i(Constants.LOG_HOME, "row view habitId: " + habitId);
                        startActivity(intent);
                    } else {
                        Toast.makeText(context, context.getResources().getString(R.string.err_you_have_alrady_performed_this_task), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            holder.iv_adapter_habitlist_statistic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(context, HabitStatistic.class));
                    finish();
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
            TextView txt_adapter_habitlist_task, txt_adapter_habitlist_numberOfDaysLeft;
            ImageView iv_adapter_habitlist_performTask, iv_adapter_habitlist_statistic;
            SwipeLayout swipeLayout;
        }

    }

    private boolean isAlreadyPerformedToday(String habitId) {

        for (int i = 0; i < areadyPerformedHabitList.size(); i++) {
            if (areadyPerformedHabitList.get(i).getHabitId().equalsIgnoreCase(habitId)) {
                return true;
            }
        }


        return false;
    }


}
