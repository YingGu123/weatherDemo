package com.example.serviceapi.api

import com.example.serviceapi.Environments
import com.example.serviceapi.bean.WeatherBean
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("${Environments.V3}/weather/weatherInfo")
    suspend fun getWeatherInfo(
        @Query("key") key: String,
        @Query("city") city: String,
        @Query("extensions") extensions: String?
    ): WeatherBean

}