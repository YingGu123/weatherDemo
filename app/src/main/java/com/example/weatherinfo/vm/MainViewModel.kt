package com.example.weatherinfo.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.weatherinfo.factory.WeatherFactory
import com.example.weatherinfo.base.BaseViewModel
import com.example.weatherinfo.model.UiState
import com.example.weatherinfo.model.WeatherLiveInfo
import kotlinx.coroutines.flow.*

class MainViewModel(app: Application) : BaseViewModel(app) {

    private val list = mutableListOf("110000", "310000", "440100", "440300", "320500", "210100")

    val weatherLiveList = MutableLiveData<MutableList<WeatherLiveInfo>>()

    private val weatherFactory by lazy {
        WeatherFactory()
    }

    fun getWeatherBaseInfo() {
        launch {
            try {
                uiStateFlow.value = UiState.Loading
                val items = mutableListOf<WeatherLiveInfo>()
                list.asFlow().flatMapMerge {
                    flow {
                        emit(weatherFactory.getWeatherBaseInfo(it))
                    }
                }.map {
                    WeatherLiveInfo.convert(it.lives?.firstOrNull())
                }.collect {
                    items.add(it)
                }

                weatherLiveList.value = items
                uiStateFlow.value = UiState.Success
            } catch (e: Exception) {
                e.printStackTrace()
                uiStateFlow.value = UiState.ErrorPage
            }
        }
    }

}