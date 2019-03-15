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

    private WebView mWebView;

//    public static final String KEY_ITEM_FULL_TEXT = "ITEM_FULL_TEXT";
//    public static final String KEY_ITEM_CATEGORY = "ITEM_CATEGORY";
//    public static final String KEY_IMAGE_URL = "KEY_IMAGE_URL";
//    public static final String KEY_ITEM_PUBLISH_DATE = "ITEM_PUBLISH_DATE";
//    public static final String KEY_ITEM_TITLE = "ITEM_TITLE";

//    private ImageView mImageView;
//    private TextView mText;
//    private TextView mTitle;
//    private TextView mDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        mWebView = findViewById(R.id.webView);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl(getIntent().getStringExtra(URL));


//        mText = findViewById(R.id.item_detail_news_text);
//        mImageView = findViewById(R.id.item_detail_news_image);
//        mTitle = findViewById(R.id.item_detail_news_title);
//        mDate = findViewById(R.id.item_detail_news_date);
//
//        mText.setText(getIntent().getStringExtra(KEY_ITEM_FULL_TEXT));
//        mDate.setText(getIntent().getStringExtra(KEY_ITEM_PUBLISH_DATE));
//        mTitle.setText(getIntent().getStringExtra(KEY_ITEM_TITLE));
//        getSupportActionBar().setTitle(getIntent().getStringExtra(KEY_ITEM_CATEGORY));
//
//        Glide.with(mImageView)
//                .load(getIntent().getStringExtra(KEY_IMAGE_URL))
//                .apply(RequestOptions.centerCropTransform())
//                .into(mImageView);
    }
}
