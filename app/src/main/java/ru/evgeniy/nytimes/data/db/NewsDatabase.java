package ru.evgeniy.nytimes.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {NewsEntity.class}, version = 1,  exportSchema = false)
public abstract class NewsDatabase extends RoomDatabase {
public abstract NewsDao getNewsDao();
}
