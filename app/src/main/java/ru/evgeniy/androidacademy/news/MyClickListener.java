package ru.evgeniy.androidacademy.news;

import android.support.annotation.NonNull;

import ru.evgeniy.androidacademy.data.db.NewsEntity;

public interface MyClickListener {
    void onItemClick(@NonNull NewsEntity item);
}
