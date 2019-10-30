package com.zakshaker.dadjokes.data.network

import com.zakshaker.dadjokes.domain.entities.Joke
import retrofit2.http.GET
import retrofit2.http.Headers

interface JokesApi {

    @GET(".")
    @Headers("Accept: application/json")
    suspend fun getRandomJoke(): Joke
}