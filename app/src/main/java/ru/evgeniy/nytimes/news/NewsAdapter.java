package ru.evgeniy.nytimes.news;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import ru.evgeniy.nytimes.R;
import ru.evgeniy.nytimes.data.db.NewsEntity;

public class NewsAdapter extends RecyclerView.Adapter<NewsHolder> {

    private List<NewsEntity> items;
    private final MyClickListener onClick;

    public NewsAdapter(@NonNull MyClickListener onClick) {
        this.onClick = onClick;
    }

    public void addData(@NonNull List<NewsEntity> items) {
        if (this.items != null){this.items.clear();}
        this.items = items;
        notifyDataSetChanged();
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
