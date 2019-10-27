package com.zakshaker.dadjokes.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zakshaker.dadjokes.domain.entities.Joke
import com.zakshaker.dadjokes.domain.interactors.JokesInteractor
import com.zakshaker.dadjokes.repositories.JokesRepositoryOnline
import kotlinx.coroutines.launch

class FeedViewModel(
    private val jokesInteractor: JokesInteractor = JokesInteractor()
) : ViewModel() {

    private val _nextRandomJokeButtonsEnabled = MutableLiveData<Boolean>()
    val isNextRandomJokeButtonEnabled = _nextRandomJokeButtonsEnabled

    private val _randomJoke = MutableLiveData<Joke>()
    val randomJoke: LiveData<Joke> = _randomJoke

    init {
        _nextRandomJokeButtonsEnabled.value = true
    }

    /**
     * Called by the UI when user clicks 'next random joke'-button
     */
    fun onLoadRandomJoke() = viewModelScope.launch {
        // disabling button when loading a random joke
        _nextRandomJokeButtonsEnabled.value = false
        try {
            _randomJoke.value = jokesInteractor.loadRandomJoke()
        } finally {
            // re-enable the sort buttons after the loading is complete
            _nextRandomJokeButtonsEnabled.value = true
        }
    }
}