package ru.evgeniy.nytimes.screens.newsEditor

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.evgeniy.nytimes.data.db.NewsEntity

interface NewsView: MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class )
    fun showNews(newsEntity: NewsEntity)
    @StateStrategyType(AddToEndSingleStrategy::class )
    fun result()
}