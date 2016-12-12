package com.example.joo.mytaste;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * Created by Joo on 2016-02-17.
 */
public class MyAdapter extends BaseAdapter {
    Context context;
    ArrayList<MyPlace> al;
    int layout;
    LayoutInflater inf;

    public MyAdapter(Context context, ArrayList<MyPlace> al, int layout) {
        this.context = context;
        this.al = al;
        this.layout = layout;
        inf = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return al.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return al.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inf.inflate(layout, null);
        }
        TextView tv1 = (TextView) convertView.findViewById(R.id.textView1);
        TextView tv2 = (TextView) convertView.findViewById(R.id.textView2);
        TextView tv3 = (TextView) convertView.findViewById(R.id.textView3);
        TextView tv4 = (TextView) convertView.findViewById(R.id.textView4);
        TextView tv5 = (TextView) convertView.findViewById(R.id.textView5);
        TextView tv6 = (TextView) convertView.findViewById(R.id.textView6);

        //TextView tv7 = (TextView) convertView.findViewById(R.id.textView7);

        MyPlace b = al.get(position);
        tv1.setText(b.id+"");
        tv2.setText(b.name);
        tv3.setText(b.menu);
        tv4.setText(b.photo);
        tv5.setText(""+b.rate);
        tv6.setText(b.comment);
        //tv7.setText("("+Double.toString(b.lat)+","+Double.toString(b.lng)+")");

        return convertView;
    }
}
