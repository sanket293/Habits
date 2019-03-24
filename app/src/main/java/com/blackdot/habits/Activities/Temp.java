package com.blackdot.habits.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.blackdot.habits.Common.Constants;
import com.blackdot.habits.R;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.util.Attributes;

public class Temp extends AppCompatActivity {
    private ListView mListView;
    private ListViewAdapter mAdapter;
    private Context mContext = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        mListView = (ListView) findViewById(R.id.listview);

           mAdapter = new ListViewAdapter(this);
        mListView.setAdapter(mAdapter);

        mAdapter.setMode(Attributes.Mode.Single);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

              //  ((SwipeLayout)(mListView.getChildAt(position ))).open(true);



                Toast.makeText(mContext, "...."+position, Toast.LENGTH_SHORT).show();


            }
        });


        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("ListView", "OnTouch");
                return false;
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext, "OnItemLongClickListener", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.e("ListView", "onScrollStateChanged");
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("ListView", "onItemSelected:" + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e("ListView", "onNothingSelected:");
            }
        });


















    }
}


//    public static int getHabitIdCounter(Context context) {
//
//        int habitIdCounter = HABIT_ID_COUNTER;
//        sharedPreferences = context.getSharedPreferences(Constants.PREFERENCE_HABIT, MODE_PRIVATE);
//        if (sharedPreferences != null) {
//
//            habitIdCounter = sharedPreferences.getInt(Constants.PREFERENCE_HABIT_ID_COUNTER, HABIT_ID_COUNTER);
//        }
//        Log.w(LOG_CONSTANTS, "get Habitcounter" + habitIdCounter);
//        return habitIdCounter;
//    }
//
//    public static void setHabitIdCounter(int habitIdCounter, Context context) {
//
//        sharedPreferences = context.getSharedPreferences(Constants.PREFERENCE_HABIT, MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putInt(Constants.PREFERENCE_HABIT_ID_COUNTER, habitIdCounter);
//        editor.commit();
//        Log.w(LOG_CONSTANTS, "set Habitcounter" + habitIdCounter);
//    }
//
//
//    public static String getNewHabitId(Context context) {
//
//        int habitIdCounter = getHabitIdCounter(context);
//        String habitId = "Habit_" + habitIdCounter + "_" + getPhoneNumber();  //example: habit_1001_6479010329
//        habitIdCounter++;
//        setHabitIdCounter(habitIdCounter, context);
//        return habitId;
//    }
