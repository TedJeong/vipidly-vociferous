package com.example.joo.mytaste.NavigationFrames;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;


import android.util.Log;
import android.view.InflateException;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joo.mytaste.AddPlaceDialogActivity;
import com.example.joo.mytaste.DeletePlaceDialogActivity;
import com.example.joo.mytaste.EditPlaceDialogActivity;
import com.example.joo.mytaste.MapWrapperLayout;
import com.example.joo.mytaste.MySQLiteOpenHelper;
import com.example.joo.mytaste.OnInfoWindowElemTouchListener;
import com.example.joo.mytaste.R;
import com.example.joo.mytaste.seemorebuttonActivity;
import com.example.joo.mytaste.testpageActivity;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

/**
 * Created by Joo on 2016-10-18.
 */
public class MapMainFragment extends Fragment {
    public static Context mainContext;// Bring select function from MapMainActivity

    static View MapMainView;
    static ViewGroup MapMainParent;

    static final LatLng SEOUL = new LatLng(37.56, 126.97);
    GoogleMap map;
    String strAddress;
    List<Address> listAddress;
    Geocoder geocoder;
    Address AddrAddress;

    EditText getAdd;
    Button SearchButton;
    Button LocateButton;
    Button AddButton;
    Button MoreButton;

    MySQLiteOpenHelper helper;
    SQLiteDatabase db;

    public MapMainFragment() {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        DBLocationToMarker();// need for two place add methods
        switch(requestCode){
            case(1002):{ // delete marker activity call
                if(resultCode == Activity.RESULT_OK) {
                    // reload the markers
                    DBLocationToMarker();
                }
            }
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(MapMainView!=null) {
            /*
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction().add(R.id.content_frame,this);
            */
        /*
            ViewGroup parent = (ViewGroup) MapMainView.getParent();
            if (parent != null) {
                parent.removeView(MapMainView);
                //MapMainView = null; no meaning
            }
            */
        }
        if(MapMainView!=null){
            //ViewGroup parent = (ViewGroup)MapMainView.getParent();
            //parent.removeView(MapMainView); null pointer
            //container.removeView(MapMainView); The specified child already has a parent. You must call removeView() on the child's parent first.
            //container.addView(MapMainView);
        }

        try {
            MapMainView = inflater.inflate(R.layout.main, container, false);
            MapMainView.setBackgroundColor(Color.WHITE);

            SearchButton = (Button) MapMainView.findViewById(R.id.SearchButton);
            AddButton = (Button) MapMainView.findViewById(R.id.AddButton);
            LocateButton = (Button) MapMainView.findViewById(R.id.LocateButton);
            MoreButton = (Button) MapMainView.findViewById(R.id.MoreButton);

            Activity acti = getActivity();
            if (isAdded() && acti != null) {
                SearchButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SearchPlaceInGoogle(v);
                    }
                });
                AddButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {// getContext or getActivity before onActivityCreated() calls error
                        //startActivity(new Intent(getActivity(), AddPlaceDialogActivity.class));
                        startActivityForResult(new Intent(MapMainView.getContext(), AddPlaceDialogActivity.class), 1002);
                        //startActivity(new Intent(MapMainView.getContext(), AddPlaceDialogActivity.class));
                    }
                });
                LocateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MoveToMe();
                    }
                });
                MoreButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent moreintent = new Intent(MapMainView.getContext(), seemorebuttonActivity.class);
                        startActivity(moreintent);
                    }
                });
            }
        } catch (InflateException e) {
        }
        return MapMainView;
    }
