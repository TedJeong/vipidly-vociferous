package com.example.joo.mytaste;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Joo on 2016-09-24.
 */
public class seemorebuttonActivity extends Activity {
    TextView gotolist;
    TextView gotosignin;
    TextView gotolivechat;
    TextView gotopost;
    TextView gotokakao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.seemorebuttonview);

        gotolist = (TextView) findViewById(R.id.gotolist);
        gotosignin = (TextView) findViewById(R.id.gotosignin);
        gotolivechat = (TextView) findViewById(R.id.gotolivechat);
        gotopost = (TextView) findViewById(R.id.gotopost);
        gotokakao = (TextView) findViewById(R.id.gotokakao);

        gotolist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotolistintent = new Intent(getApplicationContext(),placelistviewActivity.class);
                startActivity(gotolistintent);
            }
        });

        gotosignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotosigninintent = new Intent(getApplicationContext(),WebViewActivity.class);
                startActivity(gotosigninintent);
            }
        });
        gotopost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotopostintent = new Intent(getApplicationContext(),WebCardPageActivity.class);
                startActivity(gotopostintent);
            }
        });
    }


}

