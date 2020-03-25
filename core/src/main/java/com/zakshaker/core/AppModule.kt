package com.zakshaker.core

import com.zakshaker.api.RetrofitClient
import com.zakshaker.api.webservices.JokesWebservice
import com.zakshaker.db.DB
import com.zakshaker.db.dao.JokeDao
import com.zakshaker.repository.JokesRepository
import org.koin.dsl.module

private const val JOKES_API_URL = "https://icanhazdadjoke.com"
val appModule = module {

    // DAOs
    single<JokeDao> {
        DB.createInstance(get()).favoriteJokesDao()
    }

    // Webservices
    single<JokesWebservice> {
        RetrofitClient.buildRetrofit(JOKES_API_URL)
            .create(JokesWebservice::class.java)
    }

    // Repositories
    single<JokesRepository> { com.zakshaker.repository_impl.JokesRepository(get(), get()) }
}