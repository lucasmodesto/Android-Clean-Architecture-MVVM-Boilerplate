package br.com.lsm.androidsample.data.network

import com.apollographql.apollo.ApolloClient
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object NetworkClientProvider {

    fun providesApolloClient(
        baseUrl: String,
        client: OkHttpClient
    ): ApolloClient {
        return ApolloClient.builder()
            .serverUrl(baseUrl)
            .okHttpClient(client)
            .build()
    }

    fun providesOkHttpClient(interceptors: List<Interceptor>): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
        interceptors.forEach {
            clientBuilder.addInterceptor(it)
        }
        return clientBuilder.build()
    }
}