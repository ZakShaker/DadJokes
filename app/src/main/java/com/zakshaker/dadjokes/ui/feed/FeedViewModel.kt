package com.zakshaker.dadjokes.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.zakshaker.dadjokes.domain.entities.Joke
import com.zakshaker.dadjokes.domain.interactors.JokesInteractor
import com.zakshaker.dadjokes.repositories.JokesRepositoryMock

class FeedViewModel(
    jokesInteractor: JokesInteractor = JokesInteractor(JokesRepositoryMock())
) : ViewModel() {

    private val _joke = jokesInteractor.loadRandomJoke()
    val joke: LiveData<Joke> = _joke
}