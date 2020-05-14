package com.zakshaker.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zakshaker.home.feed.model.FeedElement
import com.zakshaker.home.feed.model.LeftJoke
import com.zakshaker.home.feed.model.RightJoke
import com.zakshaker.home.feed.model.WelcomeMessage
import com.zakshaker.model.JokeModel
import com.zakshaker.repository.JokesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

@ExperimentalStdlibApi
class HomeViewModel(
    private val jokesRepository: JokesRepository
) : ViewModel() {

    enum class LoadingState {
        Loaded,
        Loading
    }

    private var _loadingState = MutableLiveData<LoadingState>()
    var loadingState: LiveData<LoadingState> = _loadingState

    private
    val feedDeque = ArrayDeque<FeedElement>().apply { add(WelcomeMessage()) }
    private val _cachedFeed = MutableLiveData<ArrayDeque<FeedElement>>().apply {
        value = feedDeque
    }
    val feed: LiveData<ArrayDeque<FeedElement>> = _cachedFeed

    init {
        viewModelScope.launch {
            receiveJokes()
        }
    }

    private suspend fun receiveJokes() {
        while (true) {
            _loadingState.value = LoadingState.Loading

            val freshJoke = uploadRandomJoke()

            _loadingState.value = LoadingState.Loaded

            if (freshJoke != null)
                _cachedFeed.value = feedDeque.apply {
                    addFirst(
                        if (Random.nextBoolean()) RightJoke(freshJoke) else LeftJoke(freshJoke)
                    )
                }
            delay(1000L * Random.nextInt(4, 10))
        }
    }

    private suspend fun uploadRandomJoke(): JokeModel? = withContext(Dispatchers.IO) {
        try {
            jokesRepository.getRandomJoke()
        } catch (e: Exception) {
            Log.d("HomeViewModel", e.message)
            null
        }
    }
}