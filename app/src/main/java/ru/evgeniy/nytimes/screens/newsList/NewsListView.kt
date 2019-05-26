package ru.evgeniy.nytimes.screens.newsList

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.evgeniy.nytimes.data.db.NewsEntity

interface NewsListView: MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class )
    fun showNews(news:MutableList<NewsEntity>)
    @StateStrategyType(AddToEndSingleStrategy::class )
    fun showProgressBar(isTrue: Boolean)
    @StateStrategyType(AddToEndSingleStrategy::class )
    fun showMessage(message:String)
}