package com.example.pokedex_with_compose.network.interseptor

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

internal class HttpRequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val request = originalRequest.newBuilder().url(originalRequest.url).build()
        Timber.d(request.toString())
        return chain.proceed(request)
    }
}