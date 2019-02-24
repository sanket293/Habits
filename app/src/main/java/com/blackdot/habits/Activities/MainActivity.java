package com.blackdot.habits.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.blackdot.habits.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> arrayList
            = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        arrayList.add("Habit 1");
        arrayList.add("Habit 2");
        arrayList.add("Habit 3");
        arrayList.add("Habit 4");
        arrayList.add("Habit 5");
        arrayList.add("Habit 6");
        arrayList.add("Habit 7");
        arrayList.add("Habit 8");


        ListView list_item = (ListView) findViewById(R.id.list_item);

        list_item.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, arrayList));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add Your habits", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
