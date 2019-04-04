package ru.evgeniy.nytimes.news

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.evgeniy.nytimes.Fragments.NewsListFragment
import ru.evgeniy.nytimes.R

class MainActivity : AppCompatActivity() {
    val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



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
