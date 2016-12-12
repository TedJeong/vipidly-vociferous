package com.example.joo.mytaste.NavigationFrames;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.joo.mytaste.DeletePlaceDialogActivity;
import com.example.joo.mytaste.MyPlace;
import com.example.joo.mytaste.MySQLiteOpenHelper;
import com.example.joo.mytaste.R;
import com.example.joo.mytaste.listviewAdapter;
import com.example.joo.mytaste.placeinfoDialogActivity;
import com.google.android.gms.location.places.Place;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by anupamchugh on 10/12/15.
 */
public class PlaceListViewFragment extends Fragment {

    public PlaceListViewFragment() {
    }
    Context pContext;
    SQLiteDatabase db;
    MySQLiteOpenHelper helper;

    ArrayList<MyPlace> al = new ArrayList<>();
    listviewAdapter lv_adapter;
    EditText ed;
    ListView lv;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case(1002):{
                if(resultCode == Activity.RESULT_OK) {
                    lv_adapter = new listviewAdapter(pContext, al);
                    lv.setAdapter(lv_adapter);
                }
            }
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View PlaceListView = inflater.inflate(R.layout.placelistview, container, false);
        PlaceListView.setBackgroundColor(Color.WHITE);
        Log.d("databasemine", "onCreate");

        pContext = PlaceListView.getContext();
        ed = (EditText) PlaceListView.findViewById(R.id.locationsearch);
        lv = (ListView) PlaceListView.findViewById(R.id.listview);

        Log.d("databasemine", "Before helper");
        helper = new MySQLiteOpenHelper(pContext, "/mnt/sdcard/MyPlace.db", null, 1);
        Log.d("databasemine", "helper called");
        String fileCheck = "/mnt/sdcard/MyPlace.db";
        File file = new File(fileCheck);
        Log.d("databasemine", String.valueOf(file.exists()));

        // 자동로드/////////////////

        select();
        lv_adapter = new listviewAdapter(pContext, al);
        lv.setAdapter(lv_adapter);

        // not working

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent deleteintent = new Intent(PlaceListView.getContext(),DeletePlaceDialogActivity.class);
                if (al.get(position).getName() != null) {
                    deleteintent.putExtra("name", al.get(position).getName());
                    startActivityForResult(deleteintent,1002);
                }
                return false;
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PlaceListView.getContext(), placeinfoDialogActivity.class);
                try {
                    if (al.get(position).getName() != null) {
                        intent.putExtra("name", al.get(position).getName());
                    }
                    //if (al.get(position).getRate() != null) {
                    intent.putExtra("rate", al.get(position).getRate());
                    //}
                    if (al.get(position).getMenu() != null) {
                        intent.putExtra("menu", al.get(position).getMenu());
                    }
                    if (al.get(position).getPhoto() != null) {
                        intent.putExtra("photo", al.get(position).getPhoto());
                    }
                    if (al.get(position).getComment() != null) {
                        intent.putExtra("comment", al.get(position).getComment());
                    }

                    intent.putExtra("lat", al.get(position).getLat());
                    intent.putExtra("lng", al.get(position).getLng());
                } catch (Exception e) {
                    Log.d("databasemine", "Error in placelistviewActivity");
                }
                startActivity(intent);
            }
        });

        ed.addTextChangedListener(new TextWatcher() {
                                      @Override
                                      public void afterTextChanged(Editable arg0) {
                                          // TODO Auto-generated method stub
                                          String text = ed.getText().toString().toLowerCase(Locale.getDefault());
                                          lv_adapter.filter(text);
                                      }

                                      @Override
                                      public void beforeTextChanged(CharSequence arg0, int arg1,
                                                                    int arg2, int arg3) {
                                          // TODO Auto-generated method stub
                                      }

                                      @Override
                                      public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                                                int arg3) {
                                          // TODO Auto-generated method stub
                                      }
                                  }

        );


        return PlaceListView;
    }
    public void select() {
        db = helper.getReadableDatabase();
        Cursor c = db.query("MyPlace", null, null, null, null, null, null);
        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndex("id"));
            String name = c.getString(c.getColumnIndex("name"));
            int value = c.getInt(c.getColumnIndex("rate"));
            String imgpath = c.getString(c.getColumnIndex("photo"));
            String menu = c.getString(c.getColumnIndex("menu"));
            String comment = c.getString(c.getColumnIndex("comment"));
            double lat = c.getDouble(c.getColumnIndex("lat"));
            double lng = c.getDouble(c.getColumnIndex("lng"));
            MyPlace lo = new MyPlace(name, menu, imgpath, value, comment, lat,lng);
            al.add(lo);
        }
    }


    public long insert(String name, int val, String imgpath) {
        db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("val", val);
        values.put("imgpath", imgpath);
        long result = db.insert("park2", null, values);
        return result;
    }
}
