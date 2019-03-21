package com.blackdot.habits.Activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.blackdot.habits.R;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

public class ListViewAdapter extends BaseSwipeAdapter {

    private Context mContext;

    public ListViewAdapter(Context mContext) {
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


//    @Override
//    public View getDropDownView(int position, View convertView, ViewGroup parent) {
//
//       // return rowview(convertView, position);
////        return super.getDropDownView(position, convertView, parent);
//    }

//    @Override
//    public View generateView(final int position, ViewGroup parent) {
//
//        View v = LayoutInflater.from(mContext).inflate(R.layout.adapter_listview_home_habit_list, null);
//        SwipeLayout swipeLayout = (SwipeLayout) v.findViewById(getSwipeLayoutResourceId(position));
//
//        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
//            @Override
//            public void onOpen(SwipeLayout layout) {
//
//                //    YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
//                Toast.makeText(mContext, "open......... trash" + position, Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//
//        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
//            @Override
//            public void onDoubleClick(SwipeLayout layout, boolean surface) {
//                Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        v.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(mContext, "click delete" + position, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//
//        return v;
//    }


    private View rowview(View convertView, final int position) {

        viewHolder holder;
        View rowview = convertView;
        LayoutInflater flater;


        if (rowview == null) {

            holder = new viewHolder();
            flater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(R.layout.adapter_listview_home_habit_list, null, false);

            holder.swipeLayout = (SwipeLayout) rowview.findViewById(R.id.swipe_habit_list_adapter);
            holder.position = (TextView) rowview.findViewById(R.id.position);
            holder.text_data = (TextView) rowview.findViewById(R.id.txt_adapter_habitlist_task);
            holder.delete = (Button) rowview.findViewById(R.id.btn_adapter_habitlist_performTask);
            rowview.setTag(holder);
        } else {
            holder = (viewHolder) rowview.getTag();
        }

        holder.position.setText("position"+position);
        holder.text_data.setText("text data");

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, position+"Delete button", Toast.LENGTH_SHORT).show();
            }
        });

        holder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {
                Toast.makeText(mContext, "click delete" + position, Toast.LENGTH_SHORT).show();

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

    @Override
    public void fillValues(int position, View convertView) {
        rowview(convertView,position);
    }

    @Override
    public int getCount() {
        return 50;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}