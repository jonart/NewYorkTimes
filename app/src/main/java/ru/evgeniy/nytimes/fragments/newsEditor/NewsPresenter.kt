package ru.evgeniy.nytimes.fragments.newsEditor

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.evgeniy.nytimes.App
import ru.evgeniy.nytimes.data.db.NewsDao

@InjectViewState
class NewsPresenter: MvpPresenter<NewsView>() {

    var compositeDisposable : CompositeDisposable = CompositeDisposable()

    fun loadNews(id:Int){
        compositeDisposable.add(getNewsDao().getNewsById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { newsEntity ->
                    newsEntity?.let { viewState.showNews(it) }
                })
    }

    fun saveNews(id:Int, title:String, fullText:String){
        compositeDisposable.add(Completable.fromAction { getNewsDao().updateNewsById(id, title, fullText) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { viewState.result() })
    }

    private fun getNewsDao(): NewsDao = App.database.newsDao
}