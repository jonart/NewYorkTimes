package ru.evgeniy.nytimes.news

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
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

class EditorActivity : AppCompatActivity() {

    companion object {
        private const val ID_NEWS = "ID_NEWS"
    }

    private var newsId: Int = 0
    private var compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)
        newsId = intent.getIntExtra(ID_NEWS,0)
        loadNews(newsId)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_editor, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_save -> {
                saveNews(newsId,head_editor_of_news.text.toString(), text_editor_news.text.toString())
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
        compositeDisposable.clear()
    }

    private fun loadNews(id:Int){
        compositeDisposable.add(getNewsDao().getNewsById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { newsEntity ->
                    Glide.with(this.iv_editor_photo_news)
                            .load(newsEntity.imageUrl)
                            .apply(RequestOptions.centerCropTransform())
                            .into(this.iv_editor_photo_news)
                    head_editor_of_news.setText(newsEntity.title)
                    text_editor_news.setText(newsEntity.fullText)
                })
    }

    private fun saveNews(id:Int, title:String, fullText:String){
        compositeDisposable.add(Completable.fromAction { getNewsDao().updateNewsById(id, title, fullText) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { setResult(Activity.RESULT_OK)
                    finish() })
    }

    private fun getNewsDao(): NewsDao = App.getDatabase().newsDao
}
