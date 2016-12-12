package com.example.joo.videorecog;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;


public class IntroActivity extends AppCompatActivity {

    MediaPlayer logoMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        logoMusic = MediaPlayer.create(IntroActivity.this,R.raw.pops_sound);
        logoMusic.start();

        setContentView(R.layout.activity_intro);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(IntroActivity.this, MenuActivity.class);
                startActivity(intent);
                // 뒤로가기 했을경우 안나오도록 없애주기 >> finish!!
                finish();
            }
        }, 2000);
    }
    @Override
    protected void onPause(){
        super.onPause();
        logoMusic.release();
    }
}
