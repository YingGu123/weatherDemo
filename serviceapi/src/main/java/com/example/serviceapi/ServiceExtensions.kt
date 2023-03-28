package com.example.serviceapi

import com.example.serviceapi.bean.WeatherBean
import com.example.serviceapi.exception.ServiceException

fun WeatherBean.check(): WeatherBean {
    if (this.infocode == HttpStatus.RESPONSE_OK) {
        return this
    }
    throw ServiceException(this.info ?: "")
}