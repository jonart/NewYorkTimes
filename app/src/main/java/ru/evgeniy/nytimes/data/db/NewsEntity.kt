package ru.evgeniy.nytimes.data.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

import java.io.Serializable

import io.reactivex.annotations.NonNull

@Entity(tableName = "news")
class NewsEntity(@param:NonNull
                 @field:ColumnInfo(name = "title")
                 @get:NonNull
                 var title: String?,
                 @param:NonNull
                 @field:ColumnInfo(name = "imageUrl")
                 @get:NonNull
                 var imageUrl: String?,
                 @param:NonNull
                 @field:ColumnInfo(name = "category")
                 @get:NonNull
                 var category: String?,
                 @param:NonNull
                 @field:ColumnInfo(name = "publishDate")
                 @get:NonNull
                 var publishDate: String?,
                 @param:NonNull
                 @field:ColumnInfo(name = "previewText")
                 @get:NonNull
                 var previewText: String?,
                 @param:NonNull
                 @field:ColumnInfo(name = "fullText")
                 @get:NonNull
                 var fullText: String?,
                 @param:NonNull
                 @field:ColumnInfo(name = "url")
                 @get:NonNull
                 var url: String?) : Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}

