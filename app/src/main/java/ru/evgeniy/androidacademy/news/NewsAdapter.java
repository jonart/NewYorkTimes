package ru.evgeniy.androidacademy.news;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import ru.evgeniy.androidacademy.R;
import ru.evgeniy.androidacademy.data.NewsItem;

public class NewsAdapter extends RecyclerView.Adapter<NewsHolder> {

    private List<NewsItem> items;
    private final MyClickListener onClick;

    NewsAdapter(MyClickListener onClick) {
        this.onClick = onClick;
    }

    void setItems(List<NewsItem> items) {
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 2) return 2;
        else return 1;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new NewsHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_news, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder newsHolder, int i) {
        newsHolder.bind(items.get(i),onClick);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
