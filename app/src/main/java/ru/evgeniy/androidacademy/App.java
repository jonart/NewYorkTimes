package ru.evgeniy.androidacademy;

import android.app.Application;

import ru.evgeniy.androidacademy.data.SharedPref;
import ru.evgeniy.androidacademy.data.network.Api;
import ru.evgeniy.androidacademy.data.network.RestApi;

public class App extends Application {

    private static SharedPref mSharedPref;
    private static Api mApi;

    @Override
    public void onCreate() {
        super.onCreate();

        mSharedPref = new SharedPref(getApplicationContext());
        mApi =  RestApi.getInstance().getApi();
    }

    public static SharedPref getSharedPref() {
        return mSharedPref;
    }

    public static Api getRestApi() {
        return mApi;
    }
}
