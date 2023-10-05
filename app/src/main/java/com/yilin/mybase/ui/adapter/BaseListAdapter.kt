package com.yilin.mybase.ui.adapter

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseListAdapter<T, VH : RecyclerView.ViewHolder>(
    compare: DiffUtil.ItemCallback<T>,
    var onItemClickListener: OnItemClickListener?
) :
    ListAdapter<T, VH>(compare) {

    protected fun bindViewClickListener(holder: VH) {
        val view = holder.itemView
        view.setOnClickListener {
            onItemClickListener?.onItemClick(this, view, holder.adapterPosition)
        }
    }

    fun addData(data: T) {
        currentList.toMutableList().apply {
            add(data)
            submitList(this)
        }

    }

    fun addData(data: T, position: Int) {
        if (position <= -1) {
            return
        }
        if (position >= currentList.size) {
            addData(data)
            return
        }
        currentList.toMutableList().apply {
            add(position, data)
            submitList(this)
        }
    }

    fun remove(position: Int) {
        if (position <= -1 || position >= currentList.size) {
            return
        }
        currentList.toMutableList().apply {
            removeAt(position)
            submitList(this)
        }
    }

    fun isFirst(position: Int) = position == 0
    fun isLast(position: Int) = position == currentList.size - 1

    interface OnItemClickListener {
        fun onItemClick(adapter: BaseListAdapter<*, *>, v: View, index: Int)
    }
}