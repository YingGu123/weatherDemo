package com.example.weatherinfo.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    @JvmStatic
    fun formatDate(form: DateFormat, to: DateFormat, date: String?): String {
        var time = ""
        if (date.isNullOrEmpty()) return time
        try {
            form.parse(date)?.let {
                time = to.format(it)
            }
        } catch (e: Exception) {
            time = ""
        }
        return time
    }

    fun getDateTimeSecPattern(): SimpleDateFormat {
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
    }

    fun getDatePattern(): SimpleDateFormat {
        return SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
    }

    fun getEPattern(): SimpleDateFormat {
        return SimpleDateFormat("EEE", Locale.CHINA)
    }
}