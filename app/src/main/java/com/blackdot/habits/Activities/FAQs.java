package com.blackdot.habits.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.blackdot.habits.Models.Faq;
import com.blackdot.habits.R;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.ArrayList;

public class FAQs extends AppCompatActivity {
    private Context context = FAQs.this;
    private ListView lv_faq_list;
    private ArrayList<Faq> faqListQuestion = new ArrayList<>();
    private ListView lv_home_habitList;
    private CustomAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);


        Toolbar toolbar = findViewById(R.id.primary_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        lv_faq_list = (ListView) findViewById(R.id.lv_faq_list);
        fillListView();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void fillListView() {

        faqListQuestion = Constants.getFaqsList(context);

        if (faqListQuestion.isEmpty()) {
            Log.e(Constants.LOG_HOME
                    , "fill list view null");
            return;
        }
        if (faqListQuestion.size() <= 0) {
            Log.e(Constants.LOG_HOME, "fill listview faq size 0");
            return;
        }

        listAdapter = new CustomAdapter(context);
        lv_faq_list.setAdapter(listAdapter);
    }


    public class CustomAdapter extends BaseAdapter {

        private Context mContext;

        public CustomAdapter(Context mContext) {
            this.mContext = mContext;
        }


        @Override
        public int getCount() {
            Log.e(Constants.LOG_FAQ, "get count fn: faq " + faqListQuestion.size());
            return faqListQuestion.size();
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
            View v = null;
            v = rowview(v, position);

            return v;
        }

        private View rowview(View convertView, final int position) {

            CustomAdapter.viewHolder holder;
            View rowview = convertView;
            LayoutInflater flater;


            if (rowview == null) {

                holder = new CustomAdapter.viewHolder();
                flater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowview = flater.inflate(R.layout.adapter_listview_faq_list, null, false);

                holder.tv_faq_question = (TextView) rowview.findViewById(R.id.tv_faq_question);
                holder.tv_faq_answer = (TextView) rowview.findViewById(R.id.tv_faq_answer);

                rowview.setTag(holder);
            } else {
                holder = (CustomAdapter.viewHolder) rowview.getTag();
            }

            holder.tv_faq_question.setText(faqListQuestion.get(position).getQuestion());
            holder.tv_faq_answer.setText(faqListQuestion.get(position).getAnswer());

            return rowview;
        }

        private class viewHolder {
            TextView tv_faq_question, tv_faq_answer;
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
            case R.id.action_habitFAQ: {
                return true;
            }
            case R.id.action_feedBack: {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                intent.setData(Uri.parse(Constants.PLAYSTORE_LINK));
                startActivity(intent);
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
