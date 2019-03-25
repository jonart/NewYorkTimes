package ru.evgeniy.androidacademy.data.network;

import android.support.annotation.NonNull;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.evgeniy.androidacademy.data.network.dto.Response;

public interface Api {

    @GET("topstories/v2/{section}.json")
    Single<Response> getNews(@Path("section") @NonNull String section);
}
