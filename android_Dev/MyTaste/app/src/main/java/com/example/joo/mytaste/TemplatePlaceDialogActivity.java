package com.example.joo.mytaste;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class TemplatePlaceDialogActivity extends Activity implements OnClickListener {

    public static Context mContext;// Bring select function to MainActivity

    private Button mConfirm, mCancel;

    MySQLiteOpenHelper helper;
    SQLiteDatabase db;
    ArrayList<MyPlace> al = new ArrayList<MyPlace>();
    Button btnInsert, btnDelete, btnUpdate, btnSelectAll, btnRun;
    Button LocationEditButton;
    EditText name, menu, photo, rate, comment, lat, lng;
    TextView tv;
    int state = 0;
    MyAdapter adapter;
    ListView lv;

    RatingBar rb;

    int test =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.templateplace);
        setContent();
        mContext = this;


        btnInsert = (Button) findViewById(R.id.btn_insert);
        btnDelete = (Button) findViewById(R.id.btn_delete);
        btnUpdate = (Button) findViewById(R.id.btn_update);
        btnSelectAll = (Button) findViewById(R.id.btn_selectAll);
        btnRun = (Button) findViewById(R.id.btn_run);

        LocationEditButton = (Button) findViewById(R.id.LocationEditButton);

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


        Intent GetFromPass = getIntent();
        final double received_lat = GetFromPass.getDoubleExtra("latitude", -1);
        final double received_lng = GetFromPass.getDoubleExtra("longitude", -1);
        String text = "[마커 클릭 이벤트] latitude ="
                + received_lat + ", longitude ="
                + received_lng;
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG)
                .show();

        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rate.setText(""+ rating);
            }
        });

        // db 연결
        //helper = new MySQLiteOpenHelper(TemplatePlaceDialogActivity.this, "MyPlace.db", null, 1);
        helper = new MySQLiteOpenHelper(TemplatePlaceDialogActivity.this, "/mnt/sdcard/MyPlace.db", null, 1);
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
        btnInsert.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
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
                rate.setHint("평점 입력");
                comment.setHint("한줄 입력");
                lat.setHint(Double.toString(received_lat));
                lng.setHint(Double.toString(received_lng));

                Toast.makeText(TemplatePlaceDialogActivity.this, "정보 입력 후 실행",
                        Toast.LENGTH_SHORT).show();
                state = 1;
            }
        });

        // delete
        btnDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
                name.setEnabled(true);
                menu.setEnabled(false);
                photo.setEnabled(false);
                rate.setEnabled(false);
                comment.setEnabled(false);

                tv.setText("장소 삭제");
                name.setHint("삭제할 이름입력");
                menu.setHint("-");
                photo.setHint("-");
                rate.setHint("-");
                comment.setHint("-");
                Toast.makeText(TemplatePlaceDialogActivity.this, "장소 입력 후 실행",
                        Toast.LENGTH_SHORT).show();
                state = 2;
            }
        });

        // update
        btnUpdate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                clear();
                name.setEnabled(true);
                menu.setEnabled(true);
                photo.setEnabled(true);
                rate.setEnabled(true);
                tv.setText("장소 수정");
                name.setHint("수정할 장소명 입력");
                menu.setHint("수정할 메뉴명 입력");
                photo.setHint("수정할 사진명 입력");
                rate.setHint("수정할 평점 입력");
                Toast.makeText(TemplatePlaceDialogActivity.this, "정보 입력 후 실행",
                        Toast.LENGTH_SHORT).show();
                state = 3;
            }
        });

        // selectAll
        btnSelectAll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                al.clear();
                select();
                adapter = new MyAdapter(TemplatePlaceDialogActivity.this, al, R.layout.row);
                lv.setAdapter(adapter);
                Toast.makeText(TemplatePlaceDialogActivity.this, "DB 조회 완료", Toast.LENGTH_SHORT).show();
            }
        });

        // Run
        btnRun.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (state) {
                    case 0: // 미사용
                        Toast.makeText(TemplatePlaceDialogActivity.this, "상단 메뉴 선택 후 사용 하세요",
                                Toast.LENGTH_SHORT).show();
                        break;

                    case 1: // insert
                        lat.setText(Double.toString(received_lat),TextView.BufferType.NORMAL);
                        lng.setText(Double.toString(received_lng), TextView.BufferType.NORMAL);

                        if (name.getText().toString().equals("")
                                || menu.getText().toString().equals("")
                                || photo.getText().toString().equals("")
                                || rate.getText().toString().equals("")
                                || comment.getText().toString().equals("")
                                || lat.getText().toString().equals("")
                                || lng.getText().toString().equals("")) {
                            Toast.makeText(TemplatePlaceDialogActivity.this, "빈칸 두지 마세요",
                                    Toast.LENGTH_SHORT).show();
                            break;
                        }
                        long result1 = insert(name.getText().toString(),
                                menu.getText().toString(),
                                photo.getText().toString(),
                                Integer.parseInt(rate.getText().toString()),
                                comment.getText().toString(),
                                Double.parseDouble(lat.getText().toString()),
                                Double.parseDouble(lng.getText().toString())
                        );

                        if (result1 == -1) {
                            Toast.makeText(TemplatePlaceDialogActivity.this, "잘못된 입력이 있습니다.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(TemplatePlaceDialogActivity.this, "정상 처리되었습니다.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        break;
                    //Integer.parseInt(rate.getText().toString())
                    case 2: // delete
                        if (name.getText().toString().equals("")) {
                            Toast.makeText(TemplatePlaceDialogActivity.this, "빈칸 두지 마세요",
                                    Toast.LENGTH_SHORT).show();
                            break;
                        }
                        int result2 = delete(name.getText().toString());
                        if (result2 == 0) {
                            Toast.makeText(TemplatePlaceDialogActivity.this, "잘못된 입력이 있습니다.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(TemplatePlaceDialogActivity.this, "정상 처리되었습니다.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case 3: // update
                        if (name.getText().toString().equals("")
                                || menu.getText().toString().equals("")
                                || photo.getText().toString().equals("")
                                || rate.getText().toString().equals("")
                                || comment.getText().toString().equals("")) {
                            Toast.makeText(TemplatePlaceDialogActivity.this, "빈칸 두지 마세요",
                                    Toast.LENGTH_SHORT).show();
                            break;
                        }
                        int result3 = update(name.getText().toString(),
                                menu.getText().toString(),
                                photo.getText().toString(),
                                Integer.parseInt(rate.getText().toString()),
                                comment.getText().toString()
                        );
                        if (result3 == 0) {
                            Toast.makeText(TemplatePlaceDialogActivity.this, "잘못된 입력이 있습니다.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(TemplatePlaceDialogActivity.this, "정상 처리되었습니다.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        break;
                    default:
                }
            }
        });

    }

    public void onLocationEditButtonClicked(View view){
        this.finish();
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

    // delete
    public int delete(String name) {
        db = helper.getWritableDatabase();
        int result = db.delete("MyPlace", "name=?", new String[] { name + "" });
        return result;
    }

    // update
    public int update(String name, String menu, String photo, int rate, String comment) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("menu", menu);
        values.put("photo", photo);
        values.put("rate", rate);
        values.put("menu", comment);

        int result = db.update("MyPlace", values, "name=?",
                new String[] { name + "" });
        return result;
    }

    // select
    public void select() {
        db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT id, name, menu, photo, rate, comment, lat, lng FROM MyPlace",null);
        //Cursor c = db.query("MyPlace",null, null, null, null, null, null);
        /*
        Cursor c = db.query("MyPlace",
         new String[]{"id","name","menu","photo","rate","comment"}
         , null, null, null, null, null);
*/
        /* query 인자
        -> 1. 대상 테이블 이름
        -> 2. 값을 가져올 컬럼 이름의 배열
        -> 3. WHERE 구문. 물음표를 사용해 인자의 위치를 지정할 수 있다.
        -> 4. WHERE 구문에 들어가는 인자값
        -> 5. GROUP BY 구문
        -> 6. ORDER BY 구문
        -> 7. HAVING 구문
        */

        while (c.moveToNext()) {

            int id = c.getInt(c.getColumnIndex("id"));
            String name = c.getString(c.getColumnIndex("name"));
            String menu = c.getString(c.getColumnIndex("menu"));
            String photo = c.getString(c.getColumnIndex("photo"));
            int rate = c.getInt(c.getColumnIndex("rate"));
            String comment = c.getString(c.getColumnIndex("comment"));
            double lat = c.getDouble(c.getColumnIndex("lat"));
            double lng = c.getDouble(c.getColumnIndex("lng"));

            Log.i("SQL ", "select : " + "(name:" + name +
                    ")(menu:" + menu + ")" + "(photo:" + photo +
                    ")(rate:" + rate + ")(comment:"+comment+")");


            MyPlace m = new MyPlace();
            m.id = id;
            m.name = name;
            m.menu = menu;
            m.photo = photo;
            m.rate = rate;
            m.comment = comment;
            m.lat = lat;
            m.lng = lng;
            al.add(m);

        }

    }

    private void setContent() {
        mConfirm = (Button) findViewById(R.id.btnConfirm);
        mCancel = (Button) findViewById(R.id.btnCancel);

        mConfirm.setOnClickListener(this);
        mCancel.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnConfirm:
                this.finish();
                break;
            case R.id.btnCancel:
                this.finish();
                break;
            default:
                break;
        }
    }
}
