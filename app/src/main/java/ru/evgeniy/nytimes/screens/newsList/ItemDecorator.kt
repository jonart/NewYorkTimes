package ru.evgeniy.nytimes.screens.newsList

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class ItemDecorator(private val spacing: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.bottom = spacing
        outRect.top = spacing
        outRect.left = spacing
        outRect.right = spacing

    }
}
