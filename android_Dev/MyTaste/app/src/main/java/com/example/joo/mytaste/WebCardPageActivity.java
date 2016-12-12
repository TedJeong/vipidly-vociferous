package com.example.joo.mytaste;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Joo on 2016-10-07.
 */
public class WebCardPageActivity extends Activity {
    WebView wv;
    Button gobutton;
    TextView tvURL;
    String urls = "http://192.168.0.4:8181/AutumnToWinter/list.cdo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        //gobutton = (Button)findViewById(R.id.gobutton);
        wv = (WebView)findViewById(R.id.webview);
        //tvURL = (TextView)findViewById(R.id.txtURL);

        wv.getSettings().getJavaScriptEnabled();
        goURL(wv);
        /*
        gobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goURL(wv);
            }
        });
        */
    }

    public void goURL(View view){
        //String url = tvURL.getText().toString();
        //Log.i("URL", "Opening URL with WebView :" + url);

        final long startTime = System.currentTimeMillis();
        WebView webView = (WebView)findViewById(R.id.webview);

        // 하드웨어 가속
        // 캐쉬 끄기
        //webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                long elapsedTime = System.currentTimeMillis()-startTime;
                TextView tvSec = (TextView) findViewById(R.id.tvSec);
                tvSec.setText(String.valueOf(elapsedTime));
            }
        });
        //webView.loadUrl(url);
        webView.loadUrl(urls);
    }
}
