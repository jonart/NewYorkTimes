package ru.evgeniy.androidacademy.news;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import ru.evgeniy.androidacademy.App;
import ru.evgeniy.androidacademy.R;
import ru.evgeniy.androidacademy.data.db.NewsDao;
import ru.evgeniy.androidacademy.data.db.NewsItem;

public class NewsListActivity extends AppCompatActivity implements MyClickListener {
    private final int SPAN_COUNT = 2;
    private final int SPACING = 16;
    private final String DEFAULT_CATEGORY = "Home";


    @BindView(R.id.activity_news_recycler) RecyclerView mRecycler;
    @BindView(R.id.progress_bar) ProgressBar mProgressBar;
    @BindView(R.id.ll_news_list) LinearLayout mLinearLayout;

    private Disposable mDisposable;
    public static final String TAG = "MYTAG";
    List<NewsItem> news = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        ButterKnife.bind(this);
        initCategory();

        if (isVertical()) {
            mRecycler.setLayoutManager(new LinearLayoutManager(this));

        } else {
            mRecycler.setLayoutManager(new GridLayoutManager(this, SPAN_COUNT));
        }

        mRecycler.addItemDecoration(new ItemDecorator(SPACING));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_category_news, menu);

        MenuItem item = menu.findItem(R.id.spinner_menu);
        Spinner spinner = (Spinner) item.getActionView();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_array, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

//        spinner.setAdapter(new ArrayAdapter<Category>(this, R.layout.support_simple_spinner_dropdown_item, Category.values()));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String name = adapterView.getItemAtPosition(position).toString();
                loadData(name.toLowerCase());
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
    private void initCategory(){
        loadData(DEFAULT_CATEGORY.toLowerCase());
    }

    public boolean isOnline(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public boolean isVertical() {
        int orientation = getResources().getConfiguration().orientation;
        return orientation != Configuration.ORIENTATION_LANDSCAPE;
    }

    public void loadData(String category){
        category = category.replace(" ", "");
        if (isOnline()) {
            showProgressBar(true);
            mDisposable = App.getRestApi()
                    .get(category)
                    .map(responseStory -> StoryMappers.map(responseStory.getResults()))
                    .subscribeOn(Schedulers.io())
                    .doOnSuccess(newsItems -> getNewsDao().insertNewss(newsItems))
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorReturn(throwable -> {
                        news = getNewsDao().getNews();
                        return news;
                    })
                    .doFinally(() -> showNews(NewsListActivity.this))
                    .subscribe(responseStory -> {
                        news = responseStory;
                        showProgressBar(false);
                        //showNews(this);
                    }, throwable -> showProgressBar(false));
        }
        else {
            Snackbar.make(mLinearLayout, R.string.internet_connection ,Snackbar.LENGTH_LONG).show();
        }
    }


    private void showProgressBar(boolean isTrue){
        if (isTrue) mProgressBar.setVisibility(View.VISIBLE);
        else mProgressBar.setVisibility(View.INVISIBLE);
    }

    private void showNews(MyClickListener myClickListener){
        NewsAdapter mAdapter = new NewsAdapter(myClickListener);
        mRecycler.setAdapter(mAdapter);
        mAdapter.setItems(news);
        mProgressBar.setVisibility(View.GONE);
        mRecycler.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(NewsItem item) {
        //Intent intent = NewsDetailActivity.getStartIntent(this);
        Intent intent = new Intent(this, NewsDetailActivity.class);
        intent.putExtra(NewsDetailActivity.URL, item.getUrl());
        startActivity(intent);
        Log.d(TAG, "onCreate: click");
    }

    public NewsDao getNewsDao(){
    return ((App)getApplication()).getDatabase().getNewsDao();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mDisposable.dispose();
    }
}
