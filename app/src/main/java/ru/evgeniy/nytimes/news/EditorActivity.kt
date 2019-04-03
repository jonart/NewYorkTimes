package ru.evgeniy.nytimes.news

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.evgeniy.nytimes.App
import ru.evgeniy.nytimes.Fragments.NewsDetailFragment
import ru.evgeniy.nytimes.R
import ru.evgeniy.nytimes.data.db.NewsDao
import ru.evgeniy.nytimes.data.db.NewsEntity

class EditorActivity : AppCompatActivity() {

    val ID_NEWS = "ID_NEWS"
    private var mDisposable : Disposable? = null

    private lateinit var mImageViewNews: ImageView
    private lateinit var mHeadTextView: EditText
    private lateinit var mTextNews: EditText
    private lateinit var mButtonSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)
        mImageViewNews = findViewById(R.id.iv_editor_photo_news)
        mHeadTextView = findViewById(R.id.head_editor_of_news)
        mTextNews = findViewById(R.id.text_editor_news)
        mButtonSave = findViewById(R.id.btn_editor_save)
        val id = intent.getIntExtra(ID_NEWS,0)
        loadNews(id)
    }

    private fun loadNews(id:Int){
        mDisposable = getNewsDao().getNewsById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { newsEntity:NewsEntity ->
                    Glide.with(this.mImageViewNews)
                            .load(newsEntity.imageUrl)
                            .apply(RequestOptions.centerCropTransform())
                            .into(this.mImageViewNews)
                    mHeadTextView.setText(newsEntity.title)
                    mTextNews.setText(newsEntity.fullText)
                }
        mButtonSave.setOnClickListener {
            saveNews(id,mHeadTextView.text.toString(), mTextNews.text.toString())
        }
    }

    private fun saveNews(id:Int, title:String, fullText:String){
        mDisposable = Completable.fromAction { getNewsDao().updateNewsById(id, title, fullText) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { setResult(Activity.RESULT_OK)
                    finish() }
    }

    private fun getNewsDao(): NewsDao = App.getDatabase().newsDao
}
