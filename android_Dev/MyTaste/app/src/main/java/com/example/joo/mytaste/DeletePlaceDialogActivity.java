package com.example.joo.mytaste;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Joo on 2016-10-07.
 */
public class DeletePlaceDialogActivity extends Activity {
    Button btnconfirm,btncancel;

    MySQLiteOpenHelper helper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.delete_alert);


        helper = new MySQLiteOpenHelper(DeletePlaceDialogActivity.this, "/mnt/sdcard/MyPlace.db", null, 1);

        final Intent deleteintent = getIntent();
        final String name = deleteintent.getExtras().getString("name");

        setcontents();
        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = delete(name);
                if(result == 0){
                    Toast.makeText(getApplicationContext(),"잘못 처리 되었습니다.",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"처리 되었습니다.",Toast.LENGTH_SHORT).show();
                    setResult(Activity.RESULT_OK, deleteintent);
                    finish();
                }
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void setcontents(){
        btnconfirm = (Button) findViewById(R.id.btnConfirm);
        btncancel = (Button) findViewById(R.id.btnCancel);
    }
    public int delete(String name) {
        db = helper.getWritableDatabase();
        int result = db.delete("MyPlace", "name=?", new String[] { name + "" });
        return result;
    }
}
