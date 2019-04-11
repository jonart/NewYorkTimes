package ru.evgeniy.nytimes.data.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import ru.evgeniy.nytimes.data.network.dto.Response

interface Api {

    @GET("topstories/v2/{section}.json")
    fun getNews(@Path("section") section: String): Single<Response>
}
