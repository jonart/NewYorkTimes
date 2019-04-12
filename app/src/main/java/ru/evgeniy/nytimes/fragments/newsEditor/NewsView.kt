package ru.evgeniy.nytimes.fragments.newsEditor

import com.arellomobile.mvp.MvpView
import ru.evgeniy.nytimes.data.db.NewsEntity

interface NewsView: MvpView {
    fun showNews(newsEntity: NewsEntity)
    fun result()
}