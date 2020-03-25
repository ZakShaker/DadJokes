package com.zakshaker.api.webservices

import com.zakshaker.api.entities.Joke
import retrofit2.http.GET
import retrofit2.http.Headers

interface JokesWebservice {
    @GET(".")
    @Headers("Accept: application/json")
    suspend fun getRandomJoke(): Joke
}