package com.example.joo.mytaste;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Joo on 2016-02-17.
 */

public class AddPlaceDialogActivity extends Activity {

    public static Context mContext;// Bring select function to MainActivity

    MySQLiteOpenHelper helper;
    SQLiteDatabase db;
    ArrayList<MyPlace> al = new ArrayList<MyPlace>();
    Button btnInsert, btnCancel, btnphotobookedit;
    Button LocationEditButton;
    EditText name, menu, photo, rate, comment, lat, lng;
    TextView tv;
    MyAdapter adapter;
    ListView lv;
    PhotoBookThumbnailsCustomView pbtcv;
    RatingBar rb;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case(1001):{
                if(resultCode == Activity.RESULT_OK) {
                    Toast.makeText(getApplicationContext(),"result is set",Toast.LENGTH_LONG).show();
                    double Lat = data.getDoubleExtra("Lat",1);// default value
                    double Lng = data.getDoubleExtra("Lng",127);
                    lat.setText(String.valueOf(Lat));
                    lng.setText(String.valueOf(Lng));
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.addplace);

        mContext = this;

        btnInsert = (Button) findViewById(R.id.btn_Insert);
        btnCancel = (Button) findViewById(R.id.btn_Cancel);

        LocationEditButton = (Button) findViewById(R.id.LocationEditButton);
        btnphotobookedit = (Button) findViewById(R.id.btn_photobookedit);

        // id,name,menu,photo,rate,comment,location
        name = (EditText) findViewById(R.id.editText0);
        menu = (EditText) findViewById(R.id.editText1);
        photo = (EditText) findViewById(R.id.editText2);
        rate = (EditText) findViewById(R.id.editText3);
        comment = (EditText) findViewById(R.id.editText4);
        lat = (EditText) findViewById(R.id.editText5);
        lng = (EditText) findViewById(R.id.editText6);

        tv = (TextView) findViewById(R.id.textView_title);
        lv = (ListView) findViewById(R.id.listView1);

        rb = (RatingBar)findViewById(R.id.ratingBar);

        pbtcv = (PhotoBookThumbnailsCustomView) findViewById(R.id.pbtcv_3);

        Intent GetFromPass = getIntent();
        final double received_lat = GetFromPass.getDoubleExtra("latitude", -1);
        final double received_lng = GetFromPass.getDoubleExtra("longitude", -1);
        //final double received_lat = GetFromPass.getDoubleExtra("latitude", Double.parseDouble(lat.getText().toString()));
        //final double received_lng = GetFromPass.getDoubleExtra("longitude", Double.parseDouble(lng.getText().toString()));
        /*
        String text = "[마커 클릭 이벤트] latitude ="
                + received_lat + ", longitude ="
                + received_lng;
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG)
                .show();
        */
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rate.setText(""+ (int) rating);
            }
        });

        // db 연결
        //helper = new MySQLiteOpenHelper(AddPlaceDialogActivity.this, "MyPlace.db", null, 1);
        helper = new MySQLiteOpenHelper(AddPlaceDialogActivity.this, "/mnt/sdcard/MyPlace.db", null, 1);
        // file이 생성 되어있지 않을 경우 파일 생성을 위해 더미 데이터 생성
        //String fileChk = "/data/data/com.example.databaseexample/databases/MyPlace.db";
        String fileChk = "/mnt/sdcard/MyPlace.db";
        File file = new File(fileChk);
        if (!file.exists()) {
            insert("송정떡갈비", "떡갈비,돼지갈비", "사진", 3 ,"음", 35.138992, 126.794790);
            insert("황순옥나주곰탕", "곰탕", "사진", 2, "먹자", 35.151199, 126.854613);
            insert("또식당", "애저찜", "사진", 2, "먹어본듯", 35.149132, 126.918706);
            insert("맛객미식쇼", "어만두", "사진", 3, "먹어본듯", 37.499539, 126.777679);
            insert("남원추어탕", "추어탕", "사진", 2, "먹어본듯", 37.259061, 127.030505);
        }

        // insert
        clear();
        name.setEnabled(true);
        menu.setEnabled(true);
        photo.setEnabled(true);
        rate.setEnabled(true);
        comment.setEnabled(true);
        lat.setEnabled(true);
        lng.setEnabled(true);

        tv.setText("장소 추가");
        name.setHint("이름 입력");
        menu.setHint("메뉴 입력");
        photo.setHint("사진 입력");
        rate.setHint("1~5");
        comment.setHint("한줄 입력");
        lat.setText(Double.toString(received_lat));
        lng.setText(Double.toString(received_lng));

        //String pbtcvinput = select_photo_thumnails(received_name);
        //pbtcv.setphotothumbnails(pbtcvinput);
        /*
        pbtcv.setphotothumbnails("http://192.168.0.4:8181/AutumnToWinter/img/place/tedjeong/genroku2.jpg;" +
                "http://192.168.0.4:8181/AutumnToWinter/img/place/tedjeong/meltingmonkey.jpg;" +
                "http://192.168.0.4:8181/AutumnToWinter/img/place/tedjeong/4sushi.jpg;");
                */
        // Run
        btnInsert.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                lat.setText(Double.toString(received_lat),TextView.BufferType.NORMAL);
                lng.setText(Double.toString(received_lng), TextView.BufferType.NORMAL);
                */
                if (name.getText().toString().equals("")
                        || menu.getText().toString().equals("")
                        //|| photo.getText().toString().equals("")
                        || rate.getText().toString().equals("")
                        || comment.getText().toString().equals("")
                        || lat.getText().toString().equals("")
                        || lng.getText().toString().equals("")) {
                    Toast.makeText(AddPlaceDialogActivity.this, "빈칸 두지 마세요",
                            Toast.LENGTH_SHORT).show();

                }else {
                    long result1 = insert(name.getText().toString(),
                            menu.getText().toString(),
                            photo.getText().toString(),
                            Integer.parseInt(rate.getText().toString()),
                            comment.getText().toString(),
                            Double.parseDouble(lat.getText().toString()),
                            Double.parseDouble(lng.getText().toString())
                    );

                    if (result1 == -1) {
                        Toast.makeText(AddPlaceDialogActivity.this, "잘못된 입력이 있습니다.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddPlaceDialogActivity.this, "정상 처리되었습니다.",
                                Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
            }
        });
        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnphotobookedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photobookeditintent = new Intent(getApplicationContext(),AUILGridViewActivity.class);
                photobookeditintent.putExtra("name",name.getText().toString());
                photobookeditintent.putExtra("imgpath", photo.getText().toString());
                startActivity(photobookeditintent);
            }
        });
    }

    public void onLocationEditButtonClicked(View view){
        Intent pickplace_camera_position = new Intent(getApplicationContext(),PickPlaceActivity.class);
        startActivityForResult(pickplace_camera_position, 1001);
        //this.finish();
    }

    // 그냥 중복되어 별도로 뺀 기능
    void clear() {
        name.setText("");
        menu.setText("");
        photo.setText("");
        rate.setText("");
        comment.setText("");
        lat.setText("");
        lng.setText("");
    }

    // insert
    public long insert(String name, String menu, String photo, int rate, String comment, double lat, double lng) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("menu", menu);
        values.put("photo",photo);
        values.put("rate", rate);
        values.put("comment", comment);
        values.put("lat", lat);
        values.put("lng", lng);

        // SQL insert : (name:~)(menu:~)
        long result = db.insert("MyPlace", null, values);
        Log.i("SQL ", "insert : " + "(name:" + name +
                 ")(menu:" + menu + ")" + "(photo:" + photo +
                 ")(rate:" + rate + ")(comment:"+comment+")");
        return result;
    }

}
