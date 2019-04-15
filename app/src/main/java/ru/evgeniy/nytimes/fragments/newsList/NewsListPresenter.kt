package ru.evgeniy.nytimes.fragments.newsList

import android.content.Context
import android.net.ConnectivityManager
import com.arellomobile.mvp.InjectViewState
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.evgeniy.nytimes.App
import ru.evgeniy.nytimes.data.db.NewsDao
import ru.evgeniy.nytimes.data.db.NewsEntity
import ru.evgeniy.nytimes.fragments.BasePresenter
import ru.evgeniy.nytimes.news.StoryMappers

@InjectViewState
class NewsListPresenter : BasePresenter<NewsListView>() {

    companion object{
        private var news: MutableList<NewsEntity>? = null
        private var currentCategory: String = ""
    }


    private fun isOnline(): Boolean {
        val connectivityManager = App.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val networkInfo = connectivityManager!!.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    private fun getNewsDao(): NewsDao {
        return App.database.newsDao
    }

    private fun getNewsFromInternet(category: String) {
        if (isOnline()) {
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

    fun swiped(){
        getNewsFromInternet(currentCategory)
    }

    fun onMenuItemSelected(category: String) {
        if (category != currentCategory) {
            currentCategory = category
            getNewsFromInternet(currentCategory)
        }
    }

    fun getNewsFromDb() {
        disposable.add(Single.fromCallable { getNewsDao().news }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { newsEntities ->
                    news?.let { niceNews ->
                        news = when (niceNews.isEmpty()) {
                            true -> newsEntities
                            false -> {
                                news?.clear()
                                newsEntities
                            }
                        }
                        news?.let { viewState.showNews(it) }
                    }
                })
    }

}