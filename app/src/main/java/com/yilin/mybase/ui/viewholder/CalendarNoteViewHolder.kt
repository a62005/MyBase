package com.yilin.mybase.ui.viewholder

import com.yilin.mybase.bean.CalendarNoteBean
import com.yilin.mybase.databinding.ItemCalendarNoteBinding
import java.util.*

class CalendarNoteViewHolder(private val mBinding: ItemCalendarNoteBinding) :
    BaseVBViewHolder(mBinding) {

    fun init(item: CalendarNoteBean) {
        mBinding.tvTitle.text = item.title
        mBinding.tvContent.text = item.content
        mBinding.tvDate.text = getDate(item.dateTime)
    }

    private fun getDate(time: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = time
        return "${calendar.get(Calendar.YEAR)}/${calendar.get(Calendar.MONTH) + 1}/${
            calendar.get(
                Calendar.DAY_OF_MONTH
            )
        }"
    }
}