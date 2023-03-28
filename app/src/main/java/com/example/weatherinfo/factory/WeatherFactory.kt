package com.example.weatherinfo.factory

import com.example.serviceapi.ServiceApi
import com.example.serviceapi.bean.WeatherBean
import com.example.serviceapi.check
import com.example.weatherinfo.BuildConfig

class WeatherFactory {

    companion object {

        const val EXTENSION_BASE = "base"

        const val EXTENSION_ALL = "all"

    }

    private val api by lazy {
        ServiceApi.getWeatherService()
    }

    suspend fun getWeatherBaseInfo(city: String): WeatherBean {
        return api.getWeatherInfo(BuildConfig.AMAP_KEY, city, EXTENSION_BASE).check()
    }

    suspend fun getWeatherAllInfo(city: String): WeatherBean? {
        return api.getWeatherInfo(BuildConfig.AMAP_KEY, city, EXTENSION_ALL).check()
    }

}