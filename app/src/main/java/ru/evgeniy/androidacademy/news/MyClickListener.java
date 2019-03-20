package ru.evgeniy.androidacademy.news;

import ru.evgeniy.androidacademy.data.db.NewsItem;

public interface MyClickListener {
    void onItemClick(NewsItem item);
}
