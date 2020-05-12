package com.zakshaker.home.feed.adapter


import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.zakshaker.home.R
import com.zakshaker.home.feed.model.FeedElement
import com.zakshaker.home.feed.model.WelcomeMessage
fun welcomeMessageAdapterDelegate() =
    adapterDelegateLayoutContainer<WelcomeMessage, FeedElement>(R.layout.item_welcome_message) {

    }