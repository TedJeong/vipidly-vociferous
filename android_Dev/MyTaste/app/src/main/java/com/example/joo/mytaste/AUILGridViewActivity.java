package com.example.joo.mytaste;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;
import com.nostra13.universalimageloader.utils.ImageSizeUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Joo on 2016-10-12.
 */
public class AUILGridViewActivity extends Activity {
    MySQLiteOpenHelper helper;
    SQLiteDatabase db;
    String globalplacename;
    GridView gridview;
    gridviewAdapter gridadapter;
    gridviewAdapterselected gridadapterselected;
    Button btnloadphoto,btntakephoto,btnchoosephoto,btndeletephoto;
    final ArrayList<String> urlist = new ArrayList<String>();
    final ArrayList<Boolean> thumbnailsselection = new ArrayList<>();
    final int REQ_CODE_SELECT_IMAGE=100;
    final int REQ_CODE_TAKE_PHOTO=101;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQ_CODE_SELECT_IMAGE)
        {
            if(resultCode==Activity.RESULT_OK)
            {
                try {
                    // DB add from photoloadbutton
                    urlist.add(data.getData().toString());
                    String urlistall="";
                    for(int i=0;i<urlist.size();i++){
                        urlistall+=urlist.get(i)+";";
                    }
                    update_photo(globalplacename,urlistall);
                    thumbnailsselection.add(false);
                    gridview.setAdapter(gridadapter);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        // Take Photo
        if(requestCode == REQ_CODE_TAKE_PHOTO && resultCode == Activity.RESULT_OK){
            Uri currImageUri = data.getData();
            urlist.add(currImageUri.toString());
            String urlistall="";
            for(int i=0;i<urlist.size();i++){
                urlistall+=urlist.get(i)+";";
            }
            update_photo(globalplacename,urlistall);
            thumbnailsselection.add(false);
            gridview.setAdapter(gridadapter);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editplacephoto);
        setContents();
        helper = new MySQLiteOpenHelper(AUILGridViewActivity.this, "/mnt/sdcard/MyPlace.db", null, 1);


        /*
        String ExternalStorageDirectoryPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String targetPath = ExternalStorageDirectoryPath + "/DCIM/Camera/";
        String photoPath = targetPath + "20161009_112528.jpg";
        urlist.add("photoPath");
        */

        Intent getAUILGridViewActivityIntent = getIntent();
        final String placename = getAUILGridViewActivityIntent.getExtras().getString("name");
        String imgurl = getAUILGridViewActivityIntent.getExtras().getString("imgpath");
        globalplacename = placename;
        String delims = ";+"; // if there's no + contiguous deliminator ;; returns null string
        String[] imgurls = imgurl.split(delims);

        for(int i=0;i<imgurls.length;i++) {
            urlist.add(imgurls[i]);
            //Log.d("PagerViewTest","StringParsed : "+ urlist.get(i));
            thumbnailsselection.add(false);
        }
        //TODO : delete!!! after completion
        // sample datas for empty
        if(urlist.isEmpty()) {
            urlist.add("http://192.168.0.4:8181/AutumnToWinter/img/place/tedjeong/genroku2.jpg");
            urlist.add("http://192.168.0.4:8181/AutumnToWinter/img/place/tedjeong/meltingmonkey.jpg");
            urlist.add("http://192.168.0.4:8181/AutumnToWinter/img/place/tedjeong/4sushi.jpg");
            urlist.add("http://192.168.0.4:8181/AutumnToWinter/img/place/tedjeong/choinchef.jpg");
        }




        gridadapter = new gridviewAdapter(getLayoutInflater(),urlist);
        gridview.setAdapter(gridadapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent imagedetailintent = new Intent(getApplicationContext(), ImageDetailsActivity.class);
                //Toast.makeText(getApplicationContext(),String.valueOf(position)+" clicked!",Toast.LENGTH_SHORT).show();
                imagedetailintent.putExtra("imgpath", urlist.get(position));
                startActivity(imagedetailintent);
            }
        });
        gridview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), urlist.get(position) + " long clicked", Toast.LENGTH_LONG).show();
                return false;
            }
        });
        btnloadphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
            }
        });
        btntakephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQ_CODE_TAKE_PHOTO);
            }
        });
        btnchoosephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridadapterselected = new gridviewAdapterselected(getLayoutInflater(), urlist);
                gridview.setAdapter(gridadapterselected);
            }
        });
        btndeletephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String photopaths = select_photo(placename);
                String[] photos = photopaths.split(";+");
                String photo="";
                for (int i = urlist.size()-1; i >= 0; i--) {
                    if (thumbnailsselection.get(i) == true) {
                        Toast.makeText(getApplicationContext(), String.valueOf(i) + " is deleted.", Toast.LENGTH_SHORT).show();
                        urlist.remove(i);
                        thumbnailsselection.remove(i);
                    }else{
                        photo+=photos[i]+";";
                    }
                // TODO: 서버 계정 폴더에서는 실제 파일을 지우기
                }
                update_photo(placename, photo);
                for (int i = 0; i < thumbnailsselection.size(); i++) {
                    thumbnailsselection.set(i,false);
                }
                gridview.setAdapter(gridadapter);
            }
        });
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();
        ImageLoader.getInstance().init(config);
    }

    public class gridviewAdapter extends BaseAdapter{
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .resetViewBeforeLoading(true)
                .showImageOnFail(R.drawable.fail)
                .showImageForEmptyUri(R.drawable.empty)
                .showImageOnLoading(R.drawable.loading)
                .postProcessor(new BitmapProcessor() { // erase this option for natural arrangement
                    @Override
                    public Bitmap process(Bitmap bmp) {
                        return Bitmap.createScaledBitmap(bmp, 300, 300, false);
                    }
                })
                .build();
        Context context = getApplicationContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ArrayList<String> urls = new ArrayList<>();

        public gridviewAdapter(LayoutInflater inflater, ArrayList<String> urllist) {
            this.urls = urllist;
            this.inflater = inflater;
        }

        @Override
        public int getCount() {
            return urls.size();
        }

        @Override
        public Object getItem(int position) {
            return urls.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView view;
            if (convertView == null) {
                View v = inflater.inflate(R.layout.server_gridimagedetail, parent, false);
                view = (ImageView) v.findViewById(R.id.iv_servergridimagedetail);

                view.setLayoutParams(new GridView.LayoutParams(220, 220));
                view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                view.setPadding(2, 2, 2, 2);
            } else {
                view = (ImageView) convertView;
            }
            ImageLoader.getInstance()
                    .displayImage(urls.get(position), view, options);
            return view;
        }
    }
    public class gridviewAdapterselected extends BaseAdapter {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .resetViewBeforeLoading(true)
                .showImageOnFail(R.drawable.fail)
                .showImageForEmptyUri(R.drawable.empty)
                .showImageOnLoading(R.drawable.loading)
                .postProcessor(new BitmapProcessor() { // erase this option for natural arrangement
                    @Override
                    public Bitmap process(Bitmap bmp) {
                        return Bitmap.createScaledBitmap(bmp, 300, 300, false);
                    }
                })
                .build();
        Context context = getApplicationContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ArrayList<String> urls = new ArrayList<>();

        public gridviewAdapterselected(LayoutInflater inflater, ArrayList<String> urllist) {
            this.urls = urllist;
            this.inflater = inflater;
        }

        @Override
        public int getCount() {
            return urls.size();
        }

        @Override
        public Object getItem(int position) {
            return urls.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.placephotoselection, parent, false);
                holder.imageview = (ImageView) convertView.findViewById(R.id.thumbImage);
                holder.checkbox = (CheckBox) convertView.findViewById(R.id.itemCheckBox);
                holder.imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
                holder.imageview.setPadding(2, 2, 2, 2);
                convertView.setLayoutParams(new GridView.LayoutParams(220, 220));
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.checkbox.setId(position);
            holder.imageview.setId(position);
            holder.checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    CheckBox cb = (CheckBox) v;
                    int id = cb.getId();
                    if (thumbnailsselection.get(id)) {
                        cb.setChecked(false);
                        thumbnailsselection.set(id,false);
                    } else {
                        cb.setChecked(true);
                        thumbnailsselection.set(id,true);
                    }
                }
            });
            /*
            holder.imageview.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    CheckBox cb = (CheckBox) v;
                    int id = cb.getId();
                    if (thumbnailsselection.get(id)) {
                        cb.setChecked(false);
                        thumbnailsselection.set(id,false);
                    } else {
                        cb.setChecked(true);
                        thumbnailsselection.set(id,true);
                    }
                }
            });
            */
            if (holder.imageview == null) {
                Log.d("AUILGridViewActivity", "holder.imageView is null!");
            }
            ImageLoader.getInstance().displayImage(urls.get(position), holder.imageview, options);

            holder.checkbox.setChecked(thumbnailsselection.get(position));
            holder.id = position;
            return convertView;
        }
        class ViewHolder{
            ImageView imageview;
            CheckBox checkbox;
            int id;
        }
    }
    public void setContents(){
        gridview = (GridView)findViewById(R.id.gv_imagegrid);
        btnloadphoto = (Button)findViewById(R.id.btn_loadphoto);
        btntakephoto = (Button)findViewById(R.id.btn_takephoto);
        btnchoosephoto = (Button)findViewById(R.id.btn_choosephoto);
        btndeletephoto = (Button)findViewById(R.id.btn_deletephoto);
    }
    public int update_photo(String name, String photo) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("photo", photo);
        int result = db.update("MyPlace", values, "name=?",
                new String[] { name + "" });
        return result;
    }

    public String select_photo(String name) {
        db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT name, photo FROM MyPlace where name='"+name+"'", null);
        c.moveToNext();
        return c.getString(c.getColumnIndex("photo"));
    }
    public long insert_photo(String name, String photo){
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("photo",photo);

        // SQL insert : (name:~)(menu:~)
        long result = db.insert("MyPlace", null, values);
        return result;
    }
}
