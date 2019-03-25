package ru.evgeniy.androidacademy;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.facebook.stetho.Stetho;

import ru.evgeniy.androidacademy.data.SharedPref;
import ru.evgeniy.androidacademy.data.db.NewsDatabase;
import ru.evgeniy.androidacademy.data.network.Api;
import ru.evgeniy.androidacademy.data.network.RestApi;

public class App extends Application {

    private static SharedPref mSharedPref;
    private static Api mApi;
    private static NewsDatabase mDatabase;

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);

        mSharedPref = new SharedPref(getApplicationContext());
        mApi =  RestApi.getInstance().getApi();
        mDatabase = Room.databaseBuilder(getApplicationContext(), NewsDatabase.class, "news_database.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    public static SharedPref getSharedPref() {
        return mSharedPref;
    }

    public static Api getRestApi() {
        return mApi;
    }

    public static NewsDatabase getDatabase(){
        return mDatabase;
    }
}
