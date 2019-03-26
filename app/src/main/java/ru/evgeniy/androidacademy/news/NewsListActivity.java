package ru.evgeniy.androidacademy.news;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ru.evgeniy.androidacademy.App;
import ru.evgeniy.androidacademy.R;
import ru.evgeniy.androidacademy.data.Category;
import ru.evgeniy.androidacademy.data.db.NewsDao;
import ru.evgeniy.androidacademy.data.db.NewsEntity;

public class NewsListActivity extends AppCompatActivity implements MyClickListener {
    public static final String TAG = "MYTAG";
    private final int SPAN_COUNT = 2;
    private final int SPACING = 16;
    private Spinner mSpinner;
    private String nowCategory = "";
    private Disposable mDisposable;
    private List<NewsEntity> news;
    private NewsAdapter mAdapter = new NewsAdapter(NewsListActivity.this);


    @BindView(R.id.activity_news_recycler) RecyclerView mRecycler;
    @BindView(R.id.progress_bar) ProgressBar mProgressBar;
    @BindView(R.id.coordinator_layout_news) CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.fab) FloatingActionButton mFloatingActionButton;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        ButterKnife.bind(this);
        if (isVertical()) {
            mRecycler.setLayoutManager(new LinearLayoutManager(this));
        } else {
            mRecycler.setLayoutManager(new GridLayoutManager(this, SPAN_COUNT));
        }
        mRecycler.addItemDecoration(new ItemDecorator(SPACING));

        mFloatingActionButton.setOnClickListener(view -> loadData(nowCategory));
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mSwipeRefreshLayout.setRefreshing(false);
           reloadNews();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_category_news, menu);

        MenuItem item = menu.findItem(R.id.spinner_menu);
        mSpinner = (Spinner) item.getActionView();

        List<String> categoryList = new ArrayList<>();
        for (Category category : Category.values())
        {
            categoryList.add(category.serverValue());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,categoryList);
        mSpinner.setAdapter(adapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String name = adapterView.getItemAtPosition(position).toString();
                nowCategory = name.toLowerCase();
                Log.d(TAG, "onItemSelected: " + name);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        reloadNews();
        super.onResume();
    }

    @Override
    protected void onStop() {
        mDisposable.dispose();
        super.onStop();
    }

    @Override
    public void onItemClick(@NonNull NewsEntity item) {
        Intent intent = NewsDetailActivity.getStartIntent(this,item.getId());
        startActivity(intent);
        Log.d(TAG, "onCreate: click");
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public boolean isVertical() {
        int orientation = getResources().getConfiguration().orientation;
        return orientation != Configuration.ORIENTATION_LANDSCAPE;
    }

    @NonNull
    public NewsDao getNewsDao() {
        return App.getDatabase().getNewsDao();
    }

    public void reloadNews(){
        mDisposable = Single.fromCallable(()-> getNewsDao().getNews())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsEntities -> {
                    if (news != null) news.clear();
                    news = newsEntities;
                    showNews();
                });
    }

    public void loadData(@NonNull String category) {
        category = category.replace(" ", "");
        if (isOnline()) {
            showProgressBar(true);
            mDisposable = App.getRestApi()
                    .getNews(category)
                    .map(responseStory -> StoryMappers.map(responseStory.getResults()))
                    .subscribeOn(Schedulers.io())
                    .doOnSuccess(newsItems -> {
                        getNewsDao().deleteAllNews();
                        getNewsDao().insertAllNews(newsItems);
                        news = getNewsDao().getNews();
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorReturn(throwable -> {
                        news= getNewsDao().getNews();
                        return news;
                    })
                    .subscribe(responseStory -> {
                        showProgressBar(false);
                        showNews();
                    }, throwable -> showProgressBar(false));
        } else {
            Snackbar.make(mCoordinatorLayout, R.string.internet_connection, Snackbar.LENGTH_LONG).show();
        }
    }


    private void showProgressBar(boolean isTrue) {
        if (isTrue) mProgressBar.setVisibility(View.VISIBLE);
        else mProgressBar.setVisibility(View.INVISIBLE);
    }

    private void showNews() {
        mRecycler.setAdapter(mAdapter);
        mAdapter.addData(news);
        mProgressBar.setVisibility(View.GONE);
        mRecycler.setVisibility(View.VISIBLE);
    }
}
