package ru.evgeniy.nytimes.screens.newsDetailFragment

import com.arellomobile.mvp.InjectViewState
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.evgeniy.nytimes.App
import ru.evgeniy.nytimes.data.db.NewsDao
import ru.evgeniy.nytimes.screens.BasePresenter

@InjectViewState
class NewsDetailPresenter: BasePresenter<NewsDetailView>() {

    private fun getNewsDao(): NewsDao {
        return App.database.newsDao
    }

    private fun deleteNews(newsId:Int){
        disposable.add(Completable.fromAction { getNewsDao().deleteById(newsId) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({viewState.closeFragment()}, { it.printStackTrace() }))
    }

    fun loadNewsFromDb(newsId:Int) {
        disposable.add(getNewsDao().getNewsById(newsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { newsEntity ->
                    viewState.showNews(newsEntity)
                }
                .subscribe())
    }

    fun clickOnDelete(newsId:Int){
        deleteNews(newsId)
    }
}