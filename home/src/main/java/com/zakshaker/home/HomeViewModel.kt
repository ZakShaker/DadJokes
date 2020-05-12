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
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

@ExperimentalStdlibApi
class HomeViewModel(
    private val jokesRepository: JokesRepository
) : ViewModel() {

    enum class HomeState {
        Loading,
        Loaded,
        FailedToRefresh,
        Offline
    }

    private val _homeState = MutableLiveData<HomeState>()

    private val cachedFeed = ArrayDeque<FeedElement>().apply { add(WelcomeMessage()) }
    private val _cachedFeed = MutableLiveData<ArrayDeque<FeedElement>>().apply {
        value = cachedFeed
    }

    init {
        onRefresh()
    }


    // Observe home state
    val homeState: LiveData<HomeState> = _homeState

    // Observe live stream of jokes
    val feed: LiveData<ArrayDeque<FeedElement>> = _cachedFeed

    private var loadingJokesJob: Job? = null
    fun onRefresh() {
        loadingJokesJob?.cancel()
        loadingJokesJob = viewModelScope.launch {
            refreshJokes()
        }
    }

    private suspend fun refreshJokes() {
        withContext(Dispatchers.Main) {
            _homeState.value = HomeState.Loading

            updateJokes()
        }
    }

    /**
     * Uploads some new joke to the current list of jokes
     */
    private suspend fun updateJokes() {
        uploadRandomJoke()?.let {
            val isRight = Random.nextBoolean()
            cachedFeed.addFirst(
                if (isRight) RightJoke(it) else LeftJoke(it)
            )

            _cachedFeed.value = cachedFeed
            _homeState.value = HomeState.Loaded
        } ?: run {
            _homeState.value = HomeState.Offline
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