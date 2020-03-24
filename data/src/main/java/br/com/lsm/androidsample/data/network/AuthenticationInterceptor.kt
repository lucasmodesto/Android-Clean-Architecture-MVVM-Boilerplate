package br.com.lsm.androidsample.data.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor(private val token: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "bearer $token")
            .build()
        return chain.proceed(request)
    }
}