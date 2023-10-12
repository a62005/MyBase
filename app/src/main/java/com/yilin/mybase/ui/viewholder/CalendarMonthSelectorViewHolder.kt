package com.yilin.mybase.ui.viewholder

import androidx.core.content.ContextCompat
import com.yilin.mybase.R
import com.yilin.mybase.databinding.ItemCalendarSelectorMonthBinding

class CalendarMonthSelectorViewHolder(private val mBinding: ItemCalendarSelectorMonthBinding) :
    BaseVBViewHolder(mBinding) {

    fun setTitle(month: Int) {
        val m = "$month${getString(R.string.calendar_month)}"
        mBinding.tvTitle.text = m
    }

    fun setSelected() {
        mBinding.tvTitle.setBackgroundColor(
            ContextCompat.getColor(
                itemView.context,
                R.color.color_light_blue
            )
        )
        mBinding.tvTitle.setTextColor(
            ContextCompat.getColor(
                itemView.context,
                R.color.white
            )
        )
    }

    fun setUnselected() {
        mBinding.tvTitle.setBackgroundColor(
            ContextCompat.getColor(
                itemView.context,
                R.color.white
            )
        )
        mBinding.tvTitle.setTextColor(
            ContextCompat.getColor(
                itemView.context,
                R.color.black
            )
        )
    }
}