package com.example.joo.mytaste;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by Joo on 2016-10-12.
 */
public class PhotoBookThumbnailsCustomView extends GridLayout {

    ImageView giv1,giv2,giv3;

    public PhotoBookThumbnailsCustomView(Context context){
        super(context);
        init(context);
    }

    public PhotoBookThumbnailsCustomView(Context context, AttributeSet attrs){
        super(context,attrs);
        init(context);
    }

    public void init(final Context context){
        LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflate.inflate(R.layout.itemphotogridview,this,true);
        giv1=(ImageView)findViewById(R.id.giv_1);
        giv2=(ImageView)findViewById(R.id.giv_2);
        giv3=(ImageView)findViewById(R.id.giv_3);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();
        ImageLoader.getInstance().init(config);
    }

    public void setphotothumbnails(String imgurl) {

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .resetViewBeforeLoading(true)
                .showImageOnFail(R.drawable.michelin_temp_64)
                .showImageForEmptyUri(R.drawable.michelin_temp_gul)
                .showImageOnLoading(R.drawable.loading)
                .build();

        String delims = ";+"; // if there's no + contiguous deliminator ;; returns null string
        String[] imgurls = imgurl.split(delims);
        if(imgurls.length==0){

        }
        if(imgurls.length==1){
            ImageLoader.getInstance().displayImage(imgurls[0], giv1, options);
        }else if(imgurls.length==2){
            ImageLoader.getInstance().displayImage(imgurls[0], giv1, options);
            ImageLoader.getInstance().displayImage(imgurls[1], giv2, options);
        }else if(imgurls.length>=3){
            ImageLoader.getInstance().displayImage(imgurls[0], giv1, options);
            ImageLoader.getInstance().displayImage(imgurls[1], giv2, options);
            ImageLoader.getInstance().displayImage(imgurls[2], giv3, options);
        }
        /*
        if (imgurls.length == 0) {
            Bitmap bmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            giv1.setImageBitmap(bmap);
            giv2.setImageBitmap(bmap);
            giv3.setImageBitmap(bmap);
        } else {
            ImageLoader.getInstance().displayImage(imgurls[0], giv1, options);
            ImageLoader.getInstance().displayImage(imgurls[1], giv2, options);
            ImageLoader.getInstance().displayImage(imgurls[2], giv3, options);
        }
           */
    }
}
