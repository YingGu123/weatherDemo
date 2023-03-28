package com.example.serviceapi.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class LoggingInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val t1 = System.nanoTime()
        val requestLog = String.format(
            "Sending request %s on %s%n%s \n", request.url, chain.connection(), request.headers
        )

        val response = chain.proceed(request)

        val t2 = System.nanoTime()
        val responseLog = String.format(
            "<-- %s %s Received response for %s in %.1fms%n%s \n",
            response.code,
            response.message,
            response.request.url,
            (t2 - t1) / 1e6,
            response.headers
        )

        val responseBody = response.peekBody((1024 * 1024).toLong())

        val log = requestLog + responseLog + "\n" + responseBody.string() + "\n"

        println(log)

        return response
    }

}