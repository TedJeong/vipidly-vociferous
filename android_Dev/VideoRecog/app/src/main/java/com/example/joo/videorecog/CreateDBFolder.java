package com.example.joo.videorecog;

import java.io.*;
import android.app.*;
import android.os.*;
import android.widget.*;

/**
 * Created by Joo on 2015-11-11.
 */
public class CreateDBFolder extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String str = Environment.getExternalStorageState();
        if ( str.equals(Environment.MEDIA_MOUNTED)) {
            String root = Environment.getExternalStorageDirectory().getPath();
            String dirPath = root+"/RecogN_DB";
            File file = new File(dirPath);
            if( !file.exists() )  // 원하는 경로에 폴더가 있는지 확인
                file.mkdirs();
        }
        else
            Toast.makeText(CreateDBFolder.this, "SD Card 인식 실패", Toast.LENGTH_SHORT).show();
    }

}
