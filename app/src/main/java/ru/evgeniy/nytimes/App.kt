package ru.evgeniy.nytimes

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context

import com.facebook.stetho.Stetho

import ru.evgeniy.nytimes.data.SharedPref
import ru.evgeniy.nytimes.data.db.NewsDatabase
import ru.evgeniy.nytimes.data.network.Api
import ru.evgeniy.nytimes.data.network.RestApi

class App : Application() {

    companion object {
        lateinit var sharedPref: SharedPref
        lateinit var restApi: Api
        lateinit var database: NewsDatabase
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)

        sharedPref = SharedPref(applicationContext)
        restApi = RestApi.instance.api
        database = Room.databaseBuilder(applicationContext, NewsDatabase::class.java, "news_database.db")
                .fallbackToDestructiveMigration()
                .build()
        context = applicationContext
    }
}
