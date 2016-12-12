package com.example.joo.mytaste;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Joo on 2016-10-06.
 */
public class PickPlaceActivity extends Activity {
    GoogleMap map;
    LatLng camerafocus;
    Button btnOk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.placepick);
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        btnOk = (Button) findViewById(R.id.btn_Ok);

        final LatLng Seoul = new LatLng(37.56, 126.97);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(Seoul, 15));
        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camerafocus = map.getCameraPosition().target;
                Intent resultIntent = new Intent();
                resultIntent.putExtra("Lat", camerafocus.latitude);
                resultIntent.putExtra("Lng", camerafocus.longitude);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });

        /*
        Intent resultIntent = new Intent();
        resultIntent.putExtra("Lat",37.05);
        resultIntent.putExtra("Lng",127.04);
        setResult(Activity.RESULT_OK, resultIntent);
        */

    }

}
