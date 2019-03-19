package ru.evgeniy.androidacademy.news;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.evgeniy.androidacademy.data.NewsItem;
import ru.evgeniy.androidacademy.data.network.dto.MultimediaDTO;
import ru.evgeniy.androidacademy.data.network.dto.NewsDTO;

public class StoryMappers {

    private StoryMappers() {
        throw new AssertionError("Must be no instance");
    }

    static List<NewsItem> map(@NonNull List<NewsDTO> dtos) {

        List<NewsItem> items = new ArrayList<>();


        for (int i = 0; i < dtos.size(); i++) {
            NewsItem item = mapItem(dtos.get(i));
            items.add(item);
        }
        return Collections.unmodifiableList(items);
    }


    private static NewsItem mapItem(@NonNull NewsDTO dto) {
         String title = dto.getTitle();

         List<MultimediaDTO> multimedia = dto.getMultimedia();
         String imageUrl = mapImage(multimedia);
        Log.d("MTAG", "mapItem: " + imageUrl);
         String category = dto.getSubsection();
         String publishDate = dto.getPublished_date();
         String preview = dto.getAbstractX();
         String url = dto.getUrl();

        return new NewsItem(title,
                imageUrl,
                category,
                publishDate,
                preview,
                preview,
                url);
    }

    @Nullable
    private static String mapImage(@Nullable List<MultimediaDTO> multimedias) {
//
        if (multimedias != null && !multimedias.isEmpty()) {
            return multimedias.get(multimedias.size()-1).getUrl();
        }
        return null;

    }
}
