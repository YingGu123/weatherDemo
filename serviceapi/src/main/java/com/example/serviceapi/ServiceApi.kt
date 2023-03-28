package com.example.serviceapi

import com.example.serviceapi.api.WeatherService

object ServiceApi {

    fun getWeatherService(): WeatherService {
        return createServiceApi(WeatherService::class.java)
    }

    private fun <T> createServiceApi(clazz: Class<T>): T {
        return ServiceBuilder.buildService(clazz)
    }
}