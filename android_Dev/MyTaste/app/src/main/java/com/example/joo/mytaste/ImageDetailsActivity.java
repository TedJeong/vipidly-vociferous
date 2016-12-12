package com.example.joo.mytaste;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by Joo on 2016-09-07.
 */
public class ImageDetailsActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstances){
        super.onCreate(savedInstances);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.server_gridimagedetail);
        ImageView imagedetails = (ImageView)findViewById(R.id.iv_servergridimagedetail);
        Intent detailedimage = getIntent();
        /*
        if ( detailedimage != null && detailedimage.getAction().equals("image.detailed.page")){
        //if (detailedimage != null){
            Bitmap imagedatas = (Bitmap) detailedimage.getExtras().get("img");
            imagedetails.setImageBitmap(imagedatas);
        }
        */


        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();
        ImageLoader.getInstance().init(config);



        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .resetViewBeforeLoading(true)
                .showImageOnFail(R.drawable.fail)
                .showImageForEmptyUri(R.drawable.empty)
                .showImageOnLoading(R.drawable.loading)
                .build();
        String path = detailedimage.getStringExtra("imgpath");
        ImageLoader.getInstance().displayImage(path,imagedetails,options);
    }
}
