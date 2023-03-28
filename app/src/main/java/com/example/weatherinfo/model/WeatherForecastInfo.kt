package com.example.weatherinfo.model

import com.example.serviceapi.bean.CastBean
import com.example.serviceapi.bean.ForecastBean
import com.example.weatherinfo.utils.DateUtil

data class WeatherForecastInfo(
    val adCode: String?,
    val city: String,
    val reportTime: String,
    val casts: MutableList<WeatherCastInfo>
) {

    companion object {

        fun convert(bean: ForecastBean?): WeatherForecastInfo {
            return WeatherForecastInfo(
                bean?.adcode, bean?.city ?: "", DateUtil.formatDate(
                    DateUtil.getDateTimeSecPattern(),
                    DateUtil.getEPattern(),
                    bean?.reporttime
                ),
                bean?.casts?.map { WeatherCastInfo.convert(it) }?.toMutableList() ?: mutableListOf()
            )
        }

    }

}

data class WeatherCastInfo(
    val date: String,
    val dayWeather: String,
    val nightWeather: String,
    val dayTemp: String,
    val nightTemp: String,
    val dayWind: String,
    val nightWind: String,
    val dayPower: String,
    val nightPower: String,
) {

    companion object {

        fun convert(bean: CastBean): WeatherCastInfo {
            return WeatherCastInfo(
                DateUtil.formatDate(
                    DateUtil.getDatePattern(),
                    DateUtil.getEPattern(),
                    bean.date
                ),
                bean.dayweather ?: "",
                bean.nightweather ?: "",
                "${bean.daytemp ?: 0}",
                "${bean.nighttemp ?: 0}",
                bean.daywind ?: "",
                bean.nightwind ?: "",
                bean.daypower ?: "",
                bean.nightpower ?: ""

            )
        }

    }

}