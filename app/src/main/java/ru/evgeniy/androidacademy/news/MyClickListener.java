package ru.evgeniy.androidacademy.news;

import ru.evgeniy.androidacademy.data.db.NewsEntity;

public interface MyClickListener {
    void onItemClick(NewsEntity item);
}
