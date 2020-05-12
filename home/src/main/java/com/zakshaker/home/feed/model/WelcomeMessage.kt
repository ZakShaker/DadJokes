package com.zakshaker.home.feed.model

data class WelcomeMessage(
    override val id: Int = 1 // it is unique and always the same
) : FeedElement