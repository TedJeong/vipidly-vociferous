package com.example.joo.videorecog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Joo on 2015-11-09.
 */
public class MenuActivity extends Activity{

    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_menu);
    }
    public void Button0Clicked(View view){
        Intent test = new Intent(getApplicationContext(),TestActivity.class);
        startActivity(test);
    }
    public void Button1Clicked(View view){
        Intent VideoRecog = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(VideoRecog);
    }
    public void Button2Clicked(View view){}
    public void Button3Clicked(View view){}
    public void Button4Clicked(View view){}
    public void Button5Clicked(View view){}

}
