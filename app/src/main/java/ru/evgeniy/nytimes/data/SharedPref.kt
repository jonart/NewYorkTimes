package ru.evgeniy.nytimes.data

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context) {
    private val SHARED_PREF_NAME = "MY_SHARED_PREF"
    private val FIRST_LAUNCH = "FIRST_LAUNCH"
    private val mSharedPreferences: SharedPreferences

    init {
        mSharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }


    fun needToShowIntro(): Boolean {
        val start = mSharedPreferences.getBoolean(FIRST_LAUNCH, true)
        return if (start) {
            mSharedPreferences.edit().putBoolean(FIRST_LAUNCH, false).apply()
            true
        } else {
            mSharedPreferences.edit().putBoolean(FIRST_LAUNCH, true).apply()
            false
        }
    }
}
