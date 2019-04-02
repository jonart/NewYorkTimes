package ru.evgeniy.nytimes.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllNews(List<NewsEntity> news);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNews(NewsEntity newsEntity);

    @Query("UPDATE news SET title = :title, fullText=:fullText WHERE id = :id")
    void updateNewsById(int id, String title, String fullText);

    @Query("SELECT * FROM news")
    List<NewsEntity> getNews();

    @Query("SELECT * FROM news WHERE id = :id")
    Single<NewsEntity> getNewsById(int id);

    @Query("DELETE FROM news")
    void deleteAllNews();

    @Query("DELETE FROM news WHERE id = :id")
    void deleteById(int id);

}
