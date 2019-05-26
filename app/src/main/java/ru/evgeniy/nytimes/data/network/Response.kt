package ru.evgeniy.nytimes.data.network

import com.google.gson.annotations.SerializedName

import io.reactivex.annotations.NonNull
import ru.evgeniy.nytimes.data.network.dto.NewsDTO

class Response {

    /**
     * status : OK
     * results : []
     */

    @SerializedName("status")
    @get:NonNull
    var status: String? = null
    @SerializedName("results")
    @get:NonNull
    var results: List<NewsDTO>? = null
}
