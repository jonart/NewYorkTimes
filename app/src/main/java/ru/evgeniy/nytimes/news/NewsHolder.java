package ru.evgeniy.nytimes.news;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;

import ru.evgeniy.nytimes.R;
import ru.evgeniy.nytimes.data.db.NewsEntity;

class NewsHolder extends RecyclerView.ViewHolder {
    private final TextView mCategory;
    private final TextView mTitle;
    private final TextView mText;
    private final TextView mDate;
    private final ImageView mImageView;

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");


    NewsHolder(@NonNull View item) {
        super(item);
        mCategory = item.findViewById(R.id.item_news_layout_recycler_category);
        mTitle = item.findViewById(R.id.item_news_layout_recycler_title);
        mText = item.findViewById(R.id.item_news_layout_recycler_text);
        mDate = item.findViewById(R.id.item_news_layout_recycler_date);
        mImageView = item.findViewById(R.id.item_news_layout_recycler_img);

    }

    void bind(NewsEntity item, NewsClickListener onClick) {
        mCategory.setText(item.getCategory());
        mTitle.setText(item.getTitle());
        mText.setText(item.getFullText());
        mDate.setText(item.getPublishDate());
        if (item.getImageUrl() == null){
            Glide.with(mImageView)
                    .load(R.drawable.notavailable)
                    .apply(RequestOptions.centerCropTransform())
                    .into(mImageView);
        }
        else {
            Glide.with(mImageView)
                    .load(item.getImageUrl())
                    .apply(RequestOptions.centerCropTransform())
                    .into(mImageView);
        }

        itemView.setOnClickListener(view -> onClick.onItemClick(item));
    }
}
