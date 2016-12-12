package com.example.joo.mytaste.NavigationFrames;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.joo.mytaste.R;

/**
 * Created by Joo on 2016-10-23.
 */
public class PostFramgment extends Fragment {
    WebView wv;
    String urls = "http://192.168.0.4:8181/AutumnToWinter/list.cdo";

    public PostFramgment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View PostView = inflater.inflate(R.layout.webview, container, false);
        wv = (WebView)PostView.findViewById(R.id.webview);
        wv.loadUrl(urls);
        return PostView;
    }
}
