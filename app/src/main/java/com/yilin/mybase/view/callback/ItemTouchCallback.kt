package com.yilin.mybase.view.callback

import android.graphics.Canvas
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

/**
 * recyclerView item橫移的callback
 */
class ItemTouchCallback : SimpleItemTouchHelperCallback() {

    private var mCurrentScrollX = 0
    private var mCurrentScrollXWhenInactive = 0
    private var mInitXWhenInactive = 0f
    private var mDefaultScrollX: Int = 0
    private var mFirstInactive = false
    private var lastViewHolder: RecyclerView.ViewHolder? = null
    private var slideLayoutId: Int? = null

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val slideFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        return makeMovementFlags(dragFlags, slideFlags)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        if (viewHolder.itemView.scrollX == 0) {
            super.clearView(recyclerView, viewHolder)
        } else {
            setDefaultWidth(viewHolder)
            if (viewHolder.itemView.scrollX > mDefaultScrollX) {
                viewHolder.itemView.scrollTo(mDefaultScrollX, 0)
            } else if (viewHolder.itemView.scrollX < 0) {
                viewHolder.itemView.scrollTo(0, 0)
            }
        }

    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            if (viewHolder != null) {
                lastViewHolder?.itemView?.scrollTo(0, 0)
                lastViewHolder = viewHolder
            }
        }
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return Int.MAX_VALUE.toFloat()
    }

    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        return Int.MAX_VALUE.toFloat()
    }


    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            setDefaultWidth(viewHolder)
            if (mDefaultScrollX == 0) {
                return
            }
            if (dX > 0) {
                viewHolder.itemView.scrollTo(0, 0)
                return
            }
            if (dX == 0f) {
                mCurrentScrollX = viewHolder.itemView.scrollX
                mFirstInactive = true
            }
            if (isCurrentlyActive) {
                viewHolder.itemView.scrollTo(mCurrentScrollX + -dX.toInt(), 0)
            } else {
                if (mFirstInactive) {
                    mFirstInactive = false
                    mCurrentScrollXWhenInactive = viewHolder.itemView.scrollX
                    mInitXWhenInactive = dX
                }
                if (viewHolder.itemView.scrollX >= mDefaultScrollX) {
                    viewHolder.itemView.scrollTo(
                        (mCurrentScrollX + -dX.toInt()).coerceAtLeast(mDefaultScrollX), 0
                    )
                } else {
                    viewHolder.itemView.scrollTo(
                        (mCurrentScrollXWhenInactive * dX / mInitXWhenInactive).toInt(),
                        0
                    )
                }
            }
        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }

    }

    private fun setDefaultWidth(viewHolder: RecyclerView.ViewHolder) {
        slideLayoutId?.let {
            val view = viewHolder.itemView.findViewById<View>(it)
            mDefaultScrollX = view?.width ?: 0
        }

    }

    fun setSlideLayout(layout: Int) {
        slideLayoutId = layout
    }

    fun clearView() {
        lastViewHolder?.itemView?.scrollTo(0, 0)
    }
}