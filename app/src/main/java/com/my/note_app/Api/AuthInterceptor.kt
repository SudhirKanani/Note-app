package com.my.note_app.Api

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder();
        newRequest.addHeader("Authorization","Bearer ${123}")

        return chain.proceed(newRequest.build())
    }
}