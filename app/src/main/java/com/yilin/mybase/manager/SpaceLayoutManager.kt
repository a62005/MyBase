package com.yilin.mybase.manager

import androidx.recyclerview.widget.RecyclerView

/***
 * 橫向LinearLayoutManager頂至邊框會自動換行
 */
class SpaceLayoutManager(private val verticalSpace: Int = 12) : RecyclerView.LayoutManager() {

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.WRAP_CONTENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        detachAndScrapAttachedViews(recycler)
        val sumWidth = width
        var currentLineWidth = 0
        var currentLineTop = 0
        var lastLineMaxHeight = 0
        for (i in 0 until itemCount) {
            val view = recycler.getViewForPosition(i)
            addView(view)
            measureChildWithMargins(view, 0, 0)
            val width = getDecoratedMeasuredWidth(view)
            val height = getDecoratedMeasuredHeight(view)
            currentLineWidth += width
            if (currentLineWidth <= sumWidth) {
                layoutDecorated(
                    view,
                    currentLineWidth - width,
                    currentLineTop,
                    currentLineWidth + verticalSpace,
                    currentLineTop + height
                )
                lastLineMaxHeight = lastLineMaxHeight.coerceAtLeast(height)
            } else {
                currentLineWidth = width
                if (lastLineMaxHeight == 0) {
                    lastLineMaxHeight = height
                }
                currentLineTop += lastLineMaxHeight + verticalSpace
                if (i == 0) {
                    currentLineTop += lastLineMaxHeight + verticalSpace
                }
                layoutDecorated(view, 0, currentLineTop, width, currentLineTop + height)
                lastLineMaxHeight = height
            }
        }
    }

    override fun isAutoMeasureEnabled(): Boolean {
        return true
    }

    override fun onMeasure(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State,
        widthSpec: Int,
        heightSpec: Int
    ) {
        super.onMeasure(recycler, state, widthSpec, heightSpec)
        if (!isAutoMeasureEnabled) {
            requestSimpleAnimationsInNextLayout()
            setMeasuredDimension(width, height)
        }
    }
}