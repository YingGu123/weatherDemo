package com.example.weatherinfo.model

import com.example.serviceapi.bean.LiveBean
import com.example.weatherinfo.utils.DateUtil

data class WeatherLiveInfo(
    val adCode: String?,
    val city: String,
    val weather: String,
    val reportTime: String,
    val temperature: String,
    val humidity: String,
    val windPower: String
) {

    companion object {

        fun convert(bean: LiveBean?): WeatherLiveInfo {
            return WeatherLiveInfo(
                bean?.adcode,
                bean?.city ?: "",
                bean?.weather ?: "",
                DateUtil.formatDate(
                    DateUtil.getDateTimeSecPattern(),
                    DateUtil.getEPattern(),
                    bean?.reporttime
                ),
                "${bean?.temperature ?: 0}",
                "${bean?.humidity ?: 0}",
                bean?.windpower ?: ""
            )
        }

    }

}