package ru.evgeniy.nytimes.news

import ru.evgeniy.nytimes.data.db.NewsEntity

interface NewsClickListener {
    fun onItemClick(item: NewsEntity)
}
