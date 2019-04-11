package ru.evgeniy.nytimes.data.network.dto

import com.google.gson.annotations.SerializedName

import io.reactivex.annotations.NonNull

class MultimediaDTO {
    /**
     * url : https://static01.nyt.com/images/2019/01/21/world/21Isis1/21Isis1-thumbStandard.jpg
     * format : Standard Thumbnail
     * height : 75
     * width : 75
     * type : image
     * subtype : photo
     * caption : President Trump at Dover Air Force Base on Saturday as troops carried the remains of Scott A. Wirtz, who was killed in a suicide bombing in Manbij, Syria.
     * copyright : Doug Mills/The New York Times
     */

    @SerializedName("url")
    @get:NonNull
    var url: String? = null
    @SerializedName("format")
    @get:NonNull
    var format: String? = null
    @SerializedName("height")
    @get:NonNull
    var height: Int = 0
    @SerializedName("width")
    @get:NonNull
    var width: Int = 0
    @SerializedName("type")
    @get:NonNull
    var type: String? = null
    @SerializedName("subtype")
    @get:NonNull
    var subtype: String? = null
    @SerializedName("caption")
    @get:NonNull
    var caption: String? = null
    @SerializedName("copyright")
    @get:NonNull
    var copyright: String? = null
}
