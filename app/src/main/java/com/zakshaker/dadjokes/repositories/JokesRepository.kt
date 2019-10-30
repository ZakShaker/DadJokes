package com.zakshaker.dadjokes.repositories

import com.zakshaker.dadjokes.domain.entities.Joke

interface JokesRepository {
    /**
     * get a random joke
     */
    suspend fun getRandomJoke(): Joke

    /**
     * get a specific joke by [id]
     */
    suspend fun getTheJoke(id: String): Joke

    /**
     * search for a value with [query]
     * @return list of Jokes
     */
    suspend fun searchJokes(page: Int, limit: Int, query: String): List<Joke>
}