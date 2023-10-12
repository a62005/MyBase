package com.yilin.mybase.factory

import com.yilin.mybase.MyApp
import com.yilin.mybase.R
import com.yilin.mybase.bean.CalendarNoteBean
import com.yilin.mybase.bean.MessageBean
import com.yilin.mybase.utils.DateUtil

object MessageFactory {

    enum class PokemonMessage(val content: String) {
        ADD_FAVORITE(MyApp.instance.getString(R.string.pokemon_add_to_favorite)),
        REMOVE_FAVORITE(MyApp.instance.getString(R.string.pokemon_remove_from_favorite));

        fun getMessage(id: String, name: String): MessageBean {
            return MessageBean(
                message = content.format(name)
            )
        }
    }

    enum class NoteMessage(val content: String) {
        ADD(MyApp.instance.getString(R.string.calendar_add_note)),
        EDIT(MyApp.instance.getString(R.string.calendar_edit_note)),
        REMOVE(MyApp.instance.getString(R.string.calendar_remove_note));

        fun getMessage(note: CalendarNoteBean): MessageBean {
            return MessageBean(
                message = content.format(DateUtil.parseLongToDate(note.dateTime), note.title)
            )
        }
    }
}