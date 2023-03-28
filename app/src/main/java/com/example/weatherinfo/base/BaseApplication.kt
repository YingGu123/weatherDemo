package com.example.weatherinfo.base

import android.app.Application
import com.example.serviceapi.ServiceBuilder

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ServiceBuilder.initialize()
    }
}