package ru.evgeniy.androidacademy.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllNews(List<NewsEntity> news);
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNews(NewsEntity newsEntity);

    @Query("SELECT * FROM news")
    List<NewsEntity> getNews();

    @Query("SELECT * FROM news WHERE id = :id")
    Flowable<NewsEntity> getNewsById(int id);

    @Query("DELETE FROM news")
    void deleteAllNews();

    @Query("DELETE FROM news WHERE id = :id")
    void deleteById(int id);

}
