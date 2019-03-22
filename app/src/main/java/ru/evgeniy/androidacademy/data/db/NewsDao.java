package ru.evgeniy.androidacademy.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllNews(List<NewsEntity> news);
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNews(NewsEntity newsEntity);

    @Query("SELECT * FROM news")
    List<NewsEntity> getNews();

    @Query("SELECT * FROM news WHERE id = :id")
    List<NewsEntity> getNewsById(int id);
}
