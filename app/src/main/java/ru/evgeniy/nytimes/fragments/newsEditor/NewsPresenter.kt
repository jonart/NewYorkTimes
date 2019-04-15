package ru.evgeniy.nytimes.fragments.newsEditor

import com.arellomobile.mvp.InjectViewState
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.evgeniy.nytimes.App
import ru.evgeniy.nytimes.data.db.NewsDao
import ru.evgeniy.nytimes.fragments.BasePresenter

@InjectViewState
class NewsPresenter: BasePresenter<NewsView>() {


    private fun saveNews(id:Int, title:String, fullText:String){
        disposable.add(Completable.fromAction { getNewsDao().updateNewsById(id, title, fullText) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { viewState.result() })
    }

    private fun getNewsDao(): NewsDao = App.database.newsDao

    fun loadNews(id:Int){
        disposable.add(getNewsDao().getNewsById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { newsEntity ->
                    newsEntity?.let { viewState.showNews(it) }
                })
    }

    fun clickSaveNews(id:Int, title:String, fullText:String){
      saveNews(id,title,fullText)
    }
}