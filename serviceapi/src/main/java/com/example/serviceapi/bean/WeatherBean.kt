package com.example.serviceapi.bean

data class WeatherBean(
    val status: String?,
    val count: Int?,
    val info: String?,
    val infocode: String?,
    val lives: ArrayList<LiveBean>?,
    val forecasts: ArrayList<ForecastBean>?
)

data class LiveBean(
    val province: String?,
    val city: String?,
    val adcode: String?,
    val weather: String?,
    val temperature: Int?,
    val winddirection: String?,
    val windpower: String?,
    val humidity: Int?,
    val reporttime: String?,
    val temperature_float: Float?,
    val humidity_float: Float?
)

data class ForecastBean(
    val city: String?,
    val adcode: String?,
    val province: String?,
    val reporttime: String?,
    val casts: ArrayList<CastBean>?
)

data class CastBean(
    val date: String?,
    val week: Int?,
    val dayweather: String?,
    val nightweather: String?,
    val daytemp: Int?,
    val nighttemp: Int?,
    val daywind: String?,
    val nightwind: String?,
    val daypower: String?,
    val nightpower: String?,
    val daytemp_float: Float?,
    val nighttemp_float: Float?
)