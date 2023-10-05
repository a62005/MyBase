package com.yilin.mybase.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.yilin.mybase.R
import com.yilin.mybase.databinding.ItemCalendarSelectorMonthBinding
import com.yilin.mybase.ui.compare.IntegerCompare
import com.yilin.mybase.ui.viewholder.BaseVBViewHolder

class CalendarMonthSelectorAdapter(onItemClickListener: OnItemClickListener) : BaseBindingAdapter<Int, BaseVBViewHolder, ItemCalendarSelectorMonthBinding>(
    IntegerCompare(),
    onItemClickListener
) {
    private var defaultMonth = 0
    private var defaultYear = 0
    private var selectedYear = 0

    override fun convertPlus(
        holder: BaseVBViewHolder,
        binding: ItemCalendarSelectorMonthBinding,
        item: Int
    ) {
        if (defaultYear == selectedYear && item == defaultMonth) {
            binding.tvTitle.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.color_light_blue))
            binding.tvTitle.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
        } else {
            binding.tvTitle.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
            binding.tvTitle.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
        }
        val month = "$item${holder.getString(R.string.calendar_month)}"
        binding.tvTitle.text = month
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemCalendarSelectorMonthBinding {
        return ItemCalendarSelectorMonthBinding.inflate(inflater, parent, false)
    }

    override fun createViewHolder(
        binding: ItemCalendarSelectorMonthBinding,
        viewType: Int
    ): BaseVBViewHolder {
        return getBaseViewHolder(binding)
    }

    fun setDefaultMonth(year: Int, month: Int) {
        defaultYear = year
        defaultMonth = month
        selectedYear = year
    }

    fun checkSelectMonth(year: Int) {
        if (defaultYear == 0 && defaultMonth == 0) {
            return
        }
        if (year == defaultYear || selectedYear == defaultYear) {
            notifyItemChanged(defaultMonth - 1)
        }
        selectedYear = year
    }
}