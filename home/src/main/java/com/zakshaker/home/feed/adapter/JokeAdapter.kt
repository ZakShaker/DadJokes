package com.zakshaker.home.feed.adapter

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.zakshaker.home.R
import com.zakshaker.home.feed.model.FeedElement
import com.zakshaker.home.feed.model.LeftJoke
import com.zakshaker.home.feed.model.RightJoke
import kotlinx.android.synthetic.main.item_feed_joke_right.*

fun rightJokeAdapterDelegate() =
    adapterDelegateLayoutContainer<RightJoke, FeedElement>(R.layout.item_feed_joke_right) {
        bind {
            tv_text.text = item.jokeModel.text
        }
    }

fun leftJokeAdapterDelegate() =
    adapterDelegateLayoutContainer<LeftJoke, FeedElement>(R.layout.item_feed_joke_left) {
        bind {
            tv_text.text = item.jokeModel.text
        }
    }