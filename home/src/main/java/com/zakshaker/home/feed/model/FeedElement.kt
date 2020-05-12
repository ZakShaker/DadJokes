package com.zakshaker.home.feed.model

interface FeedElement {
    val id: Any
    fun calculatePayload(oldItem: FeedElement): Any? = null
}