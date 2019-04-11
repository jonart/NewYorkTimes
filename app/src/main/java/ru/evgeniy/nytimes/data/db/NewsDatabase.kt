package ru.evgeniy.nytimes.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [NewsEntity::class], version = 2, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract val newsDao: NewsDao
}
