package ru.evgeniy.nytimes.news

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.evgeniy.nytimes.Fragments.NewsListFragment
import ru.evgeniy.nytimes.R

class MainActivity : AppCompatActivity() {
    val mFragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val mFragment = NewsListFragment().newInstance()
        mFragmentManager.beginTransaction()
                .replace(R.id.news_container, mFragment)
                .addToBackStack(mFragment.tag)
                .commit()
    }

    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            fragmentManager.popBackStack()
        }
    }
}
