package ru.evgeniy.nytimes.data.network.dto

import com.google.gson.annotations.SerializedName

import io.reactivex.annotations.NonNull

class NewsDTO {
    /**
     * section : World
     * subsection : Middle East
     * title : Described as Defeated, Islamic State Punches Back With Guerrilla Tactics
     * abstract : All but 1 percent of the territory the Islamic State once held is gone, but to suggest that the group has been defeated, as President Trump did, is to ignore the lessons of recent history.
     * url : https://www.nytimes.com/2019/01/21/world/middleeast/isis-syria-attack-iraq.html
     * byline : By RUKMINI CALLIMACHI
     * item_type : Article
     * updated_date : 2019-01-21T03:00:06-05:00
     * created_date : 2019-01-21T03:00:06-05:00
     * published_date : 2019-01-21T03:00:06-05:00
     * material_type_facet :
     * kicker :
     * des_facet : ["United States Defense and Military Forces","Assassinations and Attempted Assassinations","United States International Relations","International Relations","Terrorism","Iraq War (2003-11)"]
     * org_facet : ["Islamic State in Iraq and Syria (ISIS)"]
     * per_facet : []
     * geo_facet : ["Manbij (Syria)","Syria","Iraq"]
     * multimedia : [{"url":"https://static01.nyt.com/images/2019/01/21/world/21Isis1/21Isis1-thumbStandard.jpg","format":"Standard Thumbnail","height":75,"width":75,"type":"image","subtype":"photo","caption":"President Trump at Dover Air Force Base on Saturday as troops carried the remains of Scott A. Wirtz, who was killed in a suicide bombing in Manbij, Syria.","copyright":"Doug Mills/The New York Times"},{"url":"https://static01.nyt.com/images/2019/01/21/world/21Isis1/21Isis1-thumbLarge.jpg","format":"thumbLarge","height":150,"width":150,"type":"image","subtype":"photo","caption":"President Trump at Dover Air Force Base on Saturday as troops carried the remains of Scott A. Wirtz, who was killed in a suicide bombing in Manbij, Syria.","copyright":"Doug Mills/The New York Times"},{"url":"https://static01.nyt.com/images/2019/01/21/world/21Isis1/merlin_149439576_e73a326c-7262-4987-a47f-b813b19afa86-articleInline.jpg","format":"Normal","height":105,"width":190,"type":"image","subtype":"photo","caption":"President Trump at Dover Air Force Base on Saturday as troops carried the remains of Scott A. Wirtz, who was killed in a suicide bombing in Manbij, Syria.","copyright":"Doug Mills/The New York Times"},{"url":"https://static01.nyt.com/images/2019/01/21/world/21Isis1/21Isis1-mediumThreeByTwo210-v2.jpg","format":"mediumThreeByTwo210","height":140,"width":210,"type":"image","subtype":"photo","caption":"President Trump at Dover Air Force Base on Saturday as troops carried the remains of Scott A. Wirtz, who was killed in a suicide bombing in Manbij, Syria.","copyright":"Doug Mills/The New York Times"},{"url":"https://static01.nyt.com/images/2019/01/21/world/21Isis1/merlin_149439576_e73a326c-7262-4987-a47f-b813b19afa86-superJumbo.jpg","format":"superJumbo","height":1134,"width":2048,"type":"image","subtype":"photo","caption":"President Trump at Dover Air Force Base on Saturday as troops carried the remains of Scott A. Wirtz, who was killed in a suicide bombing in Manbij, Syria.","copyright":"Doug Mills/The New York Times"}]
     * short_url : https://nyti.ms/2S1OdUU
     */

    @SerializedName("subsection")
    @get:NonNull
    var subsection: String? = null
    @SerializedName("title")
    @get:NonNull
    var title: String? = null
    @SerializedName("abstract")
    @get:NonNull
    var abstractX: String? = null
    @SerializedName("published_date")
    @get:NonNull
    var published_date: String? = null
    @SerializedName("multimedia")
    @get:NonNull
    var multimedia: List<MultimediaDTO>? = null

    @SerializedName("url")
    @get:NonNull
    var url: String? = null
}
