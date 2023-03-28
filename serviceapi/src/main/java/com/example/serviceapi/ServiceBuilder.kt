package com.example.serviceapi

import com.example.serviceapi.interceptor.LoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceBuilder {

    private const val DEFAULT_TIMEOUT = 30L
    private lateinit var retrofit: Retrofit

    fun initialize() {
        retrofit = getBuilder(Environments.BASE_URL).build()
    }

    private val client: OkHttpClient
        get() {

            val builder = OkHttpClient.Builder().connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS).addInterceptor(LoggingInterceptor())

            return builder.build()
        }

    private fun getBuilder(baseUrl: String): Retrofit.Builder {
        return Retrofit.Builder().client(client).addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
    }

    fun <T> buildService(service: Class<T>): T {
        checkNetworkInitialized()
        return retrofit.create(service)
    }

    private fun checkNetworkInitialized() {
        if (!::retrofit.isInitialized) {
            throw IllegalStateException("Error: Network is not initialized!")
        }
    }

}