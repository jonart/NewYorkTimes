package ru.evgeniy.nytimes.fragments.newsDetailFragment

import com.arellomobile.mvp.MvpView
import ru.evgeniy.nytimes.data.db.NewsEntity

interface NewsDetailView:MvpView {
    fun showNews(newsEntity: NewsEntity)
    fun popUp()
}