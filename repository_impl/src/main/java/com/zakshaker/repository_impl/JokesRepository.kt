package com.zakshaker.repository_impl

import com.zakshaker.api.entities.Joke
import com.zakshaker.api.webservices.JokesWebservice
import com.zakshaker.db.dao.JokeDao
import com.zakshaker.db.entities.JokeEntity
import com.zakshaker.model.JokeModel
import com.zakshaker.repository.JokesRepository

class JokesRepository(
    private val jokesDao: JokeDao,
    private val jokesWebservice: JokesWebservice
) : JokesRepository {

    override suspend fun getRandomJoke(): JokeModel = jokesWebservice.getRandomJoke().toModel()

    override suspend fun saveJokeToFavorites(joke: JokeModel)
            : Boolean = jokesDao.insertAll(joke.toEntity()).filterNot { it == -1L }.isNotEmpty()

    override suspend fun getFavoriteJokes()
            : List<JokeModel> =
        jokesDao.getAll()
            .map { it.toModel() }

    override suspend fun searchFavoriteJokes(text: String)
            : List<JokeModel> =
        jokesDao.findByText(text)
            .map { it.toModel() }


    private fun JokeEntity.toModel() = JokeModel(id, text)
    private fun JokeModel.toEntity() =
        JokeEntity(id, text)

    private fun Joke.toModel() = JokeModel(id, text)
}
