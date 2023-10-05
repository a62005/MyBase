package com.yilin.mybase.bean.message

import com.yilin.mybase.MyApp
import com.yilin.mybase.R
import com.yilin.mybase.utils.DateUtil

class CalendarNoteMessageBean(messageType: NoteMessageEnum, date: Long, title: String): MessageBean(
    title = messageType.content.format(DateUtil.parseLongToDate(date), title)
) {
    enum class NoteMessageEnum(val content: String) {
        ADD(MyApp.instance.getString(R.string.calendar_add_note)),
        EDIT(MyApp.instance.getString(R.string.calendar_edit_note)),
        REMOVE(MyApp.instance.getString(R.string.calendar_remove_note))
    }
}
