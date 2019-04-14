package ru.evgeniy.nytimes.fragments.newsDetailFragment

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.evgeniy.nytimes.App
import ru.evgeniy.nytimes.data.db.NewsDao

@InjectViewState
class NewsDetailPresenter: MvpPresenter<NewsDetailView>() {
    var disposable = CompositeDisposable()


    fun loadNewsFromDb(newsId:Int) {
        disposable.add(getNewsDao().getNewsById(newsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { newsEntity ->
                    viewState.showNews(newsEntity)
                }
                .subscribe())
    }

    fun deleteNews(newsId:Int){
        disposable.add(Completable.fromAction { getNewsDao().deleteById(newsId) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({viewState.popUp()}, { it.printStackTrace() }))
    }

    private fun getNewsDao(): NewsDao {
        return App.database.newsDao
    }
}