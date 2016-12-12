package com.example.joo.mytaste;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Joo on 2016-10-05.
 */


public class EditPlaceDialogActivity extends Activity {

    public static Context mContext;// Bring select function to MainActivity

    MySQLiteOpenHelper helper;
    SQLiteDatabase db;
    ArrayList<MyPlace> al = new ArrayList<MyPlace>();
    Button btnUpdate, btnCancel;
    Button LocationEditButton, btnphotobookedit;
    EditText name, menu, photo, rate, comment, lat, lng;
    PhotoBookThumbnailsCustomView pbtcv;
    TextView tv;
    int state = 0;
    MyAdapter adapter;
    ListView lv;

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
    int test =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.editplace);

        mContext = this;

        btnUpdate = (Button) findViewById(R.id.btn_Update);
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

        rb = (RatingBar) findViewById(R.id.ratingBar);

        pbtcv = (PhotoBookThumbnailsCustomView) findViewById(R.id.pbtcv_3);

        Intent GetFromPass = getIntent();
        String received_name = GetFromPass.getExtras().getString("name");
        String received_menu = GetFromPass.getExtras().getString("menu");
        int received_rate = GetFromPass.getIntExtra("rate", 0);
        String received_photo_temp = "";
        if(GetFromPass.getExtras().getString("photo").equals("")){
            received_photo_temp="";
        }else{
            received_photo_temp = GetFromPass.getExtras().getString("photo");
        }
        final String received_photo = received_photo_temp;
        String received_comment = GetFromPass.getExtras().getString("comment");

        double received_lat = GetFromPass.getDoubleExtra("lat", 0);
        double received_lng = GetFromPass.getDoubleExtra("lng", 0);
        /*
        String text = "[마커 클릭 이벤트] latitude ="
                + received_lat + ", longitude ="
                + received_lng;
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG)
                .show();
        */
        rb.setRating(received_rate);
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rate.setText("" + (int) rating);
            }
        });

        // db 연결
        //helper = new MySQLiteOpenHelper(EditPlaceDialogActivity.this, "MyPlace.db", null, 1);
        helper = new MySQLiteOpenHelper(EditPlaceDialogActivity.this, "/mnt/sdcard/MyPlace.db", null, 1);
        // file이 생성 되어있지 않을 경우 파일 생성을 위해 더미 데이터 생성
        //String fileChk = "/data/data/com.example.databaseexample/databases/MyPlace.db";
        String fileChk = "/mnt/sdcard/MyPlace.db";
        File file = new File(fileChk);
        if (file.exists()) {
            clear();
            name.setEnabled(true);
            menu.setEnabled(true);
            //photo.setEnabled(true);
            rate.setEnabled(true);
            comment.setEnabled(true);
            lat.setEnabled(true);
            lng.setEnabled(true);
            tv.setText("장소 수정");
            name.setText(received_name);
            menu.setText(received_menu);
            //photo.setHint("수정할 사진명 입력");
            rate.setText(String.valueOf(received_rate));
            comment.setText(received_comment);
            lat.setText(Double.toString(received_lat));
            lng.setText(Double.toString(received_lng));

            //Toast.makeText(EditPlaceDialogActivity.this, "정보 입력 후 실행",Toast.LENGTH_SHORT).show();
            String pbtcvinput = select_photo_thumnails(received_name);
            pbtcv.setphotothumbnails(pbtcvinput);
            /* sample
            pbtcv.setphotothumbnails("http://192.168.0.4:8181/AutumnToWinter/img/place/tedjeong/genroku2.jpg;" +
                    "http://192.168.0.4:8181/AutumnToWinter/img/place/tedjeong/meltingmonkey.jpg;" +
                    "http://192.168.0.4:8181/AutumnToWinter/img/place/tedjeong/4sushi.jpg;");
                    */
        }

        // update
        btnUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (name.getText().toString().equals("")
                        || menu.getText().toString().equals("")
                        //|| photo.getText().toString().equals("")
                        || rate.getText().toString().equals("")
                        || comment.getText().toString().equals("")
                        || lat.getText().toString().equals("")
                        || lng.getText().toString().equals("")
                        ) {
                    Toast.makeText(EditPlaceDialogActivity.this, "빈칸 두지 마세요",
                            Toast.LENGTH_SHORT).show();

                }else {
                    int result3 = update(name.getText().toString(),
                            menu.getText().toString(),
                            received_photo,
                            //photo.getText().toString(),
                            Integer.parseInt(rate.getText().toString()),
                            comment.getText().toString(),
                            Double.parseDouble(lat.getText().toString()),
                            Double.parseDouble(lng.getText().toString())
                    );
                    if (result3 == 0) {
                        Toast.makeText(EditPlaceDialogActivity.this, "잘못된 입력이 있습니다.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditPlaceDialogActivity.this, "정상 처리되었습니다.",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
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
                photobookeditintent.putExtra("imgpath",received_photo);
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

    // select_photopath
    public String select_photo_thumnails(String name){
        db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT name, photo FROM MyPlace where name='"+name+"'",null);
        c.moveToNext();
        return c.getString(c.getColumnIndex("photo"));
    }

    // update
    public int update(String name, String menu, String photo, int rate, String comment, double lat, double lng) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("menu", menu);
        values.put("photo", photo);
        values.put("rate", rate);
        values.put("comment", comment);

        values.put("lat", lat);
        values.put("lng", lng);

        int result = db.update("MyPlace", values, "name=?",
                new String[] { name + "" });
        return result;
    }
}
