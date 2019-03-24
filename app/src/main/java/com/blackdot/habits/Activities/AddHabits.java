package com.blackdot.habits.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.blackdot.habits.Common.Constants;
import com.blackdot.habits.Database.DataBaseHelper;
import com.blackdot.habits.Models.Habits;
import com.blackdot.habits.Models.PredefineHabits;
import com.blackdot.habits.R;

import java.util.ArrayList;
import java.util.List;

public class AddHabits extends AppCompatActivity {
    private EditText et_addhabits_addNewHabit, et_addhabits_numberOfDays;
    private Context context = AddHabits.this;
    private DataBaseHelper dataBaseHelper;
    private Spinner sp_addhabits_select_habits;
    private ArrayAdapter<PredefineHabits> predefineHabitsAdapter;
    private List<PredefineHabits> predefineHabitsList = new ArrayList<>();
    private boolean firstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habits);

        findId();
    }

    private void findId() {

        dataBaseHelper = DataBaseHelper.getInstance(getApplicationContext());

        Toolbar toolbar = findViewById(R.id.primary_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle(context
        .getResources().getString(R.string.title_activity_add_new_habit));

        sp_addhabits_select_habits = (Spinner) findViewById(R.id.sp_addhabits_select_habits);
        et_addhabits_addNewHabit = (EditText) findViewById(R.id.et_addhabits_addNewHabit);
        et_addhabits_numberOfDays = (EditText) findViewById(R.id.et_addhabits_numberOfDays);
        Constants.dismissDialog();

        fillSpinner();
    }

    public void onBtnAddHabitClick(View view) {

        if (isAllValid()) {
            Toast.makeText(context, context.getResources().getString(R.string.msg_your_new_habit_added_successfully), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(context, Home.class));
            finish();
        } else {
            Constants.dismissDialog();
        }
    }

    private boolean isAllValid() {
        Constants.showDialog(context, context.getResources().getString(R.string.dialog_please_wait), false);

        String habitName = et_addhabits_addNewHabit.getText().toString().trim();
        if (habitName.equalsIgnoreCase("") || habitName == "") {
            Toast.makeText(context, context.getResources().getString(R.string.err_enter_habit), Toast.LENGTH_SHORT).show();
            return false;
        }

        int numberOfDays = 0;
        try {
            numberOfDays = Integer.parseInt(et_addhabits_numberOfDays.getText().toString().trim());

            if (numberOfDays <= 0) {
                Toast.makeText(context, context.getResources().getString(R.string.err_enter_days), Toast.LENGTH_SHORT).show();
                return false;
            } else {

                if (numberOfDays < Constants.MINIMUM_DAYS_FOR_NEW_HABIT || numberOfDays > Constants.MAXIMUM_DAYS_FOR_NEW_HABIT) {
                    Toast.makeText(context, context.getResources().getString(R.string.err_enter_valid_days), Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        } catch (Exception ex) {

            Toast.makeText(context, context.getResources().getString(R.string.err_enter_valid_days), Toast.LENGTH_SHORT).show();
            Log.e(Constants.LOG_ADD_HABITS, "isallvalid catch" + ex.getMessage());
            return false;
        }


        Habits newHabit = new Habits();
        String habitId = Constants.getNewHabitId(context);

        newHabit.setHabitId(habitId);
        newHabit.setPhoneNumber(Constants.getPhoneNumber());
        newHabit.setHabitName(habitName);
        newHabit.setNumberOfDays(numberOfDays);
        newHabit.setNumberOfDaysLeft(numberOfDays);
        String currentDate = Constants.getCurrentDate();
        newHabit.setHabitStartDate(currentDate);
        String endDate = Constants.getCustomDate(currentDate, numberOfDays);
        newHabit.setHabitEndDate(endDate);
        newHabit.setHabitStatus(Constants.HABIT_CONTINUE);


        if (!dataBaseHelper.addNewHabit(newHabit)) {
            return false;
        }

        return true;
    }


    private void fillSpinner() {

        predefineHabitsList.clear();
        predefineHabitsList = Constants.getPredefinedHabitList(context);
        if (predefineHabitsList.size() <= 0) {
            Log.e(Constants.LOG_ADD_HABITS, "fill spinner habit size 0");
            return;
        }

//        predefineHabitsAdapter = new ArrayAdapter<PredefineHabits>(getApplicationContext(),
//                R.layout.adapter_spinner_predefined_list, R.id.tv_spinner_habitName, predefineHabitsList);

        predefineHabitsAdapter = new CustomAdapter(context, R.layout.adapter_spinner_predefined_list, predefineHabitsList);
        sp_addhabits_select_habits.setAdapter(predefineHabitsAdapter);

        sp_addhabits_select_habits.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.e(Constants.LOG_ADD_HABITS, "spinner on click" + predefineHabitsList.get(position).getHabitName());
//todo first time first select

                if (!firstTime) {
                    et_addhabits_addNewHabit.setText("");
                    et_addhabits_addNewHabit.setText(predefineHabitsList.get(position).getHabitName());
                    et_addhabits_numberOfDays.setText(predefineHabitsList.get(position).getNumberOfDays() + "");
                } else {
                    firstTime = false;
                    et_addhabits_addNewHabit.setText("");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public class CustomAdapter extends ArrayAdapter<PredefineHabits> {

        LayoutInflater layoutInflater;

        public CustomAdapter(Context context, int resouceId, List<PredefineHabits> list) {

            super(context, resouceId, list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            return rowview(convertView, position);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return rowview(convertView, position);
        }

        private View rowview(View convertView, int position) {

            PredefineHabits habits = getItem(position);

            viewHolder holder;

            View rowview = convertView;

            if (rowview == null) {

                holder = new viewHolder();
                layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowview = layoutInflater.inflate(R.layout.adapter_spinner_predefined_list, null, false);

                holder.tv_spinner_habitName = (TextView) rowview.findViewById(R.id.tv_spinner_habitName);
                holder.tv_spinner_numberOfDays = (TextView) rowview.findViewById(R.id.tv_spinner_numberOfDays);
                rowview.setTag(holder);
            } else {
                holder = (viewHolder) rowview.getTag();
            }
            holder.tv_spinner_habitName.setText(habits.getHabitName());
            holder.tv_spinner_numberOfDays.setText("" + habits.getNumberOfDays());

            return rowview;
        }

        private class viewHolder {
            TextView tv_spinner_habitName, tv_spinner_numberOfDays;
        }
    }
}

