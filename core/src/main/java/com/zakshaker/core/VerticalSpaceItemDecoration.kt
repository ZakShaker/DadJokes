package com.zakshaker.core

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


class VerticalSpaceItemDecoration(
    val leftSpaceHeight: Int = 0,
    val topSpaceHeight: Int = 16.toPx(),
    val rightSpaceHeight: Int = 0,
    val bottomSpaceHeight: Int = 16.toPx(),
    val applyToFirst: Boolean = true,
    val applyToLast: Boolean = true
) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        when {
            // First element
            position == 0 -> if (applyToFirst)
                applySpaces(outRect)
            // Last element
            position + 1 == parent.adapter?.itemCount ->
                if (applyToLast)
                    applySpaces(outRect)
            // other elements
            else ->
                applySpaces(outRect)
        }
    }

    private fun applySpaces(outRect: Rect) {
        outRect.apply {
            left = leftSpaceHeight
            top = topSpaceHeight
            right = rightSpaceHeight
            bottom = bottomSpaceHeight
        }
    }
}