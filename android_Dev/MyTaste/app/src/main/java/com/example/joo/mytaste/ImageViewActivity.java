package com.example.joo.mytaste;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Joo on 2016-10-09.
 */
public class ImageViewActivity extends Activity {
    ImageView iv;
    Bitmap bmImg;
    //String imgUrl = "http://192.168.0.4:8181/AutumnToWinter/img/tedjeong/";
    back task;

    ViewPager pager;
    Button btnPrevious,btnNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setContentView(R.layout.imageview);


        Intent imgpath = getIntent();
        String imgurl = imgpath.getExtras().getString("imgpath");
        //Toast.makeText(this,"imgpath given : "+imgurl ,Toast.LENGTH_LONG).show();
        iv = (ImageView)findViewById(R.id.iv);




        // Pager!







        // !



        task = new back();
        task.execute(imgurl);
        //task.execute(imgUrl+"night-1920.jpg");
    }

    private class back extends AsyncTask<String,Integer,Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            try{
                URL myfileurl = new URL(params[0]);
                //URL myfileurl = new URL("http://192.168.0.4:8181/AutumnToWinter/img/header-bg.jpg");
                //URL myfileurl = new URL("192.168.0.4:8181/AutumnToWinter/img/night-1920.jpg");
                //HttpURLConnection conn = (HttpURLConnection)myfileurl.openConnection();
                URLConnection conn = (URLConnection) myfileurl.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.connect();

                InputStream is = conn.getInputStream();
                bmImg = BitmapFactory.decodeStream(is);
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            iv.setImageBitmap(bmImg);
        }
    }
}
