package ru.evgeniy.androidacademy.news;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ru.evgeniy.androidacademy.App;
import ru.evgeniy.androidacademy.R;
import ru.evgeniy.androidacademy.data.db.NewsDao;
import ru.evgeniy.androidacademy.data.db.NewsEntity;

public class NewsDetailActivity extends AppCompatActivity {

    public static final String ID_NEWS = "ID_NEWS";
    Disposable mDisposable;
    int id;

    @BindView(R.id.iv_photo_news)
    ImageView mImageViewNews;
    @BindView(R.id.head_of_news)
    TextView mHeadTextView;
    @BindView(R.id.text_news)
    TextView mTextNews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        id = getIntent().getIntExtra(ID_NEWS, 0);


        loadNews(id);


//        WebView webView = findViewById(R.id.webView);
//        webView.loadUrl(getIntent().getStringExtra(URL));

    }

    private void loadNews(int id) {
        mDisposable = getNewsDao().getNewsById(id)
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsEntity -> {
                    Glide.with(mImageViewNews)
                            .load(newsEntity.getImageUrl())
                            .apply(RequestOptions.centerCropTransform())
                            .into(mImageViewNews);

                    mHeadTextView.setText(newsEntity.getTitle());
                    mTextNews.setText(newsEntity.getFullText());
                });
    }

    public NewsDao getNewsDao() {
        return ((App) getApplication()).getDatabase().getNewsDao();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                mDisposable = Completable.fromAction(() -> getNewsDao().deleteById(id))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::finish, Throwable::printStackTrace);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        mDisposable.dispose();
        super.onStop();
    }
}
