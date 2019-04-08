package ru.evgeniy.nytimes.news;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.evgeniy.nytimes.data.db.NewsEntity;
import ru.evgeniy.nytimes.data.network.dto.MultimediaDTO;
import ru.evgeniy.nytimes.data.network.dto.NewsDTO;

public class StoryMappers {

    private StoryMappers() {
        throw new AssertionError("Must be no instance");
    }

    static List<NewsEntity> map(@NonNull List<NewsDTO> dtos) {

        List<NewsEntity> items = new ArrayList<>();


        for (int i = 0; i < dtos.size(); i++) {
            NewsEntity item = toDatabaseType(dtos.get(i));
            items.add(item);
        }
        return Collections.unmodifiableList(items);
    }

    @NonNull
    private static NewsEntity toDatabaseType(@NonNull NewsDTO dto) {
         String title = dto.getTitle();

         List<MultimediaDTO> multimedia = dto.getMultimedia();
         String imageUrl = mapImage(multimedia);
         String category = dto.getSubsection();
         String publishDate = dto.getPublished_date();
         String preview = dto.getAbstractX();
         String url = dto.getUrl();

        return new NewsEntity(title,
                imageUrl,
                category,
                publishDate,
                preview,
                preview,
                url);
    }

    @Nullable
    private static String mapImage(@Nullable List<MultimediaDTO> multimedias) {

        if (multimedias != null && !multimedias.isEmpty()) {
            return multimedias.get(multimedias.size()-1).getUrl();
        }
        return null;

    }
}
