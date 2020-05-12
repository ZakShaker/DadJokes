package com.zakshaker.home.feed.model

import com.zakshaker.model.JokeModel

data class RightJoke(
    val jokeModel: JokeModel,
    override val id: String = jokeModel.id
) : FeedElement

data class LeftJoke(
    val jokeModel: JokeModel,
    override val id: String = jokeModel.id
) : FeedElement