package ru.evgeniy.nytimes

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object UtilDateFormatter{

        private const val TIMESTAMP_PATTERN = "yyyy-MM-dd'T'HH:mm:ssX"
        private const val DISPLAY_TIMESTAMP_PATTERN = "dd-MMM-yyyy HH:mm"

    fun getDate(inputDate: String): String {

        val formatter = SimpleDateFormat(TIMESTAMP_PATTERN, Locale.ENGLISH)
        val displayFormatter = SimpleDateFormat(DISPLAY_TIMESTAMP_PATTERN, Locale.ENGLISH)


        var date: Date? = null
        try {
            date = formatter.parse(inputDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return displayFormatter.format(date)
    }
}
