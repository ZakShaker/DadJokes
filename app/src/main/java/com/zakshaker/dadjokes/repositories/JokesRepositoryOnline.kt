package com.zakshaker.dadjokes.repositories

import com.zakshaker.dadjokes.data.network.ApiProvider
import com.zakshaker.dadjokes.data.network.JokesApi
import com.zakshaker.dadjokes.domain.entities.Joke

class JokesRepositoryOnline(
    private val jokesApi: JokesApi = ApiProvider.jokesApi
) : JokesRepository {
    override suspend fun getRandomJoke(): Joke = jokesApi.getRandomJoke()

    override suspend fun getTheJoke(id: String): Joke {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun searchJokes(page: Int, limit: Int, query: String): List<Joke> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}