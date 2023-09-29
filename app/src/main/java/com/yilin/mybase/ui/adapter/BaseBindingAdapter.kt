package com.yilin.mybase.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.yilin.mybase.ui.viewholder.BaseVBViewHolder

abstract class BaseBindingAdapter<T, VH : BaseVBViewHolder, VB : ViewBinding>(
    compare: DiffUtil.ItemCallback<T>,
    onItemClickListener: OnItemClickListener? = null
) :
    BaseListAdapter<T, VH>(compare, onItemClickListener) {

    abstract fun convertPlus(holder: VH, binding: VB, item: T)

    abstract fun createViewBinding(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): VB

    abstract fun createViewHolder(binding: VB, viewType: Int): VH
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = createViewBinding(LayoutInflater.from(parent.context), parent, viewType)
        val holder = createViewHolder(binding, viewType)
        bindViewClickListener(holder)
        return holder
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        convertPlus(holder, holder.binding as VB, getItem(position))
    }

    protected fun getBaseViewHolder(binding: VB): BaseVBViewHolder {
        return BaseVBViewHolder(binding)
    }

}