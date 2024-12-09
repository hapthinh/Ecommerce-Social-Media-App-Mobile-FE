package com.example.ecommerce_social_media.data.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenProvider : TokenProvider) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val url = originalRequest.url.toString()

        if(url.contains("/login") || url.contains("/signup")) {
            return chain.proceed(originalRequest)
        }

        val token = tokenProvider.getToken()

        val authenticateRequest = originalRequest.newBuilder()
            .addHeader("Authorization","Bearer $token")
            .build()

        return chain.proceed(authenticateRequest)
    }
}