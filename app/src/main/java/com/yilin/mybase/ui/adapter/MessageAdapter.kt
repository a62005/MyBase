package com.yilin.mybase.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yilin.mybase.bean.message.MessageBean
import com.yilin.mybase.databinding.ItemMessageBinding
import com.yilin.mybase.ui.compare.MessageCompare
import com.yilin.mybase.ui.viewholder.BaseVBViewHolder

class MessageAdapter : BaseBindingAdapter<MessageBean, BaseVBViewHolder, ItemMessageBinding>(
    MessageCompare()
)  {
    override fun convertPlus(
        holder: BaseVBViewHolder,
        binding: ItemMessageBinding,
        item: MessageBean
    ) {
        binding.tvContent.text = item.title
        binding.tvDate.text = item.date
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemMessageBinding {
        return ItemMessageBinding.inflate(inflater, parent, false)
    }

    override fun createViewHolder(binding: ItemMessageBinding, viewType: Int): BaseVBViewHolder {
        return getBaseViewHolder(binding)
    }
}