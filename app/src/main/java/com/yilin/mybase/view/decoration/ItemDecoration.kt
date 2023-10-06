package com.yilin.mybase.view.decoration

import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ItemDecoration(
    private val spanCount: Int,
    private val horizontalSpacing: Int = 24,
    private val verticalSpacing: Int = 24
) : RecyclerView.ItemDecoration() {

    var isIndent = false // is header and footer must be spacing for LinearLayoutManager
    var headerIndentSpacing = horizontalSpacing
    var footerIndentSpacing = verticalSpacing

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        parent.layoutManager?.let {
            if (it is GridLayoutManager) {
                val size = it.spanSizeLookup.getSpanSize(position)
                if (size == spanCount) {
                    return
                }

                when (position % spanCount) {
                    0 -> outRect.right = horizontalSpacing / 2 // left item
                    spanCount - 1 -> outRect.left = horizontalSpacing / 2 // right item
                    else -> { // middle item
                        outRect.right = horizontalSpacing / 2
                        outRect.left = horizontalSpacing / 2
                    }
                }

                if (position >= spanCount) {
                    outRect.top = verticalSpacing
                }
            } else if (it is LinearLayoutManager) {
                if (it.orientation == LinearLayoutManager.HORIZONTAL) {
                    val size = parent.adapter?.itemCount ?: 1
                    val column = position % size

                    if (position == 0 && isIndent) {
                        outRect.left = headerIndentSpacing
                    } else {
                        outRect.left = column * horizontalSpacing / spanCount
                    }
                    if (position != size - 1 && isIndent) {
                        outRect.right =
                            horizontalSpacing - (column - 1) * horizontalSpacing / spanCount
                    } else {
                        outRect.right = footerIndentSpacing
                    }
                } else {
                    outRect.top = verticalSpacing
                }
            }
        }
    }
}