package ru.evgeniy.nytimes.fragments


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_news_detail.*
import ru.evgeniy.nytimes.App
import ru.evgeniy.nytimes.R
import ru.evgeniy.nytimes.data.db.NewsDao
import ru.evgeniy.nytimes.news.EditorActivity

class NewsDetailFragment : Fragment() {

    companion object {
        private const val  REQUEST_CODE = 1
        private const val ID_NEWS = "ID_NEWS"
        private var newsId:Int = 0
    }

    private var disposable = CompositeDisposable()

    fun newInstance(id: Int): Fragment {
        val fragment = NewsDetailFragment()
        val bundle = Bundle()
        bundle.putInt(ID_NEWS, id)
        fragment.arguments = bundle
        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.apply {
            newsId = getInt(ID_NEWS)
            loadNews()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_detail_news, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_delete -> {
                disposable.add(Completable.fromAction { getNewsDao().deleteById(newsId) }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ fragmentManager?.popBackStack()}, { it.printStackTrace() }))
                return true
            }
            R.id.action_editor -> {
                val intent = Intent(context, EditorActivity::class.java)
                intent.putExtra(ID_NEWS, newsId)
                startActivityForResult(intent, REQUEST_CODE)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE -> loadNews()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }

    private fun getNewsDao(): NewsDao {
        return App.getDatabase().newsDao
    }

    private fun loadNews() {
        disposable.add(getNewsDao().getNewsById(newsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { newsEntity ->
                    Glide.with(iv_photo_news)
                            .load(newsEntity.imageUrl)
                            .apply(RequestOptions.centerCropTransform())
                            .into(iv_photo_news)
                    head_of_news.text = newsEntity.title
                    text_news.text = newsEntity.fullText

                }
                .subscribe())
    }

}
