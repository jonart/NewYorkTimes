package ru.evgeniy.nytimes.fragments.newsDetailFragment


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_news_detail.*
import ru.evgeniy.nytimes.R
import ru.evgeniy.nytimes.data.db.NewsEntity
import ru.evgeniy.nytimes.fragments.newsEditor.EditorActivity

class NewsDetailFragment : MvpAppCompatFragment(), NewsDetailView {

    companion object {
        private const val  REQUEST_CODE = 1
        private const val ID_NEWS = "ID_NEWS"
        private var newsId:Int = 0
    }

    @InjectPresenter
    lateinit var newsDetailPresenter:NewsDetailPresenter

    @ProvidePresenter
    fun provideDetailPresenter() = NewsDetailPresenter()

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
            newsDetailPresenter.loadNews(newsId)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_detail_news, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_delete -> {
                newsDetailPresenter.deleteNews(newsId)
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
                REQUEST_CODE -> newsDetailPresenter.loadNews(newsId)
            }
        }
    }

    override fun popUp() {
        fragmentManager?.popBackStack()
    }

    override fun showNews(newsEntity:NewsEntity){
        Glide.with(iv_photo_news)
                .load(newsEntity.imageUrl)
                .apply(RequestOptions.centerCropTransform())
                .into(iv_photo_news)
        head_of_news.text = newsEntity.title
        text_news.text = newsEntity.fullText
    }

    override fun onStop() {
        super.onStop()
        newsDetailPresenter.disposable.clear()
    }

}
