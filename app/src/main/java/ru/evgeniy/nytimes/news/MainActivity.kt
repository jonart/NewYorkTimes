package ru.evgeniy.nytimes.news

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.evgeniy.nytimes.fragments.NewsListFragment
import ru.evgeniy.nytimes.R

class MainActivity : AppCompatActivity() {
    companion object {
        fun create(context:Context):Intent = Intent(context, MainActivity::class.java)
    }

    private val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val mFragment = NewsListFragment().newInstance()
        fragmentManager.beginTransaction()
                .replace(R.id.news_container, mFragment)
                .addToBackStack(mFragment.tag)
                .commit()
    }

    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount <= 1) {
            finish()
            return
        } else {
            fragmentManager.popBackStack()
        }
    }
}
