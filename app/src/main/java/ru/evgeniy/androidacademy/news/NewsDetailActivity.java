package ru.evgeniy.androidacademy.news;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.evgeniy.androidacademy.App;
import ru.evgeniy.androidacademy.R;
import ru.evgeniy.androidacademy.data.db.NewsDao;

public class NewsDetailActivity extends AppCompatActivity {

    public static final String ID_NEWS = "ID_NEWS";
    private Disposable mDisposable;
    private int id;

    @BindView(R.id.iv_photo_news) ImageView mImageViewNews;
    @BindView(R.id.head_of_news) TextView mHeadTextView;
    @BindView(R.id.text_news) TextView mTextNews;

    @NonNull
    public static Intent getStartIntent(Context context, int newId) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(NewsDetailActivity.ID_NEWS, newId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        id = getIntent().getIntExtra(ID_NEWS, 0);
        loadNews(id);
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

    @NonNull
    public NewsDao getNewsDao() {
        return ((App) getApplication()).getDatabase().getNewsDao();
    }

    private void loadNews(int id) {
        mDisposable = getNewsDao().getNewsById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsEntity -> {
                    Glide.with(mImageViewNews)
                            .load(newsEntity.getImageUrl())
                            .apply(RequestOptions.centerCropTransform())
                            .into(mImageViewNews);
                    mHeadTextView.setText(newsEntity.getTitle());
                    mTextNews.setText(newsEntity.getFullText());
                }, Throwable::printStackTrace);
    }
}
