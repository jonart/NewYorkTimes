package ru.evgeniy.androidacademy.data;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    public static final String SHARED_PREF_NAME = "MY_SHARED_PREF";
    public static final String FIRST_LAUNCH = "FIRST_LAUNCH";
    SharedPreferences mSharedPreferences;

    public SharedPref(Context context) {
        mSharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }


    public boolean needToShowIntro() {
        boolean start = mSharedPreferences.getBoolean(FIRST_LAUNCH, false);
        if (start){
            return true;
        }
        else {
            mSharedPreferences.edit().putBoolean(FIRST_LAUNCH, true).commit();
            return false;
        }
    }
}
