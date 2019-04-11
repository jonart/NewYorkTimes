package ru.evgeniy.nytimes.data.network.dto

import com.google.gson.annotations.SerializedName

import io.reactivex.annotations.NonNull

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
