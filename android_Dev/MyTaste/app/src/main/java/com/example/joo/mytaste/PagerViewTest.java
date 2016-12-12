package com.example.joo.mytaste;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Joo on 2016-10-11.
 */
public class PagerViewTest extends Activity{
    ViewPager pager;
    Button btnPrevious,btnNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setContentView(R.layout.pagerview);

        Intent imgpath = getIntent();
        String imgurl = imgpath.getExtras().getString("imgpath");

        String delims = ";+"; // if there's no + contiguous deliminator ;; returns null string
        String[] imgurls = imgurl.split(delims);

        ArrayList<String>urllist = new ArrayList<>();
        for(int i=0;i<imgurls.length;i++) {
            urllist.add(imgurls[i]);
            Log.d("PagerViewTest","StringParsed : "+ urllist.get(i));
        }

        pager = (ViewPager)findViewById(R.id.pager);
        btnPrevious = (Button) findViewById(R.id.btn_previous);
        btnNext = (Button) findViewById(R.id.btn_next);

        ImagePagerAdapter pgadapter = new ImagePagerAdapter(getLayoutInflater(),urllist);
        pager.setAdapter(pgadapter);

        // Pager!

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();

        ImageLoader.getInstance().init(config);
        // !
    }

    public void mOnClick(View v){
        int position;
        switch(v.getId()){
            case R.id.btn_previous:
                position = pager.getCurrentItem();
                pager.setCurrentItem(position-1,true); // params(previous position,smoothness)
                break;
            case R.id.btn_next:
                position = pager.getCurrentItem();
                pager.setCurrentItem(position+1,true);
                break;
        }
    }

    public class ImagePagerAdapter extends PagerAdapter {

        DisplayImageOptions options=new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .resetViewBeforeLoading(true)
                .showImageOnFail(R.drawable.fail)
                .showImageForEmptyUri(R.drawable.empty)
                .showImageOnLoading(R.drawable.loading)
                .build();

        LayoutInflater inflater;
        ArrayList<String> itemlist = new ArrayList<>();
        public ImagePagerAdapter(){}

        public ImagePagerAdapter(LayoutInflater inflater,ArrayList<String> urllist) {
            this.inflater=inflater;
            this.itemlist = urllist;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return itemlist.size(); //이미지 개수 리턴(그림이 10개라서 10을 리턴)
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub

            View view=null;
            view= inflater.inflate(R.layout.pagerchildview, null);
            ImageView img = (ImageView)view.findViewById(R.id.img_viewpager_childimage);

            //ImageView에 현재 position 번째에 해당하는 이미지를 보여주기 위한 작업
            //현재 position에 해당하는 이미지를 setting
            //img.setImageResource(R.drawable.gametitle_01+position);
            ImageLoader.getInstance().displayImage(itemlist.get(position),img,options);

            //ViewPager에 만들어 낸 View 추가
            container.addView(view);

            //Image가 세팅된 View를 리턴
            return view;
        }

        //화면에 보이지 않은 View는파쾨를 해서 메모리를 관리함.
        //첫번째 파라미터 : ViewPager
        //두번째 파라미터 : 파괴될 View의 인덱스(가장 처음부터 0,1,2,3...)
        //세번째 파라미터 : 파괴될 객체(더 이상 보이지 않은 View 객체)
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // TODO Auto-generated method stub

            //ViewPager에서 보이지 않는 View는 제거
            //세번째 파라미터가 View 객체 이지만 데이터 타입이 Object여서 형변환 실시
            container.removeView((View)object);

        }

        //instantiateItem() 메소드에서 리턴된 Ojbect가 View가  맞는지 확인하는 메소드
        @Override
        public boolean isViewFromObject(View v, Object obj) {
            // TODO Auto-generated method stub
            return v==obj;
        }
    }
}
