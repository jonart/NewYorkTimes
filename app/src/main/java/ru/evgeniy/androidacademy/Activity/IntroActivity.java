package ru.evgeniy.androidacademy.Activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ru.evgeniy.androidacademy.App;
import ru.evgeniy.androidacademy.R;
import ru.evgeniy.androidacademy.data.SharedPref;
import ru.evgeniy.androidacademy.news.NewsListActivity;

public class IntroActivity extends AppCompatActivity {
    public static final int DELAY_TIME = 3;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    SharedPref Storage = App.getSharedPref();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Storage.needToShowIntro()) {
            setContentView(R.layout.activity_intro);
            Disposable disposable = Completable.complete()
                    .delay(DELAY_TIME, TimeUnit.SECONDS)
                    .subscribe(this::startSecondActivity);
            mCompositeDisposable.add(disposable);
        } else {
            startSecondActivity();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCompositeDisposable.dispose();
    }

    private void startSecondActivity() {
        startActivity(new Intent(this, NewsListActivity.class));
        finish();
    }
}