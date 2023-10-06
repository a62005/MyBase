package com.yilin.mybase.ui.viewholder

import androidx.core.content.ContextCompat
import com.yilin.mybase.R
import com.yilin.mybase.bean.message.MessageBean
import com.yilin.mybase.databinding.ItemMessageBinding

class MessageViewHolder(private val mBinding: ItemMessageBinding) : BaseVBViewHolder(mBinding) {

    fun init(item: MessageBean) {
        mBinding.tvContent.text = item.title
        mBinding.tvDate.text = item.date
        if (item.isRead) {
            setHasRead()
        } else {
            setUnread()
        }
    }

    private fun setHasRead() {
        mBinding.tvContent.setTextColor(ContextCompat.getColor(itemView.context, R.color.color_light_gray))
    }

    private fun setUnread() {
        mBinding.tvContent.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
    }
}