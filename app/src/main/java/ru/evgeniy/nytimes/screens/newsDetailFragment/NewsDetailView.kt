package ru.evgeniy.nytimes.screens.newsDetailFragment

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.evgeniy.nytimes.data.db.NewsEntity

interface NewsDetailView:MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class )
    fun showNews(newsEntity: NewsEntity)
    @StateStrategyType(AddToEndSingleStrategy::class )
    fun closeFragment()
}