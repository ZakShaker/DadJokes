package com.zakshaker.dadjokes.data.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiProvider {

    private const val BASE_URL: String = "https://icanhazdadjoke.com/"

    val jokesApi: JokesApi by lazy { defaultRetrofit.create(JokesApi::class.java) }

    private val defaultRetrofit: Retrofit by lazy {
        defaultRetrofitBuilder
            .client(defaultOkHttpBuilder.build())
            .build()
    }

    private val defaultRetrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
    }


    private val defaultOkHttpBuilder: OkHttpClient.Builder by lazy {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
    }


}