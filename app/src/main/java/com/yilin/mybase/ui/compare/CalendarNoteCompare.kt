package com.yilin.mybase.ui.compare

import androidx.recyclerview.widget.DiffUtil
import com.yilin.mybase.bean.CalendarNoteBean

class CalendarNoteCompare : DiffUtil.ItemCallback<CalendarNoteBean>() {
    override fun areItemsTheSame(oldItem: CalendarNoteBean, newItem: CalendarNoteBean): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CalendarNoteBean, newItem: CalendarNoteBean): Boolean {
        return oldItem.title == newItem.title && oldItem.content == newItem.content && oldItem.dateTime == newItem.dateTime
    }
}