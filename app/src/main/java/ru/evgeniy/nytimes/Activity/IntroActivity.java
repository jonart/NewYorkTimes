package ru.evgeniy.nytimes.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import me.relex.circleindicator.CircleIndicator;
import ru.evgeniy.nytimes.App;
import ru.evgeniy.nytimes.Fragments.IntroFragment1;
import ru.evgeniy.nytimes.Fragments.IntroFragment2;
import ru.evgeniy.nytimes.Fragments.IntroFragment3;
import ru.evgeniy.nytimes.R;
import ru.evgeniy.nytimes.data.SharedPref;
import ru.evgeniy.nytimes.news.MainActivity;

public class IntroActivity extends AppCompatActivity {
    public static final int DELAY_TIME = 3;
    final int PAGE_COUNT = 3;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    CircleIndicator mCircleIndicator;

    SharedPref Storage = App.getSharedPref();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_intro);
        mCircleIndicator = findViewById(R.id.indicator);

        ViewPager viewpager = findViewById(R.id.viewPager);
        viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {

                Fragment fragment;
                switch (i){
                    case 0: fragment = new IntroFragment1().newInstance();break;
                    case 1: fragment = new IntroFragment2().newInstance();break;
                    default:  fragment = new IntroFragment3().newInstance();break;
                }
                return fragment;
            }

            @Override
            public int getCount() {
                return PAGE_COUNT;
            }
        });
        mCircleIndicator.setViewPager(viewpager);

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
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}