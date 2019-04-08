package ru.evgeniy.nytimes.news;

import android.support.annotation.NonNull;

import ru.evgeniy.nytimes.data.db.NewsEntity;

public interface NewsClickListener {
    void onItemClick(@NonNull NewsEntity item);
}
