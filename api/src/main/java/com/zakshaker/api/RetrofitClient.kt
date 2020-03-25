package com.zakshaker.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    fun buildRetrofit(
        baseUrl: String
    ): Retrofit =
        getRetrofitBuilder(baseUrl)
            .client(defaultOkHttpBuilder.build())
            .build()


    private fun getRetrofitBuilder(
        baseUrl: String
    ): Retrofit.Builder = Retrofit.Builder()
        .baseUrl("$baseUrl/")
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().create()
            )
        )


    private val defaultOkHttpBuilder: OkHttpClient.Builder by lazy {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
    }
}