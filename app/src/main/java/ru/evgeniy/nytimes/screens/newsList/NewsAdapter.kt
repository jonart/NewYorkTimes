package ru.evgeniy.nytimes.screens.newsList

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import ru.evgeniy.nytimes.R
import ru.evgeniy.nytimes.data.db.NewsEntity

class NewsAdapter(private val onClick:(NewsEntity) -> Unit) : RecyclerView.Adapter<NewsHolder>() {

    private val news: MutableList<NewsEntity> = mutableListOf()

    fun addData(items: MutableList<NewsEntity>) {
        news.clear()
        news.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): NewsHolder {
        return NewsHolder(LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_news, viewGroup, false))
    }

    override fun onBindViewHolder(newsHolder: NewsHolder, i: Int) {
        newsHolder.bind(news[i], onClick)
    }

    override fun getItemCount(): Int {
        return news.size
    }
}
