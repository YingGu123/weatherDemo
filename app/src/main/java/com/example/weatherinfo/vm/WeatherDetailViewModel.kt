package com.example.weatherinfo.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.weatherinfo.factory.WeatherFactory
import com.example.weatherinfo.base.BaseViewModel
import com.example.weatherinfo.model.UiState
import com.example.weatherinfo.model.WeatherForecastInfo

class WeatherDetailViewModel(val app: Application) : BaseViewModel(app) {

    val weatherForecastInfo = MutableLiveData<WeatherForecastInfo>()

    private val weatherFactory by lazy {
        WeatherFactory()
    }

    fun getWeatherAllInfo(city: String?) {
        if (city == null) {
            uiStateFlow.value = UiState.ErrorPage
            return
        }
        launch {
            try {
                uiStateFlow.value = UiState.Loading
                val bean = weatherFactory.getWeatherAllInfo(city)
                weatherForecastInfo.value =
                    WeatherForecastInfo.convert(bean?.forecasts?.firstOrNull())
                uiStateFlow.value = UiState.Success
            } catch (e: Exception) {
                e.printStackTrace()
                uiStateFlow.value = UiState.ErrorPage
            }
        }
    }

}