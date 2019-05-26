package ru.evgeniy.nytimes.screens.newsList

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import ru.evgeniy.nytimes.R
import ru.evgeniy.nytimes.UtilDateFormatter
import ru.evgeniy.nytimes.data.db.NewsEntity

class NewsHolder(item: View) : RecyclerView.ViewHolder(item) {
    private val mCategory: TextView = item.findViewById(R.id.item_news_layout_recycler_category)
    private val mTitle: TextView = item.findViewById(R.id.item_news_layout_recycler_title)
    private val mText: TextView = item.findViewById(R.id.item_news_layout_recycler_text)
    private val mDate: TextView = item.findViewById(R.id.item_news_layout_recycler_date)
    private val mImageView: ImageView = item.findViewById(R.id.item_news_layout_recycler_img)


    fun bind(item: NewsEntity, onClick: (NewsEntity) -> Unit) {
        mCategory.text = item.category
        mTitle.text = item.title
        mText.text = item.fullText
        mDate.text = item.publishDate?.let { UtilDateFormatter.getDate(it) }
        if (item.imageUrl == null) {
            Glide.with(mImageView)
                    .load(R.drawable.notavailable)
                    .apply(RequestOptions.centerCropTransform())
                    .into(mImageView)
        } else {
            Glide.with(mImageView)
                    .load(item.imageUrl)
                    .apply(RequestOptions.centerCropTransform())
                    .into(mImageView)
        }

        itemView.setOnClickListener { onClick.invoke(item) }
    }
}
