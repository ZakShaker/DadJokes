package com.zakshaker.dadjokes.repositories

import androidx.lifecycle.LiveData
import com.zakshaker.dadjokes.domain.entities.Joke

interface JokesRepository {
    /**
     * get a random joke
     */
    fun getRandomJoke(): LiveData<Joke>

    /**
     * get a specific joke by [id]
     */
    fun getTheJoke(id: String): LiveData<Joke>

    /**
     * search for a value with [query]
     * @return list of Jokes
     */
    fun searchJokes(page: Int, limit: Int, query: String): LiveData<List<Joke>>
}