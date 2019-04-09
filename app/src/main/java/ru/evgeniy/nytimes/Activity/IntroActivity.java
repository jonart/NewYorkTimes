package ru.evgeniy.nytimes.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import me.relex.circleindicator.CircleIndicator;
import ru.evgeniy.nytimes.App;
import ru.evgeniy.nytimes.fragments.IntroFragment;
import ru.evgeniy.nytimes.R;
import ru.evgeniy.nytimes.data.SharedPref;
import ru.evgeniy.nytimes.news.MainActivity;

public class IntroActivity extends AppCompatActivity {
    private static final int DELAY_TIME = 60;
    private static final int PAGE_COUNT = 3;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    SharedPref Storage = App.getSharedPref();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_intro);
        CircleIndicator circleIndicator = findViewById(R.id.indicator);

        ViewPager viewpager = findViewById(R.id.viewPager);
        viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {

                Fragment fragment;
                switch (i){
                    case 0: fragment = new IntroFragment().newInstance(1);break;
                    case 1: fragment = new IntroFragment().newInstance(3);break;
                    default:  fragment = new IntroFragment().newInstance(2);break;
                }
                return fragment;
            }

            @Override
            public int getCount() {
                return PAGE_COUNT;
            }
        });
        circleIndicator.setViewPager(viewpager);

        if (Storage.needToShowIntro()) {
            Disposable disposable = Completable.complete()
                    .delay(DELAY_TIME, TimeUnit.SECONDS)
                    .subscribe(this::startMainActivity);
            compositeDisposable.add(disposable);
        } else {
            startMainActivity();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        compositeDisposable.dispose();
    }

    private void startMainActivity() {
        startActivity(MainActivity.Companion.create(this));
        finish();
    }
}