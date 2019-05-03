package ru.evgeniy.nytimes.data

import android.support.annotation.StringRes

import ru.evgeniy.nytimes.R

enum class Category(private val serverValue: String,
                    @param:StringRes internal val displayResId: Int) {

    HOME("home", R.string.category_home),
    OPINION("opinion", R.string.category_opinion),
    WORLD("world", R.string.category_world),
    NATIONAL("national", R.string.category_national),
    POLITICS("politics", R.string.category_politics),
    UPSHOT("upshot", R.string.category_upshot),
    BUSINESS("business", R.string.category_business),
    TECHNOLOGY("technology", R.string.category_technology),
    SCIENCE("science", R.string.category_science),
    HEALTH("health", R.string.category_health),
    ARTS("arts", R.string.category_arts),
    BOOKS("books", R.string.category_books),
    MOVIES("movies", R.string.category_movies),
    THEATER("theater", R.string.category_theater),
    SUNDAY_REVIEW("sundayreview", R.string.category_sundayreview),
    FASHION("fashion", R.string.category_fashion),
    T_MAGAZINE("tmagazine", R.string.category_tmagazine),
    FOOD("food", R.string.category_food),
    TRAVEL("travel", R.string.category_travel),
    MAGAZINE("magazine", R.string.category_magazine),
    REAL_ESTATE("realestate", R.string.category_realestate),
    AUTOMOBILES("automobiles", R.string.category_automobiles),
    OBITUARIES("obituaries", R.string.category_obituaries),
    INSIDER("insider", R.string.category_insider);

    @StringRes
    fun displayValue(): Int {
        return displayResId
    }

    fun serverValue(): String {
        return serverValue
    }

    override fun toString(): String {
        return serverValue
    }
}
