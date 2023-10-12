package com.yilin.common.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

open class BaseVBViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    fun getString(id: Int) = itemView.resources.getString(id)

    companion object {
        const val ITEM_HEADER = 0
        const val ITEM_BODY = 1
        const val ITEM_FOOTER = 2
    }
}