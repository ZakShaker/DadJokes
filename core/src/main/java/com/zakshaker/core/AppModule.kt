package com.zakshaker.core

import com.zakshaker.api.WebservicesApi
import com.zakshaker.api.webservices.JokesWebservice
import com.zakshaker.db.DB
import com.zakshaker.db.dao.JokeDao
import com.zakshaker.repository.JokesRepository
import org.koin.dsl.module

val appModule = module {

    // DAOs
    single<JokeDao> {
        DB.createInstance(get()).favoriteJokesDao()
    }

    // Webservices
    single<JokesWebservice> {
        WebservicesApi.getJokesWebservice()
    }

    // Repositories
    single<JokesRepository> { com.zakshaker.repository_impl.JokesRepository(get(), get()) }
}