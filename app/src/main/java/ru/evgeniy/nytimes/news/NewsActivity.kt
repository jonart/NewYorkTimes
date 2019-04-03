package ru.evgeniy.nytimes.news

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import ru.evgeniy.nytimes.Fragments.IntroFragment
import ru.evgeniy.nytimes.Fragments.NewsListFragment
import ru.evgeniy.nytimes.R
import ru.evgeniy.nytimes.data.db.NewsEntity

class NewsActivity : AppCompatActivity() {
    val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)



        fragmentManager.beginTransaction()
                .replace(R.id.news_container, NewsListFragment().newInstance())
                .addToBackStack(null)
                .commit()
    }

    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount == 0) {
            finish()
        } else {
            fragmentManager.popBackStack()
        }
    }
}
