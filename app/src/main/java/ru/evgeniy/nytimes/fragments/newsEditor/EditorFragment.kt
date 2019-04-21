package ru.evgeniy.nytimes.fragments.newsEditor

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_editor.*
import ru.evgeniy.nytimes.R
import ru.evgeniy.nytimes.data.db.NewsEntity

class EditorFragment: MvpAppCompatFragment(), NewsView {

    companion object {
        private const val ID_NEWS = "ID_NEWS"
        fun newInstance(id: Int): Fragment {
            val fragment = EditorFragment()
            val bundle = Bundle()
            bundle.putInt(ID_NEWS, id)
            fragment.arguments = bundle
            return fragment
        }
    }
    private var newsId: Int = 0


    @InjectPresenter
    lateinit var newsPresenter:NewsPresenter




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_editor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.apply {
            newsId = getInt(ID_NEWS)
            newsPresenter.loadNews(newsId)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_editor, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_save -> {
                newsPresenter.clickSaveNews(newsId,head_editor_of_news.text.toString(), text_editor_news.text.toString())
                true
            }
            R.id.action_cancel -> {
                fragmentManager?.popBackStack()
                fragmentManager?.popBackStack()
                true
            }
            else -> {
                true
            }
        }
    }

    override fun onStop() {
        super.onStop()
        newsPresenter.disposeAll()
    }

    override fun result() {
        targetFragment?.onActivityResult(targetRequestCode,Activity.RESULT_OK,null)
        fragmentManager?.popBackStack()
        fragmentManager?.popBackStack()
    }

    override fun showNews(newsEntity: NewsEntity){
        Glide.with(this.iv_editor_photo_news)
                .load(newsEntity.imageUrl)
                .apply(RequestOptions.centerCropTransform())
                .into(this.iv_editor_photo_news)
        head_editor_of_news.setText(newsEntity.title)
        text_editor_news.setText(newsEntity.fullText)
    }
}