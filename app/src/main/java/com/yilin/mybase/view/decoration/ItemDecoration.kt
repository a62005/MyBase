package com.yilin.mybase.view.decoration

import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ItemDecoration(private val spanCount: Int, private val margin: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        parent.layoutManager?.let { manager ->
            if (manager is GridLayoutManager) {
                val spanSizeLookup = manager.spanSizeLookup
                val position = parent.getChildAdapterPosition(view)
                val spanSize = spanSizeLookup.getSpanSize(position)
            }
        }
    }
}