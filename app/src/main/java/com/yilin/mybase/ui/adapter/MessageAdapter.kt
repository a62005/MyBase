package com.yilin.mybase.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yilin.common.ui.adapter.BaseBindingAdapter
import com.yilin.mybase.bean.MessageBean
import com.yilin.mybase.databinding.ItemMessageBinding
import com.yilin.mybase.ui.compare.MessageCompare
import com.yilin.mybase.ui.viewholder.MessageViewHolder

class MessageAdapter(onItemClickListener: OnItemClickListener) :
    BaseBindingAdapter<MessageBean, MessageViewHolder, ItemMessageBinding>(
        MessageCompare(),
        onItemClickListener
    ) {
    override fun convertPlus(
        holder: MessageViewHolder,
        binding: ItemMessageBinding,
        item: MessageBean
    ) {
        holder.init(item)
        binding.tvDelete.setOnClickListener {
            onItemClickListener?.onItemClick(this, it, holder.adapterPosition)
        }
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemMessageBinding {
        return ItemMessageBinding.inflate(inflater, parent, false)
    }

    override fun createViewHolder(binding: ItemMessageBinding, viewType: Int): MessageViewHolder {
        return MessageViewHolder(binding)
    }
}