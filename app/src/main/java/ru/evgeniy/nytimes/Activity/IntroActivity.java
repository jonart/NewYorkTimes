package ru.evgeniy.nytimes.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ru.evgeniy.nytimes.App;
import ru.evgeniy.nytimes.R;
import ru.evgeniy.nytimes.data.SharedPref;
import ru.evgeniy.nytimes.news.NewsActivity;

public class IntroActivity extends AppCompatActivity {
    public static final int DELAY_TIME = 3;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    SharedPref Storage = App.getSharedPref();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_intro);
//        FragmentManager mFragmentManager = getSupportFragmentManager();
//
//        mFragmentManager.beginTransaction()
//                .replace(R.id.fragmentContainer, new IntroFragment().newInstance())
//                .addToBackStack(null)
//                .commit();


        if (Storage.needToShowIntro()) {
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
        startActivity(new Intent(this, NewsActivity.class));
        finish();
    }
}