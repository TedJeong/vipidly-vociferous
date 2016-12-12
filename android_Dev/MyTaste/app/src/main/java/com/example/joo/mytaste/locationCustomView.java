package com.example.joo.mytaste;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Joo on 2016-09-24.
 */
public class locationCustomView extends LinearLayout {


    TextView tv_name;
    TextView tv_val;
    ImageView im;
    ImageButton editbtn;
    ImageButton delbtn;
    RatingBar rb;




    public locationCustomView(Context context) {
        super(context);
        init(context);
    }

    public locationCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
/*
    AdapterView.OnItemClickListener listviewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            String toastMessage = view.getTag() + " is selected. position is " + position + ", and id is " + id;

            Toast.makeText(
                    getContext(),
                    toastMessage,
                    Toast.LENGTH_SHORT
            ).show();
        }
    };

    locationCustomView.setOnItemClickListener(listviewItemClickListener);
*/

    public void init(final Context context){
        LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflate.inflate(R.layout.locationlistelem, this, true);

        tv_name = (TextView)findViewById(R.id.tv_name);
        //tv_val = (TextView)findViewById(R.id.tv_val);
        im = (ImageView)findViewById(R.id.iv_listitem);
        //editbtn = (ImageButton)findViewById(R.id.editBtn);
        //delbtn = (ImageButton)findViewById(R.id.delBtn);
        rb = (RatingBar)findViewById(R.id.ratingBar);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();

        ImageLoader.getInstance().init(config);
/*
        // List Custom view button click Listener
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent placeimageviewintent = new Intent(context,ImageViewActivity.class);
                context.startActivity(placeimageviewintent);
            }
        });
*/
/*
        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "editbutton clicked", Toast.LENGTH_SHORT).show();
            }
        });
*/
        /*
        delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "delete clicked", Toast.LENGTH_SHORT).show();
                Intent deleteintent = new Intent(, placeinfoDialogActivity.class);
                deleteintent.putExtra("name", al.get(position).getName());
                startActivityForResult(deleteintent,1002);
            }
        });
  */
    }

    public void imviewclicked(final MyPlace lo, final Context context){
        //Toast.makeText(context,lo.getName()+" clicked!",Toast.LENGTH_SHORT).show();
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent placeimageviewintent = new Intent(context,ImageViewActivity.class);
                Intent placeimageviewintent = new Intent(context, PagerViewTest.class);
                //Toast.makeText(context,lo.getPhoto()+" clicked!",Toast.LENGTH_SHORT).show();
                placeimageviewintent.putExtra("imgpath", lo.getPhoto());
                context.startActivity(placeimageviewintent);
            }
        });
    }

    public void setlocation(MyPlace lo) {
        String name = lo.getName();

        tv_name.setText(name);
        tv_name.setTextColor(Color.BLACK);
        int val = lo.getRate();
        rb.setRating(val);
        /*
        rb.setNumStars(val);
        rb.setMax(5);
        */
        //tv_val.setText(String.valueOf(val));
        //tv_val.setTextColor(Color.BLACK);


        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .resetViewBeforeLoading(true)
                .showImageOnFail(R.drawable.michelin_temp_64)
                .showImageForEmptyUri(R.drawable.michelin_temp_gul)
                .showImageOnLoading(R.drawable.loading)
                .build();

        String imgurl = lo.getPhoto();
        String delims = ";+"; // if there's no + contiguous deliminator ;; returns null string
        String[] imgurls = imgurl.split(delims);

        if (imgurls.length == 0) {
            Bitmap bmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            im.setImageBitmap(bmap);
        } else {
            ImageLoader.getInstance().displayImage(imgurls[0], im, options);
            //bimg = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(getResources(),R.drawable.tedjeong.),50,50);
            //Bitmap bbmap = BitmapFactory.createScaledBitmap()
        }

    }
}
