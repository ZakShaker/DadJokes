package com.zakshaker.core


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import java.util.*


open class SimpleRecyclerAdapter<I>(
    @LayoutRes private val itemLayout: Int,
    defaultItems: Stack<I> = Stack<I>(),
    var onItemClicked: ((item: I) -> Unit)? = null,
    private val attachView: (view: View, item: I, position: Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    constructor(
        @LayoutRes itemLayout: Int,
        defaultItems: Stack<I> = Stack<I>(),
        onItemClicked: ((item: I) -> Unit)? = null,
        attachView: (view: View, item: I) -> Unit
    ) : this(itemLayout, defaultItems, onItemClicked, { v, i, _ -> attachView(v, i) })

    var itemsList = Stack<I>().apply { addAll(defaultItems) }

    fun setItems(items: Stack<I>) {
        itemsList = items
        notifyDataSetChanged()
    }

    fun clearItems() {
        itemsList.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LayoutInflater.from(parent.context)
            .inflate(itemLayout, parent, false)
            .let { ViewHolder(it) }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemsList[position]
        val view = holder.itemView

        view.setOnClickListener { onItemClicked?.invoke(item) }
        attachView(view, item, position)
    }

    override fun getItemCount(): Int = itemsList.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
