package com.example.joo.mytaste.NavigationFrames;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.joo.mytaste.R;

/**
 * Created by anupamchugh on 10/12/15.
 */
public class UserFragment extends Fragment {
    WebView wv;
    String urls = "http://192.168.0.4:8181/AutumnToWinter/login.html";

    public UserFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View UserView = inflater.inflate(R.layout.webview, container, false);
        wv = (WebView)UserView.findViewById(R.id.webview);

        wv.getSettings().getJavaScriptEnabled();
        wv.loadUrl(urls);
        return UserView;
    }
}
