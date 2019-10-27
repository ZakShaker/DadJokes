package com.zakshaker.dadjokes.domain.interactors

import com.zakshaker.dadjokes.domain.entities.Joke
import com.zakshaker.dadjokes.repositories.JokesRepository
import com.zakshaker.dadjokes.repositories.JokesRepositoryMock
import com.zakshaker.dadjokes.repositories.JokesRepositoryOnline

class JokesInteractor(
    private val jokesRepository: JokesRepository = JokesRepositoryOnline()
) {
    suspend fun loadRandomJoke(): Joke = jokesRepository.getRandomJoke()
}