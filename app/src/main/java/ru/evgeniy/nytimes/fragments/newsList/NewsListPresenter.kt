package ru.evgeniy.nytimes.fragments.newsList

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.evgeniy.nytimes.App
import ru.evgeniy.nytimes.data.db.NewsDao
import ru.evgeniy.nytimes.data.db.NewsEntity
import ru.evgeniy.nytimes.news.StoryMappers

@InjectViewState
class NewsListPresenter: MvpPresenter<NewsListView>() {
    private var news: MutableList<NewsEntity>? = null
    var disposable = CompositeDisposable()

    fun getNewsFromInternet(category: String) {
        if (true) {
            viewState.showProgressBar(true)
            disposable.add(App.restApi
                    .getNews(category.replace(" ", ""))
                    .map { responseStory -> responseStory.results?.let { StoryMappers.map(it) } }
                    .subscribeOn(Schedulers.io())
                    .doOnSuccess { newsItems ->
                        getNewsDao().deleteAllNews()
                        newsItems?.let { getNewsDao().insertAllNews(it) }
                        news = getNewsDao().news
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorReturn {
                        news = getNewsDao().news
                        news
                    }
                    .subscribe({
                        viewState.showProgressBar(false)
                        news?.let { viewState.showNews(it) }
                    }, { viewState.showProgressBar(false) }))

        } else {
            viewState.showMessage("")
        }
    }

    fun getNewsFromDb() {
        disposable.add(Single.fromCallable { getNewsDao().news }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { newsEntities ->
                    news?.let { niceNews ->
                        when(niceNews.isEmpty()){
                            true -> {news?.clear()}
                            false -> news = newsEntities
                        }
                        news?.let { viewState.showNews(it) }
                    }
                })
    }

//    private fun isOnline(): Boolean {
//        val connectivityManager = requereContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
//        val networkInfo = connectivityManager!!.activeNetworkInfo
//        return networkInfo != null && networkInfo.isConnected
//    }

    private fun getNewsDao(): NewsDao {
        return App.database.newsDao
    }
}