package com.example.joo.mytaste;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Joo on 2016-09-24.
 */
public class placelistviewActivity extends Activity {
    Context mContext;
    SQLiteDatabase db;
    MySQLiteOpenHelper helper;

    ArrayList<MyPlace> al = new ArrayList<>();
    listviewAdapter lv_adapter;
    EditText ed;
    Button sb;
    Button lb;
    ListView lv;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case(1002):{
                if(resultCode == Activity.RESULT_OK) {
                    //Toast.makeText(getApplicationContext(),"delete is set, Lets refresh!",Toast.LENGTH_LONG).show();
                    lv_adapter = new listviewAdapter(mContext, al);
                    lv.setAdapter(lv_adapter);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("databasemine", "onCreate");
        setContentView(R.layout.placelistview);

        //final Window win = getWindow();
        //mContext = getApplicationContext();
        mContext = this;
        ed = (EditText) findViewById(R.id.locationsearch);
        //sb = (Button) findViewById(R.id.searchBtn);
        //lb = (Button) findViewById(R.id.loadBtn);
        lv = (ListView) findViewById(R.id.listview);

        Log.d("databasemine", "Before helper");
        //db = openOrCreateDatabase("park2", Context.MODE_PRIVATE,null);
        //helper = new MySQLiteOpenHelper(MainActivity.this,"testdata", null, 1);
        //helper = new MySQLiteOpenHelper(MainActivity.this, "park2manager", null, 1);
        //helper = new MySQLiteOpenHelper(placelistviewActivity.this, "/mnt/sdcard/park2manager.db", null, 1);
        helper = new MySQLiteOpenHelper(placelistviewActivity.this, "/mnt/sdcard/MyPlace.db", null, 1);
        Log.d("databasemine", "helper called");
        //String fileCheck = Environment.getExternalStorageDirectory().getPath()+"/testdata";
        //String fileCheck = Environment.getExternalStorageDirectory().getPath() + "/park2manager";
        String fileCheck = "/mnt/sdcard/MyPlace.db";
        File file = new File(fileCheck);
        Log.d("databasemine", String.valueOf(file.exists()));
/*
        if(!file.exists()){
            Log.d("databasemine","inserted..");
            insert("A story",3,"imgpath");
            insert("B story",2,"imgpath");
            insert("C story",1,"imgpath");
            insert("D story",4,"imgpath");
            insert("E story",2,"imgpath");
            insert("F story",1,"imgpath");
        }
*/
        /*
        sb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            */
                /*
                LayoutInflater inflater = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                LinearLayout linear = (LinearLayout)inflater.inflate(R.layout.seemorebuttonview, null);
                LinearLayout.LayoutParams paramlinear = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                //LinearLayout.LayoutParams paramlinear = new LinearLayout.LayoutParams(70,150);
                win.setGravity(Gravity.RIGHT);
                win.addContentView(linear, paramlinear);
                */
        /*
                Intent intent = new Intent(getApplicationContext(), seemorebuttonActivity.class);
                startActivity(intent);
            }
        });

        lb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("databasemine", "load button presezs");
                //select();
                lv_adapter = new listviewAdapter(mContext, al);
                lv.setAdapter(lv_adapter);
                Toast.makeText(placelistviewActivity.this, "lb and adapter is clicked ", Toast.LENGTH_SHORT).show();
            }
        });
*/
        // 자동로드/////////////////

        select();
        lv_adapter = new listviewAdapter(mContext, al);
        lv.setAdapter(lv_adapter);

        // not working

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent deleteintent = new Intent(getApplicationContext(),DeletePlaceDialogActivity.class);
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
                //Toast.makeText(placelistviewActivity.this, al.get(position).getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), placeinfoDialogActivity.class);
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
                    /*
                    Toast.makeText(getApplicationContext(), al.get(position).getName(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), al.get(position).getRate(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), al.get(position).getMenu(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), al.get(position).getPhoto(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), al.get(position).getComment(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), String.valueOf(al.get(position).getLat()), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), String.valueOf(al.get(position).getLng()), Toast.LENGTH_SHORT).show();
                    */
                } catch (Exception e) {
                    Log.d("databasemine", "Error in placelistviewActivity");
                }
                startActivity(intent);
            }
        });

        /*
        //ListView의 아이템 하나가 클릭되는 것을 감지하는 Listener객체 생성 (Button의 OnClickListener와 같은 역할)
        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {

            //ListView의 아이템 중 하나가 클릭될 때 호출되는 메소드
            //첫번째 파라미터 : 클릭된 아이템을 보여주고 있는 AdapterView 객체(여기서는 ListView객체)
            //두번째 파라미터 : 클릭된 아이템 뷰
            //세번째 파라미터 : 클릭된 아이템의 위치(ListView이 첫번째 아이템(가장위쪽)부터 차례대로 0,1,2,3.....)
            //네번재 파리미터 : 클릭된 아이템의 아이디(특별한 설정이 없다면 세번째 파라이터인 position과 같은 값)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub

                //클릭된 아이템의 위치를 이용하여 데이터인 문자열을 Toast로 출력
                Toast.makeText(MainActivity.this, al.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        };
        lv.setOnItemClickListener(listener);
        */

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

    }

    public void select() {
        db = helper.getReadableDatabase();
        Cursor c = db.query("MyPlace", null, null, null, null, null, null);

        //Log.d("databasemine","selection method called");
        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndex("id"));
            String name = c.getString(c.getColumnIndex("name"));
            int value = c.getInt(c.getColumnIndex("rate"));
            String imgpath = c.getString(c.getColumnIndex("photo"));
            String menu = c.getString(c.getColumnIndex("menu"));
            String comment = c.getString(c.getColumnIndex("comment"));
            double lat = c.getDouble(c.getColumnIndex("lat"));
            double lng = c.getDouble(c.getColumnIndex("lng"));
            //Log.d("databasemine","in the while loop");
            //Log.d("databasemine", al.get(0).getName()); !!!!!!!!!!!! error
            MyPlace lo = new MyPlace(name, menu, imgpath, value, comment, lat,lng);
            al.add(lo);
        }
        /*
        Log.d("databasemine", String.valueOf(al.size()));

        for(int i =0;i<al.size();i++) {
            Log.d("databasemine","aftercompletion");
            Log.d("databasemine", al.get(i).getName());
            Log.d("databasemine", al.get(i).getComment());
        }
        Log.d("databasemine","selection compeleted");
        Toast.makeText(placelistviewActivity.this, "select method is loaded", Toast.LENGTH_SHORT).show();
        */
    }


    public long insert(String name, int val, String imgpath) {
        db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("val", val);
        values.put("imgpath", imgpath);

        //Log.d("databasemine", values.get("name").toString() + values.get("val").toString() + values.get("imgpath").toString());

        long result = db.insert("park2", null, values);
        //long result = db.rawQuery("insert into park2 (name,val,imgpath)");
        return result;
    }
}
