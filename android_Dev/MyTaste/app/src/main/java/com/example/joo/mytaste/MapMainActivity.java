package com.example.joo.mytaste;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapMainActivity extends AppCompatActivity {

    public static Context mainContext;// Bring select function from MapMainActivity

    static final LatLng SEOUL = new LatLng(37.56, 126.97);

    GoogleMap map;
    String strAddress;
    List<Address> listAddress;
    Geocoder geocoder;
    Address AddrAddress;

    EditText getAdd;
    Button SearchButton;
    Button MoreButton;

    MySQLiteOpenHelper helper;
    SQLiteDatabase db;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case(1002):{ // delete marker activity call
                if(resultCode == Activity.RESULT_OK) {
                    // ! not reloading !
                    // reload the markers
                    DBLocationToMarker();
                    //onResume();
                }
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mainContext = this;
        //SQLiteDatabase db = openOrCreateDatabase("test.db", Context.MODE_PRIVATE, null);
        //mDBHelper = new DatabaseHelper(mContext,"/mnt/sdcard/" + DB_NAME, null, DATABASE_VERSION);


        helper = new MySQLiteOpenHelper(MapMainActivity.this, "/mnt/sdcard/MyPlace.db", null, 1);
/////////////////////////////////////////////error
 /*
// db 연결
        //helper = new MySQLiteOpenHelper(AddPlaceDialogActivity.this, "MyPlace.db", null, 1);
        helper = new MySQLiteOpenHelper(MapMainActivity.this, "/mnt/sdcard/MyPlace.db", null, 1);
        // file이 생성 되어있지 않을 경우 파일 생성을 위해 더미 데이터 생성
        //String fileChk = "/data/data/com.example.databaseexample/databases/MyPlace.db";
        String fileChk = "/mnt/sdcard/MyPlace.db";
        File file = new File(fileChk);
        if (!file.exists()) {
            ((AddPlaceDialogActivity)AddPlaceDialogActivity.mContext).
                    insert("송정떡갈비", "떡갈비,돼지갈비", "사진", 3, "음", 35.138992, 126.794790);
            ((AddPlaceDialogActivity)AddPlaceDialogActivity.mContext).
                    insert("황순옥나주곰탕", "곰탕", "사진", 2, "먹자", 35.151199, 126.854613);
            ((AddPlaceDialogActivity)AddPlaceDialogActivity.mContext).
                    insert("또식당", "애저찜", "사진", 2, "먹어본듯", 35.149132, 126.918706);
        }
*/
/////////////////////////////////////////////

        //final CheckTypesTask task = new CheckTypesTask();
        //task.execute(); //makes crash
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();
        MapWrapperLayout mapWrapperLayout = (MapWrapperLayout)findViewById(R.id.map_relative_layout);

        initMapInteractive(mapWrapperLayout);
        //Marker seoul = map.addMarker(new MarkerOptions().position(SEOUL).title("Seoul"));

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL, 15));
        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        DBLocationToMarker();
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

            @Override
            public void onMapLongClick(LatLng point) {

                // 현재 위도와 경도에서 화면 포인트를 알려준다
                Point screenPt = map.getProjection().
                        toScreenLocation(point);

                // 현재 화면에 찍힌 포인트로 부터 위도와 경도를 알려준다.
                LatLng pickedpoint = map.getProjection().
                        fromScreenLocation(screenPt);

                MarkerOptions markerOptions = new MarkerOptions()
                        .position(new LatLng(point.latitude, point.longitude))
                        .title("It's added!")
                        .snippet("Click if you want to edit!");

                // 마커를 추가하고 말풍선 표시한 것을 보여주도록 호출
                // map.addMarker(markerOptions).showInfoWindow();
                Intent Addplacefrommaplongclickintent = new Intent(mainContext,AddPlaceDialogActivity.class);
                Addplacefrommaplongclickintent.putExtra("latitude",pickedpoint.latitude);
                Addplacefrommaplongclickintent.putExtra("longitude", pickedpoint.longitude);
                startActivity(Addplacefrommaplongclickintent);
            }
        });
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker) {
                //onInfoEditButtonClicked(marker);
                return false;
            }
        });

        // InfoEditButton click 으로 변경
        /*
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent Pass = new Intent(mainContext, AddPlaceDialogActivity.class);
                Pass.putExtra("latitude", marker.getPosition().latitude);
                Pass.putExtra("longitude", marker.getPosition().longitude);
                startActivity(Pass);


                String text = "[마커 클릭 이벤트] latitude ="
                        + marker.getPosition().latitude + ", longitude ="
                        + marker.getPosition().longitude;
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG)
                        .show();

            }
        });
        */
