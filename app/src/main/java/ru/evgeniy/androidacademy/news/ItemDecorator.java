package ru.evgeniy.androidacademy.news;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ItemDecorator extends RecyclerView.ItemDecoration {

    private int spacing;

    public ItemDecorator(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            outRect.bottom = spacing;
            outRect.top = spacing;
            outRect.left = spacing;
            outRect.right = spacing;

    }
}
