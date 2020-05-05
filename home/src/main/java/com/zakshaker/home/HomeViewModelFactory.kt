package com.zakshaker.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zakshaker.repository.JokesRepository

class HomeViewModelFactory(
    private val jokesRepository: JokesRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        HomeViewModel(jokesRepository) as T
}