/*
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        MapFragment fragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);
    }
*/
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainContext = getActivity().getApplicationContext();
        View mainView = getView();
        helper = new MySQLiteOpenHelper(getActivity(), "/mnt/sdcard/MyPlace.db", null, 1);
        /*
        FragmentManager fm = getChildFragmentManager();
        fragment = (MapFragment) fm.findFragmentById(R.id.map);
        if (fragment == null) {
            fragment = MapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, fragment).commit();
        }
        map = ((MapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();
        */
        map = ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map)).getMap();
        MapWrapperLayout mapWrapperLayout = (MapWrapperLayout)mainView.findViewById(R.id.map_relative_layout);

        initMapInteractive(mapWrapperLayout);

        getAdd = (EditText) mainView.findViewById(R.id.SearchInput);

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
                Addplacefrommaplongclickintent.putExtra("latitude", pickedpoint.latitude);
                Addplacefrommaplongclickintent.putExtra("longitude", pickedpoint.longitude);
                startActivityForResult(Addplacefrommaplongclickintent, 1002);
            }
        });
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker) {
                //onInfoEditButtonClicked(marker);
                return false;
            }
        });
        getAdd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(v.getId() == R.id.SearchInput && actionId== EditorInfo.IME_ACTION_SEARCH){
                    SearchButton.performClick();
                }
                return false;
            }
        });

    }

    @Override
    public void onDestroyView() {
        /*
        Fragment childfragment = getFragmentManager().findFragmentById(R.id.map);
        getFragmentManager().beginTransaction().remove(childfragment);
        getFragmentManager().beginTransaction().commit();
        */
        Toast.makeText(MapMainView.getContext(), "onDestroyView Called!", Toast.LENGTH_SHORT).show();

        if(MapMainView!=null){
            ViewGroup parent = (ViewGroup)MapMainView.getParent();
            MapMainParent = parent;
            if(parent!=null){
                parent.removeView(MapMainView);
            }
        }

        super.onDestroyView();

    }
/*
    @Override
    public void onResume(){
        super.onResume();
        DBLocationToMarker();
    }
*/
    private void SearchPlaceInGoogle(View v){
        // TODO Auto-generated method stub
        switch(v.getId()){ // 본 예제의 다음 활용을 위해 switch문으로 작성됨
            case R.id.SearchButton:
                strAddress = getAdd.getText().toString();
                // EditText를 통해 입력받은 주소를 getText()로 가져와 toString()으로 String전환 후 String 변수인 strAddress에 저장
                geocoder = new Geocoder(mainContext);
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
    private void MoveToMe() {

        int googlePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(mainContext);


        // 맵의 이동
        //mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));

        GpsInfo gps = new GpsInfo(mainContext);
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

    void DBLocationToMarker(){
        map.clear();
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
            v = (View) getActivity().getLayoutInflater().inflate(R.layout.infowindow, null); // null: do not attach it to any parent window
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
            mapWrapperLayout.init(map, getPixelsFromDp(mainContext, 39 + 20));

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
                    //TODO: mainContext should be changed to placeinfodialogue with editable
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
                        Log.d("onClickConfirmed", "onClickConfirmed Error!");
                    }
                    Intent editplaceintent = new Intent(mainContext,EditPlaceDialogActivity.class);

                    editplaceintent.putExtra("name",name);
                    editplaceintent.putExtra("menu",menu);
                    editplaceintent.putExtra("photo",photo);
                    editplaceintent.putExtra("rate",rate);
                    editplaceintent.putExtra("comment",comment);

                    //editplaceintent.putExtra("latitude", marker.getPosition().latitude);
                    //editplaceintent.putExtra("longitude", marker.getPosition().longitude);
                    editplaceintent.putExtra("lat",lat);
                    editplaceintent.putExtra("lng", lng);
                    getActivity().startActivity(editplaceintent);
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
                        //Toast.makeText(mainContext,"try", Toast.LENGTH_SHORT).show();
                        Intent deleteintent = new Intent(mainContext,DeletePlaceDialogActivity.class);
                        deleteintent.putExtra("name", marker.getTitle() + "");
                        //getActivity().startActivity(deleteintent);
                        //deleteintent.putExtra("name","하림가");
                        startActivityForResult(deleteintent, 1002);
                    }catch(Exception e){
                    }
                    //Toast.makeText(mainContext, "삭제 되었습니다.", Toast.LENGTH_SHORT).show();
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

                    int rate = 0;
                    String name="";
                    try {
                        Cursor c = db.rawQuery("SELECT id, name, menu, photo, rate, comment, lat, lng FROM MyPlace where name='" + marker.getTitle() + "';", null);
                        c.moveToNext();
                        int id = c.getInt(c.getColumnIndex("id"));
                        name = c.getString(c.getColumnIndex("name"));
                        String menu = c.getString(c.getColumnIndex("menu"));
                        String photo = c.getString(c.getColumnIndex("photo"));
                        rate = c.getInt(c.getColumnIndex("rate"));
                        String comment = c.getString(c.getColumnIndex("comment"));
                        double lat = c.getDouble(c.getColumnIndex("lat"));
                        double lng = c.getDouble(c.getColumnIndex("lng"));
                    } catch (Exception e) {}

                    stitle.setText(marker.getTitle());
                    smenu.setText(marker.getSnippet());
                    sscore.setText(String.valueOf(rate));

                    // We must call mainContext to set the current marker and infoWindow references
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
