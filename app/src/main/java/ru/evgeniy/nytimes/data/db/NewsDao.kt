package ru.evgeniy.nytimes.data.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

import io.reactivex.Single

@Dao
interface NewsDao {

    @get:Query("SELECT * FROM news")
    val news: MutableList<NewsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllNews(news: List<NewsEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(newsEntity: NewsEntity)

    @Query("UPDATE news SET title = :title, fullText=:fullText WHERE id = :id")
    fun updateNewsById(id: Int, title: String, fullText: String)

    @Query("SELECT * FROM news WHERE id = :id")
    fun getNewsById(id: Int): Single<NewsEntity>

    @Query("DELETE FROM news")
    fun deleteAllNews()

    @Query("DELETE FROM news WHERE id = :id")
    fun deleteById(id: Int)

}
