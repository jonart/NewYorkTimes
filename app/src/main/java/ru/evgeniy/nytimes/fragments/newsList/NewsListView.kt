package ru.evgeniy.nytimes.fragments.newsList

import com.arellomobile.mvp.MvpView
import ru.evgeniy.nytimes.data.db.NewsEntity

interface NewsListView: MvpView {

    fun showNews(news:MutableList<NewsEntity>)
    fun showProgressBar(isTrue: Boolean)
    fun showMessage(message:String)
}