/*
        ((AddPlaceDialogActivity)AddPlaceDialogActivity.mContext).select();
        ((AddPlaceDialogActivity)AddPlaceDialogActivity.mContext).al;
*/
        getAdd = (EditText) findViewById(R.id.SearchInput);
//        getAdd.getBackground().mutate().setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.), PorterDuff.Mode.SRC_ATOP);
        SearchButton = (Button) findViewById(R.id.SearchButton);
        MoreButton = (Button) findViewById(R.id.MoreButton);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable edit) {
                String s = edit.toString();
                if (s.length() > 0) {
                    SearchButton.setEnabled(true);
                    //ProgressDialog dialog = ProgressDialog.show(MapMainActivity.this, "","Searching ...", true);
                    SearchPlaceInGoogle(SearchButton);
                    //dialog.dismiss();
                }
                else
                    SearchButton.setEnabled(false);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }
        };
        // For search with every letter change
        //getAdd.addTextChangedListener(textWatcher);
        //getAdd.OnEditorActionListener(this);
        getAdd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(v.getId() == R.id.SearchInput && actionId== EditorInfo.IME_ACTION_SEARCH){
                SearchButton.performClick();
            }
                return false;
            }
        });
        //Marker Test-------------------------------------------------------------------------------
        // 마커 표시하기 : 위치지정, 풍선말 설정
        /*
        MarkerOptions markerOptions = new MarkerOptions()
                .position(new LatLng(37.479097, 127.011784))
                .title("MarkerTest!")
                .snippet("Great!");

        // 마커를 추가하고 말풍선 표시한 것을 보여주도록 호출
        map.addMarker(markerOptions).showInfoWindow();
        */
    }

    @Override
    protected void onResume(){
        super.onResume();
        DBLocationToMarker();
    }

    public void onRefreshButtonClicked(View v){
        DBLocationToMarker();
    }
    public void onSearchButtonClicked(View v) {
        SearchPlaceInGoogle(v);
    }
    public void onMoreButtonClicked(View v) {
        Intent moreintent = new Intent(getApplicationContext(),seemorebuttonActivity.class);
        startActivity(moreintent);
    }
    private void SearchPlaceInGoogle(View v){
        // TODO Auto-generated method stub
        switch(v.getId()){ // 본 예제의 다음 활용을 위해 switch문으로 작성됨
            case R.id.SearchButton:
                strAddress = getAdd.getText().toString();
                // EditText를 통해 입력받은 주소를 getText()로 가져와 toString()으로 String전환 후 String 변수인 strAddress에 저장
                geocoder = new Geocoder(this);
                // Geocoder는 주소를 통해 위도와 경도의 값을 연산한다
                try {
                    listAddress = geocoder.getFromLocationName(strAddress, 5);
                    /* Geocoder의 getFromLocationName 메쏘드를 통해 주소를 List변수에 저장한다.
                            strAddress는 String 주소(텍스트)이고, listAddress는 String을 통해 구글 맵이
                    이해할 수 있는 주소로 변환한 값, 함수내의 숫자는 전달하는 주소값의 크기값 (1~5)
                    */
                    if (listAddress.size() > 0) { // 주소값이 존재 하면
                        AddrAddress = listAddress.get(0); // Address형태로 List<Address>를 전달
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                new LatLng(AddrAddress.getLatitude(), AddrAddress.getLongitude()), 16));
                    } // 지도에 해당위치를 뿌릴때 zoom 정도. 숫자가 클수록 확되된 화면
                } catch (IOException e) { // EditText를 통해 값을 입력 받을 때 생길 수 있는 오류를 catch한다
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
        }
    }
    public void onLocateButtonClicked(View view){
        MoveToMe();
    }
    private void MoveToMe() {

        int googlePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(MapMainActivity.this);


        // 맵의 이동
        //mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));

        GpsInfo gps = new GpsInfo(MapMainActivity.this);
        // GPS 사용유무 가져오기
        if (gps.isGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            // Creating a LatLng object for the current location
            LatLng latLng = new LatLng(latitude, longitude);

            // Showing the current location in Google Map
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng));

            // Map 을 zoom 합니다.
            map.animateCamera(CameraUpdateFactory.zoomTo(13));

            // 마커 설정.
            MarkerOptions optFirst = new MarkerOptions();
            optFirst.position(latLng);// 위도 • 경도
            optFirst.title("Current Position");// 제목 미리보기
            optFirst.snippet("You are Here!");
            optFirst.icon(BitmapDescriptorFactory.fromResource(R.drawable.eating_24));
            map.addMarker(optFirst).showInfoWindow();
        }
    }
    public void onAddButtonClicked(View v) {
        switch (v.getId()) {
            case R.id.AddButton:
                startActivity(new Intent(this, AddPlaceDialogActivity.class));
                break;
            default:
                break;
        }
    }
    void DBLocationToMarker(){
        db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT id, name, menu, photo, rate, comment, lat, lng FROM MyPlace", null);

        while (c.moveToNext()) {

            int id = c.getInt(c.getColumnIndex("id"));
            String name = c.getString(c.getColumnIndex("name"));
            String menu = c.getString(c.getColumnIndex("menu"));
            String photo = c.getString(c.getColumnIndex("photo"));
            int rate = c.getInt(c.getColumnIndex("rate"));
            String comment = c.getString(c.getColumnIndex("comment"));
            double lat = c.getDouble(c.getColumnIndex("lat"));
            double lng = c.getDouble(c.getColumnIndex("lng"));

            /*
            MyPlace m = new MyPlace();
            m.id = id;
            m.name = name;
            m.menu = menu;
            m.photo = photo;
            m.rate = rate;
            m.comment = comment;
            m.lat = lat;
            m.lng = lng;
            al.add(m);
            */
            MyAddMarker(name,menu,rate,lat,lng);
        }

    }
    void MyAddMarker(String name, String menu, int rate, double x, double y){
        LatLng loc = new LatLng(x,y);
        MarkerOptions marker = new MarkerOptions()
                .position(loc)
                .title(name)
                .snippet(menu);
        map.addMarker(marker);
    }

    private boolean initMapInteractive(final MapWrapperLayout mapWrapperLayout){
        final View v;
        final TextView stitle;
        final TextView sscore;
        final TextView smenu;

        ImageButton info_edit_button,info_delete_button,info_close_button;
        if(map != null) {

            // We want to reuse the info window for all the markers,
            // so let's create only one class member instance
            v = (View) getLayoutInflater().inflate(R.layout.infowindow, null); // null: do not attach it to any parent window
            stitle = (TextView) v.findViewById(R.id.SubjectTitle);
            sscore = (TextView) v.findViewById(R.id.SubjectScore);
            smenu = (TextView) v.findViewById(R.id.SubjectMenu);
            info_edit_button = (ImageButton) v.findViewById(R.id.InfoEditButton);
            info_delete_button = (ImageButton) v.findViewById(R.id.InfoDeleteButton);
            info_close_button = (ImageButton) v.findViewById(R.id.InfoCloseButton);

            final OnInfoWindowElemTouchListener infoButtonListener1;
            final OnInfoWindowElemTouchListener infoButtonListener2;
            final OnInfoWindowElemTouchListener infoButtonListener3;


// MapWrapperLayout initialization
            // 39 - default marker height
            // 20 - offset between the default InfoWindow bottom edge and it's content bottom edge
            mapWrapperLayout.init(map, getPixelsFromDp(this, 39 + 20));


            // We want to reuse the info window for all the markers,
            // so let's create only one class member instance

            // Setting custom OnTouchListener which deals with the pressed state
            // so it shows up



            infoButtonListener1 = new OnInfoWindowElemTouchListener(info_edit_button,
                    ResourcesCompat.getDrawable(getResources(), R.drawable.edit_24, null),
                    ResourcesCompat.getDrawable(getResources(), R.drawable.edit_24, null))
            {
                String name,menu,photo,comment;
                int rate;
                double lat, lng;
                @Override
                protected void onClickConfirmed(View v, Marker marker) {
                    // Here we can perform some action triggered after clicking the button
                    //TODO: this should be changed to placeinfodialogue with editable
                    try {
                        Cursor c = db.rawQuery("SELECT id, name, menu, photo, rate, comment, lat, lng FROM MyPlace where name='" + marker.getTitle() + "';", null);
                        c.moveToNext();
                        int id = c.getInt(c.getColumnIndex("id"));
                        name = c.getString(c.getColumnIndex("name"));
                        menu = c.getString(c.getColumnIndex("menu"));
                        photo = c.getString(c.getColumnIndex("photo"));
                        rate = c.getInt(c.getColumnIndex("rate"));
                        comment = c.getString(c.getColumnIndex("comment"));
                        lat = c.getDouble(c.getColumnIndex("lat"));
                        lng = c.getDouble(c.getColumnIndex("lng"));
                    }catch(Exception e){
                        Log.d("onClickConfirmed","onClickConfirmed Error!");
                    }
                    Intent editplaceintent = new Intent(MapMainActivity.this,EditPlaceDialogActivity.class);

                    editplaceintent.putExtra("name",name);
                    editplaceintent.putExtra("menu",menu);
                    editplaceintent.putExtra("photo",photo);
                    editplaceintent.putExtra("rate",rate);
                    editplaceintent.putExtra("comment",comment);

                    //editplaceintent.putExtra("latitude", marker.getPosition().latitude);
                    //editplaceintent.putExtra("longitude", marker.getPosition().longitude);
                    editplaceintent.putExtra("lat",lat);
                    editplaceintent.putExtra("lng", lng);
                    startActivity(editplaceintent);
                }
            };
            info_edit_button.setOnTouchListener(infoButtonListener1);

            infoButtonListener2 = new OnInfoWindowElemTouchListener(info_delete_button,
                    ResourcesCompat.getDrawable(getResources(), R.drawable.trash_bin_24,null),
                    ResourcesCompat.getDrawable(getResources(), R.drawable.trash_bin_24, null))
            {

                @Override
                protected void onClickConfirmed(View v, Marker marker) {
                    try {
                        //db = helper.getWritableDatabase();
                        //db.execSQL("delete FROM MyPlace where name='" + marker.getTitle() + "';", null);
                        //int result = db.delete("MyPlace", "name=?", new String[] { marker.getTitle() + "" });
                        //Toast.makeText(MapMainActivity.this,"try", Toast.LENGTH_SHORT).show();
                        Intent deleteintent = new Intent(MapMainActivity.this,DeletePlaceDialogActivity.class);
                        deleteintent.putExtra("name", marker.getTitle() + "");
                        //startActivity(deleteintent);
                        //deleteintent.putExtra("name","하림가");
                        startActivityForResult(deleteintent, 1002);
                    }catch(Exception e){

                    }
                    Toast.makeText(MapMainActivity.this,"marker is deleted!", Toast.LENGTH_SHORT).show();
                }
            };
            info_delete_button.setOnTouchListener(infoButtonListener2);

            infoButtonListener3 = new OnInfoWindowElemTouchListener(info_close_button,
                    ResourcesCompat.getDrawable(getResources(), R.drawable.close_24, null),
                    ResourcesCompat.getDrawable(getResources(), R.drawable.close_24, null))
            {
                @Override
                protected void onClickConfirmed(View v, Marker marker) {
                    //v.setVisibility(v.GONE);
                    marker.hideInfoWindow();
                    //mapWrapperLayout.performClick();
                    //finish();
                }
            };
            info_close_button.setOnTouchListener(infoButtonListener3);

            map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {

                    db = helper.getReadableDatabase();

                    infoButtonListener1.setMarker(marker);
                    infoButtonListener2.setMarker(marker);
                    infoButtonListener3.setMarker(marker);
                    // Setting up the infoWindow with current's marker info
                    LatLng ll = marker.getPosition();
                    stitle.setText(marker.getTitle());
                    int rate = 0;
                    try {
                        Cursor c = db.rawQuery("SELECT id, name, menu, photo, rate, comment, lat, lng FROM MyPlace where name='" + marker.getTitle() + "';", null);
                        c.moveToNext();
                        int id = c.getInt(c.getColumnIndex("id"));
                        String name = c.getString(c.getColumnIndex("name"));
                        String menu = c.getString(c.getColumnIndex("menu"));
                        String photo = c.getString(c.getColumnIndex("photo"));
                        rate = c.getInt(c.getColumnIndex("rate"));
                        String comment = c.getString(c.getColumnIndex("comment"));
                        double lat = c.getDouble(c.getColumnIndex("lat"));
                        double lng = c.getDouble(c.getColumnIndex("lng"));
                    } catch (Exception e) {

                    }
                    //int rate = 1;
                    smenu.setText(marker.getSnippet());
                    sscore.setText(String.valueOf(rate));
                    /*
                    infoTitle.setText(marker.getTitle());
                    infoSnippet.setText(marker.getSnippet());
                    infoButtonListener.setMarker(marker);
                    */
                    // We must call this to set the current marker and infoWindow references
                    // to the MapWrapperLayout
                    mapWrapperLayout.setMarkerWithInfoWindow(marker, v);

                    return v;
                }
            });
        }

        return (map != null);
    }
    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5f);
    }
    private boolean initMap(){
        if(map != null){
            map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter(){

                View v = getLayoutInflater().inflate(R.layout.infowindow,null); // null: do not attach it to any parent window
                TextView stitle = (TextView) v.findViewById(R.id.SubjectTitle);
                TextView sscore = (TextView) v.findViewById(R.id.SubjectScore);
                TextView smenu = (TextView) v.findViewById(R.id.SubjectMenu);
                ImageButton info_edit_button = (ImageButton) v.findViewById(R.id.InfoEditButton);

                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(final Marker marker) {

                    //Button info_close_button = (Button) v.findViewById(R.id.InfoCloseButton);
                    //Button info_delete_button = (Button) v.findViewById(R.id.InfoDeleteButton);

                    LatLng ll = marker.getPosition();
                    stitle.setText(marker.getTitle());
                    smenu.setText(marker.getSnippet());

                    info_edit_button.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                Toast.makeText(getApplicationContext(), marker.getPosition().latitude + "," + marker.getPosition().longitude, Toast.LENGTH_LONG).show();
                                                                /*
                                                                Intent Pass = new Intent(mainContext, EditPlaceDialogActivity.class);
                                                                Pass.putExtra("latitude", marker.getPosition().latitude);
                                                                Pass.putExtra("longitude", marker.getPosition().longitude);
                                                                startActivity(Pass);
                                                                */

                                                                /*

                                                                Intent intent = new Intent(getApplicationContext(), placeinfoDialogActivity.class);
                                                                intent.putExtra("name", al.get(position).getName());
                                                                intent.putExtra("val", al.get(position).getValue());
                                                                intent.putExtra("imgpath", al.get(position).getImgpath());
                                                                startActivity(intent);
                                                                                                                              *
                                                                * */
                                                            }
                                                        }
                    );

                    ///nfo_delete_button
                    return v;
                }
                /*
                public void onInfoEditButtonClicked(Marker marker){
                    Intent Pass = new Intent(mainContext, AddPlaceDialogActivity.class);
                    Pass.putExtra("latitude", marker.getPosition().latitude);
                    Pass.putExtra("longitude", marker.getPosition().longitude);
                    startActivity(Pass);
                }
                */
            });
        }
        return (map != null);
    }

    private class CheckTypesTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog asyncDialog = new ProgressDialog(
               MapMainActivity.this);

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            // STYPLE_SPINNER ,STYLE_HORIZONTAL
            asyncDialog.setMessage("searching..");

            // show dialog
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                for (int i = 0; i < 5; i++) {
                    //asyncDialog.setProgress(i * 30);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            asyncDialog.dismiss();
            super.onPostExecute(result);
        }
    }
    class GpsInfo extends Service implements LocationListener {

        private final Context mContext;

        // 현재 GPS 사용유무
        boolean isGPSEnabled = false;

        // 네트워크 사용유무
        boolean isNetworkEnabled = false;

        // GPS 상태값
        boolean isGetLocation = false;

        Location location;
        double lat; // 위도
        double lon; // 경도

        // GPS 정보 업데이트 거리 10미터
        private static final long MIN_DISTANCE_UPDATES = 10;

        // GPS 정보 업데이트 시간 1/1000
        private static final long MIN_TIME_UPDATES = 1000 * 60 * 1;

        protected LocationManager locationManager;

        public GpsInfo(Context context) {
            this.mContext = context;
            getLocation();
        }

        public Location getLocation() {
            try {
                locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

                isGPSEnabled = locationManager
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);

                isNetworkEnabled = locationManager
                        .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if (!isGPSEnabled && !isNetworkEnabled) {
                } else {
                    this.isGetLocation = true;
                    if (isNetworkEnabled) {
                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_UPDATES,
                                MIN_DISTANCE_UPDATES, this);

                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (location != null) {
                                // 위도 경도 저장
                                lat = location.getLatitude();
                                lon = location.getLongitude();
                            }
                        }
                    }

                    if (isGPSEnabled) {
                        if (location == null) {
                            locationManager
                                    .requestLocationUpdates(
                                            LocationManager.GPS_PROVIDER,
                                            MIN_TIME_UPDATES,
                                            MIN_DISTANCE_UPDATES,
                                            this);
                            if (locationManager != null) {
                                location = locationManager
                                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                if (location != null) {
                                    lat = location.getLatitude();
                                    lon = location.getLongitude();
                                }
                            }
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return location;
        }

        /**
         * GPS 종료
         */
        public void stopUsingGPS() {
            if (locationManager != null) {
                if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                        ) {
                    // TODO: Consider calling
                    //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    showSettingsAlert();
                }
                locationManager.removeUpdates(GpsInfo.this);
            }
        }

        /**
         * 위도값
         */
        public double getLatitude() {
            if (location != null) {
                lat = location.getLatitude();
            }
            return lat;
        }

        /**
         * 경도값
         */
        public double getLongitude() {
            if (location != null) {
                lon = location.getLongitude();
            }
            return lon;
        }

        public boolean isGetLocation() {
            return this.isGetLocation;
        }

        /**
         * GPS 정보를 가져오지 못했을때 설정값으로 갈지 물어보는 alert 창
         */
        public void showSettingsAlert() {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    mContext);

            alertDialog.setTitle("GPS 사용유무셋팅");
            alertDialog
                    .setMessage("GPS 셋팅이 되지 않았을수도 있습니다.\n 설정창으로 가시겠습니까?");

            alertDialog.setPositiveButton("Settings",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            Intent intent = new Intent(
                                    Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            mContext.startActivity(intent);
                        }
                    });
            alertDialog.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            dialog.cancel();
                        }
                    });

            alertDialog.show();
        }

        @Override
        public IBinder onBind(Intent arg0) {
            return null;
        }

        public void onLocationChanged(Location location) {
            // TODO Auto-generated method stub

        }

        public void onStatusChanged(String provider, int status,
                                    Bundle extras) {
            // TODO Auto-generated method stub

        }

        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub

        }

        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub

        }
    }
}