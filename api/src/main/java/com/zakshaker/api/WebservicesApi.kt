package com.zakshaker.api

import com.google.gson.GsonBuilder
import com.zakshaker.api.webservices.JokesWebservice
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val JOKES_API_URL = "https://icanhazdadjoke.com"

object WebservicesApi {

    fun getJokesWebservice(): JokesWebservice = getService<JokesWebservice>(JOKES_API_URL)

    private inline fun <reified T> getService(baseUrl: String): T =
        buildRetrofit(baseUrl)
            .create(T::class.java)

    private fun buildRetrofit(
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