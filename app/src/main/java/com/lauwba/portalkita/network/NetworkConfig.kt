package com.lauwba.portalkita.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkConfig {
    val BASE_URL: String = "https://lauwba.com"

    private fun setOkHttp(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().setLevel(
            HttpLoggingInterceptor.Level.BASIC).setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .callTimeout(15L, TimeUnit.SECONDS)
            .build()
    }

    private fun setRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(setOkHttp())
            .build()
    }

    fun getService(): ApiServices {
        return setRetrofit().create(ApiServices::class.java)
    }
}