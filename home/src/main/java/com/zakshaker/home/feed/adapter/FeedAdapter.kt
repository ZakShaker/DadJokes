package com.zakshaker.home.feed.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.zakshaker.home.feed.model.FeedElement

class FeedAdapter(
    vararg delegates: AdapterDelegate<List<FeedElement>>
) : AsyncListDifferDelegationAdapter<FeedElement>(FeedDiffCallback()) {
    init {
        delegates.forEach { delegate -> delegatesManager.addDelegate(delegate) }
    }

    private class FeedDiffCallback<T : FeedElement> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
            oldItem.equals(newItem)

        override fun getChangePayload(oldItem: T, newItem: T): Any? =
            newItem.calculatePayload(oldItem)
    }
}