package com.zakshaker.repository

import com.zakshaker.model.JokeModel

interface JokesRepository {
    /**
     * returns random joke
     */
    suspend fun getRandomJoke(): JokeModel

    /**
     * returns true if the joke has been saved to favorites, false otherwise
     */
    suspend fun saveJokeToFavorites(joke: JokeModel): Boolean

    /**
     * returns list of favorite jokes
     */
    suspend fun getFavoriteJokes(): List<JokeModel>

    /**
     * returns list of favorite jokes that containt text in their text
     */
    suspend fun searchFavoriteJokes(text: String): List<JokeModel>
}
