package ru.evgeniy.nytimes.news

import java.util.ArrayList
import java.util.Collections

import ru.evgeniy.nytimes.data.db.NewsEntity
import ru.evgeniy.nytimes.data.network.dto.MultimediaDTO
import ru.evgeniy.nytimes.data.network.dto.NewsDTO

class StoryMappers private constructor() {

    init {
        throw AssertionError("Must be no instance")
    }

    companion object {

        fun map(dto: List<NewsDTO>): List<NewsEntity> {

            val items = ArrayList<NewsEntity>()


            for (i in dto.indices) {
                val item = toDatabaseType(dto[i])
                items.add(item)
            }
            return Collections.unmodifiableList(items)
        }

        private fun toDatabaseType(dto: NewsDTO): NewsEntity {
            val title = dto.title

            val multimedia = dto.multimedia
            val imageUrl = mapImage(multimedia)
            val category = dto.subsection
            val publishDate = dto.published_date
            val preview = dto.abstractX
            val url = dto.url

            return NewsEntity(title,
                    imageUrl,
                    category,
                    publishDate,
                    preview,
                    preview,
                    url)
        }

        private fun mapImage(multimedia: List<MultimediaDTO>?): String? {

            return if (multimedia != null && !multimedia.isEmpty()) {
                multimedia[multimedia.size - 1].url
            } else null

        }
    }
}
