package ru.evgeniy.nytimes.news;

import android.support.annotation.NonNull;

import ru.evgeniy.nytimes.data.db.NewsEntity;

public interface MyClickListener {
    void onItemClick(@NonNull NewsEntity item);
}
