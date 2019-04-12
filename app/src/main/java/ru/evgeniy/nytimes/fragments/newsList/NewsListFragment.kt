package ru.evgeniy.nytimes.fragments.newsList


import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_news_list.*
import ru.evgeniy.nytimes.R
import ru.evgeniy.nytimes.data.Category
import ru.evgeniy.nytimes.data.db.NewsEntity
import ru.evgeniy.nytimes.fragments.newsDetailFragment.NewsDetailFragment
import ru.evgeniy.nytimes.news.ItemDecorator
import ru.evgeniy.nytimes.news.NewsAdapter
import ru.evgeniy.nytimes.news.NewsClickListener
import java.util.*

class NewsListFragment : MvpAppCompatFragment(), NewsClickListener, NewsListView {


    companion object {
        private const val SPAN_COUNT = 2
        private const val SPACING = 16
    }

    @InjectPresenter
    lateinit var newsListPresenter: NewsListPresenter

    @ProvidePresenter
    fun provideNewsListPresenter() = NewsListPresenter()


    private var spinner: Spinner? = null
    private var nowCategory = ""
    private val mAdapter = NewsAdapter(this)

    fun newInstance() = NewsListFragment()

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
            news_recycler.layoutManager = activity?.let { LinearLayoutManager(it) }
        } else {
            news_recycler.layoutManager = GridLayoutManager(activity, SPAN_COUNT)
        }
        news_recycler.addItemDecoration(ItemDecorator(SPACING))

        fab.setOnClickListener { newsListPresenter.getNewsFromInternet(nowCategory) }
        swipe_refresh.setOnRefreshListener {
            swipe_refresh.isRefreshing = false
            newsListPresenter.getNewsFromDb()
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
                if (nowCategory.isNotEmpty()) newsListPresenter.getNewsFromInternet(nowCategory)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        }
    }

    override fun onResume() {
        super.onResume()
        newsListPresenter.getNewsFromDb()
    }

    override fun onStop() {
        super.onStop()
        newsListPresenter.disposable.clear()
    }


    override fun onItemClick(item: NewsEntity) {
        activity?.supportFragmentManager?.apply {
            beginTransaction()
                    .replace(R.id.news_container, NewsDetailFragment().newInstance(item.id))
                    .addToBackStack(null)
                    .commit()
        }
    }

    override fun showProgressBar(isTrue: Boolean) {
        if (isTrue)
            progress_bar.visibility = View.VISIBLE
        else
            progress_bar.visibility = View.INVISIBLE
    }

    override fun showNews(news: MutableList<NewsEntity>) {
        news_recycler.adapter = mAdapter
        mAdapter.addData(news)
        progress_bar.visibility = View.GONE
        news_recycler.visibility = View.VISIBLE
    }

    override fun showMessage(message: String) {
        Snackbar.make(coordinator_layout_news, R.string.internet_connection, Snackbar.LENGTH_LONG)
                .setActionTextColor(Color.GRAY)
                .setAction(R.string.retry) {
                    newsListPresenter.getNewsFromInternet(nowCategory)
                }
                .show()
    }

    private fun isVertical(): Boolean {
        val orientation = resources.configuration.orientation
        return orientation != Configuration.ORIENTATION_LANDSCAPE
    }
}
