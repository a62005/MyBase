package com.yilin.mybase.ui.compare

import androidx.recyclerview.widget.DiffUtil
import com.yilin.mybase.bean.message.MessageBean

class MessageCompare : DiffUtil.ItemCallback<MessageBean>() {
    override fun areItemsTheSame(oldItem: MessageBean, newItem: MessageBean): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MessageBean, newItem: MessageBean): Boolean {
        return oldItem.isRead == newItem.isRead
    }
}