package ru.evgeniy.nytimes.fragments.newsEditor

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_editor.*
import ru.evgeniy.nytimes.App
import ru.evgeniy.nytimes.R
import ru.evgeniy.nytimes.data.db.NewsDao
import ru.evgeniy.nytimes.data.db.NewsEntity

class EditorActivity : MvpAppCompatActivity(), NewsView {

    companion object {
        private const val ID_NEWS = "ID_NEWS"
    }
    private var newsId: Int = 0


    @InjectPresenter
    lateinit var newsPresenter:NewsPresenter

    @ProvidePresenter
    fun providePresenter() = NewsPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)
        newsId = intent.getIntExtra(ID_NEWS,0)
        newsPresenter.loadNews(newsId)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_editor, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_save -> {
                newsPresenter.saveNews(newsId,head_editor_of_news.text.toString(), text_editor_news.text.toString())
                true
            }
            R.id.action_cancel -> {
                finish()
                true
            }
            else -> {
                true
            }
        }
    }

    override fun onStop() {
        super.onStop()
        newsPresenter.compositeDisposable.clear()
    }

    override fun result() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun showNews(newsEntity:NewsEntity){
        Glide.with(this.iv_editor_photo_news)
                .load(newsEntity.imageUrl)
                .apply(RequestOptions.centerCropTransform())
                .into(this.iv_editor_photo_news)
        head_editor_of_news.setText(newsEntity.title)
        text_editor_news.setText(newsEntity.fullText)
    }


}
