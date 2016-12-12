package com.example.joo.mytaste;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Joo on 2016-09-24.
 */public class placeinfoDialogActivity extends Activity implements View.OnClickListener {
    public static Context mContext;// Bring select function to MainActivity

    private Button mConfirm, mCancel, locationindicatebutton;
    EditText name, menu, imgpath, rate, comment, lat, lng;
    RatingBar rb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.placeinfodialog);
        setContent();

        Intent itemintent = getIntent();
        //try {
            String name_ = itemintent.getExtras().getString("name");
            int rate_ = itemintent.getExtras().getInt("rate");
            String imgpath_ = itemintent.getExtras().getString("photo");
            String menu_ = itemintent.getExtras().getString("menu");
            String comment_ = itemintent.getExtras().getString("comment");
            final Double lat_ = itemintent.getDoubleExtra("lat",0);
            final Double lng_ = itemintent.getDoubleExtra("lng",0);
            name.setText(name_, TextView.BufferType.EDITABLE);
            rate.setText(String.valueOf(rate_), TextView.BufferType.EDITABLE);
            //imgpath.setText(imgpath_, TextView.BufferType.EDITABLE);
            menu.setText(menu_, TextView.BufferType.EDITABLE);
            comment.setText(comment_, TextView.BufferType.EDITABLE);
            lat.setText(String.valueOf(lat_));
            lng.setText(String.valueOf(lng_));

            rb.setRating(rate_);
        //}catch(Exception e){
            Log.d("databasemine", "Error in placeinfoDialogActivity");
        //}
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rate.setText("" + (int) rating);
            }
        });

        locationindicatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent locationindicateintent = new Intent(getApplicationContext(),LocatePlaceActivity.class);
                locationindicateintent.putExtra("lat",lat_);
                locationindicateintent.putExtra("lng",lng_);
                startActivity(locationindicateintent);
            }
        });

    }

    private void setContent() {
        mConfirm = (Button) findViewById(R.id.btnConfirm);
        mCancel = (Button) findViewById(R.id.btnCancel);

        name = (EditText) findViewById(R.id.ed_name);
        menu = (EditText) findViewById(R.id.ed_menu);
        rate = (EditText) findViewById(R.id.ed_rate);
        //imgpath = (EditText) findViewById(R.id.ed_photo);
        comment = (EditText) findViewById(R.id.ed_comment);
        lat = (EditText) findViewById(R.id.ed_lat);
        lng = (EditText) findViewById(R.id.ed_lng);

        locationindicatebutton = (Button) findViewById(R.id.btn_Location);

        mConfirm.setOnClickListener(this);
        mCancel.setOnClickListener(this);

        rb = (RatingBar) findViewById(R.id.ratingBar);
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

