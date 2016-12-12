package com.example.joo.mytaste;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Joo on 2016-09-24.
 */
public class listviewAdapter extends BaseAdapter {

    Context cContext;
    ArrayList<MyPlace> itemlist = new ArrayList<>();
    ArrayList<MyPlace> searchitemlist = new ArrayList<>();
    public listviewAdapter(Context context,ArrayList<MyPlace> al)
    {
        this.cContext=context;
        this.itemlist = al;
        this.searchitemlist.addAll(itemlist);
    }
    @Override
    public int getCount() {
        return itemlist.size();
    }

    @Override
    public Object getItem(int position) {
        return itemlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        locationCustomView view;
        if (convertView == null){
            view = new locationCustomView(cContext);
        }else{
            view = (locationCustomView) convertView;
        }
        view.setlocation(itemlist.get(position));
        view.imviewclicked(itemlist.get(position),cContext);
        /*
        final int positional = position;
        view.im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(cContext, "item : " + positional + " is clicked!", Toast.LENGTH_SHORT).show();
            }
        });
        */


/*
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:
                cContext.startActivity(new Intent(cContext, PlaceInfoDialogActivity.class));
            }
        });
*/
        return view;
    }
    /*
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d("log","on item clicked button call");
            cContext.startActivity(new Intent(cContext, PlaceInfoDialogActivity.class));

        }
    */
    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        itemlist.clear();
        if (charText.length() == 0) {
            itemlist.addAll(searchitemlist);
        }
        else
        {
            for (MyPlace lo : searchitemlist)
            {
                //TODO: 한글에 맞추기
                //if (lo.getName().contains(charText))//).toLowerCase(Locale.getDefault()).contains(charText))
                if (lo.getName().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    itemlist.add(lo);
                }
            }
        }
        notifyDataSetChanged();
    }
}
