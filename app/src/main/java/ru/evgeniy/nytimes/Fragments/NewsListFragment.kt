package ru.evgeniy.nytimes.Fragments


import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_news_list.*
import ru.evgeniy.nytimes.App
import ru.evgeniy.nytimes.R
import ru.evgeniy.nytimes.data.Category
import ru.evgeniy.nytimes.data.db.NewsDao
import ru.evgeniy.nytimes.data.db.NewsEntity
import ru.evgeniy.nytimes.news.ItemDecorator
import ru.evgeniy.nytimes.news.MyClickListener
import ru.evgeniy.nytimes.news.NewsAdapter
import ru.evgeniy.nytimes.news.StoryMappers
import java.util.*

class NewsListFragment : Fragment(), MyClickListener {

    private val SPAN_COUNT = 2
    private val SPACING = 16
    private var spinner: Spinner? = null
    private var nowCategory = ""
    private var disposable: CompositeDisposable = CompositeDisposable()

    private var news: MutableList<NewsEntity>? = null
    private val mAdapter = NewsAdapter(this)


    fun newInstance(): Fragment {
        return NewsListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinner = view.findViewById(R.id.action_bar_spinner)
        if (isVertical()) {
            news_recycler.layoutManager = LinearLayoutManager(activity)
        } else {
            news_recycler.layoutManager = GridLayoutManager(activity, SPAN_COUNT)
        }
        news_recycler.addItemDecoration(ItemDecorator(SPACING))

        fab.setOnClickListener { loadData(nowCategory) }
        swipe_refresh.setOnRefreshListener {
            swipe_refresh.isRefreshing = false
            reloadNews()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_category_news, menu)

        val item = menu?.findItem(R.id.spinner_menu)
        spinner = item?.actionView as Spinner

        val categoryList = ArrayList<String>()
        categoryList.add("")
        for (category in Category.values()) {
            categoryList.add(category.serverValue())
        }
        val adapter = ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, categoryList)
        spinner?.adapter = adapter

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, l: Long) {
                val name = adapterView.getItemAtPosition(position).toString()
                nowCategory = name.toLowerCase()
                if (!nowCategory.isEmpty()) loadData(nowCategory)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        }
    }

    override fun onResume() {
        reloadNews()
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
        disposable.dispose()
    }


    override fun onItemClick(item: NewsEntity) {
        activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.news_container, NewsDetailFragment().newInstance(item.id))
                ?.addToBackStack(null)
                ?.commit()
    }


    fun loadData(category: String) {
        if (isOnline()) {
            showProgressBar(true)
            disposable.add(App.getRestApi()
                    .getNews(category.replace(" ", ""))
                    .map { responseStory -> StoryMappers.map(responseStory.results) }
                    .subscribeOn(Schedulers.io())
                    .doOnSuccess { newsItems ->
                        getNewsDao().deleteAllNews()
                        getNewsDao().insertAllNews(newsItems)
                        news = getNewsDao().news
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorReturn {
                        news = getNewsDao().news
                        news
                    }
                    .subscribe({
                        showProgressBar(false)
                        showNews()
                    }, { showProgressBar(false) }))
        } else {
            Snackbar.make(coordinator_layout_news, R.string.internet_connection, Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.GRAY)
                    .setAction(R.string.retry) {
                        loadData(category)
                    }
                    .show()
        }
    }

    private fun showProgressBar(isTrue: Boolean) {
        if (isTrue)
            progress_bar.visibility = View.VISIBLE
        else
            progress_bar.visibility = View.INVISIBLE
    }

    private fun showNews() {
        news_recycler.setAdapter(mAdapter)
        mAdapter.addData(news!!)
        progress_bar.visibility = View.GONE
        news_recycler.visibility = View.VISIBLE
    }

    private fun isOnline(): Boolean {
        val connectivityManager = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val networkInfo = connectivityManager!!.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }

    private fun isVertical(): Boolean {
        val orientation = resources.configuration.orientation
        return orientation != Configuration.ORIENTATION_LANDSCAPE
    }

    private fun getNewsDao(): NewsDao {
        return App.getDatabase().newsDao
    }

    private fun reloadNews() {
        disposable.add(Single.fromCallable { getNewsDao().news }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { newsEntities ->
                    if (news != null && news!!.isEmpty()) {
                        news?.clear()
                        news?.size
                    } else {
                        news = newsEntities
                    }
                    showNews()
                })
    }
}
