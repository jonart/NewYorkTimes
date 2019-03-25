package ru.evgeniy.androidacademy.data.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "news")
public class NewsEntity implements Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "imageUrl")
    private String imageUrl;

    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "publishDate")
    private String publishDate;

    @ColumnInfo(name = "previewText")
    private String previewText;

    @ColumnInfo(name = "fullText")
    private String fullText;

    @ColumnInfo(name = "url")
    private String url;

    public NewsEntity(@NonNull String title, @NonNull String imageUrl, @NonNull String category, @NonNull String publishDate,@NonNull  String previewText,@NonNull  String fullText, @NonNull String url) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.category = category;
        this.publishDate = publishDate;
        this.previewText = previewText;
        this.fullText = fullText;
        this.url = url;
    }

    public int getId(){return id;}

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public String getImageUrl() {
        return imageUrl;
    }

    @NonNull
    public String getCategory() {
        return category;
    }

    @NonNull
    public String getPublishDate() {
        return publishDate;
    }

    @NonNull
    public String getPreviewText() {
        return previewText;
    }

    @NonNull
    public String getFullText() {
        return fullText;
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public void setImageUrl(@NonNull String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCategory(@NonNull String category) {
        this.category = category;
    }

    public void setPublishDate(@NonNull String publishDate) {
        this.publishDate = publishDate;
    }

    public void setPreviewText(@NonNull String previewText) {
        this.previewText = previewText;
    }

    public void setFullText(@NonNull String fullText) {
        this.fullText = fullText;
    }

    public void setUrl(@NonNull String url) {
        this.url = url;
    }
}

