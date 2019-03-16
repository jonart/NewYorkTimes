package ru.evgeniy.androidacademy.news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import ru.evgeniy.androidacademy.R;

public class NewsDetailActivity extends AppCompatActivity {

    public static final String URL = "URL";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        WebView webView = findViewById(R.id.webView);
//        WebSettings webSettings = mWebView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(getIntent().getStringExtra(URL));

    }
}
