package ru.evgeniy.nytimes.news

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import ru.evgeniy.nytimes.R
import ru.evgeniy.nytimes.data.db.NewsEntity

class NewsAdapter(private val onClick: NewsClickListener) : RecyclerView.Adapter<NewsHolder>() {

    private var items: MutableList<NewsEntity>? = null

    fun addData(items: MutableList<NewsEntity>) {
        if (this.items != null) {
            this.items!!.clear()
        }
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): NewsHolder {
        return NewsHolder(LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_news, viewGroup, false))
    }

    override fun onBindViewHolder(newsHolder: NewsHolder, i: Int) {
        newsHolder.bind(items!![i], onClick)
    }

    override fun getItemCount(): Int {
        return items!!.size
    }
}
