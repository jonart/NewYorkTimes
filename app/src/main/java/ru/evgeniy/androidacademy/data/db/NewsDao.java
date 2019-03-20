package ru.evgeniy.androidacademy.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface NewsDao {

    @Insert
    void insertNewss(List<NewsItem> news);

    @Query("SELECT * from news")
    List<NewsItem> getNews();
}
