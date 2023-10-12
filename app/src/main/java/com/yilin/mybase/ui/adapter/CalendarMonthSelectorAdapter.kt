package com.yilin.mybase.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yilin.common.ui.adapter.BaseBindingAdapter
import com.yilin.mybase.databinding.ItemCalendarSelectorMonthBinding
import com.yilin.mybase.ui.compare.IntegerCompare
import com.yilin.mybase.ui.viewholder.CalendarMonthSelectorViewHolder

class CalendarMonthSelectorAdapter(onItemClickListener: OnItemClickListener) :
    BaseBindingAdapter<Int, CalendarMonthSelectorViewHolder, ItemCalendarSelectorMonthBinding>(
        IntegerCompare(),
        onItemClickListener
    ) {
    private var defaultMonth = 0
    private var defaultYear = 0
    private var selectedYear = 0

    override fun convertPlus(
        holder: CalendarMonthSelectorViewHolder,
        binding: ItemCalendarSelectorMonthBinding,
        item: Int
    ) {
        if (defaultYear == selectedYear && item == defaultMonth) {
            holder.setSelected()
        } else {
            holder.setUnselected()
        }
        holder.setTitle(item)
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
    ): CalendarMonthSelectorViewHolder {
        return CalendarMonthSelectorViewHolder(binding)
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