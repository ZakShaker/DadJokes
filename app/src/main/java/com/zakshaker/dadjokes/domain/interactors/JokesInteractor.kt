package com.zakshaker.dadjokes.domain.interactors

import androidx.lifecycle.LiveData
import com.zakshaker.dadjokes.domain.entities.Joke
import com.zakshaker.dadjokes.repositories.JokesRepository

class JokesInteractor(
    private val jokesRepository: JokesRepository
) {

    fun loadRandomJoke(): LiveData<Joke> = jokesRepository.getRandomJoke()
}