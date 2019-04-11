package ru.evgeniy.nytimes.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity

import java.util.concurrent.TimeUnit

import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import me.relex.circleindicator.CircleIndicator
import ru.evgeniy.nytimes.App
import ru.evgeniy.nytimes.fragments.IntroFragment
import ru.evgeniy.nytimes.R
import ru.evgeniy.nytimes.news.MainActivity

class IntroActivity : AppCompatActivity() {

    companion object {
        private const val DELAY_TIME = 3
        private const val PAGE_COUNT = 3
    }

    private val compositeDisposable = CompositeDisposable()
    private var storage = App.sharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_intro)
        val circleIndicator = findViewById<CircleIndicator>(R.id.indicator)

        val viewpager = findViewById<ViewPager>(R.id.viewPager)
        viewpager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(i: Int): Fragment {

                return when (i) {
                    0 -> IntroFragment().newInstance(R.drawable.device)
                    1 -> IntroFragment().newInstance(R.drawable.device_read)
                    else -> IntroFragment().newInstance(R.drawable.device_edit)
                }
            }

            override fun getCount(): Int {
                return PAGE_COUNT
            }
        }
        circleIndicator.setViewPager(viewpager)

        if (storage.needToShowIntro()) {
            val disposable = Completable.complete()
                    .delay(DELAY_TIME.toLong(), TimeUnit.SECONDS)
                    .subscribe { this.startMainActivity() }
            compositeDisposable.add(disposable)
        } else {
            startMainActivity()
        }
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    private fun startMainActivity() {
        startActivity(MainActivity.create(this))
        finish()
    }
}